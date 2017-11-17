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

/**
 * ReleaseIt generated by hbm2java
 */
@Entity
@Table(name = "release_it", catalog = "rilasci_DB")
public class ReleaseIt implements java.io.Serializable {

	private Integer id;
	private Priority priority;
	private Severity severity;
	private Status status;
	private User user;
	private String idPolarion;
	private String titolo;
	private Date dataCreazione;
	private Date dataUpdate;
	private Date dataInizio;
	private Date dataFine;
	private Set<Assignee> assignees = new HashSet<Assignee>(0);
	private Set<ReleaseitHistory> releaseitHistories = new HashSet<ReleaseitHistory>(0);

	public ReleaseIt() {
	}

	public ReleaseIt(User user) {
		this.user = user;
	}

	public ReleaseIt(Priority priority, Severity severity, Status status, User user, String idPolarion, String titolo,
			Date dataCreazione, Date dataUpdate, Date dataInizio, Date dataFine, Set<Assignee> assignees,
			Set<ReleaseitHistory> releaseitHistories) {
		this.priority = priority;
		this.severity = severity;
		this.status = status;
		this.user = user;
		this.idPolarion = idPolarion;
		this.titolo = titolo;
		this.dataCreazione = dataCreazione;
		this.dataUpdate = dataUpdate;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.assignees = assignees;
		this.releaseitHistories = releaseitHistories;
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
	@JoinColumn(name = "cod_severity")
	public Severity getSeverity() {
		return this.severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
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

	@Column(name = "id_polarion", length = 45)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "releaseIt")
	public Set<Assignee> getAssignees() {
		return this.assignees;
	}

	public void setAssignees(Set<Assignee> assignees) {
		this.assignees = assignees;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "releaseIt")
	public Set<ReleaseitHistory> getReleaseitHistories() {
		return this.releaseitHistories;
	}

	public void setReleaseitHistories(Set<ReleaseitHistory> releaseitHistories) {
		this.releaseitHistories = releaseitHistories;
	}

}
