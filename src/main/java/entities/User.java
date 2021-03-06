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
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "rilasci_db")
public class User implements java.io.Serializable {

	private Integer id;
	private String nome;
	private String idPolarion;
	private Set<TaskItHistory> taskItHistories = new HashSet<TaskItHistory>(0);
	private Set<Taskit> taskits = new HashSet<Taskit>(0);
	private Set<TaskitWorkitem> taskitWorkitems = new HashSet<TaskitWorkitem>(0);
	private Set<SupportHistory> supportHistories = new HashSet<SupportHistory>(0);
	private Set<ChecklistTestcase> checklistTestcases = new HashSet<ChecklistTestcase>(0);
	private Set<DefectHistory> defectHistories = new HashSet<DefectHistory>(0);
	private Set<Workrecords> workrecordses = new HashSet<Workrecords>(0);
	private Set<ProgettoSviluppo> progettoSviluppos = new HashSet<ProgettoSviluppo>(0);
	private Set<ReleaseHistory> releaseHistories = new HashSet<ReleaseHistory>(0);
	private Set<ReleaseitHistory> releaseitHistories = new HashSet<ReleaseitHistory>(0);
	private Set<Testcase> testcases = new HashSet<Testcase>(0);
	private Set<Documento> documentos = new HashSet<Documento>(0);
	private Set<TestcaseWorkitem> testcaseWorkitems = new HashSet<TestcaseWorkitem>(0);
	private Set<Mev> mevs = new HashSet<Mev>(0);
	private Set<AnomaliaHistory> anomaliaHistories = new HashSet<AnomaliaHistory>(0);
	private Set<Task> tasks = new HashSet<Task>(0);
	private Set<AnomaliaAssistenza> anomaliaAssistenzas = new HashSet<AnomaliaAssistenza>(0);
	private Set<Assignee> assignees = new HashSet<Assignee>(0);
	private Set<TaskHistory> taskHistories = new HashSet<TaskHistory>(0);

	public User() {
	}

	public User(String nome, String idPolarion, Set<TaskItHistory> taskItHistories, Set<Taskit> taskits,
			Set<TaskitWorkitem> taskitWorkitems, Set<SupportHistory> supportHistories,
			Set<ChecklistTestcase> checklistTestcases, Set<DefectHistory> defectHistories,
			Set<Workrecords> workrecordses, Set<ProgettoSviluppo> progettoSviluppos,
			Set<ReleaseHistory> releaseHistories, Set<ReleaseitHistory> releaseitHistories, Set<Testcase> testcases,
			Set<Documento> documentos, Set<TestcaseWorkitem> testcaseWorkitems, Set<Mev> mevs,
			Set<AnomaliaHistory> anomaliaHistories, Set<Task> tasks, Set<AnomaliaAssistenza> anomaliaAssistenzas,
			Set<Assignee> assignees, Set<TaskHistory> taskHistories) {
		this.nome = nome;
		this.idPolarion = idPolarion;
		this.taskItHistories = taskItHistories;
		this.taskits = taskits;
		this.taskitWorkitems = taskitWorkitems;
		this.supportHistories = supportHistories;
		this.checklistTestcases = checklistTestcases;
		this.defectHistories = defectHistories;
		this.workrecordses = workrecordses;
		this.progettoSviluppos = progettoSviluppos;
		this.releaseHistories = releaseHistories;
		this.releaseitHistories = releaseitHistories;
		this.testcases = testcases;
		this.documentos = documentos;
		this.testcaseWorkitems = testcaseWorkitems;
		this.mevs = mevs;
		this.anomaliaHistories = anomaliaHistories;
		this.tasks = tasks;
		this.anomaliaAssistenzas = anomaliaAssistenzas;
		this.assignees = assignees;
		this.taskHistories = taskHistories;
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

	@Column(name = "id_polarion", length = 45)
	public String getIdPolarion() {
		return this.idPolarion;
	}

	public void setIdPolarion(String idPolarion) {
		this.idPolarion = idPolarion;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<TaskItHistory> getTaskItHistories() {
		return this.taskItHistories;
	}

	public void setTaskItHistories(Set<TaskItHistory> taskItHistories) {
		this.taskItHistories = taskItHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Taskit> getTaskits() {
		return this.taskits;
	}

	public void setTaskits(Set<Taskit> taskits) {
		this.taskits = taskits;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<TaskitWorkitem> getTaskitWorkitems() {
		return this.taskitWorkitems;
	}

	public void setTaskitWorkitems(Set<TaskitWorkitem> taskitWorkitems) {
		this.taskitWorkitems = taskitWorkitems;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<SupportHistory> getSupportHistories() {
		return this.supportHistories;
	}

	public void setSupportHistories(Set<SupportHistory> supportHistories) {
		this.supportHistories = supportHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<ChecklistTestcase> getChecklistTestcases() {
		return this.checklistTestcases;
	}

	public void setChecklistTestcases(Set<ChecklistTestcase> checklistTestcases) {
		this.checklistTestcases = checklistTestcases;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<DefectHistory> getDefectHistories() {
		return this.defectHistories;
	}

	public void setDefectHistories(Set<DefectHistory> defectHistories) {
		this.defectHistories = defectHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Workrecords> getWorkrecordses() {
		return this.workrecordses;
	}

	public void setWorkrecordses(Set<Workrecords> workrecordses) {
		this.workrecordses = workrecordses;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<ProgettoSviluppo> getProgettoSviluppos() {
		return this.progettoSviluppos;
	}

	public void setProgettoSviluppos(Set<ProgettoSviluppo> progettoSviluppos) {
		this.progettoSviluppos = progettoSviluppos;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<ReleaseHistory> getReleaseHistories() {
		return this.releaseHistories;
	}

	public void setReleaseHistories(Set<ReleaseHistory> releaseHistories) {
		this.releaseHistories = releaseHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<ReleaseitHistory> getReleaseitHistories() {
		return this.releaseitHistories;
	}

	public void setReleaseitHistories(Set<ReleaseitHistory> releaseitHistories) {
		this.releaseitHistories = releaseitHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Testcase> getTestcases() {
		return this.testcases;
	}

	public void setTestcases(Set<Testcase> testcases) {
		this.testcases = testcases;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<TestcaseWorkitem> getTestcaseWorkitems() {
		return this.testcaseWorkitems;
	}

	public void setTestcaseWorkitems(Set<TestcaseWorkitem> testcaseWorkitems) {
		this.testcaseWorkitems = testcaseWorkitems;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Mev> getMevs() {
		return this.mevs;
	}

	public void setMevs(Set<Mev> mevs) {
		this.mevs = mevs;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<AnomaliaHistory> getAnomaliaHistories() {
		return this.anomaliaHistories;
	}

	public void setAnomaliaHistories(Set<AnomaliaHistory> anomaliaHistories) {
		this.anomaliaHistories = anomaliaHistories;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<AnomaliaAssistenza> getAnomaliaAssistenzas() {
		return this.anomaliaAssistenzas;
	}

	public void setAnomaliaAssistenzas(Set<AnomaliaAssistenza> anomaliaAssistenzas) {
		this.anomaliaAssistenzas = anomaliaAssistenzas;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<Assignee> getAssignees() {
		return this.assignees;
	}

	public void setAssignees(Set<Assignee> assignees) {
		this.assignees = assignees;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	public Set<TaskHistory> getTaskHistories() {
		return this.taskHistories;
	}

	public void setTaskHistories(Set<TaskHistory> taskHistories) {
		this.taskHistories = taskHistories;
	}

}
