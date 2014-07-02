package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="proc_school_report")
public class SchoolReport extends GenericModel{
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	public Operator student;
	public Operator creatorTeacher;
	public Classe classe;
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;
	public String reportLabel;
	public Date createdOn;
	public Date lastUpdatedOn;
	@OneToMany(mappedBy = "schoolReport")
	public List<SchoolReportMark> marks;
	

}
