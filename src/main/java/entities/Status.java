package entities;
// Generated Nov 28, 2017 11:11:12 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Status generated by hbm2java
 */
@Entity
@Table(name = "status", catalog = "rilasci_db")
public class Status implements java.io.Serializable {

	private Integer id;
	private String nome;
	private String polarionName;
	private Set<TaskItHistory> taskItHistories = new HashSet<TaskItHistory>(0);
	private Set<SupportHistory> supportHistories = new HashSet<SupportHistory>(0);
	private Set<AnomaliaHistory> anomaliaHistories = new HashSet<AnomaliaHistory>(0);
	private Set<TaskHistory> taskHistories = new HashSet<TaskHistory>(0);
	private Set<ReleaseHistory> releaseHistories = new HashSet<ReleaseHistory>(0);
	private Set<ReleaseitHistory> releaseitHistories = new HashSet<ReleaseitHistory>(0);
	private Set<ProgettoSviluppo> progettoSviluppos = new HashSet<ProgettoSviluppo>(0);
	private Set<DefectHistory> defectHistories = new HashSet<DefectHistory>(0);
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<Task> tasks = new HashSet<Task>(0);
	private Set<AnomaliaAssistenza> anomaliaAssistenzas = new HashSet<AnomaliaAssistenza>(0);
	private Set<Testcase> testcases = new HashSet<Testcase>(0);
	private Set<Mev> mevs = new HashSet<Mev>(0);

	public Status() {
	}

	public Status(String nome, String polarionName, Set<TaskItHistory> taskItHistories,
			Set<SupportHistory> supportHistories, Set<AnomaliaHistory> anomaliaHistories,
			Set<TaskHistory> taskHistories, Set<ReleaseHistory> releaseHistories,
			Set<ReleaseitHistory> releaseitHistories, Set<ProgettoSviluppo> progettoSviluppos,
			Set<DefectHistory> defectHistories, Set<Documento> documentos, Set<Task> tasks,
			Set<AnomaliaAssistenza> anomaliaAssistenzas, Set<Testcase> testcases, Set<Mev> mevs) {
		this.nome = nome;
		this.polarionName = polarionName;
		this.taskItHistories = taskItHistories;
		this.supportHistories = supportHistories;
		this.anomaliaHistories = anomaliaHistories;
		this.taskHistories = taskHistories;
		this.releaseHistories = releaseHistories;
		this.releaseitHistories = releaseitHistories;
		this.progettoSviluppos = progettoSviluppos;
		this.defectHistories = defectHistories;
		this.documentos = documentos;
		this.tasks = tasks;
		this.anomaliaAssistenzas = anomaliaAssistenzas;
		this.testcases = testcases;
		this.mevs = mevs;
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

	@Column(name = "nome", length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "polarion_name", length = 45)
	public String getPolarionName() {
		return this.polarionName;
	}

	public void setPolarionName(String polarionName) {
		this.polarionName = polarionName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<TaskItHistory> getTaskItHistories() {
		return this.taskItHistories;
	}

	public void setTaskItHistories(Set<TaskItHistory> taskItHistories) {
		this.taskItHistories = taskItHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<SupportHistory> getSupportHistories() {
		return this.supportHistories;
	}

	public void setSupportHistories(Set<SupportHistory> supportHistories) {
		this.supportHistories = supportHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<AnomaliaHistory> getAnomaliaHistories() {
		return this.anomaliaHistories;
	}

	public void setAnomaliaHistories(Set<AnomaliaHistory> anomaliaHistories) {
		this.anomaliaHistories = anomaliaHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<TaskHistory> getTaskHistories() {
		return this.taskHistories;
	}

	public void setTaskHistories(Set<TaskHistory> taskHistories) {
		this.taskHistories = taskHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<ReleaseHistory> getReleaseHistories() {
		return this.releaseHistories;
	}

	public void setReleaseHistories(Set<ReleaseHistory> releaseHistories) {
		this.releaseHistories = releaseHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<ReleaseitHistory> getReleaseitHistories() {
		return this.releaseitHistories;
	}

	public void setReleaseitHistories(Set<ReleaseitHistory> releaseitHistories) {
		this.releaseitHistories = releaseitHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<ProgettoSviluppo> getProgettoSviluppos() {
		return this.progettoSviluppos;
	}

	public void setProgettoSviluppos(Set<ProgettoSviluppo> progettoSviluppos) {
		this.progettoSviluppos = progettoSviluppos;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<DefectHistory> getDefectHistories() {
		return this.defectHistories;
	}

	public void setDefectHistories(Set<DefectHistory> defectHistories) {
		this.defectHistories = defectHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<AnomaliaAssistenza> getAnomaliaAssistenzas() {
		return this.anomaliaAssistenzas;
	}

	public void setAnomaliaAssistenzas(Set<AnomaliaAssistenza> anomaliaAssistenzas) {
		this.anomaliaAssistenzas = anomaliaAssistenzas;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<Testcase> getTestcases() {
		return this.testcases;
	}

	public void setTestcases(Set<Testcase> testcases) {
		this.testcases = testcases;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "status")
	public Set<Mev> getMevs() {
		return this.mevs;
	}

	public void setMevs(Set<Mev> mevs) {
		this.mevs = mevs;
	}

}
