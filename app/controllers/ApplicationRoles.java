package controllers;

import play.mvc.With;
import controllers.deadbolt.Deadbolt;

@With(Deadbolt.class)
public class ApplicationRoles extends CRUD{

}
