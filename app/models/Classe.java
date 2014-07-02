package models;

import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="school_class")
public class Classe extends Model{
	@Required
	public String fullName;
	@Email
	public String emailAddress;
	@Phone
	public String phoneNumber;
	public String physicalAddress;
	public String box;
	public String webSite;
	public String Classlabel;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator tutulaire;
	@ManyToOne
	public Operator chiefDeClass;
	@ManyToOne
	public Operator chieftain;
	@ManyToOne
	public ClassLevel level;
	@OneToMany(mappedBy = "classe")
	public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator lastUpdatedBy;
	public Date lastUpdateOn;
	@ManyToOne
	public Department department;
	

}
