
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
import repositories.CustomRecordRepository;
import security.LoginService;

@Service
@Transactional
public class CustomRecordService {

	//Repository
	@Autowired
	private CustomRecordRepository	customRecordRepository;

	//Service

	@Autowired
	private LoginService			loginService;

	@Autowired
	private CurriculaService		curriculaService;


	//Constructor
	public CustomRecordService() {
		super();
	}

	// CRUD Methods
	public CustomRecord create() {
		CustomRecord customRecord = new CustomRecord();
		customRecord.setTitle(new String());
		customRecord.setText(new String());
		customRecord.setLinks(new ArrayList<String>());

		return customRecord;
	}

	public CustomRecord save(CustomRecord customRecord) {

		CustomRecord aux = new CustomRecord();

		if (exists(customRecord.getId())) {
			aux = findOne(customRecord.getId());
			aux.setTitle(customRecord.getTitle());
			aux.setText(customRecord.getText());
			aux.setLinks(customRecord.getLinks());

			return customRecordRepository.save(aux);
		} else {
			aux = customRecordRepository.save(customRecord);
			Dancer man = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Curricula> curriculas = man.getCurriculas();
			for (Curricula c : curriculas) {
				if (!c.getCustomRecord().contains(customRecord)) {
					c.getCustomRecord().add(aux);
					curriculaService.save(c);
				}
			}

		}
		return aux;
	}

	public CustomRecord findOne(Integer id) {
		Assert.notNull(id);
		return customRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return customRecordRepository.exists(id);
	}

	public void delete(CustomRecord customRecord) {
		Assert.notNull(customRecord);
		customRecordRepository.delete(customRecord);
	}

	public List<CustomRecord> findAll() {
		return customRecordRepository.findAll();
	}

	public CustomRecord save(CustomRecord arg0, int curricula_id) {
		Assert.notNull(arg0);

		Curricula curricula = curriculaService.findOne(curricula_id);
		CustomRecord customRecord = customRecordRepository.save(arg0);

		List<CustomRecord> customRecords = curricula.getCustomRecord();
		customRecords.add(customRecord);
		curricula.setCustomRecord(customRecords);

		curriculaService.saveEditing(curricula);

		return customRecord;
	}

	public CustomRecord saveEditing(CustomRecord record) {
		return customRecordRepository.save(record);
	}

	public void delete(CustomRecord entity, int curricula_id) {
		Assert.notNull(entity);

		Curricula curricula = curriculaService.findOne(curricula_id);
		curricula.getCustomRecord().remove(entity);
		curriculaService.saveEditing(curricula);

		customRecordRepository.delete(entity);
	}

	public void delete(Iterable<CustomRecord> entities) {
		Assert.notNull(entities);
		customRecordRepository.delete(entities);
	}
	public boolean dancerContainsCustomRecord(Dancer dancer,CustomRecord cr){
		boolean res=false;
		List<Curricula> cur = dancer.getCurriculas();
		for(Curricula c: cur){
			if(c.getCustomRecord().contains(cr)){
				res = true;
				break;
			}
		}
		return res;
	}
	
	public CustomRecord clone(CustomRecord p) {
		CustomRecord res = new CustomRecord();
		res.setTitle(new String(p.getTitle()));
		res.setText(new String(p.getText()));
		res.setLinks(new ArrayList<String>(p.getLinks()));

		return customRecordRepository.save(res);
	}


}
