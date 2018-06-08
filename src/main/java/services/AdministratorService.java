
package services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Chirp;
import domain.Folder;
import domain.Style;
import domain.Tutorial;
import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	// Repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Service
	@Autowired
	private FolderService			folderService;


	// Constructor
	public AdministratorService() {
		super();
	}

	// CRUD Methods
	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);
		Administrator adm = null;

		if (exists(administrator.getId())) {
			adm = findOne(administrator.getId());

			adm.setActorName(administrator.getActorName());
			adm.setSurname(administrator.getSurname());
			adm.setEmail(administrator.getEmail());
			adm.setPhone(administrator.getPhone());
			adm.setAddress(administrator.getAddress());
			adm.setChirps(administrator.getChirps());
			adm.setFollower(administrator.getFollower());
			adm.setFolders(folderService.save(folderService.createDefaultFolders()));


			adm = administratorRepository.save(adm);
		} else {
			adm = administratorRepository.save(administrator);
		}
		return adm;
	}

	public boolean exists(Integer administratorID) {
		return administratorRepository.exists(administratorID);
	}

	// Other Methods

	public Object[] minAvgSdMaxCoursesPerAcademy() {
		return administratorRepository.minAvgSdMaxCoursesPerAcademy();
	}
	public Object[] minAvgSdMaxApplicationsPerCourse() {
		return administratorRepository.minAvgSdMaxApplicationsPerCourse();
	}

	public Object[] minAvgMaxTutorialsPerAcademy() {
		return administratorRepository.minAvgMaxTutorialsPerAcademy();
	}

	public Object[] minAvgMaxTutorialNumShows() {
		return administratorRepository.minAvgMaxTutorialNumShows();
	}

	public Double avgChirpsPerActor() {
		return administratorRepository.avgChirpsPerActor();
	}

	public Double avgSubscriptionPerActor() {
		return administratorRepository.avgSubscriptionPerActor();
	}

	public List<Tutorial> tutorialsOrderByNumShowsDes() {
		return administratorRepository.tutorialsOrderByNumShowsDes();
	}

	public Administrator findOne(Integer adminID) {
		return administratorRepository.findOne(adminID);
	}

	//Acme-dancer 2.0
	public Double ratioDancerByCurricula() {
		return administratorRepository.ratioDancerByCurricula();
	}

	public List<Style> styleByOrderCourses() {
		return administratorRepository.styleByOrderCourses();
	}

	public List<Style> styleDancerTeach() {
		return administratorRepository.styleDancerTeach();
	}

	public List<Array[]> avgFoldersPerActor() {
		return administratorRepository.avgFoldersPerActor();
	}

	public Double avgMailMessage() {
		return administratorRepository.avgMailMessage();
	}

	public List<Array[]> avgMailMessageSpamPerActor() {
		return administratorRepository.avgMailMessageSpamPerActor();
	}

	public List<Array[]> avgBannerByAcademy() {
		return administratorRepository.avgBannerByAcademy();
	}

	public Double ratioAcademyBanner() {
		return administratorRepository.ratioAcademyBanner();
	}

	public Administrator create() {
		Administrator administrator = new Administrator();
		administrator.setActorName(new String());
		administrator.setAddress(new String());
		administrator.setEmail(new String());
		administrator.setFollower(new ArrayList<Actor>());
		administrator.setPhone(new String());
		administrator.setSurname(new String());
		administrator.setChirps(new ArrayList<Chirp>());
		administrator.setFolders(new ArrayList<Folder>());

		Authority a = new Authority();
		a.setAuthority(Authority.ADMINISTRATOR);
		UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		administrator.setUserAccount(account);

		return administrator;

	}

	public Administrator saveEditing(Administrator admin) {
		Assert.notNull(admin);
		return administratorRepository.save(admin);
	}

}
