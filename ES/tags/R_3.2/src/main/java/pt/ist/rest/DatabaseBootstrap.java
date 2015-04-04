package pt.ist.rest;

import pt.ist.rest.domain.PortalRestaurante;
import pt.ist.rest.exceptions.restException;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

public class DatabaseBootstrap {
    private static boolean notInitialized = true;

    public synchronized static void init() {
        if (notInitialized) {
            FenixFramework.initialize(new Config() {
                {
                    //	                dbAlias = "//localhost:3306/restdb";
                    //	                dbUsername = "rest";
                    //	                dbPassword = "r3st";
                    dbAlias = "//localhost:3306/rest";
                    dbUsername = "root";
                    dbPassword = "rootroot";
                    domainModelPath = "src/main/dml/domain.dml";
                    rootClass = PortalRestaurante.class;
                }
            });
        }
        notInitialized = false;
    }

    public static void setup() {
        try {
            pt.ist.rest.RestSetup.populateDomain();
        } catch (restException e) {
            System.out.println("Error while populating rest application: " + e);
        }
    }

}
