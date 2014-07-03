package controllers;

import utils.helpers.UserType;
import models.Operator;

public class Security extends Secure.Security {
	  public static Operator operator=null;

    static boolean authenticate(String username, String password) {
    	operator = Operator.connect(username, password);
		System.out.println(" found========================="+operator);

        return operator != null && operator.password.equals(password);
    }
   

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
		if (operator != null) {
			System.out.println(" found= session========================"+operator);

			session.put("type", operator.typeOf);
			session.put("email", operator.emailAddress);
			session.put("username", operator.username);
			if (!operator.typeOf.equals(UserType.SUPERADMIN.getUserType())
					&& operator.school != null) {
				session.put("companycode", operator.school.code);
				session.put("companyname", operator.school.name);
			} else {
				session.put("companycode", "donatien");
				session.put("companyname", "UMWARIMU GROUP LTD");
			}

		}
        Schools.dashboard();
    }
}


