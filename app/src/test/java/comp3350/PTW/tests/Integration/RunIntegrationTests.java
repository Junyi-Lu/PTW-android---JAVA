package comp3350.PTW.tests.Integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BusinessPersistenceSeam.class,
        HSQLIntegrationTest.class
})
public class RunIntegrationTests {
    public RunIntegrationTests() {
        System.out.println("Running Integration tests");
    }


}
