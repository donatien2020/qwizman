package controllers;

import java.lang.reflect.Type;

import models.ApplicationRole;
import models.Operator;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.OperatorSerializer.LocalExclusionStrategy;

public class ApplicationRoleSerializer extends Controller implements
JsonSerializer<ApplicationRole> {

@Override
public JsonElement serialize(ApplicationRole role, Type type,
	JsonSerializationContext context) {

Gson gson = new GsonBuilder().setExclusionStrategies(
		new LocalExclusionStrategy()).create();
JsonElement elem = gson.toJsonTree(role);
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
	return !f.getName().toLowerCase().equals("id")&&!f.getName().toLowerCase().equals("name");
}
}

}
