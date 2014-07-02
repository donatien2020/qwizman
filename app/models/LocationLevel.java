package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name = "core_location_level")
public class LocationLevel extends Model{
	public String code;
	public String name;
	@OneToMany(mappedBy = "level")
	public List<Location> locations=new ArrayList<Location>();
}
