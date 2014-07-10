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
import javax.validation.constraints.NotNull;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "school_class")
public class Classe extends Model {
	@Required
	public String fullName;
	@Email
	public String emailAddress;
	@Phone
	public String phoneNumber;
	public String physicalAddress;
	public String box;
	public String webSite;
	public String classlabel;
	public String classlevel;
	@NotNull
	@ManyToOne
	public School school;
	@OneToMany(mappedBy = "classe")
	public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
	@OneToMany(mappedBy = "classe")
	public List<StudentClasse> students = new ArrayList<StudentClasse>();
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@NotNull
	@ManyToOne
	public Operator tuturaire;
	@ManyToOne
	public Operator lastUpdatedBy;
	public Date lastUpdateOn;
	@ManyToOne
	public Department department;

	public Classe() {
	}

	public Classe(String fullName, String emailAddress, String phoneNumber,
			String physicalAddress, String box, String webSite,
			String classlabel, String classlevel, School school,
			Operator creator,Operator tuturaire) {
		this.fullName = fullName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.physicalAddress = physicalAddress;
		this.box = box;
		this.webSite = webSite;
		this.classlabel = classlabel;
		this.classlevel = classlevel;
		this.school = school;
		this.creator = creator;
		this.createdOn=new Date();
		this.tuturaire=tuturaire;
	}
}
