package comp3350.PTW.tests.Integration;

import junit.framework.TestCase;

import comp3350.PTW.tests.DataAccessStub;
import comp3350.PTW.tests.persistence.DataAccessTest;
import comp3350.ptw.application.DataServices;
import comp3350.ptw.persistence.DataAccess;

/**
 * HSQLIntegrationTest
 * Group7
 * PTW
 * <p>
 */
public class HSQLIntegrationTest extends TestCase {

    public HSQLIntegrationTest(String arg0) {
        super(arg0);
    }


    public void testDataAccess() {
        DataAccess dataAccess;

        DataServices.closeDataAccess();

        System.out.println("\nStarting Integration test DataAccess (using default DB)");

        //dataAccess = DataServices.createDataAccess(new DataAccessObject(Main.getDBPath()));
        dataAccess = new DataAccessStub("testDB");
        dataAccess.open("testDb");
        DataAccessTest.dataAccessTest(dataAccess);

        DataServices.closeDataAccess();

        System.out.println("Finished Integration test DataAccess (using default DB)");
    }
}

