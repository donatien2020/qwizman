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
import utils.helpers.CustomerException;
import utils.helpers.UserType;
import utils.helpers.Utils;

public class Reports extends Controller {
	public static void getSchoolReports(String operatorId) {
		List<SchoolReport> reports = null;
		try {
			Operator user = Operator.findById(Long.parseLong(operatorId));
			if (user != null) {
				if (user.typeOf.equals(UserType.STUDENT.getUserType())
						&& user.school != null) {

						createCurrentSchoolReport(user);
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
		SchoolReport report = null;
		try {
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

		} catch (Exception e) {
		}
		return report;
	}

	public static void getSchoolReportView(String reportid) {
		SchoolReport report = null;
		try {

			if (reportid != null && !reportid.isEmpty())
				report = SchoolReport.find("id=?", reportid).first();
		} catch (Exception e) {

		}
		renderJSON(report, new SchoolReportSerializer());
	}

	public static void addReportMarks(String markid, String markvalue,
			String trimester) {
		SchoolReportMark reportMark = null;
		String msg = "Marks not Changed !";
		try {
			if (markid != null && !markid.isEmpty() && markvalue != null
					&& !markvalue.isEmpty() && trimester != null
					&& Utils.isDouble(markvalue)) {
				reportMark = SchoolReportMark.find("id=?", markid).first();
				if (reportMark != null) {
					if (trimester.contains("divOneTj")) {
						reportMark.divOneTj = new BigDecimal(markvalue);
						reportMark.divOneTot = reportMark.divOneEx
								.add(new BigDecimal(markvalue));
					} else if (trimester.contains("divOneEx")) {
						reportMark.divOneEx = new BigDecimal(markvalue);
						reportMark.divOneTot = reportMark.divOneTj
								.add(new BigDecimal(markvalue));
					} else if (trimester.contains("divTwoTj")) {
						reportMark.divTwoTj = new BigDecimal(markvalue);
						reportMark.divTwoTot = reportMark.divTwoEx
								.add(new BigDecimal(markvalue));
					} else if (trimester.contains("divTwoEx")) {
						reportMark.divTwoEx = new BigDecimal(markvalue);
						reportMark.divTwoTot = reportMark.divTwoTj
								.add(new BigDecimal(markvalue));
					} else if (trimester.contains("divThreeTj")) {
						reportMark.divThreeTj = new BigDecimal(markvalue);
						reportMark.divThreeTot = reportMark.divThreeEx
								.add(new BigDecimal(markvalue));
					} else if (trimester.contains("divThreeEx")) {
						reportMark.divThreeEx = new BigDecimal(markvalue);
						reportMark.divThreeTot = reportMark.divThreeTj
								.add(new BigDecimal(markvalue));
					}
					reportMark = reportMark.save();
					reportMark.yearTj = reportMark.divOneTj.add(
							reportMark.divTwoTj).add(reportMark.divThreeTj);
					reportMark.yearEx = reportMark.divOneEx.add(
							reportMark.divTwoEx).add(reportMark.divThreeEx);
					reportMark.yearTot = reportMark.divOneTot.add(
							reportMark.divTwoTot).add(reportMark.divThreeTot);
					reportMark = reportMark.save();
					reportMark.yearAvg = reportMark.yearTot
							.divide(new BigDecimal("3.0"),BigDecimal.ROUND_HALF_UP);
					reportMark = reportMark.save();
					msg = "Successifully Changed to " + markvalue;
				} else
					msg = "Object No Retrivable!";

			} else
				msg = "Invalid Input!";
		} catch (Exception e) {
			msg = "Internal Processing Error :" + e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}
}