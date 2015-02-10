package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "core_fault")
public class Fault extends Model {
	public String name;
	public String description;
	public String type;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator updator;
	public Date createdOn;
	@OneToMany(mappedBy = "fault")
	public List<Displine> displines;

	public Fault() {
	}

	public Fault(String name, String description, String type, School school,
			Operator creator) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.school = school;
		this.creator = creator;
		this.updator = creator;
		this.createdOn = new Date();
	}
	@Override
	public String toString() {
		return this.name;
	}
}
