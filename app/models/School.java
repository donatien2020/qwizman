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
import play.modules.search.Field;

@Entity
@Table(name = "core_school")
public class School extends Model {
	public String category;
	@NotNull
	public String code;
	@NotNull
	public String typeOf;
	@NotNull
	public String schoolName;
	@NotNull
	public String description;
	@NotNull
	public String ownerFirstName;
	@NotNull
	public String ownerLastName;
	@NotNull
	@Phone
	public String ownerPhoneNumber;
	@NotNull
	@Email
	public String ownerEmail;
	@NotNull
	public String poBox;
	public String website;
	public Boolean status;
	@ManyToOne
	public Location location;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@OneToMany(mappedBy = "school")
	public List<Classe> classes = new ArrayList<Classe>();
	@OneToMany(mappedBy = "school")
	public List<Department> departments = new ArrayList<Department>();
	@OneToMany(mappedBy = "school")
	public List<Course> courses = new ArrayList<Course>();
	@OneToMany(mappedBy = "school")
	public List<Operator> students = new ArrayList<Operator>();
	@OneToMany(mappedBy = "school")
	public List<AccademicYear> accademicYears = new ArrayList<AccademicYear>();

	public School() {
	}

	public School(String category, String code, String typeOf, String schoolName,
			String description, String ownerFirstName, String ownerLastName,
			String ownerPhoneNumber, String ownerEmail, String poBox,
			String website, Operator creator) {
		this.category = category;
		this.code = code;
		this.typeOf = typeOf;
		this.schoolName = schoolName;
		this.description = description;
		this.ownerFirstName = ownerFirstName;
		this.ownerLastName = ownerLastName;
		this.ownerPhoneNumber = ownerPhoneNumber;
		this.ownerEmail = ownerEmail;
		this.poBox = poBox;
		this.website = website;
		this.creator = creator;
		this.status = true;
		this.createdOn = new Date();
	}
}
