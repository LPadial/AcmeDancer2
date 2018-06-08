package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Academy;
import domain.Banner;
import services.AcademyService;
import services.BannerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BannerTest extends AbstractTest {

	@Autowired
	private BannerService	bannerService;

	@Autowired
	private AcademyService	academyService;


	//Caso de uso positivo crear un banner
	@Test
	public void testPositive0() {

		Academy academy = academyService.findAll().iterator().next();

		for (Academy e : academyService.findAll()) {
			if (e.getUserAccount().getUsername().equals("academy1")) {
				academy = e;
				break;
			}
		}

		template("academy1", "https://www.hola.com/", academy, null);

	}

	@Test
	public void testPositive1() {

		Academy academy = academyService.findAll().iterator().next();

		for (Academy e : academyService.findAll()) {
			if (e.getUserAccount().getUsername().equals("academy2")) {
				academy = e;
				break;
			}
		}

		template("academy2", "https://www.hola.com/", academy, null);

	}

	//Caso de uso negativo crear un banner sin academia
	@Test
	public void testNegative0() {

		template("academy2", "https://www.hola.com/", null, ConstraintViolationException.class);

	}

	//Caso de uso negativo crear un banner con un url falso
	@Test
	public void testNegative1() {
		Academy academy = academyService.findAll().iterator().next();
		for (Academy e : academyService.findAll()) {
			if (e.getUserAccount().getUsername().equals("academy2")) {
				academy = e;
				break;
			}
		}

		template("academy2", "hola", academy, ConstraintViolationException.class);

	}

	protected void template(final String username, final String url, final Academy academy, final Class<?> expected) {
		Class<?> caught = null;

		try {
			authenticate(username);
			Banner banner = bannerService.create();

			banner.setAcademy(academy);
			banner.setUrl(url);
			bannerService.save(banner);
			bannerService.flush();

			unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		checkExceptions(expected, caught);
	}

}
