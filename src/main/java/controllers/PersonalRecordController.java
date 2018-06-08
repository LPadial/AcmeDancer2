package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Curricula;
import domain.Dancer;
import domain.PersonalRecord;
import security.LoginService;
import services.PersonalRecordService;

@Controller
@RequestMapping("/personalrecord")
public class PersonalRecordController {

	@Autowired
	PersonalRecordService personalrecordService;
	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/dancer/create", method = RequestMethod.GET)
	public ModelAndView create(HttpServletRequest request, @RequestParam Integer q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q);
		result = createNewModelAndView(personalrecordService.create(), null);

		return result;
	}

	@RequestMapping(value="/dancer/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(HttpServletRequest request, @Valid PersonalRecord personalrecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(personalrecord, null);
		} else {
			try {
				personalrecordService.save(personalrecord, curricula_id);
				
				result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
				
				request.getSession().removeAttribute("curricula_id");
			} catch (Throwable th) {
				result = createEditModelAndView(personalrecord, "personalrecord.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(PersonalRecord personalrecord, String message) {
		ModelAndView result;
		result = new ModelAndView("personalrecord/create");
		result.addObject("personalrecord", personalrecord);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/dancer/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request, @RequestParam PersonalRecord q) {
		ModelAndView result;
		
		result = new ModelAndView("personalrecord/view");
		result.addObject("personalrecord", q);

		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request, @RequestParam PersonalRecord personalrecord, @RequestParam Curricula q) {
		ModelAndView result;
		request.getSession().setAttribute("curricula_id", q.getId());
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(d!=null){
			if(personalrecordService.dancerContainsPersonalRecord(d, personalrecord)){
				result = new ModelAndView("personalrecord/edit");
				result.addObject("personalrecord", personalrecord);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value="/dancer/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(HttpServletRequest request, @Valid PersonalRecord personalrecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(personalrecord, null);
		} else {
			try {
				personalrecordService.saveEditing(personalrecord);
					result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
					request.getSession().removeAttribute("curricula_id");
				} catch (Throwable th) {
					result = createEditModelAndView(personalrecord, "personalrecord.commit.error");
				}
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(PersonalRecord personalrecord, String message) {
		ModelAndView result = new ModelAndView("personalrecord/edit");

		result.addObject("personalrecord", personalrecord);
		result.addObject("message", message);

		return result;
	}

}
