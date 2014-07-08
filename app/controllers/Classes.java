package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.h2.constant.SysProperties;

import controllers.deadbolt.Deadbolt;

import models.ApplicationRole;
import models.Classe;
import models.Operator;
import models.School;
import play.db.jpa.JPA;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;
import utils.helpers.CustomerException;
import utils.helpers.UserRole;
import utils.helpers.UserType;
import utils.helpers.Utils;

@With(Deadbolt.class)
public class Classes extends Controller {
	public static void index() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<Classe> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()) && currentUser.school != null) {
				paginator = Classe.find("school=?", currentUser.school).fetch();
			}
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Classes/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void addNew() {
		try {
			render("Classes/form.html");
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		Classe classe = null;

		try {
			if (Utils.isLong(id)) {
				classe = Classe.findById(Long.parseLong(id));
			}
		} catch (Exception e) {
		}
		render("Classes/show.html", classe, msg);
	}

	public static void create(String fullName, String emailAddress,
			String phoneNumber, String physicalAddress, String box,
			String webSite, String classlabel, String classlevel) {
		try {
			if (Operators.getCurrentUser().school != null) {
				Classe classe = new Classe(fullName, emailAddress, phoneNumber,
						physicalAddress, box, webSite, classlabel, classlevel,
						Operators.getCurrentUser().school,
						Operators.getCurrentUser());
				classe = classe.save();
			}
		} catch (Exception e) {
		}

		index();
	}

	public static void modifyClasse(String classId, String fullName,
			String emailAddress, String phoneNumber, String physicalAddress,
			String box, String webSite, String classlabel, String classlevel) {
		try {
			String msg = "Class not modified!";
			if (Utils.isLong(classId)) {
				Classe classe = Classe.findById(Long.parseLong(classId));
				if (classe != null) {
					classe.fullName = fullName;
					classe.phoneNumber = phoneNumber;
					classe.emailAddress = emailAddress;
					classe.physicalAddress = physicalAddress;
					classe.box = box;
					classe.webSite = webSite;
					classe.classlabel = classlabel;
					classe.classlevel = classlevel;
					classe = classe.save();
					msg = "Successifully Modified!";

				} else
					msg = "Class Not Found";
			}
			renderJSON(new CustomerException(msg));
		} catch (Exception e) {
		}
	}

	public static void getClassesByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<Classe> opeatorResult = Classe
						.find("fullName like ? or emailAddress like ? or phoneNumber like ? or physicalAddress like ? or box like ?",
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern).fetch();
				renderJSON(opeatorResult, new OperatorSerializer(),
						new ClassSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

}
