package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.SpamWord;
import services.SpamWordService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SpamWordTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private SpamWordService spamwordService;


	//Caso de uso positivo crear un spam
	@Test
	public void positiveTest0() {

		template("admin", "shit", null);

	}

	@Test
	public void positiveTest1() {

		template("admin", "fuck", null);

	}
	//Caso de uso negativo crear spam vacio
	@Test
	public void negativeTest0() {

		template("admin", "", ConstraintViolationException.class);

	}

	@Test
	public void negativeTest1() {

		template("admin", null, ConstraintViolationException.class);

	}

	// Ancillary methods ------------------------------------------------------
	protected void template(final String username, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);

			SpamWord spamword = spamwordService.create();
			spamword.setName(name);

			spamwordService.save(spamword);
			spamwordService.flush();

			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}
}
