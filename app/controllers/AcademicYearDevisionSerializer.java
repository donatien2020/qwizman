package controllers;

import java.lang.reflect.Type;

import models.AcademicYearDevision;
import models.AccademicYear;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import controllers.AccademicYearSerializer.LocalExclusionStrategy;

public class AcademicYearDevisionSerializer extends Controller implements
		JsonSerializer<AcademicYearDevision> {

	@Override
	public JsonElement serialize(AcademicYearDevision division, Type type,
			JsonSerializationContext context) {

		Gson gson = new GsonBuilder().setExclusionStrategies(
				new LocalExclusionStrategy()).create();
		JsonElement elem = gson.toJsonTree(division);
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
					&& !f.getName().toLowerCase().equals("startat")
					&& !f.getName().toLowerCase().equals("endat")
					&& !f.getName().toLowerCase().equals("devisionStatus")
					&& !f.getName().toLowerCase().equals("devisionLabel")
					&& !f.getName().toLowerCase().equals("createdon")
					&& !f.getName().toLowerCase().equals("createdby")
					&& !f.getName().toLowerCase().equals("username")
					&& !f.getName().toLowerCase().equals("accademicYear");
		}
	}
}
