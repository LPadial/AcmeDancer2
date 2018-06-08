
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Academy;
import domain.Banner;
import security.LoginService;
import services.BannerService;

@Controller
@RequestMapping("/banner")
public class BannerController extends AbstractController {

	@Autowired
	private BannerService	bannerService;
	@Autowired
	private LoginService	loginService;


	@RequestMapping(value = "/academy/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		int q = LoginService.getPrincipal().getId();
		Academy d = (Academy) loginService.findActorByUsername(q);

		List<Banner> banner = new ArrayList<Banner>();

		banner.addAll(bannerService.listBannerActor(d.getId()));

		result = new ModelAndView("banner/list");
		result.addObject("banner", banner);

		return result;

	}

	@RequestMapping(value = "/academy/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(bannerService.create(), null);

		return result;
	}

	@RequestMapping(value = "/academy/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Banner q) {
		ModelAndView result;
		Academy a = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());

		
		if(a != null){
			if(a.getBanners().contains(q)){
				result = createEditModelAndView(q, null);
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;

	}

	@RequestMapping(value = "/academy/save.do", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCreate(@Valid Banner banner, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createNewModelAndView(banner, null);
		} else {
			try {

				bannerService.save(banner);
				result = new ModelAndView("redirect:/banner/academy/list.do");
			} catch (Throwable th) {
				result = createNewModelAndView(banner, "banner.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/academy/save-edit.do", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Banner banner, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(banner, null);
		} else {
			try {
				bannerService.save(banner);
				result = new ModelAndView("redirect:/banner/academy/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(banner, "banner.commit.error");
			}
		}
		return result;
	}
	@RequestMapping(value = "/academy/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam Banner q) {
		ModelAndView result;
		
		Academy a = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if(a!=null){
			if(a.getBanners().contains(q)){
				bannerService.delete(q);
				result = new ModelAndView("redirect:/banner/academy/list.do");
			}else{
				return new ModelAndView("redirect:/welcome/index.do");
			}
		}else{
			return new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}

	protected ModelAndView createEditModelAndView(Banner banner, String message) {
		ModelAndView result = new ModelAndView("banner/edit");

		result.addObject("banner", banner);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createNewModelAndView(Banner banner, String message) {
		ModelAndView result;
		result = new ModelAndView("banner/create");
		result.addObject("banner", banner);
		result.addObject("message", message);
		return result;
	}

}
