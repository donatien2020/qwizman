package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.h2.constant.SysProperties;

import controllers.deadbolt.Deadbolt;

import models.ApplicationRole;
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
public class Operators extends Controller {
	public static void index() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<Operator> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = Operator.findAll();
			} else if (currentUser != null
					&& currentUser.school == null
					&& (currentUser.typeOf.equals(UserType.ADMIN.getUserType()) || currentUser.typeOf
							.equals(UserType.REPRESENTATOR.getUserType())))
				paginator = Operator.find(
						"createdBy=? and typeOf IN('HEADTEACHER','TEACHER') ",
						currentUser).fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()))
				paginator = Operator.find(
						"school=? and typeOf IN('TEACHER','STUDENT') ",
						currentUser.school).fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf
							.equals(UserType.TEACHER.getUserType()))
				paginator = Operator.find(
						"school=? and typeOf=? and createdBy=?) ",
						currentUser.school, UserType.STUDENT.getUserType(),
						currentUser).fetch();

			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Operators/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void getTeachers() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<Operator> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = Operator.find("typeOf=? ",
						UserType.TEACHER.getUserType()).fetch();
			} else if (currentUser != null
					&& currentUser.school == null
					&& (currentUser.typeOf.equals(UserType.ADMIN.getUserType()) || currentUser.typeOf
							.equals(UserType.REPRESENTATOR.getUserType())))
				paginator = Operator.find("typeOf=? AND createdBy=? ",
						UserType.TEACHER.getUserType(), currentUser).fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()))
				paginator = Operator.find("school=? AND typeOf=?",
						currentUser.school, UserType.TEACHER.getUserType())
						.fetch();

			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Operators/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void getStudents() {
		try {
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<Operator> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = Operator.find("typeOf=? ",
						UserType.STUDENT.getUserType()).fetch();
			} else if (currentUser != null
					&& currentUser.school == null
					&& (currentUser.typeOf.equals(UserType.ADMIN.getUserType()) || currentUser.typeOf
							.equals(UserType.REPRESENTATOR.getUserType())))
				paginator = Operator.find("typeOf=? AND createdBy=? ",
						UserType.STUDENT.getUserType(), currentUser).fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()))
				paginator = Operator.find("school=? AND typeOf=?",
						currentUser.school, UserType.STUDENT.getUserType())
						.fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf
							.equals(UserType.TEACHER.getUserType()))
				paginator = Operator.find(
						"school=? AND typeOf=? AND createdBy=? ",
						currentUser.school, UserType.STUDENT.getUserType(),
						currentUser).fetch();

			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Operators/index.html", results);
		} catch (Exception e) {
		}

	}

	public static void addNew() {
		try {
			List<ApplicationRole> roles = getRoles();
			List<School> schools = getSchools();
			render("Operators/form.html", roles, schools);
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		Operator user = null;
		List<ApplicationRole> roles = null;
		List<School> schools = new ArrayList<School>();
		schools.add(new School("", "", "", "Select School", "", "", "", "", "",
				"", "", null));
		schools.addAll(getSchools());
		try {
			if (Utils.isLong(id)) {
				user = Operator.findById(Long.parseLong(id));
				roles = getRoles();

			}
		} catch (Exception e) {
		}
		render("Operators/show.html", user, roles, schools, msg);
	}

	public static void create(String firstName, String lastName,
			String phoneNumber, String emailAddress, String username,
			String password, String physicalAddress, String box,
			String webSite, String role, String school) {
		try {
			ApplicationRole roleApp = null;
			if (Utils.isLong(role)) {
				roleApp = ApplicationRole.findById(Long.parseLong(role));
			}
			School schoolOb = null;
			if (school != null && !school.isEmpty() && Utils.isLong(school)) {
				schoolOb = School.findById(Long.parseLong(school));
			}
			if (schoolOb == null)
				schoolOb = getCurrentUser().school;
			Operator operator = new Operator(firstName, lastName, phoneNumber,
					emailAddress, username, password, physicalAddress, box,
					webSite, Utils.idGenerator(password), roleApp);
			operator.createdBy = getCurrentUser();
			operator.school = schoolOb;
			operator = operator.save();
		} catch (Exception e) {
		}

		index();
	}

	public static void modifyUser(String userId, String firstName,
			String lastName, String phoneNumber, String emailAddress,
			String username, String password, String physicalAddress,
			String box, String webSite, String role, String school) {
		try {
			String msg = "Operator not modified!";
			School schoolOb = null;
			if (Utils.isLong(school)) {
				schoolOb = School.findById(Long.parseLong(school));
			}
			if (Utils.isLong(userId)) {
				Operator user = Operator.findById(Long.parseLong(userId));
				if (user != null && role != null && !role.isEmpty()) {
					ApplicationRole roleApp = ApplicationRole.findById(Long
							.parseLong(role));
					if (roleApp != null) {
						user.firstName = firstName;
						user.lastName = lastName;
						user.phoneNumber = phoneNumber;
						user.emailAddress = emailAddress;
						user.username = username;
						if (password != null && password != ""
								&& !password.isEmpty() && password.length() > 5) {
							String salt = Utils.idGenerator(password);
							user.password = user.encriptThis(password, salt);
							user.salt = salt;
						}
						user.physicalAddress = physicalAddress;
						user.box = box;
						user.webSite = webSite;
						user.role = roleApp;
						user.createdBy = getCurrentUser();
						if (schoolOb != null)
							user.school = schoolOb;
						user.typeOf = roleApp.name;
						user = user.save();
						msg = "Successifully Modified!";
					} else
						msg = "Role Not found !";
				} else
					msg = "Operator Not Found";
			}
			renderJSON(new CustomerException(msg));
		} catch (Exception e) {
		}
	}

	public static List<ApplicationRole> getRoles() {
		Operator currentUser = getCurrentUser();
		try {
			if (currentUser != null) {
				if (currentUser.typeOf
						.equals(UserType.SUPERADMIN.getUserType())) {
					return ApplicationRole
							.find("name=? or name=? or name=? or name=? or name=? or name=? or name=?",
									UserRole.ADMIN.getUserRole(),
									UserRole.SUPERADMIN.getUserRole(),
									UserRole.HEADTEACHER.getUserRole(),
									UserRole.TEACHER.getUserRole(),
									UserRole.STUDENT.getUserRole(),
									UserRole.REPRESENTATOR.getUserRole(),
									UserRole.ANONIMOUS.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.ADMIN
						.getUserType())) {
					return ApplicationRole.find("name=? or name=? or name=?",
							UserRole.HEADTEACHER.getUserRole(),
							UserRole.TEACHER.getUserRole(),
							UserRole.STUDENT.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.REPRESENTATOR
						.getUserType())) {
					return ApplicationRole.find("name=? or name=? or name=?",
							UserRole.HEADTEACHER.getUserRole(),
							UserRole.TEACHER.getUserRole(),
							UserRole.STUDENT.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.HEADTEACHER
						.getUserType())) {
					return ApplicationRole.find("name=? or name=?",
							UserRole.TEACHER.getUserRole(),
							UserRole.STUDENT.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.TEACHER
						.getUserType())) {
					return ApplicationRole.find("name=? ",
							UserRole.STUDENT.getUserRole()).fetch();

				}
			}
		} catch (Exception e) {
		}
		return null;
	}

	public static List<School> getSchools() {
		try {
			Operator currentUser = getCurrentUser();
			if (currentUser != null) {
				if (currentUser.typeOf
						.equals(UserType.SUPERADMIN.getUserType())
						&& currentUser.school == null) {
					return School.findAll();
				} else if (currentUser.school != null) {
					return School.find("code=?", currentUser.school.code)
							.fetch();
				}

			}
		} catch (Exception e) {
		}
		return null;
	}

	public static void getUsersByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				System.out.println("criterion =========================================================: "+criterion);
				String searchPatern = "%" + criterion + "%";
				List<Operator> opeatorResult = Operator
						.find("username like ? or firstName like ? or lastName like ? or phoneNumber like ? or emailAddress like ?",
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern).fetch();
				System.out.println(" opeatorResult :"+opeatorResult);
				renderJSON(opeatorResult, new OperatorSerializer(),
						new ApplicationRoleSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static Operator getCurrentUser() {
		try {
			return Operator.find("byUsername", Security.connected()).first();
		} catch (Exception e) {
			return null;
		}
	}

}
