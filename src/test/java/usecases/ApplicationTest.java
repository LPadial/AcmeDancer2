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

import domain.Application;
import domain.Course;
import domain.Curricula;
import domain.RoleApplication;
import domain.StatusApplication;
import services.ApplicationService;
import services.CourseService;
import services.CurriculaService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private CourseService		courseService;

	@Autowired
	private CurriculaService	curriculaService;


	//Caso de uso positvo presentar varias solicitudes
	@Test
	public void driver() {
		StatusApplication status = new StatusApplication();
		status.setValue("PENDING");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("TEACHER");

		Curricula curricula1 = curriculaService.findAll().iterator().next();
		Curricula curricula2 = curriculaService.findAll().iterator().next();
		Curricula curricula3 = curriculaService.findAll().iterator().next();

		Course course1 = courseService.findAll().iterator().next();
		Course course2 = courseService.findAll().iterator().next();
		Course course3 = courseService.findAll().iterator().next();

		template("dancer1", new Date(), status, course1, role, curricula1, null);
		template("dancer2", new Date(), status, course2, role, curricula2, null);
		template("dancer3", new Date(), status, course3, role, curricula3, null);

	}

	//Caso de uso positivo presentarse a un curso como profesor
	@Test
	public void positiveTest0() {
		StatusApplication status = new StatusApplication();
		status.setValue("PENDING");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("TEACHER");

		Curricula curricula = curriculaService.findAll().iterator().next();

		Course course = courseService.findAll().iterator().next();

		template("dancer1", new Date(), status, course, role, curricula, null);

	}

	//Caso de uso presentarse a un curso como alumno
	@Test
	public void positiveTest1() {
		StatusApplication status = new StatusApplication();
		status.setValue("PENDING");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("STUDENT");

		Curricula curricula = curriculaService.findAll().iterator().next();

		Course course = courseService.findAll().iterator().next();

		template("dancer1", new Date(), status, course, role, curricula, null);

	}
	//Caso de uso positivo aceptar las solicitudes
	@Test
	public void positiveTest2() {

		Course course = courseService.findAll().iterator().next();
		StatusApplication status = new StatusApplication();
		status.setValue("ACCEPTED");

		Application application = applicationService.findAll().iterator().next();
		application.setStatusApplication(status);
		applicationService.save(application);
		List<Application> aplicationCourse = new ArrayList<Application>();
		aplicationCourse.add(application);
		course.setApplications(aplicationCourse);
		courseService.save(course);

	}
	//Caso de uso positivo denegar las solicitudes
	@Test
	public void positiveTest3() {

		Course course = courseService.findAll().iterator().next();
		StatusApplication status = new StatusApplication();
		status.setValue("REJECTED");

		Application application = applicationService.findAll().iterator().next();
		application.setStatusApplication(status);
		applicationService.save(application);
		List<Application> aplicationCourse = new ArrayList<Application>();
		aplicationCourse.add(application);
		course.setApplications(aplicationCourse);
		courseService.save(course);

	}

	//Caso de uso positivo presentarse a un curso como otro rol
	@Test
	public void negativeTest0() {
		StatusApplication status = new StatusApplication();
		status.setValue("PENDING");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("ACME");

		Curricula curricula = curriculaService.findAll().iterator().next();

		Course course = courseService.findAll().iterator().next();

		template("dancer1", new Date(), status, course, role, curricula, ConstraintViolationException.class);

	}
	//caso de uso negativo presentarse a un curso sin curso
	@Test
	public void negativeTest1() {
		StatusApplication status = new StatusApplication();
		status.setValue("PENDING");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("TEACHER");

		Curricula curricula = curriculaService.findAll().iterator().next();
		template("dancer1", new Date(), status, null, role, curricula, ConstraintViolationException.class);

	}

	//caso de uso negativo presentarse a un curso con otro estado sin definir
	@Test
	public void negativeTest2() {
		StatusApplication status = new StatusApplication();
		status.setValue("ACME");
		RoleApplication role = new RoleApplication();
		role.setRoleValue("TEACHER");

		Course course = courseService.findAll().iterator().next();
		Curricula curricula = curriculaService.findAll().iterator().next();

		template("dancer1", new Date(), status, course, role, curricula, ConstraintViolationException.class);

	}

	//caso de uso negativo presentarse a un curso con otro estado sin estado
	@Test
	public void negativeTest3() {

		RoleApplication role = new RoleApplication();
		role.setRoleValue("TEACHER");

		Course course = courseService.findAll().iterator().next();
		Curricula curricula = curriculaService.findAll().iterator().next();

		template("dancer1", new Date(), null, course, role, curricula, ConstraintViolationException.class);

	}

	// Ancillary methods ------------------------------------------------------
	protected void template(final String username, final Date createMoment, final StatusApplication statusApplication, final Course course, final RoleApplication role, final Curricula curricula, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);
			Application application = applicationService.create();

			application.setCreateMoment(createMoment);
			application.setStatusApplication(statusApplication);
			application.setCourse(course);
			application.setRole(role);
			application.setCurricula(curricula);

			applicationService.save(application);
			applicationService.flush();

			unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}

}
