package models;

import static javax.persistence.CascadeType.PERSIST;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;
@Entity
public class School extends Model{
	@ManyToOne
	public SchoolCategory category;
	@OneToOne(cascade = PERSIST)
	public Contact contact;
	@OneToMany(mappedBy = "school")
	public List<Classe> Classes = new ArrayList<Classe>();
	public Date createdOn;
	@ManyToOne
	public Operator creator;
}
