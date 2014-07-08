package controllers;

import java.lang.reflect.Type;

import models.School;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SchoolSerializer extends Controller implements
		JsonSerializer<School> {

	@Override
	public JsonElement serialize(School scool, Type type,
			JsonSerializationContext context) {

		Gson gson = new GsonBuilder().setExclusionStrategies(
				new LocalExclusionStrategy()).create();
		JsonElement elem = gson.toJsonTree(scool);
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
			return !f.getName().toLowerCase().equals("id")&&!f.getName().toLowerCase().equals("code")
					&& !f.getName().toLowerCase().equals("typeof")
					&& !f.getName().toLowerCase().equals("description")
					&& !f.getName().toLowerCase().equals("schoolname")
					&& !f.getName().toLowerCase().equals("ownerfirstname")
					&& !f.getName().toLowerCase().equals("ownerlastname")
					&& !f.getName().toLowerCase().equals("ownerphonenumber")
					&& !f.getName().toLowerCase().equals("owneremail")
					&& !f.getName().toLowerCase().equals("pobox")
					&& !f.getName().toLowerCase().equals("website")
					&& !f.getName().toLowerCase().equals("status")
					&& !f.getName().toLowerCase().equals("createdon")
					&& !f.getName().toLowerCase().equals("location");
		}
	}

}
