package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "core_displine")
public class Displine extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@ManyToOne
	public Operator student;
	@ManyToOne
	public Operator punisher;
	public String notes;
	public BigDecimal credit;
	@ManyToOne
	public Fault fault;
	@ManyToOne
	public Sanction sanction;
	public String status;
	public Date punishmentExpiry;
	public Date comitedOn;
	public String type;

}
