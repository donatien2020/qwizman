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
import models.Course;
import models.Evaluation;
import models.Operator;
import models.School;
import models.StudentClasse;
import models.TeacherClassCourse;
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
		ValuePaginator results = null;

		try {
			Operator currentUser = Operators.getCurrentUser();
			List<Classe> paginator = new ArrayList<Classe>();
			if (currentUser != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()) && currentUser.school != null) {
				paginator = Classe.find("school=?", currentUser.school).fetch();
			} else if (currentUser != null
					&& currentUser.typeOf != null
					&& currentUser.typeOf
							.equals(UserType.TEACHER.getUserType())
					&& currentUser.school != null) {

				paginator = getTeacherClasses(currentUser);

			} else {
				paginator = Classe.findAll();
			}
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(15);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}
			render("Classes/index.html", results);
		} catch (Exception e) {
			render("Classes/index.html", results);
		}

	}

	public static List<Classe> getTeacherClasses(Operator teacher) {
		List<Classe> classes = new ArrayList<Classe>();
		try {
			EntityManager em = JPA.em();
			classes = em
					.createQuery(
							"select DISTINCT ts.classe from TeacherClassCourse ts where ts.teacher=:teacher and ts.accademicYearDevision=:devision")
					.setParameter("teacher", teacher)
					.setParameter("devision",
							AcademicYearDevisions.getCurrentDivision())
					.getResultList();
		} catch (Exception e) {

		}
		return classes;
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
		String msg = "Class Dashboard";
		List<Operator> techers = null;
		List<Operator> students = null;
		List<Course> courses = null;
		Classe classe = null;
		try {
			techers = Operators.getTeachersList();
			students = Operators.getNotAssigndStudentsList();
			courses = Courses.getCourses();
			if (Utils.isLong(id)) {
				classe = Classe.findById(Long.parseLong(id));
				if (classe != null)
					courses = Courses.getNotAssignedCoursesByClasse(classe);
				msg = "Class " + classe.fullName + " Dashbord";
			}
		} catch (Exception e) {
		}
		render("Classes/dashboard.html", classe, msg, techers, students,
				courses);
	}

	public static void getStudenClass() {
		Classe classe = null;
		String msg = "Your Class Room Is Ready !";
		try {
			Operator student = Operators.getCurrentUser();
			classe = getStudentClasse(student);
		} catch (Exception e) {
		}
		render("Classes/dashboard.html", classe, msg);
	}

	public static Classe getStudentClasse(Operator student) {
		Classe classe = null;
		try {

			if (student != null && student.school != null
					&& student.role.name.equals(UserRole.STUDENT.getUserRole())) {
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				if (division != null) {
					StudentClasse classStd = StudentClasse.find(
							"student=? and accademicYearDevision=?", student,
							division).first();
					if (classStd != null) {
						classe = classStd.classe;

					}
				}
			}
		} catch (Exception e) {
		}
		return classe;
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
					if (students != null && students.size() > 0) {
						renderJSON(students, new StudentClasseSerializer());
					} else
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
							.find("student=? and classe=? and (accademicYear=? or accademicYear is null)",
									student, classe, division.accademicYear)
							.first();
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

	public static void loadClassTeacherCourses(String classId) {
		String msg = "Teachers Loading Started !";
		try {
			if (classId != null && Utils.isLong(classId)) {
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				Classe classe = Classe.findById(Long.parseLong(classId));
				if (division != null && classe != null) {
					List<TeacherClassCourse> teachers = TeacherClassCourse
							.find("classe=? and accademicYearDevision=?",
									classe, division).fetch();
					if (teachers != null && teachers.size() > 0) {
						renderJSON(teachers, new TeacherClassCourseSerializer());
					} else
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

	public static void addTeacherToClass(String classId, String teacherId,
			String courseId) {
		String msg = "Teachers Adding Started !";
		try {
			if (classId != null && Utils.isLong(classId) && teacherId != null
					&& Utils.isLong(teacherId)) {
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				Classe classe = Classe.findById(Long.parseLong(classId));
				Course course = Course.findById(Long.parseLong(courseId));
				Operator teacher = Operator.findById(Long.parseLong(teacherId));
				if (division != null && classe != null && teacher != null
						&& course != null) {
					TeacherClassCourse teacherClassExist = TeacherClassCourse
							.find("classe=? and course=? and (accademicYear=? or accademicYear is null)",
									classe, course, division.accademicYear)
							.first();
					if (teacherClassExist == null) {
						TeacherClassCourse teacherClassCourse = new TeacherClassCourse(
								teacher, classe, course,
								division.accademicYear, division,
								Operators.getCurrentUser());
						teacherClassCourse = teacherClassCourse.save();
						if (teacherClassCourse != null
								&& teacherClassCourse.teacher != null)
							msg = "Teacher And Course successifully added to this Class :"
									+ classe.fullName;
						else
							msg = "This Class Is Empty";
					} else
						msg = "This Teacher -course assignement Is Already done!";
				} else
					msg = "Accademic Year devision or classe or course not found";
			} else
				msg = "Invalid parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}

		renderJSON(new CustomerException(msg));
	}

	public static void getMyClassCourses(String classeid) {
		List<Course> courses = new ArrayList<Course>();

		ValuePaginator results = null;

		try {
			Operator user = Operators.getCurrentUser();
			if (user != null) {
				Classe classe = Classe.findById(Long.parseLong(classeid));
				AcademicYearDevision division = AcademicYearDevisions
						.getCurrentDivision();
				EntityManager em = JPA.em();
				if (user.typeOf.equals(UserType.HEADTEACHER.getUserType())) {
					courses = em
							.createQuery(
									"select DISTINCT ts.course from TeacherClassCourse ts where ts.classe=:classe and (ts.accademicYear=:accYear or ts.accademicYear is null)")
							.setParameter("classe", classe)
							.setParameter("accYear", division.accademicYear)
							.getResultList();
				} else if (user.typeOf.equals(UserType.TEACHER.getUserType())) {
					courses = em
							.createQuery(
									"select DISTINCT ts.course from TeacherClassCourse ts where ts.classe=:classe and ts.teacher=:teacher and (ts.accademicYear=:accYear or ts.accademicYear is null)")
							.setParameter("classe", classe)
							.setParameter("teacher", user)
							.setParameter("accYear", division.accademicYear)
							.getResultList();
				}

				if (courses != null && courses.size() > 0) {
					results = new ValuePaginator(courses);
					results.setPageSize(10);
					results.setBoundaryControlsEnabled(true);
					results.setPagesDisplayed(0);
				}
			}

		} catch (Exception e) {

		}

		render("Courses/index.html", results);

	}
}
