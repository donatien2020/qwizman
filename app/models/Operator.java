package models;

import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Operator extends Model {
	public String username;
	public String password;
	@ManyToOne
	public OperatorType type;
	@OneToOne(cascade = PERSIST)
	public Contact contact;
	@OneToMany(mappedBy = "owner")
	public List<RoleOperator> roles = new ArrayList<RoleOperator>();
	@OneToMany(mappedBy = "teacher")
	public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	public Operator() {
	}

	public String toString() {
		return username;
	}

}
