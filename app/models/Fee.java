package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.data.validation.Required;
import play.db.jpa.Model;
@Entity
@Table(name="core_fee")
public class Fee  extends Model{
	@NotNull
	public String name;
	@NotNull
	public String description;
	@NotNull
	public BigDecimal feeValue;
	@NotNull
	public String feeValueUnit;
	public String periodValue;
	@NotNull
	public String periodUnit;
	@NotNull
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	public List<Payment> payment = new ArrayList<Payment>();

}
