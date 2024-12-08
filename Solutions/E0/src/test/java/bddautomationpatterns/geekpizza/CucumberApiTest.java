package bddautomationpatterns.geekpizza;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("bddautomationpatterns/geekpizza")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "bddautomationpatterns.geekpizza")
public class CucumberApiTest {
}
