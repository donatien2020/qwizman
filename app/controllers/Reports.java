package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import models.AcademicYearDevision;
import models.Classe;
import models.Course;
import models.Operator;
import models.SchoolReport;
import models.SchoolReportMark;
import models.TeacherClassCourse;
import play.mvc.Controller;
import utils.helpers.UserType;

public class Reports extends Controller {
	public static void getSchoolReports(String operatorId) {
		List<SchoolReport> reports = null;
		try {
			Operator user = Operator.findById(Long.parseLong(operatorId));
			if (user != null) {
				if (user.typeOf.equals(UserType.STUDENT.getUserType())
						&& user.school != null) {
					if (getCurrentSchoolReport(user) == null) {
						createCurrentSchoolReport(user);
					}
					reports = SchoolReport.find("student=?", user).fetch();
				} else if (user.typeOf.equals(UserType.TEACHER.getUserType())
						&& user.school != null) {
					reports = SchoolReport.find("creatorTeacher=?", user)
							.fetch();
				} else if (user.typeOf.equals(UserType.HEADTEACHER
						.getUserType()) && user.school != null) {
					reports = SchoolReport.find("school=?", user.school)
							.fetch();
				} else
					reports = SchoolReport.findAll();
			}
		} catch (Exception e) {

		}
		renderJSON(reports, new SchoolReportSerializer());
	}
	public static SchoolReport getCurrentSchoolReport(Operator operator) {
		try {
			return SchoolReport.find("student=?", operator).first();
		} catch (Exception e) {
			return null;
		}
	}
	public static SchoolReport createCurrentSchoolReport(Operator student) {
		try {
			SchoolReport report = null;
			Classe classe = Classes.getStudentClasse(student);
			if (student.school != null && classe != null) {
				SchoolReport reportExist = SchoolReport.find(
						"student=? and accademicYear=? and classe=?", student,
						AccademicYears.getCurrentYear(), classe).first();
				if (reportExist == null) {
					report = new SchoolReport(student.school, student,
							Operators.getCurrentUser(), classe,
							AccademicYears.getCurrentYear(),
							AcademicYearDevisions.getCurrentDivision(),
							"New Report");
					report = report.save();
				} else
					report = reportExist;
				if (report != null && report.id != null
						&& report.isPersistent()) {
					for (TeacherClassCourse coursee : classe.courses) {
						try {
							SchoolReportMark markExist = SchoolReportMark.find(
									"schoolReport=? and course=?", report,
									coursee.course).first();
							if (markExist == null) {
								SchoolReportMark mark = new SchoolReportMark(
										report, coursee.course, "INITIAL",
										Operators.getCurrentUser());
								mark.save();
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
			}
			return report;
		} catch (Exception e) {
			return null;
		}
	}
}