package controllers;

import java.lang.reflect.Type;

import models.Fault;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.FaultSerializer.LocalExclusionStrategy;

public class FaultSerializer extends Controller implements
		JsonSerializer<Fault> {

	@Override
	public JsonElement serialize(Fault user, Type type,
			JsonSerializationContext context) {
		Gson gson = new GsonBuilder().setExclusionStrategies(
				new LocalExclusionStrategy()).create();
		JsonElement elem = gson.toJsonTree(user);
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
					&& !f.getName().toLowerCase().equals("name")
					&& !f.getName().toLowerCase().equals("description")
					&& !f.getName().toLowerCase().equals("creator")
					&& !f.getName().toLowerCase().equals("username")
					&& !f.getName().toLowerCase().equals("type");
		}
	}

}
