package controllers;

import java.lang.reflect.Type;

import models.SchoolReport;
import models.SchoolReportMark;
import play.mvc.Controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SchoolReportMarkSerializer extends Controller implements
		JsonSerializer<SchoolReportMark> {

	@Override
	public JsonElement serialize(SchoolReportMark schoolReportMark, Type type,
			JsonSerializationContext context) {

		Gson gson = new GsonBuilder().setExclusionStrategies(
				new LocalExclusionStrategy()).create();
		JsonElement elem = gson.toJsonTree(schoolReportMark);
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
					&& !f.getName().toLowerCase().equals("course")
					&& !f.getName().toLowerCase().equals("schoolreport")
					&& !f.getName().toLowerCase().equals("updatorteacher")
					&& !f.getName().toLowerCase().equals("divtonetj")
					&& !f.getName().toLowerCase().equals("divtnetx")
					&& !f.getName().toLowerCase().equals("divtnetot")
					&& !f.getName().toLowerCase().equals("divtwotj")
					&& !f.getName().toLowerCase().equals("divtwotx")
					&& !f.getName().toLowerCase().equals("divtwotot")
					&& !f.getName().toLowerCase().equals("divthreetj")
					&& !f.getName().toLowerCase().equals("divthreeex")
					&& !f.getName().toLowerCase().equals("divthreetot")
					&& !f.getName().toLowerCase().equals("yeartj")
					&& !f.getName().toLowerCase().equals("yearex")
					&& !f.getName().toLowerCase().equals("yearavg")
					&& !f.getName().toLowerCase().equals("yeartot")
					&& !f.getName().toLowerCase().equals("observation")
					&& !f.getName().toLowerCase().equals("lastupdatedon")
					&& !f.getName().toLowerCase().equals("overtj")
					&& !f.getName().toLowerCase().equals("overex");
		}
	}

}
