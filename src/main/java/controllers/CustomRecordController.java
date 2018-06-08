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

import services.CustomRecordService;
import domain.Curricula;
import domain.CustomRecord;
import domain.Dancer;
import security.LoginService;

@Controller
@RequestMapping("/customrecord")
public class CustomRecordController extends AbstractController{

	@Autowired
	CustomRecordService customrecordService;
	
	@Autowired
	LoginService loginService;

	@RequestMapping("/dancer/create")
	public ModelAndView create(HttpServletRequest request, @RequestParam Integer q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q);
		result = createNewModelAndView(customrecordService.create(), null);

		return result;
	}

	@RequestMapping(value="/dancer/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(HttpServletRequest request, @Valid CustomRecord customrecord, BindingResult binding) {
		ModelAndView result;

		int curricula_id = (int) request.getSession().getAttribute("curricula_id");

		if (binding.hasErrors()) {
			result = createEditModelAndView(customrecord, null);
		} else {
			try {
				customrecordService.save(customrecord,curricula_id);

				result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);

				request.getSession().removeAttribute("curricula_id");
			} catch (Throwable th) {
				result = createEditModelAndView(customrecord, "customrecord.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(CustomRecord customrecord, String message) {
		ModelAndView result;
		result = new ModelAndView("customrecord/create");
		result.addObject("customrecord", customrecord);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/dancer/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @RequestParam Curricula q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q.getId());

		result = new ModelAndView("customrecord/list");
		result.addObject("customrecord", q.getCustomRecord());

		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam CustomRecord q) {
		ModelAndView result;
		
		int dancer_id = LoginService.getPrincipal().getId();
		Dancer d = (Dancer) loginService.findActorByUsername(dancer_id);
		if(d!=null){
			if(customrecordService.dancerContainsCustomRecord(d, q)){
				result = new ModelAndView("customrecord/edit");
				result.addObject("customrecord", q);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}


	@RequestMapping(value="/dancer/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(HttpServletRequest request, @Valid CustomRecord customrecord, BindingResult binding) {
		ModelAndView result;

		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(customrecord, null);
		} else {
			try {
				customrecordService.save(customrecord);
				result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
				request.getSession().removeAttribute("curricula_id");
			} catch (Throwable th) {
				result = createEditModelAndView(customrecord, "customrecord.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.POST,params = "delete")
	public ModelAndView deleteEdit(HttpServletRequest request, @Valid CustomRecord q) {
		ModelAndView result;
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");

		try {
			customrecordService.delete(q);
			result = new ModelAndView("redirect:/customrecord/dancer/mylist.do?q="+curricula_id);
			request.getSession().removeAttribute("curricula_id");
		} catch (Throwable th) {
			result = createEditModelAndView(q, "customrecord.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/dancer/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, @RequestParam CustomRecord q) {
		ModelAndView result;
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		int dancer_id = LoginService.getPrincipal().getId();
		Dancer d = (Dancer) loginService.findActorByUsername(dancer_id);
		
		if(d!=null){
			if(customrecordService.dancerContainsCustomRecord(d, q)){
				customrecordService.delete(q, curricula_id);
				result = new ModelAndView("redirect:/customrecord/dancer/list.do?q=" + curricula_id);
				request.getSession().removeAttribute("curricula_id");
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	
	@RequestMapping("/dancer/listLinks")
	public ModelAndView listLinks(HttpServletRequest request,@RequestParam CustomRecord q){
		ModelAndView res;
		request.getSession().setAttribute("customrecord_id", q.getId());
		
		res = new ModelAndView("customrecord/listLink");
		res.addObject("customrecord",q);
		
		return res;
	}	

	@RequestMapping("/dancer/addLink")
	public ModelAndView addLink(HttpServletRequest request,@RequestParam CustomRecord q){
		ModelAndView res;
		request.getSession().setAttribute("customrecord_id", q.getId());
		
		res = new ModelAndView("customrecord/addLink");
		res.addObject("link", new String());
		
		return res;
	}
	
	@RequestMapping(value = "/dancer/addLinkSave", method = RequestMethod.POST)
	public ModelAndView addLinkSave(HttpServletRequest request, @RequestParam String link){
		ModelAndView res;
		int customrecord_id = (int) request.getSession().getAttribute("customrecord_id");
		
		CustomRecord customrecord = customrecordService.findOne(customrecord_id);
		customrecord.getLinks().add(link);
		customrecordService.save(customrecord);
		res = new ModelAndView("redirect:/customrecord/dancer/edit.do?q=" + customrecord_id);
		
		request.getSession().removeAttribute("customrecord_id");
		
		return res;
	}
	
	@RequestMapping(value="/dancer/deleteLink")
	public ModelAndView deleteLink(HttpServletRequest request, @RequestParam String link){
		ModelAndView res;
		int customrecord_id = (int) request.getSession().getAttribute("customrecord_id");
		CustomRecord customrecord = customrecordService.findOne(customrecord_id);
		
		customrecord.getLinks().remove(link);
		customrecordService.save(customrecord);
		res = new ModelAndView("redirect:/customrecord/dancer/edit.do?q=" + customrecord_id);
		
		request.getSession().removeAttribute("customrecord_id");
		return res;
	}
	

	protected ModelAndView createEditModelAndView(CustomRecord customrecord, String message) {
		ModelAndView result = new ModelAndView("customrecord/edit");

		result.addObject("customrecord", customrecord);
		result.addObject("message", message);

		return result;
	}



}
