
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.Dancer;
import domain.PersonalRecord;
import repositories.PersonalRecordRepository;
import security.LoginService;

@Service
@Transactional
public class PersonalRecordService {

	// Repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//Services
	@Autowired
	private CurriculaService			curriculaService;

	@Autowired
	private LoginService				loginService;


	//Constructor
	public PersonalRecordService() {
		super();
	}
	// CRUD Methods

	public PersonalRecord create() {
		PersonalRecord personalRecord = new PersonalRecord();
		personalRecord.setFullName(new String());
		personalRecord.setEmail(new String());
		personalRecord.setNumWhatsapp(new String());
		personalRecord.setLinkedln(new String());

		return personalRecord;
	}

	public void delete(PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		personalRecordRepository.delete(personalRecord);
	}
	
	

	public List<PersonalRecord> findAll() {
		return personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(Integer id) {
		Assert.notNull(id);
		return personalRecordRepository.findOne(id);
	}

	public PersonalRecord save(PersonalRecord personalRecord) {
		PersonalRecord aux = new PersonalRecord();

		if (exists(personalRecord.getId())) {
			aux = personalRecordRepository.findOne(personalRecord.getId());
			aux.setFullName(personalRecord.getFullName());
			aux.setEmail(personalRecord.getEmail());
			aux.setNumWhatsapp(personalRecord.getNumWhatsapp());
			aux.setLinkedln(personalRecord.getLinkedln());
			return personalRecordRepository.save(aux);
		} else {
			aux = personalRecordRepository.save(personalRecord);
			Dancer man = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Curricula> curriculas = man.getCurriculas();
			for (Curricula c : curriculas) {
				if (c.getPersonalRecord().getId() == personalRecord.getId()) {
					c.setPersonalRecord(aux);
					curriculaService.save(c);
				}
			}

		}

		return aux;
	}
	
	public PersonalRecord save(PersonalRecord arg0, int curricula_id) {
		Assert.notNull(arg0);
		
		Curricula curricula = curriculaService.findOne(curricula_id);
		PersonalRecord saved = personalRecordRepository.save(arg0);
		
		curricula.setPersonalRecord(saved);
		
		curriculaService.save(curricula);
		
		return saved;
	}
	
	public PersonalRecord saveEditing(PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		
		return personalRecordRepository.save(personalRecord);
	}

	
	public boolean exists(Integer id) {
		Assert.notNull(id);
		return personalRecordRepository.exists(id);
	}
	
	public boolean dancerContainsPersonalRecord(Dancer dancer,PersonalRecord pr){
		boolean res=false;
		List<Curricula> cur = dancer.getCurriculas();
		for(Curricula c:cur){
			if(c.getPersonalRecord().equals(pr)){
				res=true;
			}
		}
		return res;
	}
	
	public PersonalRecord clone(PersonalRecord p) {
		PersonalRecord res = new PersonalRecord();
		res.setNumWhatsapp(new String(p.getNumWhatsapp()));
		res.setEmail(new String(p.getEmail()));
		res.setFullName(new String(p.getFullName()));
		res.setLinkedln(new String(p.getLinkedln()));

		return personalRecordRepository.save(res);
	}


}
