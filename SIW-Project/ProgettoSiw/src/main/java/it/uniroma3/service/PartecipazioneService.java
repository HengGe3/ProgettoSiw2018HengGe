package it.uniroma3.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Partecipazione;
import it.uniroma3.repository.PartecipazioneRepository;

@Transactional
@Service
public class PartecipazioneService {

	@Autowired
	private PartecipazioneRepository partecipazioneRepository;

	public Partecipazione save(Partecipazione partecipazione) {
		double prezzo = partecipazione.getAttivita().getPrezzo();
		double importoDovuto = partecipazione.getAllievo().getImportoDovuto() + prezzo;
		partecipazione.getAllievo().setImportoDovuto(importoDovuto);
		return this.partecipazioneRepository.save(partecipazione);
	}

	public Set<Attivita> getListaAttivita(Allievo allievo) {
		Set<Attivita> listaAttivita = new HashSet<>();
		for (Partecipazione partecipazione : allievo.getListaPartecipazioni())
			listaAttivita.add(partecipazione.getAttivita());
		return listaAttivita;
	}

	public Set<Allievo> getListaAllievi(Attivita attivita) {
		Set<Allievo> listaAllievi = new HashSet<Allievo>();
		for (Partecipazione partecipazione : attivita.getListaPartecipazioni())
			listaAllievi.add(partecipazione.getAllievo());
		return listaAllievi;
	}

	public Set<Attivita> getListaAttivitaNonPagata(Allievo allievo) {
		Set<Attivita> listaAttivita = new HashSet<>();
		for (Partecipazione partecipazione : allievo.getListaPartecipazioni()) {
			if (!partecipazione.isPagato())
				listaAttivita.add(partecipazione.getAttivita());
		}
		return listaAttivita;
	}

	public Partecipazione findById(Long id) {
		Optional<Partecipazione> partecipazione = this.partecipazioneRepository.findById(id);
		if (partecipazione.isPresent())
			return partecipazione.get();
		else
			return null;
	}

	public void delete(Long id) {
		this.partecipazioneRepository.deleteById(id);
	}

	public Partecipazione findByAllievoAndAttivita(Allievo allievoCorrente, Attivita attivita) {
		Optional<Partecipazione> partecipazione = this.partecipazioneRepository
				.findByAllievoAndAttivita(allievoCorrente, attivita);
		if (partecipazione.isPresent())
			return partecipazione.get();
		else
			return null;

	}

	public void deleteByAllievo(Allievo allievo) {
		for (Partecipazione p : this.partecipazioneRepository.findAll()) {
			if (p.getAllievo() == allievo)
				this.partecipazioneRepository.delete(p);
		}

	}

	public void deleteByAttivita(Attivita attivita) {
		for (Partecipazione p : this.partecipazioneRepository.findAll()) {
			if (p.getAttivita() == attivita)
				this.partecipazioneRepository.delete(p);
		}
	}

	// Metodo non funzionate risolto facendo @Table(uniqueConstraints =
	// {@UniqueConstraint(columnNames = {"id_allievo", "id_attivita"})}) in model
	// Partecipazione
	public boolean controlloPartecipazione(Partecipazione partecipazione) {
		List<Attivita> a = this.partecipazioneRepository.findAllByAttivita(partecipazione.getAttivita());
		return a.size() == 0;
	}

}
