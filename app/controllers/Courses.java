package controllers;

import java.util.ArrayList;
import java.util.List;
import models.AcademicYearDevision;
import models.Course;
import models.Operator;
import models.School;
import models.TeacherClassCourse;
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
			Operator currentUser = Operators.getCurrentUser();
			List<Course> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()) && currentUser.school != null) {
				paginator = Course.find("school=?", currentUser.school).fetch();
			} else
				paginator = Course.find("creator=?", currentUser).fetch();

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
		List<Course> courses = null;
		if (currentUser != null
				&& currentUser.typeOf != null
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType())
				&& currentUser.school != null) {
			courses = Course.find("school=?", currentUser.school).fetch();
		} else
			courses = Course.find("creator=? and school=?", currentUser,
					currentUser.school).fetch();

		return courses;
	}

	public static List<Course> getMyCourses() {
		Operator currentUser = Operators.getCurrentUser();
		List<Course> courses = new ArrayList<Course>();
		if (currentUser != null
				&& currentUser.typeOf != null
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType())
				&& currentUser.school != null) {
			courses = Course.find("school=?", currentUser.school).fetch();
			List<TeacherClassCourse> courseTeachers = TeacherClassCourse
					.find("teacher=? and accademicYearDevision=? and course is not null",
							currentUser,
							AcademicYearDevisions.getCurrentDivision()).fetch();

			for (TeacherClassCourse tcc : courseTeachers) {
				courses.add(tcc.course);
			}
		} else {
			List<TeacherClassCourse> courseTeachers = TeacherClassCourse
					.find("teacher=? and accademicYearDevision=? and course is not null",
							currentUser,
							AcademicYearDevisions.getCurrentDivision()).fetch();

			for (TeacherClassCourse tcc : courseTeachers) {
				courses.add(tcc.course);
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

	public static void create(String name, String content) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (creator != null && creator.school != null) {
				Course course = new Course(Utils.idGenerator(name), name,
						content, creator.school, creator);
				course = course.save();
			}
		} catch (Exception e) {
		}

		index();
	}

	public static void modifyCourse(String courseId, String name, String content) {
		try {
			if (Utils.isLong(courseId)) {
				Course course = Course.findById(Long.parseLong(courseId));
				if (course != null) {
					course.name = name;
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

}
