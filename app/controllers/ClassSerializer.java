package controllers;

import java.lang.reflect.Type;

import models.Classe;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ClassSerializer extends Controller implements
		JsonSerializer<Classe> {

	@Override
	public JsonElement serialize(Classe role, Type type,
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
			return !f.getName().toLowerCase().equals("id")
					&& !f.getName().toLowerCase().equals("classlevel")
					&& !f.getName().toLowerCase().equals("classlabel")
					&& !f.getName().toLowerCase().equals("box")
					&& !f.getName().toLowerCase().equals("physicaladdress")
					&& !f.getName().toLowerCase().equals("phonenumber")
					&& !f.getName().toLowerCase().equals("emailaddress")
					&& !f.getName().toLowerCase().equals("fullname")&& !f.getName().toLowerCase().equals("createdby")&& !f.getName().toLowerCase().equals("creator")
					&& !f.getName().toLowerCase().equals("tuturaire")
					&& !f.getName().toLowerCase().equals("createdon")&& !f.getName().toLowerCase().equals("username")&& !f.getName().toLowerCase().equals("firstname")&& !f.getName().toLowerCase().equals("lastname");
		}
	}

}
