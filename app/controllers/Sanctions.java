package controllers;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import models.Displine;
import models.Fault;
import models.Sanction;
import models.Operator;
import play.db.jpa.JPA;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import utils.helpers.CustomerException;
import utils.helpers.Utils;

public class Sanctions extends Controller {
	public static void index() {
		ValuePaginator results = null;
		try {
			List<Sanction> paginator = Sanction.findAll();
			results = new ValuePaginator(paginator);
			results.setPageSize(20);
			results.setBoundaryControlsEnabled(true);
			results.setPagesDisplayed(0);
		} catch (Exception e) {
		}
		render("Sanctions/index.html", results);
	}

	public static void addNew() {
		try {
			render("Sanctions/form.html");
		} catch (Exception e) {

		}
	}

	public static void show(String id, String msg) {
		Sanction sanction = null;
		try {
			if (Utils.isLong(id)) {
				sanction = Sanction.findById(Long.parseLong(id));
			}
			render("Sanctions/show.html", sanction, msg);
		} catch (Exception e) {
		}
	}

	public static void create(String type, String name, String description,
			String marks) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (name != "" && description != "" && marks != ""
					&& creator != null && Utils.isDouble(marks)
					&& Double.parseDouble(marks) <= 10) {

				Sanction sanction = new Sanction(creator.school, type, name,
						description, new BigDecimal(marks), creator);
				sanction = sanction.save();

			}

		} catch (Exception e) {
		}
		index();
	}

	public static void modifySanction(String sanctionId, String type,
			String name, String description, String marks) {
		String msg = "Sanction not modified!";
		try {
			System.out.println(" marks modifySanction:" + marks);

			if (sanctionId != null && sanctionId != ""
					&& Utils.isLong(sanctionId) && marks != null
					&& Utils.isDouble(marks) && Double.parseDouble(marks) <= 10) {
				Sanction sanction = Sanction.findById(Long
						.parseLong(sanctionId));
				if (sanction != null) {
					Operator creator = Operators.getCurrentUser();
					if (creator != null) {
						sanction.type = type;
						sanction.name = name;
						sanction.marks = new BigDecimal(marks);
						sanction.description = description;
						sanction.updatedBy = creator;
						sanction = sanction.save();
						msg = "Sanction Successifully Modified";
					} else
						msg = "Updator not available";
				} else
					msg = "Sanction Not Found";
			} else
				msg = "Sanction Id Is Invalid!";
			System.out.println("msg :" + msg);
		} catch (Exception e) {
			msg = "Internal processing Error :" + e.getMessage();
		}
		System.out.println("msg :" + msg);
		index();
	}

	public static void getSanctionsByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<Sanction> orgResult = Sanction.find(
						"name like ? or description like ?", searchPatern,
						searchPatern).fetch();
				renderJSON(orgResult, new SanctionSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void getAssignableSanctions(String fault) {
		if (fault != null && !fault.isEmpty() && Utils.isLong(fault)) {
			try {
				Operator currentUser = Operators.getCurrentUser();
				List<Sanction> sanctions = null;
				Fault foultObj = Fault.findById(Long.parseLong(fault));
				if (foultObj != null) {
					EntityManager em = JPA.em();
					sanctions = em
							.createQuery(
									"select DISTINCT sanct from Sanction sanct where sanct NOT IN (select DISTINCT dsplne.sanction from Displine dsplne where dsplne.fault=:fault) AND sanct.school=:school")
							.setParameter("fault", foultObj)
							.setParameter("school", currentUser.school)
							.getResultList();
					renderJSON(sanctions, new SanctionSerializer());
				}
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void addSanctionToFault(String fault, String sanction) {
		String msg = "Assignment Failed";
		if (fault != null && !fault.isEmpty() && sanction != null
				&& !sanction.isEmpty() && Utils.isLong(sanction)
				&& Utils.isLong(fault)) {
			try {
				Operator currentUser = Operators.getCurrentUser();
				if (currentUser != null) {
					Fault foultObj = Fault.findById(Long.parseLong(fault));
					Sanction sanctionObj = Sanction.findById(Long
							.parseLong(sanction));
					if (foultObj != null && sanctionObj != null) {
						Displine displine = new Displine(foultObj, sanctionObj,
								currentUser);
						displine = displine.save();
					} else
						msg = "Objects Not Retrievables";
				} else
					msg = "Operator Not Found";
			} catch (Exception e) {
				msg = "Internal Proccessing Problem :" + e.getMessage();
			}
		} else
			msg = "Invalid data from form !";
		renderJSON(new CustomerException(msg));

	}

}