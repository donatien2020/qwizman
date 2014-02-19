package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class OperatorType extends Model {
	public String name;
	public String description;
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	@OneToMany(mappedBy = "type")
	public List<Operator> operators = new ArrayList<Operator>();

	public OperatorType() {
	}

	public String toString() {
		return name;
	}
}
