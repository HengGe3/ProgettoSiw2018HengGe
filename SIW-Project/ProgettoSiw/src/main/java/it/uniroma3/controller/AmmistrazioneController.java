package it.uniroma3.controller;

import java.util.Set;

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
import it.uniroma3.service.CentroFormazioneService;
import it.uniroma3.service.PartecipazioneService;

@Controller
public class AmmistrazioneController {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private PartecipazioneService partecipazioneService;

	@Autowired
	private CentroFormazioneService cfs;

	final private String prefix = "amministrazione/";

	@RequestMapping(value = "/statistiche", method = RequestMethod.GET)
	public String statistiche(Model model) {
		model.addAttribute("partecipantiCentri", this.cfs.numeroAllieviCentri());
//		model.addAttribute("partecipantiAttivita", this.cfs.numeroAllieviAttivita());
		model.addAttribute("ricaviCentro", this.cfs.ricaviCentri());
		return this.prefix + "statistiche";
	}

	@RequestMapping(value = "/pagamento", method = RequestMethod.GET)
	public String pagamento(Model model) {
		model.addAttribute("listaAllievi", this.allievoService.getListaAllieviNonPaganti());
		return this.prefix + "pagamento";
	}

	@RequestMapping(value = "/pagaAllievoId/{id}", method = RequestMethod.GET)
	public String pagamento(@PathVariable("id") Long id, Model model) {
		Allievo allievo = this.allievoService.findById(id);
		Set<Attivita> listaAttivita = this.partecipazioneService.getListaAttivitaNonPagata(allievo);
		this.allievoService.aggiornaImporto(this.allievoService.findById(id));
		model.addAttribute("allievo", this.allievoService.findById(id));
		model.addAttribute("listaAttivita", listaAttivita);
		return this.prefix + "pagaAllievo";
	}

	@RequestMapping(value = "/effettuaPagamento/{id}", method = RequestMethod.POST)
	public String effettuaPagamento(@PathVariable("id") Long id, Model model) {
		if (this.allievoService.findById(id).getImportoDovuto() > 0) {
			for (Partecipazione p : this.allievoService.findById(id).getListaPartecipazione())
				p.setPagato(true);
			this.allievoService.aggiornaImporto(this.allievoService.findById(id));
			model.addAttribute("pagato", "Pagamento effettuato con successo");
			return this.prefix + "pagamentoEffettuato";
		}
		model.addAttribute("nonPagato", "L' Allievo aveva gia pagato tutto");
		return this.prefix + "pagamentoEffettuato";
	}

}
