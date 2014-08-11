package controllers;

import java.lang.reflect.Type;

import models.SchoolReport;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SchoolReportSerializer extends Controller implements
		JsonSerializer<SchoolReport> {

	@Override
	public JsonElement serialize(SchoolReport schoolReport, Type type,
			JsonSerializationContext context) {

		Gson gson = new GsonBuilder().setExclusionStrategies(
				new LocalExclusionStrategy()).create();
		JsonElement elem = gson.toJsonTree(schoolReport);
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
					&& !f.getName().toLowerCase().equals("student")
					&& !f.getName().toLowerCase().equals("creatorteacher")
					&& !f.getName().toLowerCase().equals("classe")
					&& !f.getName().toLowerCase().equals("description")
					&& !f.getName().toLowerCase().equals("accademicyear")
					&& !f.getName().toLowerCase()
							.equals("accademicyeardevision")
					&& !f.getName().toLowerCase().equals("marks")
					&& !f.getName().toLowerCase().equals("reportlabel")
					&& !f.getName().toLowerCase().equals("fullname")
					&& !f.getName().toLowerCase().equals("createdby")
					&& !f.getName().toLowerCase().equals("lastupdatedon")
					&& !f.getName().toLowerCase().equals("latupoperator")
					&& !f.getName().toLowerCase().equals("content")
					&& !f.getName().toLowerCase().equals("createdon")
					&& !f.getName().toLowerCase().equals("username")
					&& !f.getName().toLowerCase().equals("firstname")
					&& !f.getName().toLowerCase().equals("lastname")
					&& !f.getName().toLowerCase().equals("course")
					&& !f.getName().toLowerCase().equals("updatorteacher")
					&& !f.getName().toLowerCase().equals("divonetj")
					&& !f.getName().toLowerCase().equals("divoneex")
					&& !f.getName().toLowerCase().equals("divonetot")
					&& !f.getName().toLowerCase().equals("divtwotj")
					&& !f.getName().toLowerCase().equals("divtwoex")
					&& !f.getName().toLowerCase().equals("divtwotot")
					&& !f.getName().toLowerCase().equals("divthreetj")
					&& !f.getName().toLowerCase().equals("divthreeex")
					&& !f.getName().toLowerCase().equals("divthreetot")
					&& !f.getName().toLowerCase().equals("yeartj")
					&& !f.getName().toLowerCase().equals("yearex")
					&& !f.getName().toLowerCase().equals("yearavg")&& !f.getName().toLowerCase().equals("overtj")&& !f.getName().toLowerCase().equals("overex")
					&& !f.getName().toLowerCase().equals("yeartot")&& !f.getName().toLowerCase().equals("name");
		}
	}

}
