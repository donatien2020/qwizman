package models;

import java.math.BigDecimal;
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
public class Sanction extends Model{
	public String name;
	public String description;
	public String type;
	
	public BigDecimal marks;
	@ManyToOne
	public Fault fault;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator creator;
	@OneToMany(mappedBy = "sanction")
	public List<Displine> displines ;
}
