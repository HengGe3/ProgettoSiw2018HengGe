package it.uniroma3.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Partecipazione;

public interface PartecipazioneRepository extends CrudRepository<Partecipazione, Long>{

	public List<Attivita> findAllByAttivita(Attivita attivita);
	
	public Optional<Partecipazione> findById(Long Id);

	public Optional<Partecipazione> findByAllievoAndAttivita(Allievo allievoCorrente, Attivita attivita);

}
