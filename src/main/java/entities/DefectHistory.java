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
 * DefectHistory generated by hbm2java
 */
@Entity
@Table(name = "defect_history", catalog = "rilasci_db")
public class DefectHistory implements java.io.Serializable {

	private Integer id;
	private Defect defect;
	private Status status;
	private User user;
	private Date dataUpdate;

	public DefectHistory() {
	}

	public DefectHistory(Defect defect, Status status, User user, Date dataUpdate) {
		this.defect = defect;
		this.status = status;
		this.user = user;
		this.dataUpdate = dataUpdate;
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
	@JoinColumn(name = "cod_defect")
	public Defect getDefect() {
		return this.defect;
	}

	public void setDefect(Defect defect) {
		this.defect = defect;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_status")
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_author")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_update", length = 19)
	public Date getDataUpdate() {
		return this.dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

}
