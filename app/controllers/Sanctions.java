package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.Fault;
import models.Sanction;
import models.Operator;
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
			String marks, String fault) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (name != "" && description != "" && marks != "" && fault != ""
					&& creator != null && fault != null
					&& Utils.isDouble(marks)) {
				Fault faultt = Fault.findById(Long.parseLong(fault));
				if (faultt != null) {
					Sanction sanction = new Sanction(creator.school, faultt,
							type, name, description, new BigDecimal(marks),
							creator);
					sanction = sanction.save();
				}
			}

		} catch (Exception e) {
		}
		index();
	}

	public static void modifySanction(String sanctionId, String type,
			String name, String description,String marks,String fault) {
		String msg = "Sanction not modified!";
		try {
			if (sanctionId != null && sanctionId != ""
					&& Utils.isLong(sanctionId)) {
				Sanction sanction = Sanction.findById(Long
						.parseLong(sanctionId));
				if (sanction != null) {
					Operator creator = Operators.getCurrentUser();
					if (creator != null) {
						Fault faultt = Fault.findById(Long.parseLong(fault));
						sanction.type = type;
						sanction.name = name;
						sanction.description = description;
						sanction.updatedBy = creator;
						if(faultt!=null)
							sanction.fault=faultt;
						sanction = sanction.save();
						msg = "Sanction Successifully Modified";
					} else
						msg = "Updator not available";
				} else
					msg = "Sanction Not Found";
			} else
				msg = "Sanction Id Is Invalid!";
		} catch (Exception e) {
			msg = "Internal processing Error :" + e.getMessage();
		}
		renderJSON(new CustomerException(msg));
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

}