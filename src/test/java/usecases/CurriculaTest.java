package usecases;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Curricula;
import domain.CustomRecord;
import domain.EndorserRecord;
import domain.PersonalRecord;
import domain.StyleRecord;
import services.CurriculaService;
import services.CustomRecordService;
import services.EndorserRecordService;
import services.PersonalRecordService;
import services.StyleRecordService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CurriculaTest extends AbstractTest {

	@Autowired
	private CurriculaService		curriculaService;

	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CustomRecordService		customRecordService;

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private StyleRecordService		styleRecordService;


	//Caso de uso positivo crear y guardar una curricula con personalRecord
	@Test
	public void positiveTest0() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());

		template("dancer1", personalrecord, new ArrayList<CustomRecord>(), new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), null);

	}

	//Caso de uso positivo crear y guardar una curricula con 2 CustomRecord
	@Test
	public void positiveTest1() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		List<CustomRecord> copyCustom = new ArrayList<CustomRecord>();

		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));
		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));
		template("dancer3", personalrecord, copyCustom, new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), null);

	}

	//Caso de uso positivo crear y guardar una curricula con 2 endorserRecord

	@Test
	public void positiveTest2() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		List<CustomRecord> copyCustom = new ArrayList<CustomRecord>();
		List<EndorserRecord> endorserRecordsCopy = new ArrayList<EndorserRecord>();

		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));
		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));

		endorserRecordsCopy.add(endorserRecordService.clone(endorserRecordService.findAll().iterator().next()));
		endorserRecordsCopy.add(endorserRecordService.clone(endorserRecordService.findAll().iterator().next()));
		template("dancer3", personalrecord, copyCustom, endorserRecordsCopy, new ArrayList<StyleRecord>(), null);

	}

	//Caso de uso positivo crear y guardar una curricula con 2 styleRecord y completa
	@Test
	public void positiveTest4() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		List<CustomRecord> copyCustom = new ArrayList<CustomRecord>();
		List<EndorserRecord> endorserRecordsCopy = new ArrayList<EndorserRecord>();
		List<StyleRecord> styleRecords = new ArrayList<StyleRecord>();

		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));
		copyCustom.add(customRecordService.clone(customRecordService.findAll().iterator().next()));

		endorserRecordsCopy.add(endorserRecordService.clone(endorserRecordService.findAll().iterator().next()));
		endorserRecordsCopy.add(endorserRecordService.clone(endorserRecordService.findAll().iterator().next()));

		styleRecords.add(styleRecordService.clone(styleRecordService.findAll().iterator().next()));
		styleRecords.add(styleRecordService.clone(styleRecordService.findAll().iterator().next()));
		template("dancer3", personalrecord, copyCustom, endorserRecordsCopy, styleRecords, null);

	}

	//Caso de uso negativo crear una curricula sin personal record
	@Test
	public void negativeTest0() {

		template("dancer3", null, new ArrayList<CustomRecord>(), new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), ConstraintViolationException.class);

	}

	//Caso de uso negativo crear una curricula con null en customrecords
	@Test
	public void negativeTest1() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());

		template("dancer2", personalrecord, null, new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), ConstraintViolationException.class);

	}

	//Caso de uso negativo crear una curricula entrando con null en endorserRecords
	@Test
	public void negativeTest2() {
		PersonalRecord personalrecord = personalRecordService.clone(personalRecordService.findAll().iterator().next());

		template("dancer1", personalrecord, new ArrayList<CustomRecord>(), null, new ArrayList<StyleRecord>(), ConstraintViolationException.class);

	}

	//Caso de uso positivo, crear varias curiculas
	@Test
	public void driver() {
		PersonalRecord personalrecord1 = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		PersonalRecord personalrecord2 = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		PersonalRecord personalrecord3 = personalRecordService.clone(personalRecordService.findAll().iterator().next());
		template("dancer1", personalrecord1, new ArrayList<CustomRecord>(), new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), null);
		template("dancer2", personalrecord2, new ArrayList<CustomRecord>(), new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), null);
		template("dancer3", personalrecord3, new ArrayList<CustomRecord>(), new ArrayList<EndorserRecord>(), new ArrayList<StyleRecord>(), null);
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(final String username, final PersonalRecord personalRecord, final List<CustomRecord> customRecord, final List<EndorserRecord> endorserRecord, List<StyleRecord> styleRecord, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);

			Curricula curricula = curriculaService.create();
			curricula.setPersonalRecord(personalRecord);
			curricula.setCustomRecord(customRecord);
			curricula.setEndorserRecord(endorserRecord);
			curricula.setStyleRecord(styleRecord);

			curriculaService.save(curricula);
			curriculaService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}

}
