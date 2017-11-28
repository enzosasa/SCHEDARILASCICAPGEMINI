package entities;
// Generated Nov 28, 2017 11:11:12 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ChecklistTestcase generated by hbm2java
 */
@Entity
@Table(name = "checklist_testcase", catalog = "rilasci_db")
public class ChecklistTestcase implements java.io.Serializable {

	private Integer id;
	private Checklist checklist;
	private Esito esito;
	private Testcase testcase;
	private User user;
	private Date dataEsecuzione;

	public ChecklistTestcase() {
	}

	public ChecklistTestcase(Checklist checklist, Esito esito, Testcase testcase, User user, Date dataEsecuzione) {
		this.checklist = checklist;
		this.esito = esito;
		this.testcase = testcase;
		this.user = user;
		this.dataEsecuzione = dataEsecuzione;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_checlist")
	public Checklist getChecklist() {
		return this.checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_esito")
	public Esito getEsito() {
		return this.esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_testcase")
	public Testcase getTestcase() {
		return this.testcase;
	}

	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_esecuzione", length = 19)
	public Date getDataEsecuzione() {
		return this.dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

}
