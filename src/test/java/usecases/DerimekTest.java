package usecases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Derimek;
import domain.Course;
import domain.Curricula;
import services.DerimekService;
import services.CourseService;
import services.CurriculaService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DerimekTest extends AbstractTest {

	@Autowired
	private DerimekService	derimekService;

	@Autowired
	private CourseService		courseService;

	@Autowired
	private CurriculaService	curriculaService;


	//Caso de uso positvo presentar varias solicitudes
	@Test
	public void driver() {

		

		template("admin", "12-98765", new Date(), "hola1", "description1", 1, false, "", null);
		template("admin", "12-98766", new Date(), "hola3", "description2", 0, false, "", null);
		template("admin", "12-98767", new Date(), "hola2", "description3", -1, false, "", null);
		template("admin", "12-98768", new Date(), "hola5", "description4", 1, false, "", null);
		template("admin", "12-98769", new Date(), "hola7", "description5", 1, false, "", null);

	}
	
	// Ancillary methods ------------------------------------------------------
	protected void template(final String username, final String ticker, final Date moment, final String title, final String description, final Integer score, final Boolean isCancelled, final String justification, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);
			Derimek derimek = derimekService.create();

			derimek.setDescription(description);
			derimek.setIsCancelled(isCancelled);
			derimek.setJustification(justification);
			derimek.setMoment(moment);
			derimek.setScore(score);
			derimek.setTicker(ticker);
			derimek.setTitle(title);			

			derimekService.save(derimek);
			derimekService.flush();

			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}

}
