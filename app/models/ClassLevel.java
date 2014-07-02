package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="core_classe_level")
public class ClassLevel extends Model{
public String name;
public String description;
//Ordinary Level,Secondary,undergraduate,....
@OneToMany(mappedBy = "level")
public List<Classe> Classes = new ArrayList<Classe>();
@OneToMany(mappedBy = "level")
public List<ClassLabel> lebels=new ArrayList<ClassLabel>();
public Date createdOn;
@ManyToOne
public Operator creator;
}
