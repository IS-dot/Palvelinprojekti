package com.example.Juoksuprojekti.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Juoksuprojekti.domain.Run;
import com.example.Juoksuprojekti.domain.RunRepository;
import com.example.Juoksuprojekti.domain.User;
import com.example.Juoksuprojekti.domain.UserRepository;

@Controller
public class JuoksuController {

	@Autowired
	private RunRepository repository;

	@Autowired
	private UserRepository urepository;

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/username", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Long currentUserName(Authentication authentication) {
		String nimi = authentication.getName();
		User kayttis = urepository.findByUsername(nimi);
		System.out.println("ONKO KÄYTTIKSESSÄ MITÄÄN " + kayttis);
		Long kayttisid = kayttis.getUserId();
		// tämä postataan toiseen metodiin

		System.out.println("TÄSSÄ ON USERID: " + kayttisid);

		return kayttisid;

	}

	@RequestMapping(value = { "/", "/runninglist" })
	public String runList(Model model) {
		model.addAttribute("runs", repository.findAllByOrderByPerfDayDesc());
		model.addAttribute("users", urepository.findAll());
		return "runninglist";
	}
	// lisätään adminlist, jonne päästetään vain admin käyttäjä
	// täällä näkyy kaikki kirjat

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = { "/adminlist" })
	public String adminList(Model model, Run run) {
		double totalMatka = totalDistance();
		double kokoMatka = run.getTotalDist();
		if (kokoMatka != totalMatka) {
			kokoMatka = totalMatka;
			run.setTotalDist(kokoMatka);
		}
		System.out.println("mihin tämä run meneen " + run.getTotalDist());
		model.addAttribute("runs", repository.findAllByOrderByPerfDayDesc());
		model.addAttribute("users", urepository.findAll());

		return "adminlist";
	}

	@RequestMapping(value = "/count", method = { RequestMethod.GET, RequestMethod.POST })
	public double totalDistance() {
		List<Run> allRuns = runListRest();

		double matka;
		int i = 0;
		int n = 1;
		matka = allRuns.get(0).getDistance();

		int last = allRuns.size() - 1;

		System.out.println("mikä on matka ensin" + matka);
		for (i = 0; i < last;) {
			matka = matka + allRuns.get(n).getDistance();
			System.out.println("Mikä on matka yhteensä " + matka);
			n++;
			i++;
		}

		System.out.println("MATKA LOPULTA: " + matka);

		return matka;
	}

	// RESTful service to get all runs
	@RequestMapping(value = "/runs", method = RequestMethod.GET)
	public @ResponseBody List<Run> runListRest() {
		return (List<Run>) repository.findAll();
	}

	// RESTful service to get run by id
	@RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Run> findRunRest(@PathVariable("id") Long runId) {
		return repository.findById(runId);
	}

	// RESTful service to get all runs by type
	@RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
	public @ResponseBody List<Run> findTypeRunsRest(@PathVariable String type) {
		return (List<Run>) repository.findAllByType(type);
	}

	// RESTful service to get user by id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<User> findUserRest(@PathVariable("id") Long userId) {
		return urepository.findById(userId);
	}

	@RequestMapping(value = "/add")
	public String addRun(Model model, Authentication authentication) {
		Long nytKayttisId = currentUserName(authentication);
		System.out.println("OLLAAN ADDRUNISSA, toimiiko userid: " + nytKayttisId);
		Run newRun = new Run();
		model.addAttribute("run", newRun);
		model.addAttribute("user", urepository.findAll());

		urepository.findByUserId(nytKayttisId);
		User user = new User(); // ei oo kyllä sinänsä uus käyttäjä vaan vanha
		// mutta luodaan ikään kuin uusi instanssi
		user = urepository.findByUserId(nytKayttisId);

		System.out.println("MIKÄ ON MATKA YHTEENSÄ JOS JOKA LISÄYKSELLÄ LISÄÄ" + user.getDistTogether());

		urepository.save(user); // nyt se kyllä tallentaa sen muuten vaan

		model.addAttribute("user", user);
		System.out.println("TOIMIIKO TÄMÄ EDES TEORIASSA " + user);

		return "addrun";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/adminadd")
	public String adminAddRun(Model model, Authentication authentication) {
		Run newRun = new Run();
		model.addAttribute("run", newRun);
		// admin-puolella valitaan kaikista käyttäjistä
		model.addAttribute("users", urepository.findAll());
		return "adminadd";
	}

//	// RESTful service to get user by username
//	@RequestMapping(value = "/username/{id}", method = RequestMethod.GET)
//	public @ResponseBody User findUsername(@PathVariable("id") String username) {
//		return urepository.findByUsername(username);
//	}
//

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editRun(@PathVariable("id") Long runId, Model model, Authentication authentication) {
		System.out.println("TULLAAN editRuniin" + runId);

		Long nytKayttisId = currentUserName(authentication);
		System.out.println("OLLAAN EDITRUNISSA, toimiiko userid: " + nytKayttisId);
		model.addAttribute("user", urepository.findAll());
//		
		// urepository.save(user);
		urepository.findByUserId(nytKayttisId);
		User user = new User(); // ei oo kyllä sinänsä uus käyttäjä vaan vanha
		// mutta luodaan ikään kuin uusi instanssi
		user = urepository.findByUserId(nytKayttisId);
		// etsitään aiemmin luotu run id:llä
		model.addAttribute("run", repository.findById(runId));

		Optional<Run> thisRun = repository.findById(runId);
		System.out.println("MIkä on thisrun " + thisRun);
		double oldDistance = thisRun.get().getDistance();
		System.out.println("Mikä on distance ennen muokkausta " + oldDistance);
		System.out.println("KOKONAISMATKA ENNEN KUN KOSKETTIIN LASKEMATKAAN " + user.getDistTogether());
		// eli meillä on nyt se vanha matka
		// kerrotaan vanha matka -1 että saadaan vähennettyä se kokonaismatkasta
		// laskematka-metodin avulla
		double fixer = (-1.0);
		double fixOldDistance = fixer * oldDistance;
		// vähennetään kokonaismatkasta vanha matka
		user.laskeMatka(fixOldDistance);
		System.out.println("KOKONAISMATKA KUN NYT EDITOITAVA MATKA ON VÄHENNETTY, MUTTA EI VIELÄ TALLENNETTU UUDESTAAN "
				+ user.getDistTogether());

		model.addAttribute("user", user);

		return "editrun";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/adminedit/{id}", method = RequestMethod.GET)
	public String adminEditRun(@PathVariable("id") Long runId, Model model) {
		System.out.println("TULLAAN admineditRuniin" + runId);

		Optional<Run> thisRun = findRunRest(runId);
		Long thisUserId = thisRun.get().getUser().getUserId();
		Optional<User> thisUser = findUserRest(thisUserId);
		System.out.println("mikä on thisUser nyt " + thisUser);
		Long userIdNow = thisUser.get().getUserId();
		System.out.println("olisko tämä nyt se userid " + userIdNow);

		User user = urepository.findByUserId(userIdNow);
		System.out.println("Admineditrunissa userid on " + thisUser);
		model.addAttribute("run", repository.findById(runId)); // etsitään aiemmin
		// luotu run id:llä

		// model.addAttribute("users", urepository.findAll());
		model.addAttribute("user", user);

		return "adminedit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Run run, User user) {

		repository.save(run);
		// lasketaan lisäys
		// lisäys lasketaan per käyttäjä
		user.laskeMatka(run.getDistance());
		urepository.save(user);

		return "redirect:runninglist";
	}

	@RequestMapping(value = "/adminsave", method = RequestMethod.POST)
	public String adminSave(Run run, User user) {

		repository.save(run);
		// lasketaan lisäys
		// lisäys lasketaan per käyttäjä
		user.laskeMatka(run.getDistance());
		System.out.println("Mikä on userid täällä tallennettaessa ekaa kertaa " + user);
		// täällä on vielä oikein
		urepository.save(user);

		return "redirect:adminlist";
	}

	@RequestMapping(value = "/adminsaveedit", method = { RequestMethod.GET, RequestMethod.POST })
	public String adminSaveEdit(Run run, User user) {

		Long thisId = run.getId();
		Optional<Run> thisRun = findRunRest(thisId);
		double oldeDistance = thisRun.get().getDistance();
		System.out.println("Mikäs matka täällä näkyy " + oldeDistance);
		double fixDistance = (-1.0) * oldeDistance;

		// Long thisId = run.getId();
		// Optional<Run> thisRun = findRunRest(thisId);
		User editUser = thisRun.get().getUser();
		Long editUserId = editUser.getUserId();
		System.out.println("mikä on edituserid " + editUserId);

		user = urepository.findByUserId(editUserId);
		System.out.println("mikä on tallennettu käyttäjä " + user);
		urepository.save(user);
		run.setUser(user);
		user.laskeMatka(fixDistance);
		System.out.println("KOKONAISMATKA SAVEEDITISSÄ ENNEN REPO.SAVERUNIA " + user.getDistTogether());

		repository.save(run);

		System.out.println("matka yhteensä ennen uuden lisäystä " + user.getDistTogether());
		System.out.println("nyt tallennetun juoksun matka " + run.getDistance());

		user.laskeMatka(run.getDistance());

		urepository.save(user);
		return "redirect:adminlist";
	}

	@RequestMapping(value = "/saveedit", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveEdit(Run run, Authentication authentication) {
		Long thisId = run.getId();
		Optional<Run> thisRun = findRunRest(thisId);
		double oldeDistance = thisRun.get().getDistance();
		System.out.println("Mikäs matka täällä näkyy " + oldeDistance);
		double fixDistance = (-1.0) * oldeDistance;

		Long nytKayttisId = currentUserName(authentication);
		System.out.println("OLLAAN SAVEEDITISSÄ, toimiiko userid: " + nytKayttisId);
		urepository.findByUserId(nytKayttisId);
//		User user = new User(); // ei oo kyllä uus käyttäjä vaan vanha
//		user = urepository.findByUserId(nytKayttisId);
		User user = urepository.findByUserId(nytKayttisId);

		System.out.println("TOIMIIKO TÄMÄ EDES TEORIASSA " + user);
		System.out.println("toimiiko nyt userID " + user.getUserId());
		user.laskeMatka(fixDistance);
		System.out.println("KOKONAISMATKA SAVEEDITISSÄ ENNEN REPO.SAVERUNIA " + user.getDistTogether());

		repository.save(run);

		System.out.println("matka yhteensä ennen uuden lisäystä " + user.getDistTogether());
		System.out.println("nyt tallennetun juoksun matka " + run.getDistance());

		user.laskeMatka(run.getDistance());
		// nyt se laskee uuden hinnan vanhojen kertyneiden päälle
		// eli pitäisi jotenkin miinustaa vanha hinta alta pois editissä
		// eli tarvittais varmaan väliluokka jota ei nyt ole
		urepository.save(user);

		return "redirect:runninglist";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long runId, User user, Model model) {

		// Long thisId = run.getId();
		// Long thisId = runId;
		Optional<Run> thisRun = findRunRest(runId);
		System.out.println("MIKÄ ON THISRUN " + thisRun);

		double oldeDistance = thisRun.get().getDistance();
		System.out.println("Mikäs matka täällä näkyy " + oldeDistance);
		double fixDistance = (-1.0) * oldeDistance;

		User editUser = thisRun.get().getUser();
		Long editUserId = editUser.getUserId();
		System.out.println("mikä on edituserid " + editUserId);

		user = urepository.findByUserId(editUserId);
		System.out.println("mikä on tallennettu käyttäjä " + user);
		urepository.save(user);
		thisRun.get().setUser(user);
		user.laskeMatka(fixDistance);

		System.out.println("KOKONAISMATKA DELETESSÄ ENNEN REPO.SAVERUNIA " + user.getDistTogether());

		repository.deleteById(runId);
		return "redirect:../adminlist";
	}

}
