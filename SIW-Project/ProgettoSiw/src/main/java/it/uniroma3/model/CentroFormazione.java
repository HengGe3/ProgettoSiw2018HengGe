package it.uniroma3.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CentroFormazione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public Long getId() {
		return id;
	}

	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String indirizzo;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String telefono;
	@Column(nullable = false)
	private int capienzaMassima;

	@OneToMany(mappedBy = "centroFormazione")
	private List<Attivita> listaAttivita;

	public CentroFormazione() {
	}

	public CentroFormazione(String nome, String indirizzo, String email, String telefono, int capienzaMassima) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.email = email;
		this.telefono = telefono;
		this.capienzaMassima = capienzaMassima;
	}
	
	public List<Attivita> getListaAttivita() {
		return listaAttivita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getCapienzaMassima() {
		return capienzaMassima;
	}

	public void setCapienzaMassima(int capienzaMassima) {
		this.capienzaMassima = capienzaMassima;
	}

	@Override
	public String toString() {
		return nome.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((indirizzo == null) ? 0 : indirizzo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		CentroFormazione other = (CentroFormazione) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (indirizzo == null) {
			if (other.indirizzo != null)
				return false;
		} else if (!indirizzo.equals(other.indirizzo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
