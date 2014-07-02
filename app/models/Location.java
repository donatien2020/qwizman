package models;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name = "location")
public class Location extends GenericModel {
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	public String code;
	public String name;
	public String coordinate;
	@ManyToOne
	public Location parent;
	@ManyToOne
	public LocationLevel level;
	@OneToMany(mappedBy = "parent")
	public List<Location> children = new ArrayList<Location>();

	public Location() {

	}



	
}
