package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.AccademicYear;
import models.AccademicYear;
import models.Operator;
import models.School;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;
import utils.helpers.CustomerException;
import utils.helpers.UserType;
import utils.helpers.Utils;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class AccademicYears extends Controller {
	public static SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");

	public static void index() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<AccademicYear> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = AccademicYear.findAll();
			} else if (currentUser != null
					&& currentUser.typeOf.equals(UserType.ADMIN.getUserType())
					&& currentUser.school == null) {
				paginator = AccademicYear.findAll();
			} else if (currentUser != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()) && currentUser.school != null) {
				paginator = AccademicYear.find("school=?", currentUser.school)
						.fetch();
			}
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("AccademicYears/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void addNew() {
		try {
			render("AccademicYears/form.html");
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		AccademicYear accademicYear = null;

		try {
			if (id != null && !id.isEmpty()) {

				accademicYear = AccademicYear.find("id=?", id).first();
			} else
				msg = "The Id is empty";

		} catch (Exception e) {
			msg = "Internal error :" + e.getMessage();
			e.printStackTrace();
		}
		render("AccademicYears/show.html", accademicYear, msg);
	}

	public static void create(String description, String startAt, String endAt) {
		try {
			if (Operators.getCurrentUser().school != null) {
				AccademicYear yearExist = AccademicYear.find(
						"school=? and yearStatus=?",
						Operators.getCurrentUser().school, new Boolean(true))
						.first();
				if (yearExist == null) {
					AccademicYear accademicYears = new AccademicYear(
							description, formater.parse(startAt),
							formater.parse(endAt),
							Operators.getCurrentUser().school,
							Operators.getCurrentUser());
					accademicYears = accademicYears.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		index();
	}

	public static void modifyAccademicYear(String yearId, String description,
			String startAt, String endAt) {
		try {
			String msg = "Accademic Year not modified!";
			if (yearId != null && !yearId.isEmpty()) {
				AccademicYear accademicYears = AccademicYear.find("id=?",
						yearId).first();
				if (accademicYears != null) {
					accademicYears.description = description;
					accademicYears.startAt = formater.parse(startAt);
					accademicYears.endAt = formater.parse(endAt);
					accademicYears = accademicYears.save();
					msg = "Successifully Modified!";

				} else
					msg = "Accademic Year Not Found";
			}
			renderJSON(new CustomerException(msg));
		} catch (Exception e) {
			renderJSON(new CustomerException(e.getMessage()));
		}
	}

	public static void getAccademicYearsByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<AccademicYear> yearResult = AccademicYear.find(
						"description like ?", searchPatern).fetch();
				renderJSON(yearResult, new AccademicYearSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static AccademicYear getCurrentYear() {
		try {
			if (Operators.getCurrentUser().school != null)
				return AccademicYear.find("school=? and yearStatus=?",
						Operators.getCurrentUser().school, new Boolean(true))
						.first();
		} catch (Exception e) {
		}
		return null;
	}
}
