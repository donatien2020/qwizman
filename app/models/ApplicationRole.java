package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Required;
import play.db.jpa.Model;
import play.modules.search.Indexed;

import javax.persistence.Entity;

import models.deadbolt.Role;

import java.util.List;

@Indexed
@Entity
@Table(name = "core_customer")
public class ApplicationRole extends Model implements Role {
	@Required
	public String name;

	public ApplicationRole(String name) {
		this.name = name;
	}

	public String getRoleName() {
		return name;
	}

	public static ApplicationRole getByName(String name) {
		return ApplicationRole.find("byName", name).first();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
