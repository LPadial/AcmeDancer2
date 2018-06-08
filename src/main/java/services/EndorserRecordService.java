package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.Dancer;
import domain.EndorserRecord;
import repositories.EndorserRecordRepository;
import security.LoginService;

@Service
@Transactional
public class EndorserRecordService {
	
	//Repository
		@Autowired
		private EndorserRecordRepository	endorserRecordRepository;

		//Service

		@Autowired
		private LoginService			loginService;

		@Autowired
		private CurriculaService		curriculaService;


		//Constructor
		public EndorserRecordService() {
			super();
		}

		// CRUD Methods
		public EndorserRecord create() {
			EndorserRecord endorserRecord = new EndorserRecord();
			endorserRecord.setFullName(new String());
			endorserRecord.setContact(new String());

			return endorserRecord;
		}

		public EndorserRecord save(EndorserRecord endorserRecord) {

			EndorserRecord aux = new EndorserRecord();

			if (exists(endorserRecord.getId())) {
				aux = endorserRecordRepository.findOne(endorserRecord.getId());
				aux.setFullName(endorserRecord.getFullName());
				aux.setContact(endorserRecord.getContact());

				return endorserRecordRepository.save(aux);
			} else {
				aux = endorserRecordRepository.save(endorserRecord);
				Dancer man = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
				List<Curricula> curriculas = man.getCurriculas();
				for (Curricula c : curriculas) {
					if (!c.getEndorserRecord().contains(endorserRecord)) {
						c.getEndorserRecord().add(aux);
						curriculaService.save(c);
					}
				}

			}
			return aux;
		}

		public EndorserRecord findOne(Integer id) {
			Assert.notNull(id);
			return endorserRecordRepository.findOne(id);
		}

		public boolean exists(Integer id) {
			Assert.notNull(id);
			return endorserRecordRepository.exists(id);
		}

		public void delete(EndorserRecord endorserRecord) {
			Assert.notNull(endorserRecord);
			endorserRecordRepository.delete(endorserRecord);
		}

		public List<EndorserRecord> findAll() {
			return endorserRecordRepository.findAll();
		}
		
		public EndorserRecord save(EndorserRecord arg0, int curricula_id) {
			Assert.notNull(arg0);
			
			Curricula curricula = curriculaService.findOne(curricula_id);
			EndorserRecord endorserRecord = endorserRecordRepository.save(arg0);
			
			List<EndorserRecord> endorserRecords = curricula.getEndorserRecord();
			endorserRecords.add(endorserRecord);
			curricula.setEndorserRecord(endorserRecords);
			
			curriculaService.saveEditing(curricula);
			
			return endorserRecord;
		}
		
		public EndorserRecord saveEditing(EndorserRecord record) {
			return endorserRecordRepository.save(record);
		}
		
		public void delete(EndorserRecord entity, int curricula_id) {
			Assert.notNull(entity);
			
			Curricula curricula = curriculaService.findOne(curricula_id);
			curricula.getEndorserRecord().remove(entity);
			curriculaService.saveEditing(curricula);
			
			endorserRecordRepository.delete(entity);
		}
		
		public void delete(Iterable<EndorserRecord> entities) {
			Assert.notNull(entities);
			endorserRecordRepository.delete(entities);
		}
		
		public boolean dancerContainsEndorserRecord(Dancer dancer,EndorserRecord er){
			boolean res=false;
			List<Curricula> cur = dancer.getCurriculas();
			for(Curricula c:cur){
				if(c.getEndorserRecord().contains(er)){
					res=true;
				}
			}
			return res;
		}
		
		public EndorserRecord clone(EndorserRecord p) {
			EndorserRecord res = new EndorserRecord();
			res.setContact(new String(p.getContact()));
			res.setFullName(new String(p.getFullName()));

			return endorserRecordRepository.save(res);
		}


}
