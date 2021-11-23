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

//	@RequestMapping(value = "/username", method = RequestMethod.GET)
//	@ResponseBody
//	public String currentUserNameSimple(HttpServletRequest request) {
//		Principal principal = request.getUserPrincipal();
//		return principal.getName();
//	}
	// tää on nyt rest-metodi
	// miten saan tän metodin joko kutsuttua siinä add-metodissa
	// tää ohjattua suoraan thymeleafiin
	// paikallisia muuttujia nääs
	@RequestMapping(value = "/username", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public Long currentUserName(Authentication authentication) {
		String nimi = authentication.getName();
		User kayttis = urepository.findByUsername(nimi);
		System.out.println("ONKO KÄYTTIKSESSÄ MITÄÄN " + kayttis);
		Long kayttisid = kayttis.getUserId();
		// eli tämä pitäis oikeestaan postata toiseen metodiin
		// mites helvetissä se onnistuu
		// voiko toi return olla periaatteessa mitä vaan?
		// esim redirect:addbook? tuleeko sen keräämät tiedot sillon mukana
		// vai voiko se olla return: urepository.findById(id);
		// munhan tarvitsee syöttää se id vaan sinne thymeleafiin
		System.out.println("TÄSSÄ ON USERID: " + kayttisid);
		// redirectAttributes.addAttribute("id", "idValue");
		// redirectAttributes.addFlashAttribute("fa", faValue);
		return kayttisid;
		// voiko tän laittaa sinne user-entityyn?
		// return id;
	}

	@RequestMapping(value = { "/", "/runninglist" })
	public String bookList(Model model) {
		model.addAttribute("runs", repository.findAll());
		model.addAttribute("users", urepository.findAll());
		return "runninglist";
	}
	// lisätään adminbooklist, jonne ohjataan vain admin käyttäjä
	// täällä näkyy kaikki kirjat

//	@PreAuthorize("hasAuthority('ADMIN')")
//	@RequestMapping(value = "/adminbooklist")
//	public String adminBookList(Model model) {
//		model.addAttribute("books", repository.findAll());
//		return "booklist";
//	}

	// RESTful service to get all books
	@RequestMapping(value = "/runs", method = RequestMethod.GET)
	public @ResponseBody List<Run> runListRest() {
		return (List<Run>) repository.findAll();
	}

	// RESTful service to get book by id
	@RequestMapping(value = "/run/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Run> findRunRest(@PathVariable("id") Long runId) {
		return repository.findById(runId);
	}

	// RESTful service to get all books by one author
	@RequestMapping(value = "/type/{type}", method = RequestMethod.GET)
	public @ResponseBody List<Run> findTypeRunsRest(@PathVariable String type) {
		return (List<Run>) repository.findAllByType(type);
	}

	// RESTful service to get user by id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<User> findUserRest(@PathVariable("id") Long userId) {
		return urepository.findById(userId);
	}

	// http://localhost:8080/api/users/3/books --> get all books with userId three
	// okay so what do I do with this info
	//

	@RequestMapping(value = "/add")
	public String addBook(Model model) {
		model.addAttribute("run", new Run());
		// model.addAttribute("categories", crepository.findAll());
		model.addAttribute("users", urepository.findAll());
		return "addrun";
	}

//	@RequestMapping(value = "/add")
//	public String addUserBook(Model model, Authentication authentication) {
//		Long nytKayttisId = currentUserName(authentication);
//		System.out.println("OLLAAN ADDBOOKISSA, toimiiko userid: " + nytKayttisId);
//		model.addAttribute("book", new Book());
//		// model.addAttribute("categories", crepository.findAll());
//		// model.addAttribute("user", urepository.findAll());
//		urepository.findByUserId(nytKayttisId);
//		User user = new User(); // ei oo kyllä uus käyttäjä vaan vanha
//		user = urepository.findByUserId(nytKayttisId); // päivittääkö tämä tän "uuden" vanhaksi tällä id:llä
//		// sit pitäis vielä työntää tämä meidän user sinne kirjaan
//		// urepository.save(user); // nyt se kyllä tallentaa sen muuten vaan, ei tää
//		// liity nyt mitenkään siihen
//		// entityyn mitä ollaan luomassa
//		model.addAttribute("user", user);
//		urepository.save(user);
//		System.out.println("TOIMIIKO TÄMÄ EDES TEORIASSA " + user);
//		// ei tämä kyllä näin toimi
//		return "adduserbook";
//	}

//	// RESTful service to get user by username
//	@RequestMapping(value = "/username/{id}", method = RequestMethod.GET)
//	public @ResponseBody User findUsername(@PathVariable("id") String username) {
//		return urepository.findByUsername(username);
//	}
//
//	@RequestMapping(value = "/add/{id}", method = { RequestMethod.GET, RequestMethod.POST })
//	public String addBookUser(@PathVariable("id") Long id, Model model, Authentication authentication) {
//		// Long currentUserId = currentUserName(Authentication authentication);
//		String nimi = authentication.getName();
//		User kayttis = urepository.findByUsername(nimi);
//		Long kayttisid = kayttis.getUserId();
//		System.out.println("TULLAAN addBookiin userId valmiina " + kayttisid);
//		// Optional<Book> book = repository.findById(bookId);
//		model.addAttribute("book", new Book());
//
//		model.addAttribute("user", urepository.findById(kayttisid)); // tarvittais ikään kuin add id
//		// ei oo mitään löydettävää tällä id:llä koska se kirja luodaan tyhjänä tossa
//		// edellisellä rivillä
//		// tai enemmänki kyllä se löytää userid:n, mutta se ei lisää sitä pelkästään
//		// tällä
//		// se pitää saada tallennettua suoraan siihen uuteen kirjaan
//
//		model.addAttribute("categories", crepository.findAll());
//		return "adduserbook";
//	}

//	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//	public String editBook(@PathVariable("id") Long bookId, Model model) {
//		System.out.println("TULLAAN editBookiin" + bookId);
//		// Optional<Book> book = repository.findById(bookId);
//		model.addAttribute("book", repository.findById(bookId)); // etsitään aiemmin luotu kirja id:llä
//		model.addAttribute("users", urepository.findAll());
//		model.addAttribute("categories", crepository.findAll());
//		return "editbook";
//	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Run run) {
		repository.save(run);
		return "redirect:runninglist";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long runId, Model model) {
		repository.deleteById(runId);
		return "redirect:../runninglist";
	}

//	@GetMapping("/index")
//	public String books(Model model, @RequestParam(name = "book", required = false) String book) {
//		model.addAttribute("book", book);
//		return "index";
//	}

}