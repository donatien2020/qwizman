package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import controllers.deadbolt.Deadbolt;

import models.*;
@With(Deadbolt.class)
public class Application extends Controller {

    public static void index() {
    	 String user = Security.connected();
        render("@index",user);
    }

}