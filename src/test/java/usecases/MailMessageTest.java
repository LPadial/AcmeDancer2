package usecases;

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Dancer;
import domain.MailMessage;
import domain.Priority;
import services.DancerService;
import services.MailMessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MailMessageTest extends AbstractTest {

	// System under test ------------------------------------------------------
	@Autowired
	private MailMessageService	mailmessageService;
	@Autowired
	private DancerService		dancerService;


	//Caso de uso positivo crear un mensaje
	@Test
	public void positiveTest0() {

		Priority priority = new Priority();
		priority.setValue("NEUTRAL");

		Dancer dancer = dancerService.findAll().iterator().next();

		for (Dancer e : dancerService.findAll()) {
			if (e.getUserAccount().getUsername().equals("dancer2")) {
				dancer = e;
				break;
			}
		}

		template("dancer2", new Date(), "subject", "body", priority, dancer, dancer, null);

	}

	@Test
	public void positiveTest1() {

		Priority priority = new Priority();
		priority.setValue("LOW");

		Dancer dancer = dancerService.findAll().iterator().next();

		for (Dancer e : dancerService.findAll()) {
			if (e.getUserAccount().getUsername().equals("dancer2")) {
				dancer = e;
				break;
			}
		}

		template("dancer2", new Date(), "subject0", "body sample body", priority, dancer, dancer, null);

	}
	//Caso de uso negativo crear un mensaje sin actor que lo envia
	@Test
	public void negativeTest0() {

		Priority priority = new Priority();
		priority.setValue("LOW");

		Dancer dancer = dancerService.findAll().iterator().next();

		for (Dancer e : dancerService.findAll()) {
			if (e.getUserAccount().getUsername().equals("dancer2")) {
				dancer = e;
				break;
			}
		}

		template("dancer2", new Date(), "subject0", "body sample body", priority, null, dancer, ConstraintViolationException.class);

	}

	@Test
	public void positiveTest2() {

		Priority priority = new Priority();
		priority.setValue("LOW");

		Dancer dancer = dancerService.findAll().iterator().next();

		for (Dancer e : dancerService.findAll()) {
			if (e.getUserAccount().getUsername().equals("dancer2")) {
				dancer = e;
				break;
			}
		}

		template("dancer2", null, "subject0", "body sample body", priority, dancer, dancer, ConstraintViolationException.class);

	}
	//caso de uso positivo enviar un mensaje a un correo existente en el sistema
	@Test
	public void positiveTest3() {

		Priority priority = new Priority();
		priority.setValue("LOW");

		Dancer dancer = dancerService.findAll().iterator().next();

		for (Dancer e : dancerService.findAll()) {
			if (e.getUserAccount().getUsername().equals("dancer2")) {
				dancer = e;
				break;
			}
		}

		mailmessageService.send("hello", "body sample", "LOW", dancer.getEmail());
	}
	//caso de uso negativo enviar un mensaje a un correo inexistente en el sistema

	@Test
	public void negativeTest3() {
		try {
			mailmessageService.send("hello", "body sample", "LOW", "falseMail@false.com");
		} catch (Exception ex) {
			checkExceptions(IllegalArgumentException.class, ex.getClass());
		}
	}

	// Ancillary methods ------------------------------------------------------
	protected void template(final String username, final Date sent, final String subject, final String body, final Priority priority, final Actor sender, final Actor recipient, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);

			MailMessage mailmessage = mailmessageService.create();
			mailmessage.setSent(sent);
			mailmessage.setSubject(subject);
			mailmessage.setBody(body);
			mailmessage.setPriority(priority);
			mailmessage.setSender(sender);
			mailmessage.setRecipient(recipient);

			mailmessageService.save(mailmessage);
			mailmessageService.flush();

			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}
}
