package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "proc_school_report_mark")
public class SchoolReportMark extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@ManyToOne
	public SchoolReport schoolReport;
	public Course course;
	public BigDecimal divOneTj;
	public BigDecimal divOneEx;
	public BigDecimal divOneTot;
	public BigDecimal divTwoTj;
	public BigDecimal divTwoEx;
	public BigDecimal divTwoTot;
	public BigDecimal divThreeTj;
	public BigDecimal divThreeEx;
	public BigDecimal divThreeTot;
	public BigDecimal yearTj;
	public BigDecimal yearEx;
	public BigDecimal yearAvg;
	public BigDecimal yearTot;
	public String observation;
	public Operator updatorTeacher;
	public Date lastUpdatedOn;
}
