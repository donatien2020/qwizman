package controllers;

import java.lang.reflect.Type;

import models.StudentClasse;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.StudentClasseSerializer.LocalExclusionStrategy;

public class StudentClasseSerializer extends Controller implements
JsonSerializer<StudentClasse> {

@Override
public JsonElement serialize(StudentClasse studentClasse, Type type,
	JsonSerializationContext context) {

Gson gson = new GsonBuilder().setExclusionStrategies(
		new LocalExclusionStrategy()).create();
JsonElement elem = gson.toJsonTree(studentClasse);
JsonObject obj = elem.getAsJsonObject();
return elem;
}

public static class LocalExclusionStrategy implements ExclusionStrategy {
@Override
public boolean shouldSkipClass(Class<?> klass) {
	return false;
}

@Override
public boolean shouldSkipField(FieldAttributes f) {
	return !f.getName().toLowerCase().equals("firstname")
			&& !f.getName().toLowerCase().equals("id")&& !f.getName().toLowerCase().equals("name")
			&& !f.getName().toLowerCase().equals("lastname")
			&& !f.getName().toLowerCase().equals("phonenumber")
			&& !f.getName().toLowerCase().equals("emailaddress")
			&& !f.getName().toLowerCase().equals("username")
			&& !f.getName().toLowerCase().equals("student")
			&& !f.getName().toLowerCase().equals("classe")
			&& !f.getName().toLowerCase().equals("physicaladdress")
			&& !f.getName().toLowerCase().equals("box")
			&& !f.getName().toLowerCase().equals("webSite")
			&& !f.getName().toLowerCase().equals("accademicYearDevision")
			&& !f.getName().toLowerCase().equals("role");
}
}

}
