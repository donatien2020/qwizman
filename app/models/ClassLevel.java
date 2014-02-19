package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class ClassLevel extends Model{
public String name;
public String description;
@OneToMany(mappedBy = "level")
public List<Classe> Classes = new ArrayList<Classe>();
public Date createdOn;
@ManyToOne
public Operator creator;
}
