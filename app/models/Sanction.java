package models;

import java.math.BigDecimal;
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
@Table(name = "core_sanction")
public class Sanction extends Model {
	public String name;
	public String description;
	public String type;
	public BigDecimal marks;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public Operator updatedBy;
	public Date createdOn;
	@OneToMany(mappedBy = "sanction")
	public List<Displine> displines;

	public Sanction() {
	}

	public Sanction(School school, String type, String name,
			String description, BigDecimal marks, Operator creator) {
		this.createdOn = new Date();
		this.school = school;
		this.type = type;
		this.name = name;
		this.description = description;
		this.marks = marks;
		this.creator = creator;
		this.updatedBy = creator;

	}

	@Override
	public String toString() {
		return this.name;
	}
}
