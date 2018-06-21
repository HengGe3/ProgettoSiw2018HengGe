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
import it.uniroma3.controller.validator.AllievoValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Partecipazione;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.PartecipazioneService;

@Controller
public class AllievoController {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private PartecipazioneService partecipazioneService;

	@Autowired
	private AllievoValidator validator;

	final private String prefix = "allievo/";

	private Allievo allievo;

	public String showAllievo(Model model, Allievo allievo) {
		this.allievoService.aggiornaImporto(allievo);
		model.addAttribute("responsabile", Progettosiw.getRsc());
		model.addAttribute("allievo", this.allievoService.findById(allievo.getId()));
		model.addAttribute("listaAttivita", this.partecipazioneService.getListaAttivita(allievo));
		return this.prefix + "showAllievo";
	}

	public String allievoForm(Model model) {
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "allievoForm";
	}

	// Inserisci Allievo metodo Supporto
	@RequestMapping("/inserisciAllievo")
	public String inserisciAllievo(Model model) {
		model.addAttribute("responsabile", Progettosiw.getRsc());
		model.addAttribute("allievo", new Allievo());
		return allievoForm(model);
	}
	
	// Persistence Allievo
	@RequestMapping(value = "/confermaAllievo", method = RequestMethod.POST)
	public String nuovoAllievo(@Valid @ModelAttribute("allievo") Allievo allievo, Model model,
			BindingResult bindingResult) {
		this.validator.validate(allievo, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.allievoService.uploadParametri(allievo);
			if (this.allievoService.alreadyExists(allievo)) {
				model.addAttribute("exists", "Allievo esiste gia'");
				return allievoForm(model);
			} else {
				this.allievo = allievo;
				model.addAttribute("allievo", allievo);
				return this.prefix + "confermaAllievo";
			}
		}
		return allievoForm(model);
	}

	@RequestMapping(value = "/saveAllievo", method = RequestMethod.POST)
	public String saveAllievo(Model model) {
		allievo.setListaPartecipazioni(new ArrayList<>());
		this.allievoService.save(this.allievo);
		model.addAttribute("allievo", this.allievo);
		model.addAttribute("listaAttivita", allievo.getListaPartecipazioni());
		return showAllievo(model, this.allievo);
	}

	// Search Allievo Email
	@RequestMapping("/findAllievo")
	public String findAllievo(@RequestParam("email") String email, Model model) {
		if (!email.equals("") && email != null) {
			Allievo allievo = this.allievoService.findByEmail(email);
			if (allievo == null) {
				model.addAttribute("notexists", "Allievo non esiste");
				return listaAllievi(model);
			} else
				return showAllievo(model, allievo);
		}
		model.addAttribute("errorParam", "Inserisci Email");
		return listaAllievi(model);
	}

	// Search Allievo tramite ID
	@RequestMapping(value = "/findAllievoId/{id}", method = RequestMethod.GET)
	public String findAllievo(@PathVariable("id") Long id, Model model) {
		return showAllievo(model, this.allievoService.findById(id));
	}

	// Modifica Allievo tramite id Supporto
	@RequestMapping(value = "/modificaAllievo/{id}", method = RequestMethod.GET)
	public String modificaAllievo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allievo", this.allievoService.findById(id));
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "mergeAllievo";
	}

	// Update Allievo tramite Id
	@RequestMapping(value = "/updateAllievo/{id}", method = RequestMethod.POST)
	public String updateAllievo(@PathVariable("id") Long id, @RequestParam("nome") String nome,
			@RequestParam("cognome") String cognome, @RequestParam("email") String email,
			@RequestParam("dataNascita") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataNascita,
			@RequestParam("telefono") String telefono, @RequestParam("luogoNascita") String luogoNascita, Model model) {
		Allievo allievo = this.allievoService.update(this.allievoService.findById(id), nome, cognome, email,
				dataNascita, telefono, luogoNascita);
		this.allievoService.uploadParametri(allievo);
		this.allievoService.save(allievo);
		return showAllievo(model, allievo);
	}

	// Mostra Lista Allievi
	@RequestMapping("/listaAllievi")
	public String listaAllievi(Model model) {
		model.addAttribute("listaAllievi", this.allievoService.getListaAllieviCentro());
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "allievoList";
	}

	@RequestMapping(value = "/cancellaAllievo/{id}", method = RequestMethod.GET)
	public String cancellaAllievo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allievo", this.allievoService.findById(id));
		model.addAttribute("listaAttivita",
				this.partecipazioneService.getListaAttivita(this.allievoService.findById(id)));
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "confermaCancellazione";
	}

	@RequestMapping(value = "/deleteAllievo/{id}", method = RequestMethod.POST)
	public String deleteAllievo(@PathVariable("id") Long id, Model model) {
		this.partecipazioneService.deleteByAllievo(this.allievoService.findById(id));
		this.allievoService.delete(id);
		model.addAttribute("responsabile", Progettosiw.getRsc());
		return this.prefix + "messaggioCancellazione";
	}

}