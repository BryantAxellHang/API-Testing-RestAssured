package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.BaseTest;

public class Hooks {

    @Before
    public void setUp() {
        BaseTest.initialize();
    }

    @After
    public void tearDown() {
        BaseTest.quit();
    }
}
