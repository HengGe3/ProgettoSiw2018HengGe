package it.uniroma3.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"allievo_id", "attivita_id"})})
public class Partecipazione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "allievo_id")
	private Allievo allievo;

	@ManyToOne
	@JoinColumn(name = "attivita_id")
	private Attivita attivita;
	
	@Column
	private boolean pagato;

	public Partecipazione(Allievo allievo, Attivita attivita) {
		this.allievo = allievo;
		this.attivita = attivita;
	}

	public Partecipazione() {
	}

	public Allievo getAllievo() {
		return allievo;
	}

	public void setAllievo(Allievo allievo) {
		this.allievo = allievo;
	}

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

	public boolean isPagato() {
		return pagato;
	}

	public void setPagato(boolean pagato) {
		this.pagato = pagato;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allievo == null) ? 0 : allievo.hashCode());
		result = prime * result + ((attivita == null) ? 0 : attivita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partecipazione other = (Partecipazione) obj;
		if (allievo == null) {
			if (other.allievo != null)
				return false;
		} else if (!allievo.equals(other.allievo))
			return false;
		if (attivita == null) {
			if (other.attivita != null)
				return false;
		} else if (!attivita.equals(other.attivita))
			return false;
		return true;
	}

}
