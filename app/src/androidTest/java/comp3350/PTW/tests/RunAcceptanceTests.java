package comp3350.PTW.tests;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.PTW.tests.acceptance.StartUpAcceptanceTest;
import comp3350.PTW.tests.acceptance.PTWListAcceptanceTest;
import comp3350.PTW.tests.acceptance.WatchedListAcceptanceTest;
import comp3350.PTW.tests.acceptance.WatchingListAcceptanceTest;
import comp3350.PTW.tests.acceptance.SuggestedAcceptanceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StartUpAcceptanceTest.class,
        PTWListAcceptanceTest.class,
        WatchingListAcceptanceTest.class,
        WatchedListAcceptanceTest.class,
        SuggestedAcceptanceTest.class
})
public class RunAcceptanceTests
{
    public RunAcceptanceTests()
    {
        System.out.println("Running Acceptance tests");
    }
}
