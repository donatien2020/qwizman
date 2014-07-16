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
import play.db.jpa.GenericModel;
import play.modules.search.Indexed;
import org.hibernate.annotations.GenericGenerator;

@Indexed
@Entity
@Table(name="proc_assesment_process")
public class AssesmentProcess extends GenericModel{
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String id;
	@ManyToOne
public Assesment assesment;
	@ManyToOne
public Question question;
	@OneToMany(mappedBy = "assesmentProcess")
	public List<Answer> answers = new ArrayList<Answer>();
public Date assecedOn;
@ManyToOne
public Operator attendant;
public AssesmentProcess(){}
public AssesmentProcess(Assesment assesment,Question question,Operator attendant){
	this.assesment=assesment;
	this.question=question;
	this.attendant=attendant;
	this.assecedOn=new Date();
}

}
