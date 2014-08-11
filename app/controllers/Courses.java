package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

import models.AcademicYearDevision;
import models.AccademicYear;
import models.Classe;
import models.Course;
import models.Operator;
import models.School;
import models.StudentClasse;
import models.TeacherClassCourse;
import play.db.jpa.JPA;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import utils.helpers.CustomerException;
import utils.helpers.StudentStatus;
import utils.helpers.UserType;
import utils.helpers.Utils;

public class Courses extends Controller {
	public static void index() {
		ValuePaginator results = null;
		try {
			List<Course> paginator = getCourses();
			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(10);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}

		} catch (Exception e) {
		}
		render("Courses/index.html", results);
	}

	public static List<Course> getCourses() {
		Operator currentUser = Operators.getCurrentUser();
		List<Course> paginator = new ArrayList<Course>();
		AcademicYearDevision accDiv = AcademicYearDevisions
				.getCurrentDivision();

		if (currentUser != null
				&& currentUser.typeOf != null
				&& (currentUser.typeOf
						.equals(UserType.SUPERADMIN.getUserType())
						|| currentUser.typeOf.equals(UserType.REPRESENTATOR
								.getUserType()) || currentUser.typeOf
							.equals(UserType.ADMIN.getUserType()))
				&& currentUser.school == null) {
			paginator = Course.findAll();
		} else if (currentUser != null
				&& currentUser.typeOf != null
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType())
				&& currentUser.school != null) {
			paginator = Course.find("school=?", currentUser.school).fetch();
		} else if (currentUser != null && currentUser.typeOf != null
				&& currentUser.typeOf.equals(UserType.TEACHER.getUserType())
				&& currentUser.school != null) {
			if (accDiv != null) {
				List<TeacherClassCourse> teacherClasses = TeacherClassCourse
						.find("teacher=? and (accademicYear=? or accademicYear is null)",
								currentUser, accDiv.accademicYear).fetch();
				for (TeacherClassCourse classe : teacherClasses)
					paginator.add(classe.course);
				List<Course> courses = new ArrayList<Course>();
				courses = Course.find("creator=?", currentUser).fetch();
				if (courses != null)
					paginator.addAll(courses);
			}
		} else if (currentUser != null && currentUser.typeOf != null
				&& currentUser.typeOf.equals(UserType.STUDENT.getUserType())
				&& currentUser.school != null) {
			if (accDiv != null) {
				List<TeacherClassCourse> teacherClasses = TeacherClassCourse
						.find("teacher=? and (accademicYear=? or accademicYear is null)",
								currentUser, accDiv.accademicYear).fetch();
				for (TeacherClassCourse classee : teacherClasses)
					paginator.add(classee.course);
			}
		} else
			paginator = Course.find("creator=?", currentUser).fetch();
		return paginator;
	}

	public static List<Course> getMyCourses() {
		Operator currentUser = Operators.getCurrentUser();
		List<Course> courses = new ArrayList<Course>();
		AcademicYearDevision accDiv = AcademicYearDevisions
				.getCurrentDivision();
		if (currentUser != null
				&& currentUser.typeOf != null
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType())
				&& currentUser.school != null) {
			courses = Course.find("school=?", currentUser.school).fetch();

		} else {
			if (accDiv != null) {
				List<TeacherClassCourse> teacherClasses = TeacherClassCourse
						.find("teacher=? and (accademicYear=? or accademicYear is null)",
								currentUser, accDiv.accademicYear).fetch();

				for (TeacherClassCourse tcc : teacherClasses) {
					courses.add(tcc.course);
				}
			}

		}

		return courses;
	}

	public static void addNew() {
		try {
			List<Operator> tuturaires = Operators.getTeachersList();
			render("Courses/form.html", tuturaires);
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		Course course = null;

		try {
			if (Utils.isLong(id)) {
				course = Course.findById(Long.parseLong(id));
			}
		} catch (Exception e) {
		}
		render("Courses/show.html", course, msg);
	}

	public static void dashboard(String id) {
		Course course = null;
		String msg = "Course Dashboard";
		try {
			if (Utils.isLong(id)) {
				course = Course.findById(Long.parseLong(id));
				msg = "Course " + course.name + " Dashbord";
			}
		} catch (Exception e) {
		}
		render("Courses/dashboard.html", course, msg);
	}

	public static void create(String name, String content, String overTj,
			String overEx) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (name != "" && content != "" && creator != null
					&& creator.school != null && Utils.isDouble(overTj)
					&& Utils.isDouble(overEx)) {
				if (Double.parseDouble(overTj) >= 5
						&& Double.parseDouble(overEx) >= 5) {
					Course course = new Course(Utils.idGenerator(name), name,
							content, creator.school, new BigDecimal(overTj),
							new BigDecimal(overEx), creator);
					course = course.save();
				}
			}
		} catch (Exception e) {
		}

		index();
	}

	public static void modifyCourse(String courseId, String name,
			String content, String overTj, String overEx) {
		try {
			if (Utils.isLong(courseId)) {
				Course course = Course.findById(Long.parseLong(courseId));
				if (course != null && Utils.isDouble(overTj)
						&& Utils.isDouble(overEx) && name != ""
						&& content != "") {

					course.name = name;
					if (Double.parseDouble(overTj) >= 5
							&& Double.parseDouble(overEx) >= 5) {
						course.overTj = new BigDecimal(overTj);
						course.overEx = new BigDecimal(overEx);
					}
					course.content = content;
					course = course.save();

				}
			}
		} catch (Exception e) {
		}
		index();
	}

	public static void getCoursesByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<Course> opeatorResult = Course.find(
						"code like ? or name like ? or content like ?",
						searchPatern, searchPatern, searchPatern).fetch();
				renderJSON(opeatorResult, new OperatorSerializer(),
						new CourseSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static List<Course> getNotAssignedCoursesByClasse(Classe classe) {

		List<Course> courses = new ArrayList<Course>();
		try {
			AccademicYear accYear = AccademicYears.getCurrentYear();
			if (accYear != null && classe != null) {
				EntityManager em = JPA.em();
				courses = em
						.createQuery(
								"select DISTINCT crs from Course crs where crs NOT IN (select DISTINCT course.course from TeacherClassCourse course where course.classe =:classe and (course.accademicYear=:accYear OR course.accademicYear IS NULL))")
						.setParameter("classe", classe)
						.setParameter("accYear", accYear).getResultList();
			}

		} catch (Exception e) {
		}
		return courses;
	}

}
