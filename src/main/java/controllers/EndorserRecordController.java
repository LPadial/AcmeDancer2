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
import domain.EndorserRecord;
import security.LoginService;
import services.EndorserRecordService;

@Controller
@RequestMapping("/endorserrecord")
public class EndorserRecordController extends AbstractController{
	
	@Autowired
	private EndorserRecordService endorserrecordService;
	
	@Autowired
	LoginService loginService;

	@RequestMapping("/dancer/create")
	public ModelAndView create(HttpServletRequest request, @RequestParam Curricula q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q.getId());
		result = createNewModelAndView(endorserrecordService.create(), null);

		return result;
	}

	@RequestMapping(value="/dancer/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(HttpServletRequest request, @Valid EndorserRecord endorserrecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(endorserrecord, null);
		} else {
			try {
				endorserrecordService.save(endorserrecord,curricula_id);
				
				result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
				
				request.getSession().removeAttribute("curricula_id");
			} catch (Throwable th) {
				result = createEditModelAndView(endorserrecord, "endorserrecord.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(EndorserRecord endorserrecord, String message) {
		ModelAndView result;
		result = new ModelAndView("endorserrecord/create");
		result.addObject("endorserrecord", endorserrecord);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/dancer/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @RequestParam Curricula q) {
		ModelAndView result;
		
		request.getSession().setAttribute("curricula_id", q.getId());
		
		result = new ModelAndView("endorserrecord/list");
		result.addObject("endorserrecord", q.getEndorserRecord());

		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam EndorserRecord q) {
		ModelAndView result;
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if(d!=null){
			if(endorserrecordService.dancerContainsEndorserRecord(d, q)){
				result = new ModelAndView("endorserrecord/edit");
				result.addObject("endorserrecord", q);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}

	@RequestMapping(value="/dancer/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(HttpServletRequest request, @Valid EndorserRecord endorserrecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(endorserrecord, null);
		} else {
			try {
				endorserrecordService.save(endorserrecord);
					result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
					request.getSession().removeAttribute("curricula_id");
				} catch (Throwable th) {
					result = createEditModelAndView(endorserrecord, "endorserrecord.commit.error");
				}
			}
		return result;
	}
	
	@RequestMapping(value = "/dancer/edit", method = RequestMethod.POST,params = "delete")
	public ModelAndView deleteEdit(HttpServletRequest request, @Valid EndorserRecord q) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");

		try {
			endorserrecordService.delete(q);
			result = new ModelAndView("redirect:/endorserrecord/dancer/mylist.do?q="+curricula_id);
			request.getSession().removeAttribute("curricula_id");
		} catch (Throwable th) {
			result = createEditModelAndView(q, "endorserrecord.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/dancer/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, @RequestParam EndorserRecord q) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		try {
			endorserrecordService.delete(q, curricula_id);
			result = new ModelAndView("redirect:/endorserrecord/dancer/list.do?q=" + curricula_id);
			request.getSession().removeAttribute("curricula_id");
		} catch(Throwable th) {
			result = createEditModelAndView(q, "endorserrecord.commit.error");
		}
		
		return result;
	}



	protected ModelAndView createEditModelAndView(EndorserRecord endorserrecord, String message) {
		ModelAndView result = new ModelAndView("endorserrecord/edit");

		result.addObject("endorserrecord", endorserrecord);
		result.addObject("message", message);

		return result;
	}

}
