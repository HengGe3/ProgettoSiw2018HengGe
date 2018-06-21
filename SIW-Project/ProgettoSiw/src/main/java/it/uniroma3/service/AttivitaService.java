package it.uniroma3.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.Progettosiw;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.CentroFormazione;
import it.uniroma3.model.Partecipazione;
import it.uniroma3.repository.AttivitaRepository;

@Transactional
@Service
public class AttivitaService {

	@Autowired
	private AttivitaRepository attivitaRepository;

	// Metodi di ricerca
	public Attivita findById(Long id) {
		Optional<Attivita> attivita = this.attivitaRepository.findById(id);
		if (attivita.isPresent())
			return attivita.get();
		else
			return null;
	}

	public List<Attivita> findAll() {
		return (List<Attivita>) this.attivitaRepository.findAll();
	}

	public Attivita findByDescrizione(String descrizione) {
		Optional<Attivita> attivita = this.attivitaRepository.findByDescrizione(descrizione);
		if (attivita.isPresent())
			return attivita.get();
		else
			return null;
	}

	public Attivita findByDescrizioneAndOraInizioAndOraFine(String descrizione, Date oraInizio, Date oraFine) {
		Optional<Attivita> attivita = this.attivitaRepository.findByDescrizioneAndOraInizioAndOraFine(descrizione,
				oraInizio, oraFine);
		if (attivita.isPresent())
			return attivita.get();
		else
			return null;
	}

	public List<Attivita> findByCentroFormazione(CentroFormazione centroFormazione) {
		if (centroFormazione != null)
			return this.attivitaRepository.findByCentroFormazione(centroFormazione);
		else
			return (List<Attivita>) this.attivitaRepository.findAll();
	}

	public void uploadParametri(Attivita attivita) {
		attivita.setDescrizione(uploadString(attivita.getDescrizione()));
	}

	public String uploadString(String str) {
		StringBuilder b = new StringBuilder(str);
		int i = 0;
		do {
			b.replace(i, i + 1, b.substring(i, i + 1).toUpperCase());
			i = b.indexOf(" ", i) + 1;
		} while (i > 0 && i < b.length());
		return b.toString();
	}

	public boolean alreadyExists(Attivita attivita) {
		Optional<Attivita> attivitaTrovata = this.attivitaRepository.findByDescrizioneAndOraInizioAndOraFine(
				attivita.getDescrizione(), attivita.getOraInizio(), attivita.getOraFine());
		return attivitaTrovata.isPresent();
	}

	public List<Attivita> getListaAttivita() {

		if (Progettosiw.getRsc().getCentroFormazione() == null)
			return (List<Attivita>) this.attivitaRepository.findAll();

		return (List<Attivita>) this.attivitaRepository
				.findAllByCentroFormazione(Progettosiw.getRsc().getCentroFormazione());
	}

	public List<Attivita> getListaAttivitaPossibili(Allievo allievoCorrente) {

		List<Partecipazione> listaPAllievo = allievoCorrente.getListaPartecipazioni();
		List<Attivita> listaAttivitaAllievo = new ArrayList<>();

		for (Partecipazione p : listaPAllievo)
			listaAttivitaAllievo.add(p.getAttivita());

		List<Attivita> listaAttivitaPossibili = (List<Attivita>) this.attivitaRepository.findAll();

		for (Attivita a : listaAttivitaAllievo)
			listaAttivitaPossibili.remove(a);

		if (Progettosiw.getRsc().getCentroFormazione() == null)
			return listaAttivitaPossibili;

		listaAttivitaPossibili = (List<Attivita>) this.attivitaRepository
				.findByCentroFormazione(Progettosiw.getRsc().getCentroFormazione());
		for (Attivita a : listaAttivitaAllievo)
			listaAttivitaPossibili.remove(a);

		return listaAttivitaPossibili;
	}

	// Metodi persistence
	public Attivita save(Attivita attivita) {
		return this.attivitaRepository.save(attivita);
	}

	public Attivita update(Attivita attivita, String descrizione, Double prezzo, Date dataAttivita, Date oraInizio,
			Date oraFine) {
		attivita.setDescrizione(descrizione);
		attivita.setPrezzo(prezzo);
		attivita.setDataAttivita(dataAttivita);
		attivita.setOraInizio(oraInizio);
		attivita.setOraFine(oraFine);
		return attivita;
	}

	public void delete(Long id) {
		this.attivitaRepository.deleteById(id);
	}

	public Map<Attivita, Integer> numeroAllieviAttivita() {
		Map<Attivita, Integer> centro2Integer = new HashMap<>();
		for (Attivita attivita : this.attivitaRepository.findAll()) {
			int n = attivita.getListaPartecipazioni().size();
			centro2Integer.put(attivita, n);
		}
		return sortByValue(centro2Integer);
	}

	public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
		list.sort(Entry.comparingByValue());

		Map<K, V> result = new LinkedHashMap<>();
		for (Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

}
