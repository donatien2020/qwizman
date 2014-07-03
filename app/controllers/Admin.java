package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import play.mvc.Scope.Session;

import java.io.File;
import java.util.*;

import controllers.Secure.Security;
import controllers.deadbolt.Deadbolt;

import models.*;

@With(Deadbolt.class)
public class Admin extends Controller {
	@Before
	static void setConnectedUser() {
		try {
			if (Security.isConnected()) {
				Operator user = Operator.find("byUsername", Security.connected())
						.first();
				if (user != null && user.id!=null) {
					System.out.println(" found=========================");
					session.put("user", user.firstName);
					renderArgs.put("user",  user.firstName);
				} else {
					System.out.println(" not found=========================");

					try {
						Secure.logout();
					} catch (Throwable e) {

						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public static void index() {
		String user = Security.connected();
		session.put("login", user);
		render("@index", user);

	}

	

}