package it.uniroma3.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.CentroFormazione;
import it.uniroma3.repository.CentroFormazioneRepository;

@Transactional
@Service
public class CentroFormazioneService {

	@Autowired
	private CentroFormazioneRepository centroRepository;

	@Autowired
	private PartecipazioneService partecipazioneService;

	// Metodi di ricerca
	public CentroFormazione findById(Long id) {
		Optional<CentroFormazione> CentroFormazione = this.centroRepository.findById(id);
		if (CentroFormazione.isPresent())
			return CentroFormazione.get();
		else
			return null;
	}

	public CentroFormazione findByNome(String nome) {
		Optional<CentroFormazione> CentroFormazione = this.centroRepository.findByNome(nome);
		if (CentroFormazione.isPresent())
			return CentroFormazione.get();
		else
			return null;
	}

	public CentroFormazione findByIndirizzo(String indirizzo) {
		Optional<CentroFormazione> CentroFormazione = this.centroRepository.findByIndirizzo(indirizzo);
		if (CentroFormazione.isPresent())
			return CentroFormazione.get();
		else
			return null;
	}

	public List<CentroFormazione> findAll() {
		return (List<CentroFormazione>) this.centroRepository.findAll();
	}

	// Metodi di supporto
	public void uploadParametri(CentroFormazione centroFormazione) {
		centroFormazione.setNome(uploadString(centroFormazione.getNome()));
		centroFormazione.setIndirizzo(uploadString(centroFormazione.getIndirizzo()));
		centroFormazione.getEmail().toLowerCase();
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

	public boolean alreadyExists(CentroFormazione centroFormazione) {
		Optional<CentroFormazione> centroFormazioneTrovato = this.centroRepository
				.findByNome(centroFormazione.getNome());
		if (centroFormazioneTrovato.isPresent())
			return true;
		else
			return false;
	}

	public boolean verificaDisponibilita(CentroFormazione centro) {
		int n = 0;
		for (Attivita a : centro.getListaAttivita())
			n = n + a.getListaPartecipazioni().size();
		return centro.getCapienzaMassima() > n;
	}

	public Map<CentroFormazione, Integer> numeroAllieviCentri() {
		Map<CentroFormazione, Integer> centro2Integer = new HashMap<>();
		for (CentroFormazione centro : this.centroRepository.findAll()) {
			int n = 0;
			for (Attivita attivita : centro.getListaAttivita()) {
				n = this.partecipazioneService.getListaAllievi(attivita).size();
				if (!centro2Integer.containsKey(centro))
					centro2Integer.put(centro, n);
				else
					centro2Integer.put(centro, centro2Integer.get(centro) + n);
			}
		}
		return sortByValue(centro2Integer);
	}

	public Map<CentroFormazione, Double> ricaviCentri() {
		Map<CentroFormazione, Double> centro2Integer = new HashMap<>();
		for (CentroFormazione centro : this.centroRepository.findAll()) {
			Double k = (double) 0;
			for (Attivita attivita : centro.getListaAttivita()) {
				k = attivita.getPrezzo() * attivita.getListaPartecipazioni().size();
				if (!centro2Integer.containsKey(centro))
					centro2Integer.put(centro, k);
				else
					centro2Integer.put(centro, centro2Integer.get(centro) + k);
			}
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

	public CentroFormazione save(CentroFormazione centroFormazione) {
		return this.centroRepository.save(centroFormazione);
	}

	public CentroFormazione update(CentroFormazione CentroFormazioneTrovato, String nome, String indirizzo,
			String email, String telefono, int capienzaMassima) {
		CentroFormazioneTrovato.setNome(nome);
		CentroFormazioneTrovato.setIndirizzo(indirizzo);
		CentroFormazioneTrovato.setEmail(email);
		CentroFormazioneTrovato.setTelefono(telefono);
		CentroFormazioneTrovato.setCapienzaMassima(capienzaMassima);
		return CentroFormazioneTrovato;
	}

	public void delete(Long id) {
		this.centroRepository.deleteById(id);
	}

}
