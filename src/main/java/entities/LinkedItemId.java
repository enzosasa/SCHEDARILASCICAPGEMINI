package entities;
// Generated Nov 28, 2017 11:11:12 AM by Hibernate Tools 5.1.0.Alpha1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * LinkedItemId generated by hbm2java
 */
@Embeddable
public class LinkedItemId implements java.io.Serializable {

	private String idPolarionPadre;
	private String idPolarionFiglio;

	public LinkedItemId() {
	}

	public LinkedItemId(String idPolarionPadre, String idPolarionFiglio) {
		this.idPolarionPadre = idPolarionPadre;
		this.idPolarionFiglio = idPolarionFiglio;
	}

	@Column(name = "id_polarion_padre", nullable = false, length = 45)
	public String getIdPolarionPadre() {
		return this.idPolarionPadre;
	}

	public void setIdPolarionPadre(String idPolarionPadre) {
		this.idPolarionPadre = idPolarionPadre;
	}

	@Column(name = "id_polarion_figlio", nullable = false, length = 45)
	public String getIdPolarionFiglio() {
		return this.idPolarionFiglio;
	}

	public void setIdPolarionFiglio(String idPolarionFiglio) {
		this.idPolarionFiglio = idPolarionFiglio;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LinkedItemId))
			return false;
		LinkedItemId castOther = (LinkedItemId) other;

		return ((this.getIdPolarionPadre() == castOther.getIdPolarionPadre())
				|| (this.getIdPolarionPadre() != null && castOther.getIdPolarionPadre() != null
						&& this.getIdPolarionPadre().equals(castOther.getIdPolarionPadre())))
				&& ((this.getIdPolarionFiglio() == castOther.getIdPolarionFiglio())
						|| (this.getIdPolarionFiglio() != null && castOther.getIdPolarionFiglio() != null
								&& this.getIdPolarionFiglio().equals(castOther.getIdPolarionFiglio())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdPolarionPadre() == null ? 0 : this.getIdPolarionPadre().hashCode());
		result = 37 * result + (getIdPolarionFiglio() == null ? 0 : this.getIdPolarionFiglio().hashCode());
		return result;
	}

}
