package controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.DerimekService;
import domain.Administrator;
import domain.Derimek;

@Controller
@RequestMapping("/derimek")
public class DerimekController extends AbstractController {

	//Services

	@Autowired
	private DerimekService derimekService;

	@Autowired
	private LoginService loginService;
	

	// Constructors -----------------------------------------------------------
	public DerimekController(){
		super();
	}
	

	//Actions

	@RequestMapping("/administrator/mylist")
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("derimek/mylist");

		Administrator d = (Administrator) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		result.addObject("derimeks", derimekService.findAll());
		result.addObject("mios", d.getDerimeks());
		result.addObject("yo",d);

		return result;
	}
	
	@RequestMapping("/view")
	public ModelAndView view(@RequestParam Derimek q) {
		ModelAndView result;

		result = new ModelAndView("derimek/view");
		result.addObject("derimek", q);

		return result;
	}
	
	@RequestMapping("administrator/cancelView")
	public ModelAndView cancelView(@RequestParam Derimek q) {
		ModelAndView result;

		result = new ModelAndView("derimek/cancelView");
		result.addObject("derimek", q);

		return result;
	}
	
	
	@RequestMapping(value = "/administrator/save-edit.do", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Derimek derimek, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(derimek, null);
		} else {
			try {
				derimekService.save(derimek);
				result = new ModelAndView("redirect:/derimek/administrator/mylist.do");
			} catch (Throwable th) {
				result = createEditModelAndView(derimek, "derimek.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Derimek derimek, String message) {
		ModelAndView result = new ModelAndView("derimek/edit");

		result.addObject("derimek", derimek);
		result.addObject("message", message);

		return result;
	}
}
