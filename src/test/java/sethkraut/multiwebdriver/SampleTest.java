package sethkraut.multiwebdriver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Sample
 */
@RunWith(MultiWebDriverTestRunner.class)
public class SampleTest {
    private WebDriver webDriver;

    @Test
    public void canLoadGoogle() {
        webDriver.navigate().to("http://www.google.com");
    }
}
