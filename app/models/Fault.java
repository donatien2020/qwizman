package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name = "core_fault")
public class Fault extends Model{
	public String name;
	public String description;
	public String type;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator creator;
	
	@OneToMany(mappedBy = "fault")
	public List<Sanction> sactions ;
	@OneToMany(mappedBy = "sanction")
	public List<Displine> displines ;

}
