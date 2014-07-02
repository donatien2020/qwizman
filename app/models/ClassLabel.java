package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.crypto.Data;

import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name="core_class_label")
public class ClassLabel extends Model{
	public String name;
	@ManyToOne
	public ClassLevel level;
	@ManyToOne
	public Operator creator;
	public Date addedOn;


}
