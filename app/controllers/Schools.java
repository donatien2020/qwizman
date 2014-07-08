package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ApplicationRole;
import models.Location;
import models.LocationLevel;

import models.Operator;
import models.School;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;
import utils.helpers.CustomerException;
import utils.helpers.UserRole;
import utils.helpers.UserType;
import utils.helpers.Utils;
import controllers.Secure.Security;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class Schools extends Controller {
	public static void index() {
		ValuePaginator results = null;
		try {
			List<School> paginator = School.findAll();
			results = new ValuePaginator(paginator);
			results.setPageSize(3);
			results.setBoundaryControlsEnabled(true);
			results.setPagesDisplayed(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		render("Schools/index.html", results);
	}

	public static void addNew() {
		try {
			render("Schools/form.html");
		} catch (Exception e) {

		}
	}

	public static void show(String id, String msg) {
		School school = null;
		try {
			if (Utils.isLong(id)) {
				school = School.findById(Long.parseLong(id));
			}
			render("Schools/show.html", school, msg);
		} catch (Exception e) {
		}
	}

	public static void create(String typeOf, String name, String description,
			String ownerFirstName, String ownerLastName,
			String ownerPhoneNumber, String ownerEmail, String poBox,
			String website, String category) {
		try {
			School school = new School(category, Utils.idGenerator(name),
					typeOf, name, description, ownerFirstName, ownerLastName,
					ownerPhoneNumber, ownerEmail, poBox, website,
					Operators.getCurrentUser());
			school = school.save();

		} catch (Exception e) {
		}
		index();
	}

	public static void modifyCompany(String companyId, String companyCategory,
			String typeOf, String schoolName, String description,
			String ownerFistName, String ownerLastName,
			String ownerPhoneNumber, String ownerEmail, String poBox,
			String webSite) {
		String msg = "School not modified!";
		try {
			if (companyId != null && companyId != "" && Utils.isLong(companyId)) {
				School company = School.findById(Long.parseLong(companyId));
				if (company != null) {
					company.typeOf = typeOf;
					company.schoolName = schoolName;
					company.description = description;
					company.ownerFirstName = ownerFistName;
					company.ownerLastName = ownerLastName;
					company.ownerEmail = ownerEmail;
					company.poBox = poBox;
					company.website = webSite;
					company = company.save();
					msg = "School Successifully Modified";
				} else
					msg = "School Not Found";
			} else
				msg = "School Id Is Invalid!";
		} catch (Exception e) {
			msg = "Internal processing Error :" + e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void getCompanysByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<School> orgResult = School
						.find("schoolName like ? or code like ? or description like ? or ownerFirstName like ? or ownerPhoneNumber like ? or ownerEmail like ?",
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern, searchPatern).fetch();
				renderJSON(orgResult, new SchoolSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void dashboard() {
		Operator user = Operators.getCurrentUser();
		School ownerr = user.school;
		render("Schools/dashboard.html", ownerr);
	}
}
