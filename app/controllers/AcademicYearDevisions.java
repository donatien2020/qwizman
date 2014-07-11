package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.AcademicYearDevision;
import models.AccademicYear;
import models.Operator;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import play.mvc.With;
import utils.helpers.CustomerException;
import utils.helpers.UserType;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class AcademicYearDevisions extends Controller {
	public static SimpleDateFormat formater = new SimpleDateFormat("MM/dd/yyyy");

	public static void index() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<AcademicYearDevision> paginator = null;
			AccademicYear yearExist = AccademicYear.find(
					"school=? and yearStatus=?", currentUser.school,
					new Boolean(true)).first();
			if (yearExist != null) {
				paginator = yearExist.devisions;
			}
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(50);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("AcademicYearDevisions/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void addNew() {
		try {
			render("AcademicYearDevisions/form.html");
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		AcademicYearDevision accademicYearDivision = null;

		try {
			if (id != null && !id.isEmpty()) {

				accademicYearDivision = AcademicYearDevision.find("id=?", id)
						.first();
			} else
				msg = "The Id is empty";

		} catch (Exception e) {
			msg = "Internal error :" + e.getMessage();
			e.printStackTrace();
		}
		render("AcademicYearDevisions/show.html", accademicYearDivision, msg);
	}

	public static void create(String description, String devisionLabel,
			String startAt, String endAt) {
		try {
			if (Operators.getCurrentUser().school != null) {
				AccademicYear yearExist = AccademicYear.find(
						"school=? and yearStatus=?",
						Operators.getCurrentUser().school, new Boolean(true))
						.first();
				if (yearExist != null) {
					AcademicYearDevision divisionExist = AcademicYearDevision
							.find("accademicYear=? and devisionStatus=?",
									yearExist, new Boolean(true)).first();
					if (divisionExist == null) {
						Date start = formater.parse(startAt);
						Date end = formater.parse(endAt);
						if (start.compareTo(end) < 0) {
							if (end.compareTo(yearExist.endAt) <= 0) {
								AcademicYearDevision accademicYears = new AcademicYearDevision(
										description, devisionLabel, start, end,
										yearExist, Operators.getCurrentUser());
								accademicYears = accademicYears.save();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		index();
	}

	public static void modifyAcademicYearDevision(String yearDevisionId,
			String description, String devisionLabel, String startAt,
			String endAt) {
		try {
			String msg = "Accademic Year Division not modified!";
			if (yearDevisionId != null && !yearDevisionId.isEmpty()) {
				AcademicYearDevision accademicYearDev = AcademicYearDevision
						.find("id=?", yearDevisionId).first();
				if (accademicYearDev != null) {
					Date start = formater.parse(startAt);
					Date end = formater.parse(endAt);
					if (start.compareTo(end) < 0) {
						if (end.compareTo(accademicYearDev.accademicYear.endAt) <= 0) {
							accademicYearDev.description = description;
							accademicYearDev.devisionLabel = devisionLabel;
							accademicYearDev.startAt = start;
							accademicYearDev.endAt = end;
							accademicYearDev = accademicYearDev.save();
							msg = "Successifully Modified!";
						} else
							msg = "Accademic Year Division end date should be less than or eqwal to the end of the year";
					} else
						msg = "Accademic Year Division Start Date should be less tha its end date!";
				} else
					msg = "Accademic Year Division Not Found";
			}
			renderJSON(new CustomerException(msg));
		} catch (Exception e) {
			renderJSON(new CustomerException(e.getMessage()));
		}
	}

	public static AcademicYearDevision getCurrentDivision() {
		try {
			AccademicYear year = AccademicYears.getCurrentYear();
			if (year != null)
				return AcademicYearDevision.find(
						"accademicYear=? and devisionStatus=?", year,
						new Boolean(true)).first();
		} catch (Exception e) {
		}
		return null;
	}
}
