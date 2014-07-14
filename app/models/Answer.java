package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import play.db.jpa.GenericModel;
import play.modules.search.Indexed;

@Indexed
@Entity
@Table(name = "proc_answer")
public class Answer extends GenericModel {
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	@ManyToOne
	public AssesmentProcess assesmentProcess;
	@ManyToOne
	public QuestionOption questionOption;
	public Date answeredOn;
	@ManyToOne
	public Operator attendant;
	
}
