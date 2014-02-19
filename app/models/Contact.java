package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Contact  extends Model{
	public String fullName;
	public String email1;
	public String phone1;
	public String physicalAddress;
	public String box;
	public String webSite;
	public String tinNumber;
	public Contact(){}
	public String toString() {
		return fullName;
	}
}
