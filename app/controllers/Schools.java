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
		List<Location> intaras = null;
		try {
			LocationLevel inatara = LocationLevel.find("code=?", "002").first();
			if (inatara != null)
				intaras = Location.find("level=?", inatara).fetch();
			render("Schools/form.html", intaras);
		} catch (Exception e) {

		}
		render("Schools/form.html", intaras);
	}

	public static void show(String id, String msg) {
		School school = null;
		List<Location> intaras = null;
		try {
			if (Utils.isLong(id)) {
				school = School.findById(Long.parseLong(id));
				LocationLevel inatara = LocationLevel.find("code=?", "002")
						.first();
				if (inatara != null)
					intaras = Location.find("level=?", inatara).fetch();
			}
			render("Schools/show.html", school, msg,
					intaras);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void create(String tinNumber, String typeOf, String name,
			String description, String ownerFirstName, String ownerLastName,
			String ownerPhoneNumber, String ownerEmail, String poBox,
			String website, String level, String umudugudu) {
		try {
			if (level != null && Utils.isLong(level) && umudugudu != "") {
				Location locationObj = Location.find("code=?", umudugudu)
						.first();
				if (locationObj != null) {
					School school = new School();
					school.code = Utils.idGenerator(name);
					school.creator = Operators.getCurrentUser();
					school = school.save();
				}
			}
			index();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void modifyCompany(String companyId, String tinNumber,
			String typeOf, String name, String description,
			String ownerFirstName, String ownerLastName,
			String ownerPhoneNumber, String ownerEmail, String poBox,
			String website, String level, String location) {
		String msg = "School not modified!";
		try {
			if (companyId != null && companyId != "" && Utils.isLong(companyId)
					&& Utils.isLong(level) && location != "") {
				School company = School.findById(Long
						.parseLong(companyId));
				Location locationObj = Location.find("code=?", location)
						.first();
				if (company != null) {
					company.tinNumber = tinNumber;
					company.typeOf = typeOf;
					company.name = name;
					company.description = description;
					company.ownerFirstName = ownerFirstName;
					company.ownerLastName = ownerLastName;
					company.ownerEmail = ownerEmail;
					company.poBox = poBox;
					company.website = website;
					if (locationObj != null)
						company.location = locationObj;
					company = company.save();
					msg = "Successifully Modified";
				} else
					msg = "School Not Found";
			} else
				msg = "Company Id Is Invalid!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void getCompanysByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<School> orgResult = School
						.find("tinNumber like ? or name like ? or code like ? or name like ? or description like ? or ownerFirstName like ? or ownerPhoneNumber like ? or ownerEmail like ?",
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern).fetch();
				renderJSON(orgResult, new SchoolSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void dashboard() {
		System.out.println(" go to dashboard ======================");
		Operator user=Operators.getCurrentUser();
		School ownerr=user.school;
		System.out.println(" go to dashboard rendering ok  ======================");

		render("Schools/dashboard.html",ownerr);
	}
}
