package basic;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.ReversedLinesFileReader;

import entities.Csv;
import entities.LinkedItem;
import entities.LinkedItemId;
import entities.Project;
import entities.Release;
import entities.ReleaseHistory;
import entities.ReleaseIt;
import entities.ReleaseitHistory;
import entities.Status;
import entities.User;

public class Scheduler extends Thread {
	public final static int LOOP_ON_DELAY = 0, LOOP_CERTAIN_TIME = 1, ONE_SHOT = 2;
	public final static int TRUNCATE_DATA = -1, APPEND_DATA = 1, UPDATE_DATA = 2, ARRANGE_DATA = 3;

	private GregorianCalendar start;
	private long time;
	private int mode, operationStatus;
	private boolean status, DEBUG = true;
	private Date startDate;
	private String user;
	private Timer t;
	private final long delay24h = 3600 * 24 * 1000;
	private ResourceBundle rb = ResourceBundle.getBundle("config");
	private String uniqueID;

	public Scheduler(int mode, int operationStatus, long time, String user) {
		this.mode = mode;
		this.setOperationStatus(operationStatus);
		this.time = time;
		this.user = user;
		this.status = false;

		uniqueID = System.currentTimeMillis() + user;
	}

	/**
	 * Scheduler's run mode.
	 */
	@Override
	public void run() {
		if (status)
			switch (mode) {
			case LOOP_ON_DELAY:
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						if (status) {
							performOperations();
						} else
							stopScheduler();
					}
				}, start.getTime(), time);
				break;
			case LOOP_CERTAIN_TIME:
				start.setTimeInMillis(time);
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						if (status) {
							performOperations();
						} else
							stopScheduler();
					}
				}, start.getTime(), delay24h);
				break;
			case ONE_SHOT:
				performOperations();
				stopScheduler();
				break;
			}
	}

	/**
	 * This is the scheduled method. It switches on selected operation.
	 */
	private synchronized void performOperations() {
		try {
			switch (operationStatus) {
			case TRUNCATE_DATA:
				HibernateUtil.rawQuery("TRUNCATE csv;");
				if (DEBUG) {
					Util.writeLog(toString());
					Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, toString());
				}
				return;
			case APPEND_DATA:
				appendData();
				if (DEBUG) {
					Util.writeLog(toString());
					Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, toString());
				}
				break;
			case UPDATE_DATA:
				// TODO updateData();
				if (DEBUG) {
					Util.writeLog(toString());
					Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, toString());
				}
				break;
			case ARRANGE_DATA:
				arrangeData();
				if (DEBUG) {
					Util.writeLog(toString());
					Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, toString());
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * It reads csv file with most recently latest modified date
	 */
	private synchronized File readCsvFile() {
		try {
			File lastUpdatedFile = null, directory = new File(rb.getString("file.path"));
			File[] filesList = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.getName().endsWith(".csv");
				}
			});

			for (File f : filesList) {
				if (lastUpdatedFile == null) {
					lastUpdatedFile = f;
					continue;
				}
				if (new Date(lastUpdatedFile.lastModified()).before(new Date(f.lastModified())))
					lastUpdatedFile = f;
			}
			if (lastUpdatedFile == null)
				throw new NullPointerException("No csv file in folder " + rb.getString("file.path"));

			return lastUpdatedFile;
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("readCsvFile()", e);
			Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	/**
	 * It appends data in to csv table.
	 * 
	 * complexity: T(n)= Ω( c + (n*m - 3n)) ~ T(n) = O(n^2)
	 */
	private void appendData() {
		try {
			File lastUpdatedFile = readCsvFile();
			SimpleDateFormat sdfConverter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			basic.State currentState = new basic.State();
			Csv csvToFill = new Csv();

			Iterable<CSVRecord> records = CSVFormat.newFormat(';').parse(new FileReader(lastUpdatedFile));

			// Reading first extract date
			Date globalFirstExtract = null;
			// Reading last extract date
			ReversedLinesFileReader rlfr = new ReversedLinesFileReader(lastUpdatedFile, Charset.forName("UTF-8"));
			Date globalLastExtract = null;
			while (globalLastExtract == null) {
				String line = rlfr.readLine();
				if (line == null || line.length() == 0 || !line.split(";")[2].trim().equals(currentState.A))
					continue;
				globalLastExtract = sdfConverter.parse(line.split(";")[4].trim());
			}
			rlfr.close();

			/*
			 * Caratteri da rimuovere dovuti a codifica errata durante la
			 * generazione del file csv
			 */
			String regex = "[ยงÂ§]";

			// n
			for (CSVRecord row : records) {

				// Avoid blank rows
				if (row.size() == 0)
					continue;

				// Reading first extract date
				if (globalFirstExtract == null)
					globalFirstExtract = sdfConverter.parse(row.get(3).trim());

				if (csvToFill.getFileName() == null)
					csvToFill.setFileName(lastUpdatedFile.getName());
				if (csvToFill.getFileDate() == null)
					csvToFill.setFileDate(new Date(Long.parseLong(lastUpdatedFile.getName().substring(
							lastUpdatedFile.getName().indexOf("_") - 13, lastUpdatedFile.getName().indexOf("_")))));
				if (csvToFill.getRepository() == null)
					csvToFill.setRepository(
							lastUpdatedFile.getName().substring(0, lastUpdatedFile.getName().indexOf("_") - 14));
				if (csvToFill.getInizioEstrazione() == null)
					csvToFill.setInizioEstrazione(sdfConverter.parse(row.get(3).trim()));
				if (csvToFill.getFineEstrazione() == null)
					csvToFill.setFineEstrazione(sdfConverter.parse(row.get(4).trim()));

				// m - 3
				for (int i = 2; i < row.size() - 1; i++) {
					switch (i) {
					case 2:
						// idRelease
						if (currentState.getCurrentState() == null)
							csvToFill.setIdPolarion(row.get(1).trim().replaceAll(regex, ""));

						// colonna
						String nextNaturalState = currentState.getNextNaturalState();
						currentState.setCurrentState(row.get(i).trim().replaceAll(regex, ""));

						if (nextNaturalState != null && !nextNaturalState.equals(currentState.getCurrentState()))
							Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, "States missmatch expected: ["
									+ nextNaturalState + "] parsed: [" + currentState.getCurrentState() + "]");
						break;
					case 3:
						if (currentState.getCurrentState().equals(currentState.A)) {
							/*
							 * colonnaA
							 * 
							 * dataInizioEstrazioneString;
							 * dataFineEstrazioneString;
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaA() == null)
									csvToFill.setColonnaA(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaA(
											csvToFill.getColonnaA() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.B)) {
							/*
							 * ColonnaB
							 * 
							 * idReleaseDiProgetto; titleReleaseDiProgetto;
							 * priorityReleaseDiProgetto;
							 * severityReleaseDiProgetto; progettoPolarion;
							 * versione; link; release;
							 * dataCreazioneReleaseDiProgetto;
							 * dataUltimoAggiornamentoReleaseDP
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getProgettoPolarion() == null && i == 7)
									csvToFill.setProgettoPolarion(row.get(i).trim().replaceAll(regex, ""));
								if (csvToFill.getColonnaB() == null)
									csvToFill.setColonnaB(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaB(
											csvToFill.getColonnaB() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.C)) {
							/*
							 * ColonnaC
							 * 
							 * progettoRelease
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaC() == null)
									csvToFill.setColonnaC(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaC(
											csvToFill.getColonnaC() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.D)) {
							/*
							 * ColonnaD
							 * 
							 * progettoSviluppo
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaD() == null)
									csvToFill.setColonnaD(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaD(
											csvToFill.getColonnaD() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.E)) {
							/*
							 * ColonnaE
							 * 
							 * progettoMev
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaE() == null)
									csvToFill.setColonnaE(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaE(
											csvToFill.getColonnaE() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.F)) {
							/*
							 * ColonnaF
							 * 
							 * progettoDocumento
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaF() == null)
									csvToFill.setColonnaF(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaF(
											csvToFill.getColonnaF() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.G)) {
							/*
							 * ColonnaG
							 * 
							 * progettoRds
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaG() == null)
									csvToFill.setColonnaG(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaG(
											csvToFill.getColonnaG() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.H)) {
							/*
							 * ColonnaH
							 * 
							 * progettoDefect
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaH() == null)
									csvToFill.setColonnaH(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaH(
											csvToFill.getColonnaH() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.I)) {
							/*
							 * ColonnaI
							 * 
							 * progettoAnomalia
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaI() == null)
									csvToFill.setColonnaI(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaI(
											csvToFill.getColonnaI() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.J)) {
							/*
							 * ColonnaJ
							 * 
							 * progettoReleaseIt
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaJ() == null)
									csvToFill.setColonnaJ(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaJ(
											csvToFill.getColonnaJ() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.K)) {
							/*
							 * ColonnaK
							 * 
							 * taskInfo
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaK() == null)
									csvToFill.setColonnaK(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaK(
											csvToFill.getColonnaK() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.L)) {
							/*
							 * ColonnaL
							 * 
							 * workRecordInfo
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaL() == null)
									csvToFill.setColonnaL(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaL(
											csvToFill.getColonnaL() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.M)) {
							/*
							 * ColonnaM
							 * 
							 * infoTestcase
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaM() == null)
									csvToFill.setColonnaM(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaM(
											csvToFill.getColonnaM() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
						} else if (currentState.getCurrentState().equals(currentState.N)) {
							/*
							 * ColonnaN
							 * 
							 * infoChecklist
							 */
							while (i < row.size() - 1) {
								if (csvToFill.getColonnaN() == null)
									csvToFill.setColonnaN(row.get(i).trim().replaceAll(regex, ""));
								else
									csvToFill.setColonnaN(
											csvToFill.getColonnaN() + ";" + row.get(i).trim().replaceAll(regex, ""));
								i++;
							}
							boolean done = HibernateUtil.save(csvToFill);
							if (done) {
								csvToFill = new Csv();
								currentState = new basic.State();
							} else {
								if (Util.DEBUG)
									Util.writeLog("Impossible to persist created object csv: " + csvToFill.toString(),
											new IllegalStateException());
								throw new IllegalStateException(
										"Impossible to persist created object csv: " + csvToFill.toString());
							}
						}
						break;
					} // switch
				} // for
			} // for
			Logger.getLogger(Scheduler.class.getName()).log(Level.INFO, toString());
		} catch (Exception e) {
			if (Util.DEBUG)
				Util.writeLog("appendData()", e);
			Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * It fills other database' tables starting from csv's one
	 */
	private void arrangeData() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			List<Csv> list = HibernateUtil.readAllCsv();
			for (Csv csv : list) {
				// Se non esiste il progetto lo creo
				Project pForName = HibernateUtil.readProjectForName(csv.getProgettoPolarion());
				if (pForName == null) {
					pForName = new Project();
					pForName.setNome(csv.getProgettoPolarion());
					HibernateUtil.save(pForName);
				}
				Release releaseDiProgetto = null;
				if (csv.getColonnaB() != null && csv.getColonnaB().length() > 0) {
					/*
					 * ColonnaB
					 * 
					 * idReleaseDiProgetto; titleReleaseDiProgetto;
					 * priorityReleaseDiProgetto; severityReleaseDiProgetto;
					 * progettoPolarion; versione; link; release;
					 * dataCreazioneReleaseDiProgetto;
					 * dataUltimoAggiornamentoReleaseDP
					 */
					String[] bElements = csv.getColonnaB().split(";");
					releaseDiProgetto = HibernateUtil.readRelease(bElements[0].trim());
					boolean toUpdate = false, toSave = false;

					if (releaseDiProgetto == null) {
						releaseDiProgetto = new Release();
						releaseDiProgetto.setIdPolarion(bElements[0].trim());
						toSave = true;
					}
					if (releaseDiProgetto.getTitolo() == null
							|| !releaseDiProgetto.getTitolo().equals(bElements[1].trim())) {
						releaseDiProgetto.setTitolo(bElements[1].trim());
						if (!toSave)
							toUpdate = true;
					}
					if (bElements[2] != null && bElements[2].trim().length() > 0) {
						if (!toSave && releaseDiProgetto.getPriority() != null
								&& releaseDiProgetto.getPriority().getValore() != Float.parseFloat(bElements[2].trim()))
							toUpdate = true;
						if (toSave || toUpdate)
							releaseDiProgetto
									.setPriority(HibernateUtil.readPriority(Float.parseFloat(bElements[2].trim())));
					}
					if (bElements[3] != null && bElements[3].trim().length() > 0) {
						if (!toSave && releaseDiProgetto.getSeverity() != null
								&& !releaseDiProgetto.getSeverity().getNome().equals(bElements[3].trim()))
							toUpdate = true;
						if (toSave || toUpdate)
							releaseDiProgetto.setSeverity(HibernateUtil.readSeverity(bElements[3].trim()));
					}
					releaseDiProgetto.setProject(pForName);
					if (bElements[5] != null && bElements[5].trim().length() > 0) {
						if (!toSave && releaseDiProgetto.getVersione() != null
								&& !releaseDiProgetto.getVersione().equals(bElements[5].trim()))
							toUpdate = true;
						releaseDiProgetto.setVersione(bElements[5].trim());
					}
					if (bElements[6] != null && bElements[6].trim().length() > 0) {
						if (!toSave && releaseDiProgetto.getLink() != null
								&& !releaseDiProgetto.getLink().equals(bElements[6].trim()))
							toUpdate = true;
						releaseDiProgetto.setLink(bElements[6].trim());
					}
					if (bElements[7] != null && bElements[7].trim().length() > 0) {
						if (!toSave && releaseDiProgetto.getType() != null
								&& !releaseDiProgetto.getType().equals(bElements[6].trim()))
							toUpdate = true;
						releaseDiProgetto.setType(bElements[7].trim());
					}
					if (bElements[8] != null && bElements[8].trim().length() > 0) {
						Date dataCreazione = sdf.parse(bElements[8].trim());
						if (!toSave && releaseDiProgetto.getDataCreazione() != null
								&& !releaseDiProgetto.getDataCreazione().equals(dataCreazione))
							toUpdate = true;
						releaseDiProgetto.setDataCreazione(dataCreazione);
					}
					if (bElements[9] != null && bElements[9].trim().length() > 0) {
						Date dataUltimoAggiornamento = sdf.parse(bElements[9].trim());
						if (!toSave && releaseDiProgetto.getDataUpdate() != null
								&& !releaseDiProgetto.getDataUpdate().equals(dataUltimoAggiornamento))
							toUpdate = true;
						releaseDiProgetto.setDataUpdate(dataUltimoAggiornamento);
					}
					if (toSave)
						HibernateUtil.save(releaseDiProgetto);
					else if (toUpdate)
						HibernateUtil.update(Release.class, releaseDiProgetto);

				}
				if (csv.getColonnaC() != null && csv.getColonnaC().length() > 0) {
					/*
					 * ColonnaC
					 * 
					 * progettoRelease
					 */
					String regex = Pattern.quote("(") + "(.*?)" + Pattern.quote(")");
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(csv.getColonnaC());
					while (matcher.find()) {
						String[] history = matcher.group().split("^");
						Status status = HibernateUtil.readStatus(history[0].trim());
						Date dataUpdate = sdf.parse(history[1]);
						User user = HibernateUtil.readUser(history[2].trim());
						ReleaseHistory rHistory = HibernateUtil.readReleaseHistory(releaseDiProgetto, status,
								dataUpdate, user);
						if (rHistory == null) {
							rHistory = new ReleaseHistory();
							rHistory.setRelease(releaseDiProgetto);
							rHistory.setStatus(status);
							rHistory.setDataUpdate(dataUpdate);
							rHistory.setUser(user);
							HibernateUtil.save(rHistory);
						}
					}
				}
				if (csv.getColonnaD() != null && csv.getColonnaD().length() > 0) {
					// TODO D
					/*
					 * ColonnaD
					 * 
					 * progettoSviluppo
					 */
				}
				if (csv.getColonnaE() != null && csv.getColonnaE().length() > 0) {
					// TODO E
					/*
					 * ColonnaE
					 * 
					 * progettoMev
					 */
				}
				if (csv.getColonnaF() != null && csv.getColonnaF().length() > 0) {
					// TODO F
					/*
					 * ColonnaF
					 * 
					 * progettoDocumento
					 */
				}
				if (csv.getColonnaG() != null && csv.getColonnaG().length() > 0) {
					// TODO G
					/*
					 * ColonnaG
					 * 
					 * progettoRds
					 */
				}
				if (csv.getColonnaH() != null && csv.getColonnaH().length() > 0) {
					// TODO H
					/*
					 * ColonnaH
					 * 
					 * progettoDefect
					 */
				}
				if (csv.getColonnaI() != null && csv.getColonnaI().length() > 0) {
					// TODO I
					/*
					 * ColonnaI
					 * 
					 * progettoAnomalia
					 */
				}
				ReleaseIt releaseIT = null;
				if (csv.getColonnaJ() != null && csv.getColonnaJ().length() > 0) {
					/*
					 * ColonnaJ
					 * 
					 * progettoReleaseIt
					 */
					boolean hasLinkedItem = false;
					if (csv.getColonnaJ().startsWith("<") && csv.getColonnaJ().endsWith(">")) {
						csv.setColonnaJ(csv.getColonnaJ().substring(1, csv.getColonnaJ().length() - 1));
						hasLinkedItem = true;
					}
					String[] elements = csv.getColonnaJ().split("|");
					if (hasLinkedItem) {
						LinkedItemId lIId = new LinkedItemId();
						lIId.setIdPolarionPadre(elements[1]);
						lIId.setIdPolarionFiglio(releaseDiProgetto.getIdPolarion());
						LinkedItem li = HibernateUtil.readLinkedItem(lIId);
						if (li == null) {
							li = new LinkedItem();
							li.setId(lIId);
							HibernateUtil.save(li);
						}
					}
					releaseIT = HibernateUtil.readReleaseIT(elements[1]);
					boolean toSave = false;
					if (releaseIT == null || !releaseIT.getTitolo().equals(elements[2].trim())) {
						if (releaseIT == null) {
							releaseIT = new ReleaseIt();
							toSave = true;
						}
						releaseIT.setTitolo(elements[2].trim());
						if (elements[3] != null && elements[3].trim().length() > 0)
							releaseIT.setDataInizio(sdf.parse(elements[3].trim()));
						if (elements[4] != null && elements[4].trim().length() > 0)
							releaseIT.setDataFine(sdf.parse(elements[4].trim()));
						if (toSave)
							HibernateUtil.save(releaseIT);
						else
							HibernateUtil.update(ReleaseIt.class, releaseIT);
					}
					if (elements.length > 5 && elements[5] != null && elements[5].trim().length() > 0) {
						String regex = Pattern.quote("(") + "(.*?)" + Pattern.quote(")");
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(elements[5].trim());
						boolean isFirstRow = true;
						// formato sdf per Thu Feb 18 00:00:00 CET 2016
						SimpleDateFormat literalSdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
						while (matcher.find()) {
							String[] history = matcher.group().split("^");
							Status status = HibernateUtil.readStatus(history[0].trim());

							Date dataUpdate = literalSdf.parse(history[1]);

							if (isFirstRow && releaseIT.getDataCreazione() == null) {
								releaseIT.setDataCreazione(dataUpdate);
								HibernateUtil.update(ReleaseIt.class, releaseIT);
								isFirstRow = false;
							}

							ReleaseitHistory rItHistory = null;
							if (history.length > 2) {
								User user = HibernateUtil.readUser(history[2].trim());
								rItHistory = HibernateUtil.readReleaseItHistory(releaseDiProgetto, status, dataUpdate,
										user);
							}
							if (rItHistory == null) {
								rItHistory = new ReleaseitHistory();
								rItHistory.setReleaseIt(releaseIT);
								rItHistory.setStatus(status);
								rItHistory.setDataUpdate(dataUpdate);
								HibernateUtil.save(rItHistory);
							}
						}
					}
				}
				if (csv.getColonnaK() != null && csv.getColonnaK().length() > 0) {
					// TODO K
					/*
					 * ColonnaK
					 * 
					 * taskInfo
					 */
				}
				if (csv.getColonnaL() != null && csv.getColonnaL().length() > 0) {
					// TODO L
					/*
					 * ColonnaL
					 * 
					 * workRecordInfo
					 */
				}
				if (csv.getColonnaM() != null && csv.getColonnaM().length() > 0) {
					// TODO M
					/*
					 * ColonnaM
					 * 
					 * infoTestcase
					 */
				}
				if (csv.getColonnaN() != null && csv.getColonnaN().length() > 0) {
					// TODO N
					/*
					 * ColonnaN
					 * 
					 * infoChecklist
					 */
				}
			}
		} catch (Exception e) {
			Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * It starts scheduler.
	 */
	@Override
	public synchronized void start() {
		t = new Timer();
		start = new GregorianCalendar();
		startDate = start.getTime();
		status = true;
		run();
	}

	/**
	 * It completely stops the scheduler and clears all scheduled jobs.
	 */
	public synchronized void stopScheduler() {
		status = false;
		start = null;
		if (t != null) {
			t.cancel();
			t.purge();
			t = null;
		}
		startDate = null;
		super.interrupt();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy HH:mm:ss.zzz");
		if (status)
			sb.append("Scheduler is running\n");
		else
			sb.append("Scheduler is not running\n");
		if (startDate != null)
			sb.append("Start date: " + sdf.format(startDate).toUpperCase() + "\n");
		if (user != null)
			sb.append("Owner user: " + user + "\n");

		switch (mode) {
		case LOOP_ON_DELAY:
			sb.append("Current Execution Mode: LOOP ON DELAY\n");
			sb.append("Delay time: " + time + "ms\n");
			break;
		case LOOP_CERTAIN_TIME:
			sb.append("Current Execution Mode: LOOP CERTAIN TIME\n");
			sb.append("Delay time: [24h]\n");
			sb.append("Actoions performing time: " + sdf.format(time) + " \n");
			break;
		case ONE_SHOT:
			sb.append("Current Execution Mode: ONE SHOT\n");
			break;
		}
		switch (operationStatus) {
		case TRUNCATE_DATA:
			sb.append("Current Operation Type: TRUNCATE_DATA\n");
			break;
		case APPEND_DATA:
			sb.append("Current Operation Type: APPEND_DATA\n");
			break;
		case UPDATE_DATA:
			sb.append("Current Operation Type: UPDATE_DATA\n");
			break;
		case ARRANGE_DATA:
			sb.append("Current Operation Type: ARRANGE_DATA\n");
			break;
		}
		if (start != null) {
			long currentTime = System.currentTimeMillis() - startDate.getTime();
			sb.append("Current scheduler elapsed time: " + currentTime / 1000 + " sec");
		}
		return sb.toString();
	}

	/**
	 * 
	 * @return current Date, scheduler's time.
	 */
	public Date getCurrentTime() {
		return start.getTime();
	}

	/**
	 * Time is used with the mode variable: If mode is LOOP_ON_DELAY, time is
	 * the delay. If mode is LOOP_CERTAIN_TIME, delay is 24h and time will be
	 * the schedule date. If mode is ONE_SHOT, time is the value when the
	 * scheduler will perform the operations.
	 * 
	 * @return long time.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Time is used with the mode variable: If mode is LOOP_ON_DELAY, time is
	 * the delay. If mode is LOOP_CERTAIN_TIME, delay is 24h and time will be
	 * the schedule date. If mode is ONE_SHOT, time is the value when the
	 * scheduler will perform the operations.
	 * 
	 * @param long
	 *            time.
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * 
	 * @return current scheduler's mode.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * LOOP_ON_DELAY: it cycles and performs operations with a certain cadence.
	 * LOOP_CERTAIN_TIME: delay will be always 24h and schedule will executes
	 * operations in a certain time. ONE_SHOT: scheduler will performs
	 * operations only one time.
	 * 
	 * @param mode,
	 *            it sets scheduler's behavior.
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * 
	 * @return running status, true if running false otherwise.
	 */
	public boolean getCurrentStatus() {
		return status;
	}

	/**
	 * 
	 * @return user who create current scheduler's instance.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @return first date when scheduler started
	 */
	public Date getStartDate() {
		return startDate;
	}

	public int getOperationStatus() {
		return operationStatus;
	}

	public void setOperationStatus(int operationStatus) {
		this.operationStatus = operationStatus;
	}

	/**
	 * 
	 * @return uniqueID that identify the current scheduler
	 */
	public String getUniqueID() {
		return uniqueID;
	}
}
