package it.uniroma3.controller;

import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.Progettosiw;
import it.uniroma3.controller.validator.AttivitaValidator;
import it.uniroma3.model.Attivita;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.PartecipazioneService;

@Controller
public class AttivitaController {

	@Autowired
	private AttivitaService attivitaService;

	@Autowired
	private PartecipazioneService partecipazioneService;

	@Autowired
	private AttivitaValidator validator;

	final private String prefix = "attivita/";

	private Attivita attivita;

	public String showAttivita(Model model, Attivita attivita) {
		model.addAttribute("attivita", attivita);
		if (this.partecipazioneService.getListaAllievi(attivita) != null)
			model.addAttribute("listaAllievi", this.partecipazioneService.getListaAllievi(attivita));
		return this.prefix + "showAttivita";
	}

	public String attivitaForm() {
		return this.prefix + "attivitaForm";
	}

	@RequestMapping("/inserisciAttivita")
	public String addAttivita(Model model) {
		model.addAttribute("attivita", new Attivita());
		return attivitaForm();
	}

	@RequestMapping(value = "/confermaAttivita", method = RequestMethod.POST)
	public String confermaAttivita(@Valid @ModelAttribute("attivita") Attivita attivita, Model model,
			BindingResult bindingResult) {
		this.validator.validate(attivita, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.attivitaService.uploadParametri(attivita);
			if (this.attivitaService.alreadyExists(attivita)) {
				model.addAttribute("exists", "Attivita esiste gia'");
				return attivitaForm();
			} else {
				this.attivita = attivita;
				this.attivita.setCentroFormazione(Progettosiw.getRsc().getCentroFormazione());
				model.addAttribute("attivita", attivita);
				return this.prefix + "confermaAttivita";
			}
		}
		return attivitaForm();
	}

	@RequestMapping(value = "/saveAttivita", method = RequestMethod.POST)
	public String saveAttivita(Model model) {
		attivita.setCentroFormazione(Progettosiw.getRsc().getCentroFormazione());
		this.attivita.setListaPartecipazioni(new ArrayList<>());
		this.attivitaService.save(this.attivita);
		model.addAttribute("attivita", this.attivita);
		model.addAttribute("listaAllievi", this.attivita.getListaPartecipazioni());
		return showAttivita(model, this.attivita);
	}

	@RequestMapping(value = "/findAttivita")
	public String findAttivita(@RequestParam("descrizione") String descrizione, Model model) {

		if (!descrizione.equals("") && descrizione != null) {
			descrizione = this.attivitaService.uploadString(descrizione);
			Attivita attivita = this.attivitaService.findByDescrizione(descrizione);
			if (attivita == null) {
				model.addAttribute("notexists", "Attivita non esiste");
				return listaAttivita(model);
			} else
				return showAttivita(model, this.attivitaService.findByDescrizione(descrizione));
		}
		model.addAttribute("errorParam", "Inserisci Descrizione");
		return listaAttivita(model);
	}

	@RequestMapping(value = "/findAttivitaId/{id}", method = RequestMethod.GET)
	public String findAttivita(@PathVariable("id") Long id, Model model) {
		return showAttivita(model, this.attivitaService.findById(id));
	}

	@RequestMapping(value = "/modificaAttivita/{id}", method = RequestMethod.GET)
	public String modificaAttivita(@PathVariable("id") Long id, Model model) {
		model.addAttribute("attivita", this.attivitaService.findById(id));
		return this.prefix + "mergeAttivita";
	}

	@RequestMapping(value = "/updateAttivita/{id}", method = RequestMethod.POST)
	public String updateAttivita(@PathVariable("id") Long id, @RequestParam("descrizione") String decrizione,
			@RequestParam("prezzo") Double prezzo,
			@RequestParam("dataAttivita") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataAttivita,
			@RequestParam("oraInizio") @DateTimeFormat(pattern = "HH:mm") Date oraInizio,
			@RequestParam("oraFine") @DateTimeFormat(pattern = "HH:mm") Date oraFine, Model model) {
		Attivita attivita = this.attivitaService.update(this.attivitaService.findById(id), decrizione, prezzo,
				dataAttivita, oraInizio, oraFine);
		this.attivitaService.uploadParametri(attivita);
		this.attivitaService.save(attivita);
		return showAttivita(model, attivita);
	}

	@RequestMapping(value = "/listaAttivita", method = RequestMethod.GET)
	public String listaAttivita(Model model) {
		model.addAttribute("listaAttivita", this.attivitaService.getListaAttivita());
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "attivitaList";
	}

	@RequestMapping(value = "/cancellaAttivita/{id}", method = RequestMethod.GET)
	public String cancelllaAttivita(@PathVariable("id") Long id, Model model) {
		model.addAttribute("attivita", this.attivitaService.findById(id));
		model.addAttribute("listaAllievi",
				this.partecipazioneService.getListaAllievi(this.attivitaService.findById(id)));
		return this.prefix + "confermaCancellazione";
	}

	@RequestMapping(value = "/deleteAttivita/{id}", method = RequestMethod.POST)
	public String deleteAllievo(@PathVariable("id") Long id, Model model) {
		this.partecipazioneService.deleteByAttivita(this.attivitaService.findById(id));
		this.attivitaService.delete(id);
		return this.prefix + "messaggioCancellazione";
	}

}