
package services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Academy;
import domain.Actor;
import domain.Administrator;
import domain.Dancer;
import domain.Folder;
import domain.MailMessage;
import repositories.FolderRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FolderService {

	//Repositories

	@Autowired
	private FolderRepository		folderRepository;
	@Autowired
	private AcademyService			academyService;
	@Autowired
	private DancerService			dancerService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private MailMessageService		mailMessageService;

	//Services


	//Constructor

	public FolderService() {
		super();
	}

	//CRUD Methods

	public List<Folder> folderOfSelf() {
		UserAccount userAccount = LoginService.getPrincipal();

		return folderRepository.folderOfSelf(userAccount.getUsername());
	}

	public Folder create() {
		Folder folder = new Folder();

		folder.setFolderName(new String());
		folder.setMessages(new ArrayList<MailMessage>());

		return folder;
	}

	public void delete(Folder entity) {
		Assert.notNull(entity);

		UserAccount userAccount = LoginService.getPrincipal();
		Actor actor = folderRepository.selectByUsername(userAccount.getUsername());
		actor.getFolders().remove(entity);

		if (actor instanceof Dancer) {
			dancerService.saveEditing((Dancer) actor);
		} else if (actor instanceof Academy) {
			academyService.saveEditing((Academy) actor);
		} else if (actor instanceof Administrator) {
			administratorService.saveEditing((Administrator) actor);
		}
		mailMessageService.delete(entity.getMessages());
		folderRepository.delete(entity);
	}

	public List<Folder> findAll() {
		return folderRepository.findAll();
	}

	public Folder findOne(Integer arg0) {
		Assert.notNull(arg0);

		return folderRepository.findOne(arg0);
	}

	public List<Folder> save(List<Folder> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());

		return folderRepository.save(entities);
	}

	public Folder saveCreate(Folder folder) {
		Assert.notNull(folder);
		
		Folder saved = folderRepository.save(folder);
		UserAccount userAccount = LoginService.getPrincipal();

		Actor actor = folderRepository.selectByUsername(userAccount.getUsername());
		actor.getFolders().add(saved);

		if (actor instanceof Academy) {
			academyService.saveEditing((Academy) actor);
		} else if (actor instanceof Dancer) {
			dancerService.saveEditing((Dancer) actor);
		} else if (actor instanceof Administrator) {
			administratorService.saveEditing((Administrator) actor);
		}

		return saved;
	}

	public Folder save(Folder arg0) {
		Assert.notNull(arg0);

		return folderRepository.save(arg0);
	}

	//Others Methods

	public List<Folder> foldersByActor(int actor_id) {
		Assert.notNull(actor_id);

		return folderRepository.foldersByActor(actor_id);
	}

	public List<Folder> createDefaultFolders() {
		List<Folder> folders = new ArrayList<Folder>();

		Folder inbox = create();
		inbox.setFolderName("inbox");
		inbox.setMessages(new LinkedList<MailMessage>());

		Folder outbox = create();
		outbox.setFolderName("outbox");
		outbox.setMessages(new LinkedList<MailMessage>());

		Folder trashbox = create();
		trashbox.setFolderName("trashbox");
		trashbox.setMessages(new LinkedList<MailMessage>());

		Folder spambox = create();
		spambox.setFolderName("spambox");
		spambox.setMessages(new LinkedList<MailMessage>());

		folders.add(inbox);
		folders.add(outbox);
		folders.add(trashbox);
		folders.add(spambox);

		return folders;
	}

	public void flush() {
		folderRepository.flush();
	}
	
	
}
