
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Course;
import domain.Curricula;
import domain.Dancer;
import domain.RoleApplication;
import domain.StatusApplication;

@Service
@Transactional
public class ApplicationService {

	//Repositories
	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private DancerService dancerService;

	@Autowired
	private CourseService courseService;

	//Services


	//Constructor
	public ApplicationService() {
		super();
	}

	//CRUD Methods
	public Application create() {
		Application application = new Application();
		application.setCreateMoment(new Date());
		return application;

	}

	public List<Application> findAll() {
		return applicationRepository.findAll();
	}

	//Other Methods
	public Application save(Application application) {
		Assert.notNull(application);
		Application app= new Application();
		if(exists(application.getId())){
			app=findOne(application.getId());
			app.setCourse(application.getCourse());
			app.setCreateMoment(application.getCreateMoment());
			app.setStatusApplication(application.getStatusApplication());
			return applicationRepository.save(app);
		}else{
			return applicationRepository.save(application);
		}
	}

	public void delete(Integer appId) {
		Application app = findOne(appId);
		List<Dancer> dans = dancerService.findAll();
		Dancer dan = null;
		for(Dancer d: dans){
			if(d.getApplications().contains(app)){
				dan = d;
				break;
			}
		}
		if(dan != null){
			List<Application> apps = dan.getApplications();
			apps.remove(app);
			dan.setApplications(apps);
			dancerService.save(dan);
		}

		applicationRepository.delete(appId);
	}


	public Application findOne(Integer arg0) {
		return applicationRepository.findOne(arg0);
	}

	public Collection<Application> applicationsOfCourse(int courseID) {
		return applicationRepository.applicationsOfCourse(courseID);
	}

	public boolean exists(Integer arg0) {
		return applicationRepository.exists(arg0);
	}

	private static Date plusDate(Date reff) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(reff);
		calendar.add(Calendar.DAY_OF_YEAR, -7);
		Date date = calendar.getTime();

		return date;
	}

	public Application accept(Application application) {
		Assert.notNull(application);

		Date date_actual = new Date();

		if(application.getRole().getRoleValue().equalsIgnoreCase("TEACHER")){
			if(date_actual.before(plusDate(application.getCourse().getStart()))){//Menos una semana
				StatusApplication sa = new StatusApplication();
				sa.setValue("ACCEPTED");
				application.setStatusApplication(sa);
			}
		}

		if(application.getRole().getRoleValue().equalsIgnoreCase("STUDENT")){
			if(date_actual.before(application.getCourse().getEnd())){
				StatusApplication sa = new StatusApplication();
				sa.setValue("ACCEPTED");
				application.setStatusApplication(sa);
			}
		}

		return applicationRepository.save(application);

	}

	public Application denied(Application application) {
		Assert.notNull(application);

		StatusApplication sa = new StatusApplication();
		sa.setValue("REJECTED");
		application.setStatusApplication(sa);

		return applicationRepository.save(application);

	}

	public Application applyStudent(Course course) {

		Assert.notNull(course);
		
		Date date_actual = new Date();
		Application application=new Application();

		Dancer dancer = dancerService.selectByUserAccountId();

		if(date_actual.before(course.getEnd()) && course.getStageCourse().getCourseValue().equalsIgnoreCase("DELIVERING")){
			RoleApplication ra = new RoleApplication();
			ra.setRoleValue("STUDENT");
			application.setRole(ra);
			application.setCourse(course);
			application.setCreateMoment(new Date());
			StatusApplication sa = new StatusApplication();
			sa.setValue("PENDING");
			application.setStatusApplication(sa);
			application= applicationRepository.save(application);
			course.getApplications().add(application);
			courseService.save(course);
			List<Application> applications = dancer.getApplications();
			applications.add(application);
			dancer.setApplications(applications);
			dancerService.save(dancer);
		}
		return applicationRepository.save(application);
	}

	public Application applyTeacher(Course course, Curricula curricula) {
		Assert.notNull(course);
		
		Date date_actual = new Date();
		Application application = new Application();

		Dancer dancer = dancerService.selectByUserAccountId();

		if(date_actual.before(plusDate(course.getStart()))&& course.getStageCourse().getCourseValue().equalsIgnoreCase("ORGANISING")){
			
			RoleApplication ra = new RoleApplication();
			ra.setRoleValue("TEACHER");
			application.setRole(ra);
			
			application.setCourse(course);
			
			application.setCreateMoment(new Date());
			
			StatusApplication sa = new StatusApplication();
			sa.setValue("PENDING");
			application.setStatusApplication(sa);
			
			application.setCurricula(curricula);
			
			application= applicationRepository.save(application);
			
			course.getApplications().add(application);
			courseService.save(course);
			
			List<Application> applications = dancer.getApplications();
			applications.add(application);
			dancer.setApplications(applications);
			
			dancerService.save(dancer);
		}
		return applicationRepository.save(application);
	}


	public Collection<Application> allApplicationsOfAcademy(int AcademyID) {
		return applicationRepository.allApplicationsOfAcademy(AcademyID);
	}

	public Collection<Application> applicationsPendingOfAcademy(int AcademyID) {
		return applicationRepository.applicationsPendingOfAcademy(AcademyID);
	}

	public Collection<Application> applicationsAcceptedOrRejectedOfAcademy(int AcademyID) {
		return applicationRepository.applicationsAcceptedOrRejectedOfAcademy(AcademyID);
	}

	public Collection<Application> applicationsPendingOfCourse(int courseId) {
		return applicationRepository.applicationsPendingOfCourse(courseId);
	}

	public void flush() {
		applicationRepository.flush();
	}
	

}
