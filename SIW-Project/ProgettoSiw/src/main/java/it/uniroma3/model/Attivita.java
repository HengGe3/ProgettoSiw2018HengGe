package it.uniroma3.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Attivita {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String descrizione;
	@Column(nullable = false)
	private double prezzo;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date dataAttivita;

	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date oraInizio;
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	private Date oraFine;

	@ManyToOne
	private CentroFormazione centroFormazione;

	@OneToMany(mappedBy = "attivita")
	private List<Partecipazione> listaPartecipazioni;

	public Attivita() {
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public CentroFormazione getCentroFormazione() {
		return centroFormazione;
	}

	public void setCentroFormazione(CentroFormazione centroFormazione) {
		this.centroFormazione = centroFormazione;
	}

	public List<Partecipazione> getListaPartecipazioni() {
		return listaPartecipazioni;
	}

	public void setListaPartecipazioni(List<Partecipazione> listaPartecipazioni) {
		this.listaPartecipazioni = listaPartecipazioni;
	}

	public Long getId() {
		return id;
	}

	public Date getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(Date oraInizio) {
		this.oraInizio = oraInizio;
	}

	public Date getOraFine() {
		return oraFine;
	}

	public void setOraFine(Date oraFine) {
		this.oraFine = oraFine;
	}
	
	public Date getDataAttivita() {
		return dataAttivita;
	}

	public void setDataAttivita(Date dataAttivita) {
		this.dataAttivita = dataAttivita;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((centroFormazione == null) ? 0 : centroFormazione.hashCode());
		result = prime * result + ((dataAttivita == null) ? 0 : dataAttivita.hashCode());
		result = prime * result + ((descrizione == null) ? 0 : descrizione.hashCode());
		result = prime * result + ((oraFine == null) ? 0 : oraFine.hashCode());
		result = prime * result + ((oraInizio == null) ? 0 : oraInizio.hashCode());
		long temp;
		temp = Double.doubleToLongBits(prezzo);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Attivita other = (Attivita) obj;
		if (centroFormazione == null) {
			if (other.centroFormazione != null)
				return false;
		} else if (!centroFormazione.equals(other.centroFormazione))
			return false;
		if (dataAttivita == null) {
			if (other.dataAttivita != null)
				return false;
		} else if (!dataAttivita.equals(other.dataAttivita))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		if (oraFine == null) {
			if (other.oraFine != null)
				return false;
		} else if (!oraFine.equals(other.oraFine))
			return false;
		if (oraInizio == null) {
			if (other.oraInizio != null)
				return false;
		} else if (!oraInizio.equals(other.oraInizio))
			return false;
		if (Double.doubleToLongBits(prezzo) != Double.doubleToLongBits(other.prezzo))
			return false;
		return true;
	}

}
