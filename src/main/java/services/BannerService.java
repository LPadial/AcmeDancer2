
package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Academy;
import domain.Banner;
import repositories.BannerRepository;
import security.LoginService;

@Service
@Transactional
public class BannerService {

	//Repository
	@Autowired
	private BannerRepository	bannerRepository;

	//Service
	@Autowired
	private AcademyService		academyService;

	@Autowired
	private LoginService		loginService;


	//Constructor
	public BannerService() {
		super();
	}

	public Banner create() {
		Banner banner = new Banner();
		banner.setUrl(new String());
		Academy ac = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		banner.setAcademy(ac);
		return banner;
	}
	
	public Banner getRandom() {
		return bannerRepository.getRandom().iterator().next();
	}

	public Banner save(Banner banner) {
		Assert.notNull(banner);

		Banner aux = new Banner();
		Academy a = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		if (exists(banner.getId())) {

			aux = bannerRepository.findOne(banner.getId());
			aux.setUrl(banner.getUrl());

			return bannerRepository.save(aux);

		} else {

			aux = bannerRepository.save(banner);

			List<Banner> banners = a.getBanners();
			banners.add(aux);
			a.setBanners(banners);
			academyService.save(a);

		}

		return bannerRepository.save(banner);
	}
	

	public List<Banner> save(List<Banner> arg0) {
		return bannerRepository.save(arg0);
	}

	public Banner findOne(Integer id) {
		Assert.notNull(id);
		return bannerRepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return bannerRepository.exists(id);
	}

	public void delete(Banner banner) {
		Assert.notNull(banner);

		Academy ac = (Academy) loginService.findActorByUsername(LoginService.getPrincipal().getId());
		Assert.isTrue(ac.getBanners().contains(banner));
		List<Banner> banners = ac.getBanners();
		banners.remove(banner);

		ac.setBanners(banners);
		academyService.save(ac);

		bannerRepository.delete(banner);
	}

	public List<Banner> findAll() {
		return bannerRepository.findAll();
	}
	
	

	//other methods
	public List<Banner> listBannerActor(int a) {
		return bannerRepository.listBannerActor(a);
	}

	public void flush() {
		bannerRepository.flush();
	}
	
	

}
