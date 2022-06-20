package com.bestbuy.bestbuyinfo;

import com.bestbuy.testbase.TestBase;
import cucumber.api.CucumberOptions;

//@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/resources/feature",
            tags = "@UTILITIES")
public class UtilitiesRunner extends TestBase {
}
