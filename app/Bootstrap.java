import java.util.Properties;

import models.Operator;

import play.*;
import play.jobs.*;
import play.test.*;
@OnApplicationStart
public class Bootstrap extends Job {
	public void doJob() {
		if(Play.configuration.getProperty("application.mode").equals("prod")){
			if (Operator.count() == 0) {
				Fixtures.loadModels("initial-data-prod.yml");
			}
		}else{
					if (Operator.count() == 0) {
				Fixtures.loadModels("initial-data.yml");

			} else {
				Fixtures.deleteAllModels();
				Fixtures.loadModels("initial-data.yml");
			}
	
		}
		
	}
}