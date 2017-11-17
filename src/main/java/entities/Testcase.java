package entities;
// Generated Oct 25, 2017 9:10:32 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Testcase generated by hbm2java
 */
@Entity
@Table(name = "testcase", catalog = "rilasci_DB", uniqueConstraints = @UniqueConstraint(columnNames = "id_polarion"))
public class Testcase implements java.io.Serializable {

	private Integer id;
	private Priority priority;
	private Resolution resolution;
	private Status status;
	private User user;
	private String idPolarion;
	private String titolo;
	private Date dataCreazione;
	private Date dataUpdate;
	private Date dataInizio;
	private Date dataFine;
	private Integer codTipo;
	private Set<ChecklistTestcase> checklistTestcases = new HashSet<ChecklistTestcase>(0);
	private Set<TestcaseWorkitem> testcaseWorkitems = new HashSet<TestcaseWorkitem>(0);

	public Testcase() {
	}

	public Testcase(User user) {
		this.user = user;
	}

	public Testcase(Priority priority, Resolution resolution, Status status, User user, String idPolarion,
			String titolo, Date dataCreazione, Date dataUpdate, Date dataInizio, Date dataFine, Integer codTipo,
			Set<ChecklistTestcase> checklistTestcases, Set<TestcaseWorkitem> testcaseWorkitems) {
		this.priority = priority;
		this.resolution = resolution;
		this.status = status;
		this.user = user;
		this.idPolarion = idPolarion;
		this.titolo = titolo;
		this.dataCreazione = dataCreazione;
		this.dataUpdate = dataUpdate;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.codTipo = codTipo;
		this.checklistTestcases = checklistTestcases;
		this.testcaseWorkitems = testcaseWorkitems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_priority")
	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_resolution")
	public Resolution getResolution() {
		return this.resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_status")
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_author", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "id_polarion", unique = true, length = 45)
	public String getIdPolarion() {
		return this.idPolarion;
	}

	public void setIdPolarion(String idPolarion) {
		this.idPolarion = idPolarion;
	}

	@Column(name = "titolo", length = 45)
	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_creazione", length = 19)
	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_update", length = 19)
	public Date getDataUpdate() {
		return this.dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inizio", length = 10)
	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_fine", length = 10)
	public Date getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	@Column(name = "cod_tipo")
	public Integer getCodTipo() {
		return this.codTipo;
	}

	public void setCodTipo(Integer codTipo) {
		this.codTipo = codTipo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testcase")
	public Set<ChecklistTestcase> getChecklistTestcases() {
		return this.checklistTestcases;
	}

	public void setChecklistTestcases(Set<ChecklistTestcase> checklistTestcases) {
		this.checklistTestcases = checklistTestcases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testcase")
	public Set<TestcaseWorkitem> getTestcaseWorkitems() {
		return this.testcaseWorkitems;
	}

	public void setTestcaseWorkitems(Set<TestcaseWorkitem> testcaseWorkitems) {
		this.testcaseWorkitems = testcaseWorkitems;
	}

}
