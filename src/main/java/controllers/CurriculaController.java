package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CurriculaService;
import domain.Curricula;
import domain.Dancer;

@Controller
@RequestMapping("/curricula")
public class CurriculaController extends AbstractController {

	//Services

	@Autowired
	private CurriculaService curriculaService;

	@Autowired
	private LoginService loginService;
	

	// Constructors -----------------------------------------------------------
	public CurriculaController(){
		super();
	}
	

	//Actions

	@RequestMapping("/dancer/mylist")
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("curricula/list");

		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("curriculas", d.getCurriculas());

		return result;
	}

	@RequestMapping(value="/dancer/save", method=RequestMethod.GET)
	public ModelAndView saveCreate() {
		ModelAndView result;
		
		Curricula c = curriculaService.createAndSave();
		
		result = new ModelAndView("redirect:/curricula/dancer/edit.do?q=" + c.getId());
			
		return result;
	}
	

	@RequestMapping(value = "/actor/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam Curricula q) {
		ModelAndView result;

		result = new ModelAndView("curricula/view");
		result.addObject("curricula", q);

		return result;
	}

	@RequestMapping(value = "/dancer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Curricula q) {
		ModelAndView result;
		Dancer d = (Dancer) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		
		if(d!=null){
			if(d.getCurriculas().contains(q)){
				result = new ModelAndView("curricula/edit");
				result.addObject("curricula", q);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}
	
	@RequestMapping("/dancer/delete")
	public ModelAndView delete(@RequestParam Curricula q) {
		ModelAndView result;
		
		int dancer_id = LoginService.getPrincipal().getId();
		Dancer d = (Dancer) loginService.findActorByUsername(dancer_id);
		
		if(d!=null){
			if(d.getCurriculas().contains(q)){
				curriculaService.delete(q);
				result = new ModelAndView("redirect:/curricula/dancer/mylist.do?q=" + dancer_id);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}

		return result;

	}

	protected ModelAndView createEditModelAndView(Curricula curricula, String message) {
		ModelAndView result = new ModelAndView("curricula/edit");

		result.addObject("curricula", curricula);
		result.addObject("message", message);

		return result;
	}
}
