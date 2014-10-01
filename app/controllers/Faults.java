package controllers;

import java.util.List;

import models.Operator;
import models.Fault;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import utils.helpers.CustomerException;
import utils.helpers.Utils;

public class Faults extends Controller {
	public static void index() {
		ValuePaginator results = null;
		try {
			List<Fault> paginator = Fault.findAll();
			results = new ValuePaginator(paginator);
			results.setPageSize(20);
			results.setBoundaryControlsEnabled(true);
			results.setPagesDisplayed(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		render("Faults/index.html", results);
	}

	public static void addNew() {
		try {
			render("Faults/form.html");
		} catch (Exception e) {

		}
	}

	public static void show(String id, String msg) {
		Fault fault = null;
		try {
			if (Utils.isLong(id)) {
				fault = Fault.findById(Long.parseLong(id));
			}
			render("Faults/show.html", fault, msg);
		} catch (Exception e) {
		}
	}

	public static void create(String type, String name, String description) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (creator != null) {
				Fault fault = new Fault(name, description, type,
						creator.school, creator);
				fault = fault.save();
			}

		} catch (Exception e) {
		}
		index();
	}

	public static void modifyFault(String faultId, String type, String name,
			String description) {
		String msg = "Fault not modified!";
		try {
			if (faultId != null && faultId != "" && Utils.isLong(faultId)) {
				Fault fault = Fault.findById(Long.parseLong(faultId));
				if (fault != null) {
					Operator creator = Operators.getCurrentUser();
					if (creator != null) {
						fault.type = type;
						fault.name = name;
						fault.description = description;
						fault.updator = creator;
						fault = fault.save();
						msg = "Fault Successifully Modified";
					} else
						msg = "Updator not available";
				} else
					msg = "Fault Not Found";
			} else
				msg = "Fault Id Is Invalid!";
		} catch (Exception e) {
			msg = "Internal processing Error :" + e.getMessage();
		}
		index();
	}

	public static void getFaultsByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<Fault> orgResult = Fault
						.find("name like ? or description like ?",
								searchPatern, searchPatern)
						.fetch();
				renderJSON(orgResult, new FaultSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

}
