package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
@Entity
public class Answer extends Model{
	@ManyToOne
public AssesmentProcess assesmentProcess;
	@ManyToOne
public QuestionOption questionOption;
public Date answeredOn;
}
