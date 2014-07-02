package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;
@Entity
@Table(name="core_department")
public class Department extends Model{
	@Required
	@Unique
	public String code;
	@Required
	public String name;
	@Required
	public String description;
	@Required
	@NotNull
	@ManyToOne
	public School school;
	@NotNull
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	
}
