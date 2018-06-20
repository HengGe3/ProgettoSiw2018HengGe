package it.uniroma3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.Progettosiw;
import it.uniroma3.model.CentroFormazione;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroFormazioneService;

@Controller
public class CentroFormazioneController {

	@Autowired
	private CentroFormazioneService centroFormazioneService;

	final private String prefix = "centroFormazione/";

	// Search CentroFormazione tramite ID
	@RequestMapping(value = "/findCentroFormazioneId/{id}", method = RequestMethod.GET)
	public String findCentroFormazione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("centroFormazione", this.centroFormazioneService.findById(id));
		model.addAttribute("listaAttivita", this.centroFormazioneService.findById(id).getListaAttivita());
		return this.prefix + "showCentroFormazione";
	}

	@RequestMapping(value = "/findCentroFormazione", method = RequestMethod.GET)
	public String cercaCentroFormazione(@RequestParam("nome") String nome, Model model) {

		if (!nome.equals("") && nome != null) {

			CentroFormazione centroFormazione = this.centroFormazioneService
					.findByNome(this.centroFormazioneService.uploadString(nome));

			if (centroFormazione == null) {
				model.addAttribute("notexists", "Centro Formazione non esiste");
				return this.prefix + "findCentroFormazione";
			} else {
				model.addAttribute("centroFormazione", centroFormazione);
				model.addAttribute("listaAttivita", centroFormazione.getListaAttivita());
				return this.prefix + "showCentroFormazione";
			}
		}
		model.addAttribute("errorParam", "Inserisci Nome");
		return this.prefix + "findCentroFormazione";
	}

	@RequestMapping("/homeCentroFormazione")
	public String homeCentroFormazione(Model model) {
		model.addAttribute("responsabile", Progettosiw.rsc);
		model.addAttribute("listaCentriFormazione", this.centroFormazioneService.findAll());
		return this.prefix + "gestioneCentriFormazione";
	}

	// Ricerca CentroFormazione metodo Supporto
	@RequestMapping("/cercaCentroFormazione")
	public String cercaCentroFormazione() {
		return this.prefix + "findCentroFormazione";
	}

	// Modifica CentroFormazione tramite id Supporto
	@RequestMapping(value = "/modificaCentroFormazione/{id}", method = RequestMethod.GET)
	public String modificaCentroFormazione(@PathVariable("id") Long id, Model model) {
		model.addAttribute("centroFormazione", this.centroFormazioneService.findById(id));
		return this.prefix + "mergeCentroFormazione";
	}

	// Update CentroFormazione tramite Id
	@RequestMapping(value = "/updateCentroFormazione/{id}", method = RequestMethod.POST)
	public String updateCentroFormazione(@PathVariable("id") Long id, @RequestParam("nome") String nome,
			@RequestParam("indirizzo") String indirizzo, @RequestParam("email") String email,
			@RequestParam("telefono") String telefono, @RequestParam("capienzaMassima") int capienzaMassima,
			Model model) {
		CentroFormazione CentroFormazione = this.centroFormazioneService
				.update(this.centroFormazioneService.findById(id), nome, indirizzo, email, telefono, capienzaMassima);
		this.centroFormazioneService.uploadParametri(CentroFormazione);
		this.centroFormazioneService.save(CentroFormazione);
		model.addAttribute("centroFormazione", CentroFormazione);
		model.addAttribute("", this.centroFormazioneService.findById(id).getListaAttivita());
		return this.prefix + "showCentroFormazione";
	}

	// // Delete CentroFormazione tramite Id
	// @RequestMapping(value = "/admin/cancellaCentroFormazione/{id}", method =
	// RequestMethod.GET)
	// public String cancelllaCentroFormazione(@PathVariable("id") Long id, Model
	// model) {
	// this.centroFormazioneService.delete(id);
	// return "centroFormazione/deleteCentroFormazione";
	// }
	// // Persistence CentroFormazione
	// @RequestMapping(value = "/admin/saveCentroFormazione", method =
	// RequestMethod.POST)
	// public String nuovoCentroFormazione(@Valid
	// @ModelAttribute("centroFormazione") CentroFormazione centroFormazione,
	// Model model, BindingResult bindingResult) {
	//
	// this.validator.validate(centroFormazione, bindingResult);
	// if (!bindingResult.hasErrors()) {
	// this.centroFormazioneService.uploadParametri(centroFormazione);
	// if (this.centroFormazioneService.alreadyExists(centroFormazione)) {
	// model.addAttribute("exists", "CentroFormazione esiste gia'");
	// return "centroFormazione/centroFormazioneForm";
	// } else {
	//
	// this.centroFormazioneService.save(centroFormazione);
	// model.addAttribute("centroFormazioneTrovato", centroFormazione);
	// return "centroFormazione/showCentroFormazione";
	// }
	// }
	// return "centroFormazioneForm";
	// }
	// // Setta CentroFormazione
	// @RequestMapping("/settaCentroFormazione")
	// public String settaCentriFormazione(Model model) {
	// model.addAttribute("listaCentriFormazione",
	// this.centroFormazioneService.findAll());
	// return "settaCentroFormazione";
	// }
	//
	// // Settare CentroFormazione tramite ID
	// @RequestMapping(value = "/setCentroFormazioneId/{id}", method =
	// RequestMethod.GET)
	// public String settaCentroFormazione(@PathVariable("id") Long id, Model model)
	// {
	// this.setCentroFormazione(this.centroFormazioneService.findById(id));
	// model.addAttribute("centroFormazioneScelto",
	// this.getCentroFormazione().getNome());
	// return "/listaCentri/" + this.centroFormazione.getNome().toString();
	// }
}