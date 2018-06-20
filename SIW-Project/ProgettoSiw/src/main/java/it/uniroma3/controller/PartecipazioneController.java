package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Partecipazione;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroFormazioneService;
import it.uniroma3.service.PartecipazioneService;

@Controller
public class PartecipazioneController {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private AttivitaService attivitaService;

	@Autowired
	private CentroFormazioneService centroFormazioneService;

	@Autowired
	private PartecipazioneService partecipazioneService;

	final private String prefix = "partecipazione/";

	private Allievo allievoCorrente;
	private Attivita attivitaCorrente;

	@RequestMapping(value = "/partecipaAttivita/{id}", method = RequestMethod.GET)
	private String insPartecipazione(@PathVariable("id") Long id, Model model) {
		this.allievoCorrente = this.allievoService.findById(id);
		model.addAttribute("listaAttivita", this.attivitaService.getListaAttivitaPossibili(this.allievoCorrente));
		return this.prefix + "attivitaList";
	}

	@RequestMapping(value = "/confermaPartecipazione/{id}", method = RequestMethod.GET)
	public String findAttivita(@PathVariable("id") Long id, Model model) {
		this.attivitaCorrente = this.attivitaService.findById(id);
		if (this.centroFormazioneService.verificaDisponibilita(this.attivitaCorrente.getCentroFormazione())) {
			model.addAttribute("attivita", this.attivitaCorrente);
			model.addAttribute("allievo", this.allievoCorrente);
			return this.prefix + "confermaPartecipazione";
		}
		model.addAttribute("centroFull", "I centro non puo piu ospitare altri Allievi, scegliere un'altra attivita");
		model.addAttribute("listaAttivita", this.attivitaService.getListaAttivitaPossibili(this.allievoCorrente));
		return this.prefix + "attivitaList";
	}

	@RequestMapping(value = "/savePartecipazione", method = RequestMethod.POST)
	public String savePartecipazione(Model model) {
		Partecipazione partecipazione = new Partecipazione(this.allievoCorrente, this.attivitaCorrente);
		this.allievoCorrente.getListaPartecipazioni().add(partecipazione);
		this.partecipazioneService.save(partecipazione);
		this.allievoService.aggiornaImporto(this.allievoCorrente);
		model.addAttribute("attivita", this.attivitaCorrente);
		model.addAttribute("allievo", this.allievoCorrente);
		return this.prefix + "partecipazioneRegistrata";
	}

	@RequestMapping(value = "/cancellaPartecipazione/{id}", method = RequestMethod.GET)
	private String cancellaPartecipazione(@PathVariable("id") Long id, Model model) {
		this.allievoCorrente = this.allievoService.findById(id);
		model.addAttribute("allievo", this.allievoService.findById(this.allievoCorrente.getId()));
		model.addAttribute("listaAttivita", this.partecipazioneService.getListaAttivita(this.allievoCorrente));
		return this.prefix + "cancellaPartecipazione";
	}

	@RequestMapping(value = "/confermaCancellazioneId/{id}")
	private String confermaPartecipazioneId(@PathVariable("id") Long id, Model model) {
		this.attivitaCorrente = this.attivitaService.findById(id);
		model.addAttribute("allievo", this.allievoService.findById(this.allievoCorrente.getId()));
		model.addAttribute("attivita", this.attivitaService.findById(id));
		return this.prefix + "confermaCancellazione";
	}

	@RequestMapping(value = "/deletePartecipazione")
	private String deletePartecipazione(Model model) {
		Partecipazione p = this.partecipazioneService.findByAllievoAndAttivita(this.allievoCorrente,
				this.attivitaCorrente);
		this.partecipazioneService.delete(p.getId());
		this.allievoCorrente.getListaPartecipazioni().remove(p);
		return this.prefix + "messaggioCancellazione";
	}
	
	@RequestMapping(value = "/indietroPartecipazione")
	private String annullaPartecipazione(Model model) {
		model.addAttribute("allievo", this.allievoService.findById(this.allievoCorrente.getId()));
		model.addAttribute("listaAttivita", this.partecipazioneService.getListaAttivita(this.allievoCorrente));
		return "allievo/showAllievo";
	}

}