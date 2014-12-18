	package sethkraut.multiwebdriver;

	import org.junit.runners.BlockJUnit4ClassRunner;
	import org.junit.runners.model.FrameworkMethod;
	import org.junit.runners.model.InitializationError;
	import org.openqa.selenium.WebDriver;

	import java.lang.reflect.Field;

public class SingleWebDriverTestRunner extends BlockJUnit4ClassRunner {
	private final WebDriver webDriver;
	
	public SingleWebDriverTestRunner(Class<?> klass, WebDriver webDriver) throws InitializationError {
		super(klass);
		this.webDriver = webDriver;
	}

	// Test Description methods
	@Override
	protected String getName() {
		return super.getName() + " on " + driverName();
	}

	private String driverName() {
		return webDriver.getClass().getSimpleName();
	}

	@Override
	protected String testName(FrameworkMethod method) {
		return super.testName(method) + " on " + driverName();
	}

	@Override
	protected Object createTest() throws Exception {
		Object o = super.createTest();
		
		for (Field f: o.getClass().getDeclaredFields()) {
			if (f.getType().isAssignableFrom(WebDriver.class)) {
				f.setAccessible(true);
				f.set(o, webDriver);
			}
		}
		
		return o;
	}
}
