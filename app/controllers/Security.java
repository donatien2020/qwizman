package controllers;

import utils.helpers.UserType;
import models.Operator;

public class Security extends Secure.Security {
	  public static Operator operator=null;
    static boolean authenticate(String username, String password) {
    	operator = Operator.connect(username, password);
        return operator != null;
    }
   

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
		if (operator != null) {
			session.put("type", operator.typeOf);
			session.put("email", operator.emailAddress);
			session.put("username", operator.username);
			if (!operator.typeOf.equals(UserType.SUPERADMIN.getUserType())
					&& operator.school != null) {
				session.put("companycode", operator.school.code);
				session.put("companyname", operator.school.schoolName);
			} else {
				session.put("companycode", "donatien");
				session.put("companyname", "UMWARIMU GROUP LTD");
			}

		}
        Schools.dashboard();
    }
}


