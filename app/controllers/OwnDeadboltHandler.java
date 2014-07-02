package controllers;
import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import controllers.deadbolt.RestrictedResourcesHandler;
import models.Operator;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.RoleHolder;
import play.mvc.Controller;
public class OwnDeadboltHandler extends Controller implements DeadboltHandler
{
    public void beforeRoleCheck()
    {
        // Note that if you provide your own implementation of Secure's Security class you would refer to that instead
        if (!Secure.Security.isConnected())
        {
            try
            {
                if (!session.contains("username"))
                {
                    flash.put("url", "GET".equals(request.method) ? request.url : "/");
                    Secure.login();
                }
            }
            catch (Throwable t)
            {
                // handle this in an app-specific way
            }
        }
    }

    public RoleHolder getRoleHolder()
    {
        String username = Secure.Security.connected();
        return Operator.getByUsername(username);
    }

    public void onAccessFailure(String controllerClassName)
    {
       forbidden();
    }

    public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor()
    {
        return new ExternalizedRestrictionsAccessor()
        {
            public ExternalizedRestrictions getExternalizedRestrictions(String name)
            {
                return null;
            }
        };
    }

    public RestrictedResourcesHandler getRestrictedResourcesHandler()
    {
        return null;
    }
}