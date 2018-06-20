package it.uniroma3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.Progettosiw;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.ResponsabileService;

@Controller
public class MainController {

	@Autowired
	private ResponsabileService r;

	@RequestMapping(value = { "/", "/home", "/index" })
	public String home() {
		return "index";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		if (Progettosiw.getRsc() != null) {
			Progettosiw.setRsc(null);
			model.addAttribute("logout", "Fatto Logout");
			return "index";
		}
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login2(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		if (!username.equals("") || !password.equals("")) {
			Optional<Responsabile> responsabile = this.r.findByUsernameAndPassword(username, password);
			if (responsabile == null) {
				model.addAttribute("noEsiste", "Dati Errati");
				return "index";
			} else {
				Progettosiw.setRsc(responsabile.get());
				return homeGestione(model);
			}
		}
		model.addAttribute("errorParam", "Inserisci Dati");
		return "index";
	}

	@RequestMapping(value = "/homeGestione", method = RequestMethod.GET)
	public String homeGestione(Model model) {
		model.addAttribute("responsabile", Progettosiw.getRsc());
		if (Progettosiw.getRsc().getRole().equals("admin"))
			return "amministrazione/amministrazione";
		return "listaCentri/centro" + Progettosiw.getRsc().getCentroFormazione().getNome();
	}

	@RequestMapping("/contatti")
	public String contatti() {
		return "contatti";
	}

}
