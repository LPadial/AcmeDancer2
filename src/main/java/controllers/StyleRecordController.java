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
import domain.StyleRecord;
import security.LoginService;
import services.StyleRecordService;
import services.StyleService;

@Controller
@RequestMapping("/stylerecord")
public class StyleRecordController extends AbstractController{

	@Autowired
	private StyleRecordService stylerecordService;

	@Autowired
	private StyleService styleService;
	
	@Autowired
	private LoginService loginService;
	

	@RequestMapping("/dancer/create")
	public ModelAndView create(HttpServletRequest request, @RequestParam Curricula q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q.getId());
		result = createNewModelAndView(stylerecordService.create(), null);

		return result;
	}

	@RequestMapping(value="/dancer/save", method=RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(HttpServletRequest request, @Valid StyleRecord stylerecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(stylerecord, null);
		} else {
			try {
				stylerecordService.save(stylerecord,curricula_id);
				
				result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
				
				request.getSession().removeAttribute("curricula_id");
			} catch (Throwable th) {
				result = createEditModelAndView(stylerecord, "stylerecord.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(StyleRecord stylerecord, String message) {
		ModelAndView result;
		result = new ModelAndView("stylerecord/create");
		result.addObject("stylerecord", stylerecord);
		result.addObject("styles", styleService.findAll());
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/dancer/list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, @RequestParam Curricula q) {
		ModelAndView result;

		request.getSession().setAttribute("curricula_id", q.getId());

		result = new ModelAndView("stylerecord/list");
		result.addObject("stylerecord", q.getStyleRecord());

		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam StyleRecord q) {
		ModelAndView result;
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if(d!=null){
			if(stylerecordService.dancerContainsStyleRecord(d,q)){
				result = new ModelAndView("stylerecord/edit");
				result.addObject("styles", styleService.findAll());
				result.addObject("stylerecord", q);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}

	@RequestMapping(value="/dancer/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(HttpServletRequest request, @Valid StyleRecord stylerecord, BindingResult binding) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(stylerecord, null);
		} else {
			try {
				stylerecordService.save(stylerecord);
					result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + curricula_id);
					request.getSession().removeAttribute("curricula_id");
				} catch (Throwable th) {
					result = createEditModelAndView(stylerecord, "stylerecord.commit.error");
				}
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(StyleRecord stylerecord, String message) {
		ModelAndView result = new ModelAndView("stylerecord/edit");

		result.addObject("stylerecord", stylerecord);
		result.addObject("styles", styleService.findAll());
		result.addObject("message", message);

		return result;
	}
	
	@RequestMapping(value = "/dancer/edit", method = RequestMethod.POST,params = "delete")
	public ModelAndView deleteEdit(HttpServletRequest request, @Valid StyleRecord q) {
		ModelAndView result;
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(d!=null){
			if(stylerecordService.dancerContainsStyleRecord(d, q)){
				stylerecordService.delete(q);
				result = new ModelAndView("redirect:/stylerecord/dancer/mylist.do?q="+curricula_id);
				request.getSession().removeAttribute("curricula_id");
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}
	
	@RequestMapping(value = "/dancer/delete", method = RequestMethod.GET)
	public ModelAndView delete(HttpServletRequest request, @RequestParam StyleRecord q) {
		ModelAndView result;
		
		int curricula_id = (int) request.getSession().getAttribute("curricula_id");
		
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(d!=null){
			if(stylerecordService.dancerContainsStyleRecord(d, q)){
				stylerecordService.delete(q, curricula_id);
				result = new ModelAndView("redirect:/stylerecord/dancer/list.do?q=" + curricula_id);
				request.getSession().removeAttribute("curricula_id");
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		return result;
	}


}
