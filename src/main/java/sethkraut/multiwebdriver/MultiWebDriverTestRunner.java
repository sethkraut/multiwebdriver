package sethkraut.multiwebdriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
 import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MultiWebDriverTestRunner extends ParentRunner<Runner> {
	private List<WebDriver> drivers = new ArrayList<WebDriver>(
			Arrays.asList(new FirefoxDriver(), new ChromeDriver())
	);

	public MultiWebDriverTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	@Override
	protected Description describeChild(Runner child) {
		return child.getDescription();
	}

	private List<Runner> children = null;
	@Override
	protected List<Runner> getChildren() {
		if (children == null) {
			children = getChildrenNew();
		}
		
		return children;
	}
	
	protected List<Runner> getChildrenNew() {
		List<Runner> runners = new ArrayList<Runner>();

		for (WebDriver driver: drivers) {
			try {
				Class<?> javaClass = getTestClass().getJavaClass();
				runners.add(new SingleWebDriverTestRunner(javaClass, driver));
			} catch (InitializationError e) {
				e.printStackTrace();
			}
		}

		return runners;
	}

	@Override
	protected void runChild(Runner child, RunNotifier notifier) {
		child.run(notifier);
	}
}
