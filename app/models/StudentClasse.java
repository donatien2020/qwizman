package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "school_student_class")
public class StudentClasse extends Model {
	@ManyToOne
	@NotNull
	public Operator student;
	@ManyToOne
	@NotNull
	public Classe classe;
	@NotNull
	public String status;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator lastUpdatedBy;
	@ManyToOne
	@NotNull
	// when an accademic year is closed it load all student, teachers with the
	// same status
	public AcademicYearDevision accademicYearDevision;
	@ManyToOne
	public AccademicYear accademicYear;
	public Date lastUpdateOn;

	public StudentClasse() {
	}
	public StudentClasse(Operator student, Classe classe, String status,
			Operator creator, AcademicYearDevision accademicYearDevision) {
		this.status = status;
		this.student = student;
		this.classe = classe;
		this.creator = creator;
		this.lastUpdatedBy = creator;
		this.lastUpdateOn = new Date();
		if (accademicYearDevision != null)
			this.accademicYear = accademicYearDevision.accademicYear;
		this.accademicYearDevision = accademicYearDevision;
	}
}
