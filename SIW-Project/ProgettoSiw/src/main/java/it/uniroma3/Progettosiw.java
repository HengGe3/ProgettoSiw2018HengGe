package it.uniroma3;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.CentroFormazione;
import it.uniroma3.model.Partecipazione;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroFormazioneService;
import it.uniroma3.service.PartecipazioneService;
import it.uniroma3.service.ResponsabileService;

//(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Progettosiw {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private AttivitaService attivitaService;
	
	@Autowired
	private PartecipazioneService partecipazioneService;

	@Autowired
	private ResponsabileService responsabileService;

	@Autowired
	private CentroFormazioneService CentroFormazioneService;
	
	public static Responsabile rsc;

	public static void main(String[] args) {
		SpringApplication.run(Progettosiw.class, args);
	}

	@PostConstruct
	public void init() {
		
		CentroFormazione cc = new CentroFormazione();
		cc.setNome("Culturale");
		cc.setEmail("culturale@centro.it");
		cc.setIndirizzo("Via Cultura");
		cc.setTelefono("06453453");
		cc.setCapienzaMassima(10);
		this.CentroFormazioneService.save(cc);
		
		CentroFormazione cs = new CentroFormazione();
		cs.setNome("Sportivo");
		cs.setEmail("sportivo@centro.it");
		cs.setIndirizzo("Via sport");
		cs.setTelefono("0678653");
		cs.setCapienzaMassima(200);
		this.CentroFormazioneService.save(cs);
		
		CentroFormazione ca = new CentroFormazione();
		ca.setNome("Artistico");
		ca.setEmail("sportivo@centro.it");
		ca.setIndirizzo("Via sport");
		ca.setTelefono("0678653");
		ca.setCapienzaMassima(40);
		this.CentroFormazioneService.save(ca);
		
		Responsabile r = new Responsabile();
		r.setNome("Cultura");
		r.setCognome("cultura");
		r.setEmail("cultura");
		r.setUsername("cultura");
		r.setPassword("cultura");
		r.setRole("responsabile");
		r.setCentroFormazione(cc);
		this.responsabileService.save(r);
		
		r = new Responsabile();
		r.setNome("sport");
		r.setCognome("sport");
		r.setEmail("sport");
		r.setUsername("sport");
		r.setPassword("sport");
		r.setRole("responsabile");
		r.setCentroFormazione(cs);
		this.responsabileService.save(r);
		
		r = new Responsabile();
		r.setNome("arte");
		r.setCognome("arte");
		r.setEmail("arte");
		r.setUsername("arte");
		r.setPassword("arte");
		r.setRole("responsabile");
		r.setCentroFormazione(ca);
		this.responsabileService.save(r);
		
		r = new Responsabile();
		r.setNome("admin");
		r.setCognome("admin");
		r.setEmail("admin");
		r.setUsername("admin");
		r.setPassword("admin");
		r.setRole("admin");
		this.responsabileService.save(r);
		
		Attivita robot = new Attivita();
		robot.setDescrizione("Robot, Umaninoidi, E Cyborg");
		robot.setPrezzo(20);
		robot.setDataAttivita(new Date());
		robot.setCentroFormazione(cc);
		attivitaService.save(robot);
		
		Attivita incrontro = new Attivita();
		incrontro.setDescrizione("La Potenza dell' Incontro");
		incrontro.setPrezzo(10);
		incrontro.setDataAttivita(new Date());
		incrontro.setCentroFormazione(cc);
		attivitaService.save(incrontro);
		
		Attivita canto = new Attivita();
		canto.setDescrizione("Canto");
		canto.setPrezzo(30);
		canto.setDataAttivita(new Date());
		canto.setCentroFormazione(ca);
		attivitaService.save(canto);
		
		Attivita danza = new Attivita();
		danza.setDescrizione("Danza");
		danza.setPrezzo(20);
		danza.setDataAttivita(new Date());
		danza.setCentroFormazione(ca);
		attivitaService.save(danza);
		
		Attivita pittura = new Attivita();
		pittura.setDescrizione("Pittura");
		pittura.setPrezzo(20);
		pittura.setDataAttivita(new Date());
		pittura.setCentroFormazione(ca);
		attivitaService.save(pittura);
		
		Attivita calcio = new Attivita();
		calcio.setDescrizione("Calcio");
		calcio.setPrezzo(25);
		calcio.setDataAttivita(new Date());
		calcio.setCentroFormazione(cs);
		attivitaService.save(calcio);
		
		Attivita tennis = new Attivita();
		tennis.setDescrizione("Tennis");
		tennis.setPrezzo(40);
		tennis.setDataAttivita(new Date());
		tennis.setCentroFormazione(cs);
		attivitaService.save(tennis);
		
		Attivita pallavolo = new Attivita();
		pallavolo.setDescrizione("Pallavolo");
		pallavolo.setPrezzo(20);
		pallavolo.setDataAttivita(new Date());
		pallavolo.setCentroFormazione(cs);
		attivitaService.save(pallavolo);
		
		Allievo allievoH = new Allievo();
		allievoH.setNome("Heng");
		allievoH.setCognome("Ge");
		allievoH.setDataNascita(new Date());
		allievoH.setEmail("geheng@gmail.com");
		allievoH.setTelefono("065432");
		allievoH.setLuogoNascita("Cina");
		allievoH.setImportoDovuto(60);
		allievoService.save(allievoH);
		
		Allievo allievoD = new Allievo();
		allievoD.setNome("Davide");
		allievoD.setCognome("Vergari");
		allievoD.setDataNascita(new Date());
		allievoD.setEmail("davidevergari@gmail.com");
		allievoD.setTelefono("06565324");
		allievoD.setLuogoNascita("Roma");
		allievoD.setImportoDovuto(30);
		allievoService.save(allievoD);
		
		Allievo allievoF = new Allievo();
		allievoF.setNome("Francesca");
		allievoF.setCognome("Dorazio");
		allievoF.setDataNascita(new Date());
		allievoF.setEmail("francescadorazio@gmail.com");
		allievoF.setTelefono("0687654353");
		allievoF.setLuogoNascita("Roma");
		allievoF.setImportoDovuto(30);
		allievoService.save(allievoF);
		
		Partecipazione partecipazione = new Partecipazione();
		partecipazione.setAllievo(allievoH);
		partecipazione.setAttivita(robot);
		this.partecipazioneService.save(partecipazione);
		
		partecipazione = new Partecipazione();
		partecipazione.setAllievo(allievoH);
		partecipazione.setAttivita(tennis);
		this.partecipazioneService.save(partecipazione);
		
		partecipazione = new Partecipazione();
		partecipazione.setAllievo(allievoD);
		partecipazione.setAttivita(pittura);
		this.partecipazioneService.save(partecipazione);
		
		partecipazione = new Partecipazione();
		partecipazione.setAllievo(allievoD);
		partecipazione.setAttivita(incrontro);
		this.partecipazioneService.save(partecipazione);
		
		partecipazione = new Partecipazione();
		partecipazione.setAllievo(allievoF);
		partecipazione.setAttivita(canto);
		this.partecipazioneService.save(partecipazione);

	}
	
	public static Responsabile getRsc() {
		return rsc;
	}

	public static void setRsc(Responsabile rsc) {
		Progettosiw.rsc = rsc;
	}

}
