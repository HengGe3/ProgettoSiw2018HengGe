package it.uniroma3;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import it.uniroma3.model.Responsabile;

//(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Progettosiw {

//	@Autowired
//	private AllievoService allievoService;
//
//	@Autowired
//	private AttivitaService attivitaService;
//
//	@Autowired
//	private ResponsabileService r;
//
//	@Autowired
//	private CentroFormazioneService CentroFormazioneService;
	
	public static Responsabile rsc;

	public static void main(String[] args) {
		SpringApplication.run(Progettosiw.class, args);
	}

	@PostConstruct
	public void init() {

//		Date data = new Date();
//		Allievo allievo = new Allievo();
//		allievo.setNome("Heng");
//		allievo.setCognome("Ge");
//		allievo.setDataNascita(data);
//		allievo.setEmail("geheng@gmail.com");
//		allievo.setTelefono("065432");
//		allievo.setLuogoNascita("Cina");
//		allievoService.save(allievo);
//
//		Responsabile r = new Responsabile();
//		r.setNome("admin");
//		r.setCognome("admin");
//		r.setEmail("admin");
//		r.setUsername("admin");
//		r.setPassword("admin");
//		r.setRole("admin");
//		this.r.save(r);
//
//		CentroFormazione centroFormazione = new CentroFormazione();
//		centroFormazione.setNome("Artistico");
//		centroFormazione.setEmail("artistico@centro.it");
//		centroFormazione.setIndirizzo("Via Arte");
//		centroFormazione.setTelefono("06453453");
//		centroFormazione.setCapienzaMassima(30);
//		this.CentroFormazioneService.save(centroFormazione);
//
//		r = new Responsabile();
//		r.setNome("arte");
//		r.setCognome("arte");
//		r.setEmail("arte");
//		r.setUsername("rArte");
//		r.setPassword("arte");
//		r.setRole("responsabile");
//		r.setCentroFormazione(centroFormazione);
//		this.r.save(r);
//
//		Attivita attivita = new Attivita();
//		attivita.setDescrizione("Pittura");
//		attivita.setPrezzo(70);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		attivita = new Attivita();
//		attivita.setDescrizione("Canto");
//		attivita.setPrezzo(170);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		attivita = new Attivita();
//		attivita.setDescrizione("Danza");
//		attivita.setPrezzo(140);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		centroFormazione = new CentroFormazione();
//		centroFormazione.setNome("Sportivo");
//		centroFormazione.setEmail("sportivo@centro.it");
//		centroFormazione.setIndirizzo("Via sport");
//		centroFormazione.setTelefono("0678653");
//		centroFormazione.setCapienzaMassima(300);
//		this.CentroFormazioneService.save(centroFormazione);
//
//		r = new Responsabile();
//		r.setNome("arte");
//		r.setCognome("arte");
//		r.setEmail("arte");
//		r.setUsername("rSport");
//		r.setPassword("sport");
//		r.setRole("responsabile");
//		r.setCentroFormazione(centroFormazione);
//		this.r.save(r);
//
//		attivita = new Attivita();
//		attivita.setDescrizione("Calcio");
//		attivita.setPrezzo(150);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		attivita = new Attivita();
//		attivita.setDescrizione("Tennis");
//		attivita.setPrezzo(120);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		attivita = new Attivita();
//		attivita.setDescrizione("Pallavolo");
//		attivita.setPrezzo(100);
//		attivita.setDataAttivita(new Date());
//		attivita.setCentroFormazione(centroFormazione);
//		attivitaService.save(attivita);
//
//		centroFormazione = new CentroFormazione();
//		centroFormazione.setNome("Sportivo");
//		centroFormazione.setEmail("sportivo@centro.it");
//		centroFormazione.setIndirizzo("Via sport");
//		centroFormazione.setTelefono("0678653");
//		centroFormazione.setCapienzaMassima(300);
//		this.CentroFormazioneService.save(centroFormazione);

	}
	
	public static Responsabile getRsc() {
		return rsc;
	}

	public static void setRsc(Responsabile rsc) {
		Progettosiw.rsc = rsc;
	}

}
