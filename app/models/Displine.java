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
import utils.helpers.StatusManager;

@Indexed
@Entity
@Table(name = "core_displine")
public class Displine extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@ManyToOne
	public Fault fault;
	@ManyToOne
	public Sanction sanction;
	@ManyToOne
	public Operator mapedBY;
	public String status;
	public Date addedOn;

	public Displine() {
	}

	public Displine(Fault fault, Sanction sanction, Operator creator) {
		this.fault = fault;
		this.sanction = sanction;
		this.mapedBY = creator;
		this.addedOn = new Date();
		this.status = StatusManager.ACTIVE.getStatus();
	}
	@Override
	public String toString() {
		return " <==> "+sanction.name;
	}
}
