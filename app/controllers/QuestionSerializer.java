package controllers;

import java.lang.reflect.Type;

import models.Question;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.EvaluationSerializer.LocalExclusionStrategy;

public class QuestionSerializer extends Controller implements
JsonSerializer<Question> {

@Override
public JsonElement serialize(Question question, Type type,
	JsonSerializationContext context) {

Gson gson = new GsonBuilder().setExclusionStrategies(
		new LocalExclusionStrategy()).create();
JsonElement elem = gson.toJsonTree(question);
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
	return !f.getName().toLowerCase().equals("id")
			&& !f.getName().toLowerCase().equals("description")
			&& !f.getName().toLowerCase().equals("totalmarks")
			&& !f.getName().toLowerCase().equals("qStatus")
			&& !f.getName().toLowerCase().equals("marks")
			&& !f.getName().toLowerCase().equals("maxallowedoptions")
			&& !f.getName().toLowerCase().equals("qstatus")
			&& !f.getName().toLowerCase().equals("fullname")&& !f.getName().toLowerCase().equals("createdby")&& !f.getName().toLowerCase().equals("creator")
			&& !f.getName().toLowerCase().equals("content")
			&& !f.getName().toLowerCase().equals("createdon")&& !f.getName().toLowerCase().equals("username")&& !f.getName().toLowerCase().equals("firstname")&& !f.getName().toLowerCase().equals("lastname");
}
}

}
