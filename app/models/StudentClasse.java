package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="school_student_class")
public class StudentClasse extends Model{
	@ManyToOne
	public Operator student;
	@ManyToOne
	public Classe classe;
	public String status;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator lastUpdatedBy;
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;
	public Date lastUpdateOn;
}
