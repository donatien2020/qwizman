package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Chapter;
import models.Course;
import models.Operator;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import utils.helpers.CustomerException;
import utils.helpers.UserType;
import utils.helpers.Utils;

public class Chapters extends Controller {
	public static void index(String courceid, String msg) {
		ValuePaginator results = null;
		Course course = null;

		try {
			course = Course.findById(Long.parseLong(courceid));
			if (course != null) {
				List<Chapter> paginator = course.chapters;
				if (paginator != null && paginator.size() > 0) {
					results = new ValuePaginator(paginator);
					results.setPageSize(10);
					results.setBoundaryControlsEnabled(true);
					results.setPagesDisplayed(0);
				} else
					msg = "This Course Has No Chapters";
			} else
				msg = "Cource Not Found !";

		} catch (Exception e) {
		}
		render("Chapters/index.html", results, msg, course);
	}

	public static List<Chapter> getChapters() {
		return null;

	}

	public static void addNew(String courceid, String msg) {
		try {
			Course course = Course.findById(Long.parseLong(courceid));
			if (course != null) {
				render("Chapters/form.html", course, msg);
			} else {
				msg = "The Course Is not Available";
				index(courceid, msg);
			}

		} catch (Exception e) {
		}
	}

	public static void show(String chapterid, String msg) {
		Chapter chapter = null;
		try {
			if (chapterid != null) {
				chapter = Chapter.find("id=?", chapterid).first();
				if (chapter == null)
					msg = "Chapter Not found !";
			}
		} catch (Exception e) {
		}
		render("Chapters/show.html", chapter, msg);
	}

	public static void dashboard(String id) {
		Chapter chapter = null;
		String msg = "Chapter Dashboard";
		try {
			if (id != null) {
				chapter = Chapter.find("id=?", id).first();
				msg = "Chapter " + chapter.name + " Dashbord";
			}
		} catch (Exception e) {
		}
		render("Chapters/dashboard.html", chapter, msg);
	}

	public static void create(String name, String content, String couseid) {
		String msg = "Creating New Chapter";
		try {
			System.out
					.println(" hellllllllllllllllllllllllllllllllllllllllllllllllllllllllllll:"
							+ couseid);
			Operator creator = Operators.getCurrentUser();
			if (creator != null && creator.school != null) {
				Course course = Course.findById(Long.parseLong(couseid));
				if (course != null) {
					System.out.println(" helooooooooooooooooooooooooooooooooo");
					Chapter chapter = new Chapter(name, content, course,
							creator, AcademicYearDevisions.getCurrentDivision());
					chapter = chapter.save();
					System.out
							.println(" helooooooooooooooooooooooooooooooooo end"
									+ chapter.id);
				} else
					msg = "Course Not Identified";
			} else
				msg = "Teacher Not Available";
		} catch (Exception e) {
			e.printStackTrace();
		}

		index(couseid, msg);
	}

	public static void modifyChapter(String name, String content,
			String chapterid) {
		Chapter chapter = null;
		try {
			if (chapterid != null) {
				chapter = Chapter.find("id=?", chapterid).first();
				if (chapter != null) {
					chapter.name = name;
					chapter.descrition = content;
					chapter = chapter.save();

				}
			}
		} catch (Exception e) {
		}
		dashboard(chapterid);
	}

}
