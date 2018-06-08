/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import repositories.AdministratorRepository;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Service

	@Autowired
	private AdministratorRepository administratorRepository;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		result.addObject("course", administratorRepository.minAvgSdMaxCoursesPerAcademy());
		result.addObject("app", administratorRepository.minAvgSdMaxApplicationsPerCourse());
		result.addObject("tutorialAcad", administratorRepository.minAvgMaxTutorialsPerAcademy());
		result.addObject("tutorialShow", administratorRepository.minAvgMaxTutorialNumShows());
		result.addObject("tutorialSee", administratorRepository.tutorialsOrderByNumShowsDes());
		result.addObject("chirpActor", administratorRepository.avgChirpsPerActor());
		result.addObject("chirpSubcription", administratorRepository.avgSubscriptionPerActor());
		result.addObject("curriculaRatio", administratorRepository.ratioDancerByCurricula());
		result.addObject("styleCourse", administratorRepository.styleByOrderCourses());
		result.addObject("styleDancer", administratorRepository.styleDancerTeach());
		result.addObject("avgFoldersPerActor", administratorRepository.avgFoldersPerActor());
		result.addObject("avgMailMessage", administratorRepository.avgMailMessage());
		result.addObject("avgMailMessageSpamPerActor", administratorRepository.avgMailMessageSpamPerActor());
		result.addObject("avgBannerByAcademy", administratorRepository.avgBannerByAcademy());
		result.addObject("ratioAcademyBanner", administratorRepository.ratioAcademyBanner());
		
		//Acme-Dancer 2.0
		result.addObject("ratioDancerByCurricula", administratorRepository.ratioDancerByCurricula());
		result.addObject("styleByOrderCourses", administratorRepository.styleByOrderCourses());
		result.addObject("styleDancerTeach", administratorRepository.styleDancerTeach());
		result.addObject("avgFoldersPerActor", administratorRepository.avgFoldersPerActor());
		result.addObject("avgMailMessage", administratorRepository.avgMailMessage());
		result.addObject("avgMailMessageSpamPerActor", administratorRepository.avgMailMessageSpamPerActor());
		result.addObject("avgBannerByAcademy", administratorRepository.avgBannerByAcademy());
		result.addObject("ratioAcademyBanner", administratorRepository.ratioAcademyBanner());

		return result;
	}

}
