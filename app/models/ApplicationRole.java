package models;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.db.jpa.Model;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class Role extends Model {
	public String name;
	public String description;
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	@OneToMany(mappedBy = "role")
	public List<RoleOperator> operators = new ArrayList<RoleOperator>();

	public Role() {
	}

	public String toString() {
		return name;
	}

}
