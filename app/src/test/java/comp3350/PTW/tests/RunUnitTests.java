package comp3350.PTW.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.PTW.tests.business.RecommendTest;
import comp3350.PTW.tests.business.SearchTest;
import comp3350.PTW.tests.business.UIShowAccessTest;
import comp3350.PTW.tests.objects.PrioritizedShowTest;
import comp3350.PTW.tests.objects.RatedShowTest;
import comp3350.PTW.tests.objects.ShowTest;
import comp3350.PTW.tests.persistence.DataAccessTest;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SearchTest.class,
        RecommendTest.class,
        UIShowAccessTest.class,

        ShowTest.class,
        PrioritizedShowTest.class,
        RatedShowTest.class,

        DataAccessTest.class

})

public class RunUnitTests {

}