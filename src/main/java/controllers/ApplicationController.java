package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Academy;
import domain.Application;
import domain.Course;
import domain.Curricula;
import domain.Dancer;
import security.LoginService;
import services.ApplicationService;
import services.CourseService;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------
	public ApplicationController() {
		super();
	}

	// Actions

	@RequestMapping(value = "/dancer/mylist", method = RequestMethod.GET)
	public ModelAndView listByDancer() {
		ModelAndView result;

		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result = new ModelAndView("application/mylist");
		result.addObject("applications", d.getApplications());

		return result;
	}

	@RequestMapping(value = "/academy/listByCourse", method = RequestMethod.GET)
	public ModelAndView listByCourse(@RequestParam Course q) {
		ModelAndView result;

		result = new ModelAndView("application/list");
		result.addObject("applications", q.getApplications());
		result.addObject("a", 2);

		return result;
	}

	@RequestMapping("/academy/accept")
	public ModelAndView accept(@RequestParam Application q) {

		Academy a = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(a != null){
			if(a.getCourses().contains(q.getCourse())){
				applicationService.accept(q);
				int id = q.getCourse().getId();
				return new ModelAndView("redirect:/application/academy/listByCourse.do?q=" + id);
				
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
	}

	@RequestMapping("/academy/denied")
	public ModelAndView denied(@RequestParam Application q) {
		
		Academy a = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(a != null){
			if(a.getCourses().contains(q.getCourse())){
				applicationService.denied(q);
				int id = q.getCourse().getId();
				return new ModelAndView("redirect:/application/academy/listByCourse.do?q=" + id);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		

	}
	
	@RequestMapping("/dancer/apply")
	public ModelAndView apply(HttpServletRequest request,@RequestParam Course q){
		ModelAndView result;
		
		Dancer dancer = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		request.getSession().setAttribute("course_id", q.getId());
		result= new ModelAndView("application/apply");
		result.addObject("curricula", dancer.getCurriculas());
		
		return result;
	}

	@RequestMapping(value="/dancer/applyTeacher", method=RequestMethod.GET)
	public ModelAndView applyTeacher(HttpServletRequest request, @RequestParam Curricula q) {
		
		int course_id = (int) request.getSession().getAttribute("course_id");
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Course c = courseService.findOne(course_id);
		
		if(d != null){
			if(d.getCurriculas().contains(q)){
				applicationService.applyTeacher(c,q);
				request.getSession().removeAttribute("course_id");
				return new ModelAndView("redirect:/course/list.do");

			}else{
				return new ModelAndView("redirect:/course/list.do");
			}
		}else{
			return new ModelAndView("redirect:/course/list.do");
		}
	}

	@RequestMapping("/dancer/applyStudent")
	public ModelAndView applyStudent(@RequestParam Course q) {

		ModelAndView result = new ModelAndView("redirect:/course/list.do");

		Dancer dancer = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		List<Application> apps = dancer.getApplications();
		List<Course> courses = new ArrayList<Course>();
		for (Application app : apps) {
			courses.add(app.getCourse());
		}
		if (!courses.contains(q)) {
			applicationService.applyStudent(q);
			result = new ModelAndView("redirect:/course/list.do");
			result.addObject("courses", courseService.findAll());
		}

		return result;
	}

}
