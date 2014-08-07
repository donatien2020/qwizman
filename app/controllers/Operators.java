package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.h2.constant.SysProperties;

import controllers.deadbolt.Deadbolt;
import models.AcademicYearDevision;
import models.ApplicationRole;
import models.Classe;
import models.Operator;
import models.School;
import models.StudentClasse;
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
			String msg = "all";
			Operator currentUser = Operators.getCurrentUser();
			ValuePaginator results = null;
			List<Operator> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = Operator.findAll();
			} else if (currentUser != null
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
			render("Operators/index.html", results, msg);
		} catch (Exception e) {
		}

	}

	public static void getTeachers() {
		try {
			String msg = "teachers";
			ValuePaginator results = null;
			List<Operator> paginator = getTeachersList();
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Operators/index.html", results, msg);
		} catch (Exception e) {
		}

	}

	public static List<Operator> getTeachersList() {
		List<Operator> teachers = null;
		try {
			Operator currentUser = Operators.getCurrentUser();
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				teachers = Operator.find("typeOf=? ",
						UserType.TEACHER.getUserType()).fetch();
			} else if (currentUser != null
					&& currentUser.school == null
					&& (currentUser.typeOf.equals(UserType.ADMIN.getUserType()) || currentUser.typeOf
							.equals(UserType.REPRESENTATOR.getUserType())))
				teachers = Operator.find("typeOf=? AND createdBy=? ",
						UserType.TEACHER.getUserType(), currentUser).fetch();
			else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()))
				teachers = Operator.find("school=? AND typeOf=?",
						currentUser.school, UserType.TEACHER.getUserType())
						.fetch();
		} catch (Exception e) {
		}
		return teachers;
	}

	public static void getStudents() {
		try {
			String msg = "students";
			ValuePaginator results = null;
			List<Operator> paginator = getStudentsList();

			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Operators/index.html", results, msg);
		} catch (Exception e) {
		}

	}

	public static List<Operator> getStudentsList() {
		List<Operator> paginator = null;
		Operator currentUser = Operators.getCurrentUser();

		if (currentUser != null
				&& currentUser.typeOf.equals(UserType.SUPERADMIN.getUserType())
				&& currentUser.school == null) {
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
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType()))
			paginator = Operator.find("school=? AND typeOf=?",
					currentUser.school, UserType.STUDENT.getUserType()).fetch();
		else if (currentUser != null && currentUser.school != null
				&& currentUser.typeOf.equals(UserType.TEACHER.getUserType()))
			paginator = Operator.find("school=? AND typeOf=? AND createdBy=? ",
					currentUser.school, UserType.STUDENT.getUserType(),
					currentUser).fetch();
		return paginator;
	}

	public static void addNew(String pageParam) {
		try {
			List<ApplicationRole> roles = getRoles(pageParam);
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
				roles = getRoles(msg);

			}
		} catch (Exception e) {
		}
		render("Operators/show.html", user, roles, schools, msg);
	}

	public static void create(String firstName, String lastName,
			String phoneNumber, String emailAddress, String username,
			String password, String physicalAddress, String box,
			String webSite, String role, String school, String degree) {
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
					webSite, Utils.idGenerator(password), roleApp, degree);
			operator.createdBy = getCurrentUser();
			operator.school = schoolOb;
			operator = operator.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		index();
	}

	public static void modifyUser(String userId, String firstName,
			String lastName, String phoneNumber, String emailAddress,
			String username, String password, String physicalAddress,
			String box, String webSite, String role, String school,
			String degree) {
		String msg = "Operator not modified!";

		try {
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
						user.degree = degree;
						if (password != null && password != ""
								&& !password.isEmpty()) {
							String salt = Utils.idGenerator(password);
							user.password = user.encriptThis(password, salt);
							user.salt = salt;
						} else
							System.out.println(" unverfied condition ");
						user.physicalAddress = physicalAddress;
						user.box = box;
						user.webSite = webSite;
						user.role = roleApp;
						user.createdBy = getCurrentUser();
						if (schoolOb != null)
							user.school = schoolOb;
						else
							user.school = getCurrentUser().school;
						user.typeOf = roleApp.name;
						user.save();
						msg = "Successifully Modified !!";
					} else
						msg = "Role Not found !";
				} else
					msg = "Operator Not Found";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJSON(new CustomerException(msg));
	}

	public static List<ApplicationRole> getRoles(String pageParam) {
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
					return ApplicationRole.find("name=? or name=?",
							UserRole.HEADTEACHER.getUserRole(),
							UserRole.REPRESENTATOR.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.REPRESENTATOR
						.getUserType())) {
					return ApplicationRole.find("name=?",
							UserRole.HEADTEACHER.getUserRole()).fetch();

				} else if (currentUser.typeOf.equals(UserType.HEADTEACHER
						.getUserType())) {
					if (pageParam != null && pageParam.equals("teachers"))
						return ApplicationRole.find("name=?",
								UserRole.TEACHER.getUserRole()).fetch();
					else if (pageParam != null && pageParam.equals("students"))
						return ApplicationRole.find("name=?",
								UserRole.STUDENT.getUserRole()).fetch();
					else
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
				} else if (currentUser.typeOf.equals(UserType.REPRESENTATOR
						.getUserType())
						|| currentUser.typeOf.equals(UserType.ADMIN
								.getUserType())) {
					return School.find("creator=?", currentUser).fetch();
				} else if (currentUser.typeOf.equals(UserType.HEADTEACHER
						.getUserType()) && currentUser.school != null) {
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
				String searchPatern = "%" + criterion + "%";
				List<Operator> opeatorResult = Operator
						.find("username like ? or firstName like ? or lastName like ? or phoneNumber like ? or emailAddress like ?",
								searchPatern, searchPatern, searchPatern,
								searchPatern, searchPatern).fetch();
				renderJSON(opeatorResult, new OperatorSerializer(),
						new ApplicationRoleSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void getMyStudentsByClass() {
		String msg = "Student List";
		List<Operator> students = new ArrayList<Operator>();
		ValuePaginator results = null;
		try {
			EntityManager em = JPA.em();
			List<Classe> classes = Classes.getTeacherClasses(getCurrentUser());

			AcademicYearDevision division = AcademicYearDevisions
					.getCurrentDivision();
			List<StudentClasse> stds = em
					.createQuery(
							"select ts from StudentClasse ts where ts.classe in (:classes) and ts.accademicYearDevision=:devision")
					.setParameter("classes", classes)
					.setParameter("devision", division).getResultList();
			for (StudentClasse student : stds)
				students.add(student.student);
			if (students != null && students.size() > 0) {
				results = new ValuePaginator(students);
				results.setPageSize(30);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
		} catch (Exception e) {

		}
		render("Operators/students.html", results, msg);
	}

	public static Operator getCurrentUser() {
		try {
			return Operator.find("byUsername", Security.connected()).first();
		} catch (Exception e) {
			return null;
		}
	}

	public static void getOperator(String username) {
		Operator operator = null;
		try {
			if (username != null && !username.isEmpty()) {
				operator = Operator.find("byUsername", username).first();
			} else
				operator = getCurrentUser();

		} catch (Exception e) {

		}
		render("Operators/dashboard.html", operator);
	}
}
