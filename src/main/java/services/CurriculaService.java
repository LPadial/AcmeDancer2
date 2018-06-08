
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.CustomRecord;
import domain.Dancer;
import domain.EndorserRecord;
import domain.PersonalRecord;
import domain.StyleRecord;
import repositories.CurriculaRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculaService {

	// Repository
	@Autowired
	private CurriculaRepository	curriculaRepository;


	//Services

	@Autowired
	private LoginService		loginService;

	@Autowired
	private DancerService		dancerService;

	@Autowired
	private PersonalRecordService personalRecordService;

	@Autowired
	private StyleRecordService styleRecordService;

	@Autowired
	private CustomRecordService customRecordService;

	@Autowired
	private EndorserRecordService endorserRecordService;




	//Constructor
	public CurriculaService() {
		super();
	}

	// CRUD Methods

	public Curricula create() {
		Curricula curricula = new Curricula();

		curricula.setPersonalRecord(new PersonalRecord());
		curricula.setEndorserRecord(new ArrayList<EndorserRecord>());
		curricula.setCustomRecord(new ArrayList<CustomRecord>());
		curricula.setStyleRecord(new ArrayList<StyleRecord>());

		return curricula;
	}



	public List<Curricula> findAll() {
		return curriculaRepository.findAll();
	}

	public List<Curricula> save(List<Curricula> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());

		return curriculaRepository.save(entities);
	}

	public Curricula save(Curricula arg0) {
		Assert.notNull(arg0);

		Curricula saved = curriculaRepository.save(arg0);
		Dancer dancer = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		dancer.getCurriculas().add(saved);

		dancerService.saveEditing(dancer);

		return saved;
	}

	public Curricula createAndSave() {
		Curricula created = create();
		Dancer dancer = dancerService.selectByUserAccountId();

		PersonalRecord default_record = new PersonalRecord();
		default_record.setEmail(dancer.getEmail());
		default_record.setNumWhatsapp(dancer.getPhone());
		default_record.setFullName(String.format("%s %s", dancer.getActorName(), dancer.getSurname()));
		default_record.setLinkedln("https://es.linkedin.com");

		created.setPersonalRecord(personalRecordService.saveEditing(default_record));

		Curricula saved = curriculaRepository.save(created);

		dancer.getCurriculas().add(saved);

		dancerService.saveEditing(dancer);

		return saved;
	}

	public Curricula saveEditing(Curricula curricula) {
		return curriculaRepository.save(curricula);
	}

	public Curricula findOne(Integer id) {
		Assert.notNull(id);
		return curriculaRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return curriculaRepository.exists(id);
	}

	public void delete(Curricula curricula) {
		Assert.notNull(curricula);

		styleRecordService.delete(curricula.getStyleRecord());
		customRecordService.delete(curricula.getCustomRecord());
		endorserRecordService.delete(curricula.getEndorserRecord());
		personalRecordService.delete(curricula.getPersonalRecord());
		
		Dancer dancer = dancerService.selectByUserAccountId();
		dancer.getCurriculas().remove(curricula);
		
		dancerService.saveEditing(dancer);

		curriculaRepository.delete(curricula);

	}

	public void flush() {
		curriculaRepository.flush();
	}
	
	

}
