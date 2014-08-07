package models;

import static javax.persistence.CascadeType.PERSIST;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import models.deadbolt.Role;
import models.deadbolt.RoleHolder;
import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.GenericModel;
import play.db.jpa.Model;
import play.modules.search.Indexed;
import utils.helpers.UserRole;
import utils.helpers.UserType;
import utils.helpers.Utils;

@Indexed
@Entity
@Table(name = "core_operator")
public class Operator extends Model implements RoleHolder {
	public String code;
	@Unique
	@Column(name="username",unique=true)
	public String username;
	public String password;
	@Required
	@NotNull
	public String firstName;
	@NotNull
	public String lastName;
	@Email
	public String emailAddress;
	@Phone
	public String phoneNumber;
	public String physicalAddress;
	public String box;
	public String webSite;
	public Boolean isActive;
	public Boolean isAdmin;
	@NotNull
	public String degree;
	// Super admin, Admin,Head Teacher,Second Head Teacher,Teacher,Student
	@NotNull
	public String typeOf;
	@Required
	@ManyToOne
	public ApplicationRole role;
	@OneToMany(mappedBy = "teacher")
	public List<TeacherClassCourse> courses = new ArrayList<TeacherClassCourse>();
	@OneToMany(mappedBy = "student")
	public List<SchoolReport> schoolReports = new ArrayList<SchoolReport>();
	public Date createdOn;
	@ManyToOne
	public Operator createdBy;
	@ManyToOne
	public School school;
	@ManyToOne
	public Department department;
	@NotNull
	public String salt;

	public Operator() {
	}

	public Operator(String firstName, String lastName, String phoneNumber,
			String emailAddress, String username, String password,
			String physicalAddress, String box, String webSite, String salt,
			ApplicationRole role,String degree) {
		this.code=Utils.idGenerator(firstName);
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.username = username;
		this.password = encriptThis(password, salt);
		if (role != null)
			this.typeOf = role.name;
		this.physicalAddress = physicalAddress;
		this.box = box;
		this.webSite = webSite;
		this.salt = salt;
		this.isActive = new Boolean(true);
		this.createdOn = new Date();
		if (role != null) {
			this.role = role;
			this.typeOf = role.name;
			if (role.name != null
					&& role.name.equals(UserType.ADMIN.getUserType()))
				this.isAdmin = new Boolean(true);
			else
				this.isAdmin = new Boolean(false);
		}
		this.degree=degree;
	}

	public static Operator getByUsername(String username) {
		return Operator.find("byUsername", username).first();
	}

	public static Operator connect1(String username, String password) {
		return Operator.find("byUsernameAndPassword", username, password)
				.first();
	}

	public static Operator connect(String username, String password) {
		Operator byUsername = getByUsername(username);
		if (byUsername != null && byUsername.isActive) {
			if (!encodePasswordForLogin(byUsername, password).equals(
					byUsername.password)) {
				return null;
			}
		}

		return byUsername;

	}

	@Override
	public String toString() {
		return this.username;
	}

	public List<? extends Role> getRoles() {
		return Arrays.asList(role);
	}

	public static String generateSalt(String password) {
		if (password != null)
			return utils.helpers.Utils.idGenerator(password);
		return "";
	}

	public String encriptThis(String password, String salt) {
		try {
			String passwordAndSalt = password + salt;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] dataBytes = new byte[1024];
			int nread = 0;
			while (nread <= passwordAndSalt.length() - 1) {
				md.update(dataBytes, 0, nread);
				nread++;
			}
			byte[] mdbytes = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String encodePasswordForLogin(Operator operator,
			String password) {
		try {
			String passwordAndSalt = password + operator.salt;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] dataBytes = new byte[1024];
			int nread = 0;
			while (nread <= passwordAndSalt.length() - 1) {
				md.update(dataBytes, 0, nread);
				nread++;
			}
			byte[] mdbytes = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mdbytes.length; i++) {
				sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
