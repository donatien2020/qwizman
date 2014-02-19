package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class SchoolCategory extends Model{
	public String name;
	public String description;
	@OneToMany(mappedBy = "category")
	public List<School> schools = new ArrayList<School>();
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	public SchoolCategory() {
	}

	public String toString() {
		return name;
	}
}
