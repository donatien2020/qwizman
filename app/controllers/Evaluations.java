package controllers;

import java.math.BigDecimal;
import java.util.List;

import models.AcademicYearDevision;
import models.Assesment;
import models.AssesmentProcess;
import models.Classe;
import models.Course;
import models.Evaluation;
import models.Operator;
import models.Question;
import models.QuestionOption;
import models.TeacherClassCourse;
import play.modules.paginate.ValuePaginator;
import play.mvc.Controller;
import utils.helpers.CustomerException;
import utils.helpers.UserRole;
import utils.helpers.UserType;
import utils.helpers.Utils;

public class Evaluations extends Controller {
	public static void index() {
		ValuePaginator results = null;
		try {
			AcademicYearDevision division = AcademicYearDevisions
					.getCurrentDivision();
			Operator currentUser = Operators.getCurrentUser();
			List<Evaluation> paginator = null;
			if (currentUser != null
					&& currentUser.typeOf != null
					&& currentUser.typeOf.equals(UserType.SUPERADMIN
							.getUserType()) && currentUser.school == null) {
				paginator = Evaluation.findAll();
			} else if (currentUser != null
					&& currentUser.typeOf != null
					&& currentUser.typeOf.equals(UserType.HEADTEACHER
							.getUserType()) && currentUser.school != null) {
				paginator = Evaluation.find(
						"school=? and accademicYearDevision=?",
						currentUser.school, division).fetch();
			} else if (currentUser != null
					&& currentUser.school != null
					&& currentUser.role.name.equals(UserRole.STUDENT
							.getUserRole())) {
				Classe Classe = Classes.getStudenClasse();
				if (Classe != null && division != null) {
					paginator = Evaluation
							.find("school=? and classe=? and accademicYearDevision=?",
									currentUser.school, Classe, division)
							.fetch();
				}
			} else
				paginator = Evaluation.find("createdBy=?", currentUser).fetch();

			if (paginator != null && paginator.size() > 0) {
				results = new ValuePaginator(paginator);
				results.setPageSize(10);
				results.setBoundaryControlsEnabled(true);
				results.setPagesDisplayed(0);
			}

		} catch (Exception e) {
		}
		render("Evaluations/index.html", results);
	}

	public static List<Evaluation> getEvaluations() {
		Operator currentUser = Operators.getCurrentUser();
		List<Evaluation> evaluations = null;
		if (currentUser != null
				&& currentUser.typeOf != null
				&& currentUser.typeOf
						.equals(UserType.HEADTEACHER.getUserType())
				&& currentUser.school != null) {
			evaluations = Evaluation.find("school=?", currentUser.school)
					.fetch();
		} else
			evaluations = Evaluation.find("creator=? and school=?",
					currentUser, currentUser.school).fetch();

		return evaluations;
	}

	public static void addNew() {
		try {
			List<Course> courses = Courses.getMyCourses();
			render("Evaluations/form.html", courses);
		} catch (Exception e) {
		}
	}

	public static void show(String id, String msg) {
		Evaluation evaluation = null;

		try {
			if (id != null) {
				evaluation = Evaluation.find("id=?", id).first();
			}
		} catch (Exception e) {
		}
		render("Evaluations/show.html", evaluation, msg);
	}

	public static void dashboard(String id) {
		Evaluation evaluation = null;
		String msg = "Evaluation Dashboard";
		try {
			if (id != null) {
				evaluation = Evaluation.find("id=?", id).first();
				msg = "Evaluation " + evaluation.name + " Dashbord";
			}
		} catch (Exception e) {
		}
		render("Evaluations/dashboard.html", evaluation, msg);
	}

	public static void create(String name, String description, String evalType,
			String totalMarks, String duration, String course) {
		try {
			Operator creator = Operators.getCurrentUser();
			if (creator != null && creator.school != null
					&& Utils.isLong(course)) {
				Course courseObj = Course.findById(Long.parseLong(course));
				TeacherClassCourse classe = TeacherClassCourse.find(
						"teacher=? and course=? and accademicYearDevision=?",
						creator, courseObj,
						AcademicYearDevisions.getCurrentDivision()).first();
				if (classe != null) {
					System.out
							.println(" savingggggggggggggggggggggggggggggggggggg");
					Evaluation evaluation = new Evaluation(name, description,
							evalType, new BigDecimal(totalMarks),
							new BigDecimal(duration), courseObj,
							AcademicYearDevisions.getCurrentDivision(),
							creator.school, creator, classe.classe);
					evaluation = evaluation.save();
				}
			}
		} catch (Exception e) {
		}

		index();
	}

	public static void modifyEvaluation(String evaluationId, String name,
			String description, String evalType, String totalMarks,
			String duration) {
		try {
			if (evaluationId != null && name != null && totalMarks != null) {
				Evaluation evaluation = Evaluation.find("id=?", evaluationId)
						.first();
				if (evaluation != null) {
					evaluation.name = name;
					evaluation.description = description;
					evaluation.evalType = evalType;
					evaluation.totalMarks = new BigDecimal(totalMarks);
					evaluation.duration = new BigDecimal(duration);
					evaluation = evaluation.save();

				}
			}
		} catch (Exception e) {
		}
		index();
	}

	public static void getEvaluationsByCriterion(String criterion) {
		if (criterion != null && !criterion.isEmpty()) {
			try {
				String searchPatern = "%" + criterion + "%";
				List<Evaluation> evaluationResult = Evaluation.find(
						"evalType like ? or name like ? or description like ?",
						searchPatern, searchPatern, searchPatern).fetch();
				renderJSON(evaluationResult, new EvaluationSerializer());
			} catch (Exception e) {
				renderJSON(new CustomerException(e.getMessage()));
			}
		} else
			renderJSON(new CustomerException("Invalid Search Criterion!"));

	}

	public static void addQuestion(String evaluationId, String content,
			String marks, String maxOptions, String totalMarks, String duration) {
		String msg = "Question Not Added !";
		try {
			if (evaluationId != null && content != null && marks != null
					&& maxOptions != null && Utils.isDouble(marks)
					&& Utils.isDouble(maxOptions)) {
				if (Double.parseDouble(maxOptions) <= 3
						&& Double.parseDouble(maxOptions) > 0) {
					Evaluation evaluation = Evaluation.find("id=?",
							evaluationId).first();
					if (evaluation != null) {
						BigDecimal currentMarks = getEvaluationMark(
								evaluationId).add(new BigDecimal(marks));
						if (currentMarks.doubleValue() <= evaluation.totalMarks
								.doubleValue()) {
							Question question = new Question(content,
									new BigDecimal(maxOptions), new BigDecimal(
											marks), evaluation,
									Operators.getCurrentUser());
							question = question.save();
							msg = "Quession " + question.content
									+ "Successifully added !";
						} else
							msg = "Total Evaluation Marks Unbalanced !";

					} else
						msg = "Evaluation not found";
				} else
					msg = "Max Options must be >0 and <=3";

			} else
				msg = "Invalid Parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void loadQuestions(String evaluationId) {
		String msg = "Questions Not Found !";
		try {
			if (evaluationId != null) {
				Evaluation evaluation = Evaluation.find("id=?", evaluationId)
						.first();
				if (evaluation != null) {
					if (evaluation.questions != null)
						renderJSON(evaluation.questions,
								new QuestionSerializer());
					else
						msg = "The Evaluation has No Questions";

				} else
					msg = "Evaluation not found";

			} else
				msg = "Invalid Parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void removeQuestion(String questionId) {
		String msg = "Question Not Found !";
		try {
			if (questionId != null) {
				Question question = Question.find("id=?", questionId).first();
				if (question != null) {
					if (question.assesments == null
							|| question.assesments.size() == 0) {
						if (question.options != null)
							for (QuestionOption option : question.options)
								option.delete();
						question.delete();
					} else
						msg = "This Question Can't be deleted!";

					msg = "Question Successifully Deleted !";

				} else
					msg = "Question not found";

			} else
				msg = "Invalid Parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void addQuestionOption(String qestionId, String content,
			String marks) {
		String msg = "Question Option Not Added !";
		try {
			if (qestionId != null && content != null && marks != null
					&& Utils.isDouble(marks)) {
				Question question = Question.find("id=?", qestionId).first();
				BigDecimal currentMarks = getQuestionMark(qestionId).add(
						new BigDecimal(marks));

				if (question != null) {
					if (currentMarks.doubleValue() <= question.marks
							.doubleValue()) {
						QuestionOption option = new QuestionOption(content,
								new BigDecimal(marks), question,
								Operators.getCurrentUser());
						option = option.save();
						msg = "Quession Option " + option.content
								+ " Successifully added !";
					} else
						msg = "The Sum Of total Marks Unbalanced !";
				} else
					msg = "Question not found";

			} else
				msg = "Invalid Parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static void removeQuestionOption(String optionId) {
		String msg = "Question Not Found !";
		try {
			if (optionId != null) {
				QuestionOption question = QuestionOption.find("id=?", optionId)
						.first();
				if (question != null) {
					if (question.answers == null
							|| question.answers.size() == 0) {

						question.delete();
						msg = "Question Successifully Deleted !";

					} else
						msg = "Can't delete any allread answered Question Option";

				} else
					msg = "Question not found";

			} else
				msg = "Invalid Parameters";
		} catch (Exception e) {
			msg = e.getMessage();
		}
		renderJSON(new CustomerException(msg));
	}

	public static BigDecimal getQuestionMark(String questionId) {
		BigDecimal totalMarks = new BigDecimal("0.0");
		try {

			if (questionId != null) {
				Question question = Question.find("id=?", questionId).first();
				if (question != null) {

					for (QuestionOption option : question.options) {
						totalMarks = totalMarks.add(option.marks);
					}

				}

			}
		} catch (Exception e) {

		}
		return totalMarks;
	}

	public static BigDecimal getEvaluationMark(String evaluationId) {
		BigDecimal totalMarks = new BigDecimal("0.0");
		try {
			if (evaluationId != null) {
				Evaluation evaluation = Evaluation.find("id=?", evaluationId)
						.first();
				if (evaluation != null) {

					for (Question question : evaluation.questions) {
						totalMarks = totalMarks.add(question.marks);
					}

				}

			}
		} catch (Exception e) {

		}
		return totalMarks;
	}

	public static void takeEvaluation(String id) {
		String msg = "Evaluation not available";
		Evaluation evaluation = null;
		try {
			Operator attendnt = Operators.getCurrentUser();
			evaluation = Evaluation.find("id=?", id).first();
			if (evaluation != null) {

				Assesment assessment = null;
				Assesment assessmentExist = Assesment.find(
						"evaluation=? and attendant=?", evaluation, attendnt)
						.first();
				if (assessmentExist == null) {
					assessment = new Assesment(evaluation, attendnt);
					assessment = assessment.save();
				} else
					assessment = assessmentExist;
				if (assessment.id != null) {

					for (Question question : evaluation.questions) {
						AssesmentProcess process = null;
						AssesmentProcess processExist = AssesmentProcess.find(
								"assesment=? and question=? and attendant=?",
								assessment, question, attendnt).first();
						if (processExist == null) {
							process = new AssesmentProcess(assessment,
									question, attendnt);
							process = process.save();
						} else
							process = processExist;
						if (process.id != null) {
							msg = "Assesment Started !";
						} else
							msg = "Assesment Failed Inthe Midle";
					}
					render("Evaluations/dashboard.html", assessment, msg);
				} else
					msg = "Assesment Completly Failed ";

			} else
				msg = "This Evaluation is not ready";
		} catch (Exception e) {
		}
		render("Evaluations/dashboard.html", evaluation, msg);
	}
}
