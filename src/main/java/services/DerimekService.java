package services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DerimekRepository;
import security.LoginService;
import domain.Administrator;
import domain.Derimek;

@Service
@Transactional
public class DerimekService {

	//Repository
	@Autowired
	private DerimekRepository	derimekRepository;

	//Service
	@Autowired
	private CourseService		courseService;

	@Autowired
	private LoginService		loginService;

	@Autowired
	private AdministratorService		administratorService;


	//Constructor
	public DerimekService() {
		super();
	}

	public Derimek create() {
		Derimek derimek= new Derimek();

		derimek.setDescription(new String());
		derimek.setMoment(new Date());
		derimek.setScore(0);
		derimek.setTitle(new String());
		derimek.setTicker(nextTicker());
		derimek.setIsCancelled(false);

		return derimek;
	}

	private String nextTicker() {
		final String dictionary = new String("0123456789");

		StringBuilder str = new StringBuilder();
		Random rand = new Random();
		for(int i = 0; i < 2; i++) {
			str.append(dictionary.charAt(rand.nextInt(dictionary.length()))); }
		str.append("-");
		for(int i = 0; i < 5; i++) {
			str.append(dictionary.charAt(rand.nextInt(dictionary.length()))); }

		return str.toString();
	}

	public Derimek save(Derimek derimek) {
		Assert.notNull(derimek);

		Derimek aux = new Derimek();
		Administrator a = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (exists(derimek.getId())) {

			aux = derimekRepository.findOne(derimek.getId());
			aux.setJustification(derimek.getJustification());
			aux.setIsCancelled(true);


		} else {

			aux = derimekRepository.save(derimek);

			a.getDerimeks().add(aux);
			administratorService.save(a);

			return derimekRepository.save(aux);


		}

		return derimekRepository.save(derimek);
	}


	public List<Derimek> save(List<Derimek> arg0) {
		return derimekRepository.save(arg0);
	}

	public Derimek findOne(Integer id) {
		Assert.notNull(id);
		return derimekRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return derimekRepository.exists(id);
	}

	public List<Derimek> findAll() {
		return derimekRepository.findAll();
	}
	
	public void cancel(Derimek q) {
		Assert.notNull(q);
		q.setIsCancelled(true);
		derimekRepository.save(q);
	}




	//other methods

	public void flush() {
		derimekRepository.flush();
	}




}
