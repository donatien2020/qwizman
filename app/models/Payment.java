package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
@Table(name="core_payment")
public class Payment  extends Model{
	public String description;
	@NotNull
	@ManyToOne
	public School school;
	@NotNull
	@ManyToOne
	public Fee fee;
	@NotNull
	public Date paidOn;
	@ManyToOne
	public Operator receivedBy;
	@NotNull
	public String paymentStatus;
}
