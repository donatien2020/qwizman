package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.crypto.Data;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;
@Indexed
@Entity
@Table(name="school_accademic_year")
public class AccademicYear extends GenericModel {
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	public String description;
	public int yearLabel;
	public Date startAt;
	public Date endAt;
	public Boolean yearStatus;
	@ManyToOne
	public School school;
	@ManyToOne
	public Operator createdBy;
	public Date createdOn;
	@OneToMany(mappedBy = "accademicYear")
	public List<AcademicYearDevision> devisions = new ArrayList<AcademicYearDevision>();

}
