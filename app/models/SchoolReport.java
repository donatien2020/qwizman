package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "proc_school_report")
public class SchoolReport extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@NotNull
	@ManyToOne
	public Operator student;
	@NotNull
	@ManyToOne
	public Operator creatorTeacher;
	@NotNull
	@ManyToOne
	public Classe classe;
	@NotNull
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator latUpOperator;
	@NotNull
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;
	@NotNull
	@ManyToOne
	public AccademicYear accademicYear;
	public String reportLabel;
	public Date createdOn;
	public Date lastUpdatedOn;
	@OneToMany(mappedBy = "schoolReport")
	public List<SchoolReportMark> marks = new ArrayList<SchoolReportMark>();
	public SchoolReport() {
	}
	public SchoolReport( School school,Operator student, Operator creatorTeacher,
			Classe classe, AccademicYear accademicYear,
			AcademicYearDevision accademicYearDevision, String reportLabel) {
		this.school=school;
		this.student = student;
		this.creatorTeacher = creatorTeacher;
		this.classe = classe;
		this.accademicYear = accademicYear;
		this.accademicYearDevision = accademicYearDevision;
		this.reportLabel = reportLabel;
		this.latUpOperator = creatorTeacher;
		this.createdOn = new Date();
		this.lastUpdatedOn = new Date();
	}
}
