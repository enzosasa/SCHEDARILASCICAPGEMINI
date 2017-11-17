package entities;
// Generated Oct 25, 2017 9:10:32 AM by Hibernate Tools 5.1.0.Alpha1

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
 * Priority generated by hbm2java
 */
@Entity
@Table(name = "priority", catalog = "rilasci_DB")
public class Priority implements java.io.Serializable {

	private Integer id;
	private String nome;
	private Float valore;
	private String polarionName;
	private Set<Mev> mevs = new HashSet<Mev>(0);
	private Set<Testcase> testcases = new HashSet<Testcase>(0);
	private Set<ReleaseIt> releaseIts = new HashSet<ReleaseIt>(0);
	private Set<ProgettoSviluppo> progettoSviluppos = new HashSet<ProgettoSviluppo>(0);
	private Set<Task> tasks = new HashSet<Task>(0);
	private Set<Defect> defects = new HashSet<Defect>(0);
	private Set<Taskit> taskits = new HashSet<Taskit>(0);
	private Set<Support> supports = new HashSet<Support>(0);
	private Set<Release> releases = new HashSet<Release>(0);
	private Set<Anomalia> anomalias = new HashSet<Anomalia>(0);
	private Set<Documento> documentos = new HashSet<Documento>(0);

	public Priority() {
	}

	public Priority(String polarionName) {
		this.polarionName = polarionName;
	}

	public Priority(String nome, Float valore, String polarionName, Set<Mev> mevs, Set<Testcase> testcases,
			Set<ReleaseIt> releaseIts, Set<ProgettoSviluppo> progettoSviluppos, Set<Task> tasks, Set<Defect> defects,
			Set<Taskit> taskits, Set<Support> supports, Set<Release> releases, Set<Anomalia> anomalias,
			Set<Documento> documentos) {
		this.nome = nome;
		this.valore = valore;
		this.polarionName = polarionName;
		this.mevs = mevs;
		this.testcases = testcases;
		this.releaseIts = releaseIts;
		this.progettoSviluppos = progettoSviluppos;
		this.tasks = tasks;
		this.defects = defects;
		this.taskits = taskits;
		this.supports = supports;
		this.releases = releases;
		this.anomalias = anomalias;
		this.documentos = documentos;
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

	@Column(name = "valore", precision = 3, scale = 1)
	public Float getValore() {
		return this.valore;
	}

	public void setValore(Float valore) {
		this.valore = valore;
	}

	@Column(name = "polarion_name", nullable = false, length = 45)
	public String getPolarionName() {
		return this.polarionName;
	}

	public void setPolarionName(String polarionName) {
		this.polarionName = polarionName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Mev> getMevs() {
		return this.mevs;
	}

	public void setMevs(Set<Mev> mevs) {
		this.mevs = mevs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Testcase> getTestcases() {
		return this.testcases;
	}

	public void setTestcases(Set<Testcase> testcases) {
		this.testcases = testcases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<ReleaseIt> getReleaseIts() {
		return this.releaseIts;
	}

	public void setReleaseIts(Set<ReleaseIt> releaseIts) {
		this.releaseIts = releaseIts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<ProgettoSviluppo> getProgettoSviluppos() {
		return this.progettoSviluppos;
	}

	public void setProgettoSviluppos(Set<ProgettoSviluppo> progettoSviluppos) {
		this.progettoSviluppos = progettoSviluppos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Task> getTasks() {
		return this.tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Defect> getDefects() {
		return this.defects;
	}

	public void setDefects(Set<Defect> defects) {
		this.defects = defects;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Taskit> getTaskits() {
		return this.taskits;
	}

	public void setTaskits(Set<Taskit> taskits) {
		this.taskits = taskits;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Support> getSupports() {
		return this.supports;
	}

	public void setSupports(Set<Support> supports) {
		this.supports = supports;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Release> getReleases() {
		return this.releases;
	}

	public void setReleases(Set<Release> releases) {
		this.releases = releases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Anomalia> getAnomalias() {
		return this.anomalias;
	}

	public void setAnomalias(Set<Anomalia> anomalias) {
		this.anomalias = anomalias;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "priority")
	public Set<Documento> getDocumentos() {
		return this.documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}

}
