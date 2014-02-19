package models;

import java.util.Date;

import javax.persistence.ManyToOne;
import play.db.jpa.Model;
import javax.persistence.Entity;
import java.util.List;
@Entity
public class RoleOperator  extends Model{
	@ManyToOne
	public Operator owner;
	@ManyToOne
	public Role role;
	public Date assignedOn;
	@ManyToOne
	public Operator createdBy;
	public RoleOperator() {
	}

	public String toString() {
		return owner.username + role.name + assignedOn.toString();
	}
}
