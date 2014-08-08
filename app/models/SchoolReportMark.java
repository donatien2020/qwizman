package models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@NotNull
	@ManyToOne
	public SchoolReport schoolReport;
	@NotNull
	@ManyToOne
	public Course course;
	@NotNull
	public BigDecimal divOneTj;
	@NotNull
	public BigDecimal divOneEx;
	@NotNull
	public BigDecimal divOneTot;
	@NotNull
	public BigDecimal divTwoTj;
	@NotNull
	public BigDecimal divTwoEx;
	@NotNull
	public BigDecimal divTwoTot;
	@NotNull
	public BigDecimal divThreeTj;
	@NotNull
	public BigDecimal divThreeEx;
	@NotNull
	public BigDecimal divThreeTot;
	@NotNull
	public BigDecimal yearTj;
	@NotNull
	public BigDecimal yearEx;
	@NotNull
	public BigDecimal yearAvg;
	@NotNull
	public BigDecimal yearTot;
	@NotNull
	public String observation;
	@NotNull
	@ManyToOne
	public Operator updatorTeacher;
	@NotNull
	public Date lastUpdatedOn;

	public SchoolReportMark() {
	}
	public SchoolReportMark(SchoolReport schoolReport, Course course,
			String observation, Operator updatorTeacher) {
		BigDecimal defaultValue = new BigDecimal("0.0");
		this.schoolReport = schoolReport;
		this.course = course;
		this.divOneTj = defaultValue;
		this.divOneEx = defaultValue;
		this.divOneTot = defaultValue;
		this.divTwoTj = defaultValue;
		this.divTwoEx = defaultValue;
		this.divTwoTot = defaultValue;
		this.divThreeTj = defaultValue;
		this.divThreeEx = defaultValue;
		this.divThreeTot = defaultValue;
		this.yearTj = defaultValue;
		this.yearEx = defaultValue;
		this.yearAvg = defaultValue;
		this.yearTot = defaultValue;
		this.observation = observation;
		this.updatorTeacher = updatorTeacher;
		this.lastUpdatedOn = new Date();
	}
}
