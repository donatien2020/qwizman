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
import javax.xml.crypto.Data;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="school_accademic_year")
public class AccademicYear extends GenericModel {
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	@NotNull
	public String description;
	@NotNull
	public Date startAt;
	@NotNull
	public Date endAt;
	@NotNull
	public Boolean yearStatus;
	@NotNull
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator createdBy;
	public Date createdOn;
	@OneToMany(mappedBy = "accademicYear")
	public List<AcademicYearDevision> devisions = new ArrayList<AcademicYearDevision>();
	public AccademicYear(){}
	public AccademicYear(String description,Date startAt,Date endAt,School school,Operator createdBy){
		this.createdBy=createdBy;
		this.createdOn=new Date();
		this.description=description;
		this.startAt=startAt;
		this.endAt=endAt;
		this.yearStatus=new Boolean(true);
		this.school=school;
	}
}
