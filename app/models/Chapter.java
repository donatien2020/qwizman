package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
import utils.helpers.ChapterStatus;

@Indexed
@Entity
@Table(name = "school_chapter")
public class Chapter extends GenericModel {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String id;
	@Required
	@MaxSize(100)
	public String name;
	@Required
	@MaxSize(10000)
	@Column(columnDefinition="TEXT")
	public String descrition;
	public String chapterStatus;
	@ManyToOne
	public Course course;
	public Date createdOn;
	@ManyToOne
	public Operator creator;
	@ManyToOne
	public AcademicYearDevision accademicYearDevision;

	@Override
	public String toString() {
		return this.name;
	}

	public Chapter() {
	}

	public Chapter(String name, String descrition, Course course,
			Operator creator, AcademicYearDevision accademicYearDevision) {
		this.name = name;
		this.descrition = descrition;
		this.chapterStatus = ChapterStatus.PENDING.getChapterStatus();
		this.course = course;
		this.creator = creator;
		this.accademicYearDevision = accademicYearDevision;
	}
}
