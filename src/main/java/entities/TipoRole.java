package entities;
// Generated Oct 25, 2017 9:10:32 AM by Hibernate Tools 5.1.0.Alpha1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TipoRole generated by hbm2java
 */
@Entity
@Table(name = "tipo_role", catalog = "rilasci_DB")
public class TipoRole implements java.io.Serializable {

	private int id;
	private String nome;
	private Set<LinkedItem> linkedItems = new HashSet<LinkedItem>(0);

	public TipoRole() {
	}

	public TipoRole(int id) {
		this.id = id;
	}

	public TipoRole(int id, String nome, Set<LinkedItem> linkedItems) {
		this.id = id;
		this.nome = nome;
		this.linkedItems = linkedItems;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nome", length = 45)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoRole")
	public Set<LinkedItem> getLinkedItems() {
		return this.linkedItems;
	}

	public void setLinkedItems(Set<LinkedItem> linkedItems) {
		this.linkedItems = linkedItems;
	}

}
