
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Curricula;
import domain.StyleRecord;
import domain.Dancer;
import domain.Style;
import repositories.StyleRecordRepository;
import security.LoginService;

@Service
@Transactional
public class StyleRecordService {

	//Repository
	@Autowired
	private StyleRecordRepository	styleRecordRepository;

	//Service
	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private LoginService			loginService;


	//Constructor
	public StyleRecordService() {
		super();
	}

	//CRUD Methods

	public StyleRecord create() {
		StyleRecord styleRecord = new StyleRecord();

		styleRecord.setNumYear(new Integer(0));
		styleRecord.setStyle(new Style());

		return styleRecord;

	}

	public StyleRecord save(StyleRecord styleRecord) {
		StyleRecord aux = new StyleRecord();
		
		if (exists(styleRecord.getId())) {
			aux = styleRecordRepository.findOne(styleRecord.getId());
			aux.setNumYear(styleRecord.getNumYear());
			aux.setStyle(styleRecord.getStyle());

			return styleRecordRepository.save(aux);
		} else {
			aux = styleRecordRepository.save(styleRecord);
			Dancer man = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
			List<Curricula> curriculas = man.getCurriculas();
			for (Curricula c : curriculas) {
				if (!c.getStyleRecord().contains(styleRecord)) {
					c.getStyleRecord().add(aux);
					curriculaService.save(c);
				}
			}

		}

		return aux;
	}
	
	public List<StyleRecord> save(List<StyleRecord> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		
		return styleRecordRepository.save(entities);
	}

	
	public StyleRecord save(StyleRecord arg0, int curricula_id) {
		Assert.notNull(arg0);
		System.out.println("Num yer" + arg0.getNumYear());
		System.out.println("Style" + arg0.getStyle());
		Curricula curricula = curriculaService.findOne(curricula_id);
		StyleRecord styleRecord = styleRecordRepository.save(arg0);
		
		
		List<StyleRecord> styleRecords = curricula.getStyleRecord();
		styleRecords.add(styleRecord);
		curricula.setStyleRecord(styleRecords);
		
		curriculaService.saveEditing(curricula);
		
		return styleRecord;
	}
	
	public StyleRecord saveEditing(StyleRecord record) {
		return styleRecordRepository.save(record);
	}



	public StyleRecord findOne(Integer id) {
		Assert.notNull(id);
		return styleRecordRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return styleRecordRepository.exists(id);
	}

	public void delete(StyleRecord styleRecord) {
		Assert.notNull(styleRecord);
		styleRecordRepository.delete(styleRecord);
	}

	public List<StyleRecord> findAll() {
		return styleRecordRepository.findAll();
	}
	
	public void delete(StyleRecord entity, int curricula_id) {
		Assert.notNull(entity);
		
		Curricula curricula = curriculaService.findOne(curricula_id);
		curricula.getStyleRecord().remove(entity);
		curriculaService.saveEditing(curricula);
		
		styleRecordRepository.delete(entity);
	}
	
	public void delete(Iterable<StyleRecord> entities) {
		Assert.notNull(entities);
		styleRecordRepository.delete(entities);
	}
	
	public boolean dancerContainsStyleRecord(Dancer dancer,StyleRecord sr){
		boolean res=false;
		List<Curricula> cur = dancer.getCurriculas();
		for(Curricula c:cur){
			if(c.getStyleRecord().contains(sr)){
				res=true;
			}
		}
		return res;
	}
	
	public StyleRecord clone(StyleRecord p) {
		StyleRecord res = new StyleRecord();

		res.setNumYear(new Integer(p.getNumYear()));
		res.setStyle(p.getStyle());

		return styleRecordRepository.save(res);
	}



}
