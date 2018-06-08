
package repositories;

import java.lang.reflect.Array;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Administrator;
import domain.Style;
import domain.Tutorial;

public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//El m�nimo, media, desviaci�n est�ndar y m�ximo de cursos por academia.
	@Query("select min(a.courses.size),avg(a.courses.size),stddev(a.courses.size),max(a.courses.size) from Academy a")
	Object[] minAvgSdMaxCoursesPerAcademy();

	//El m�nimo, media, desviaci�n est�ndar y m�ximo de solicitudes por curso.
	@Query("select min(c.applications.size),avg(c.applications.size),stddev(c.applications.size),max(c.applications.size) from Course c")
	Object[] minAvgSdMaxApplicationsPerCourse();

	//El m�nimo, media y m�ximo de tutoriales por academia.
	@Query("select min(a.tutorials.size),avg(a.tutorials.size),max(a.tutorials.size) from Academy a")
	Object[] minAvgMaxTutorialsPerAcademy();

	//El m�nimo, media y m�ximo de veces que un tutorial se muestra.
	@Query("select min(t.numShows),avg(t.numShows),max(t.numShows) from Tutorial t")
	Object[] minAvgMaxTutorialNumShows();

	//La lista de tutoriales, ordenados en orden descendente de acuerdo al
	//n�mero de veces que se han visto.
	@Query("select t from Tutorial t order by t.numShows desc")
	List<Tutorial> tutorialsOrderByNumShowsDes();

	//La media de chirps por actor.
	@Query("select avg(a.chirps.size) from Actor a")
	Double avgChirpsPerActor();

	//La media de suscripciones por actor.
	@Query("select (count(ap)/(select count(a) from Actor a)) from Application ap where ap.statusApplication like 'ACCEPTED'")
	Double avgSubscriptionPerActor();

	//Acme-Dancer 2.0

	//El ratio de bailarines que han registrado un curr�culo
	@Query("select count(o) * 1. / count(o.curriculas.size) from Dancer o")
	Double ratioDancerByCurricula();

	//Una lista de estilos en orden decreciente de n�mero de cursos en los que se ense�an
	@Query("select c from Style c order by c.courses.size desc")
	List<Style> styleByOrderCourses();

	//Una lista de estilos en orden decreciente de n�mero de bailarines que pueden ense�arlos
	@Query("select c.style from Curricula d join d.styleRecord c order by d.styleRecord.size desc")
	List<Style> styleDancerTeach();

	//La media de carpetas por actor.
	@Query("select a.actorName,((a.folders.size *1.0)/(select count(c) from Folder c)) from Actor a")
	List<Array[]> avgFoldersPerActor();

	//La media de mensajes en las carpetas del sistema
	@Query("select avg(a.messages.size) from Folder a")
	Double avgMailMessage();

	//La media de mensajes spam por actor
	@Query("select a.actorName,((b.messages.size *1.0)/(select count(c) from MailMessage c)) from Actor a join a.folders b where b.folderName like '%spambox%'")
	List<Array[]> avgMailMessageSpamPerActor();
	//La media de banners por academia
	@Query("select a.actorName,((a.banners.size *1.0)/(select count(c) from Banner c)) from Academy a")
	List<Array[]> avgBannerByAcademy();

	//El ratio de academias que han registrado al menos un banner
	@Query("select count(o) * 1. / count(o.banners.size) from Academy o where o.banners.size>0")
	Double ratioAcademyBanner();
}
