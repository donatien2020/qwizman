package controllers;

import java.util.ArrayList;
import java.util.List;

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
import utils.helpers.StudentStatus;
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
			List<Operator> tuturaires = Operators.getTeachersList();
			render("Classes/form.html", tuturaires);
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

	public static void dashboard(String id) {
		Classe classe = null;
		String msg = "Class Dashboard";
		List<Operator> techers = Operators.getTeachersList();
		List<Operator> students = Operators.getStudentsList();
		try {
			if (Utils.isLong(id)) {
				classe = Classe.findById(Long.parseLong(id));
				msg = "Class " + classe.fullName + " Dashbord";
			}
		} catch (Exception e) {
		}
		render("Classes/dashboard.html", classe, msg, techers, students);
	}

	public static void create(String fullName, String emailAddress,
			String phoneNumber, String physicalAddress, String box,
			String webSite, String classlabel, String classlevel,
			String tuturaire) {
		try {
			if (Operators.getCurrentUser().school != null
					&& Utils.isLong(tuturaire)) {
				Operator tutu = Operator.findById(Long.parseLong(tuturaire));
				Classe classe = new Classe(fullName, emailAddress, phoneNumber,
						physicalAddress, box, webSite, classlabel, classlevel,
						Operators.getCurrentUser().school,
						Operators.getCurrentUser(), tutu);
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

	public static void loadClassStudents(String classId) {
		String msg = "Students Loading Started !";
		try {
			if (classId != null && Utils.isLong(classId)) {
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				Classe classe = Classe.findById(Long.parseLong(classId));
				if (division != null && classe != null) {
					List<StudentClasse> students = StudentClasse.find(
							"classe=? and accademicYearDevision=?", classe,
							division).fetch();
					if (students != null && students.size() > 0){
						System.out.println(" serrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
						renderJSON(students,new StudentClasseSerializer());
					}else
						msg = "This Class Is Empty";
				} else
					msg = "Accademic Year devision or classe not found";
			} else
				msg = "Invalid Class Id";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void addStudentToClass(String classId, String studentId) {
		String msg = "Students Adding Started !";
		try {
			if (classId != null && Utils.isLong(classId) && studentId != null
					&& Utils.isLong(studentId)) {
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				Classe classe = Classe.findById(Long.parseLong(classId));
				Operator student = Operator.findById(Long.parseLong(studentId));
				if (division != null && classe != null && student != null) {
					StudentClasse studentClassExist = StudentClasse
							.find("student=? and classe=? and accademicYearDevision=?",
									student, classe, division).first();
					if (studentClassExist == null) {
						StudentClasse studentClass = new StudentClasse(student,
								classe,
								StudentStatus.ACTIVE.getStudentStatus(),
								Operators.getCurrentUser(), division);
						studentClass = studentClass.save();
						if (studentClass != null
								&& studentClass.student != null)
							msg = "Student successifully added";
						else
							msg = "This Class Is Empty";
					} else
						msg = "This Student Is Already In this class!";
				} else
					msg = "Accademic Year devision or classe not found";
			} else
				msg = "Invalid Class Id";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}
}
