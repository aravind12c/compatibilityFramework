package compatibility.Driver;

import io.appium.java_client.ios.IOSDriver;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import compatibility.test.TestPack;

@SuppressWarnings("rawtypes")
public class IOSWebDriver extends TestPack implements NewDriver {

	IOSDriver remoteDriver = null;

	public IOSDriver getNewDriver() {
		remoteDriver = getIOSDriver();
		return remoteDriver;
	}

	private IOSDriver getIOSDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.android();

		String appiumVersion = "5.6";
		String deviceVersion = "10.0";
		String deviceName = "Iphone";

		String appHost = "127.0.0.1";
		String selPort = "4444";
		try {
			System.out.println("SET CAps");

			capabilities.setCapability("appiumVersion", appiumVersion);
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("version", deviceVersion);

			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("platform", Platform.IOS);
			capabilities.setCapability("automationName", "Appium");
			capabilities.setCapability("browserName", "chrome");

			capabilities.setCapability("newCommandTimeout", 60 * 5);

			System.out.println("Launch");
			remoteDriver =  new IOSDriver(new URL("http://" + appHost + ":" + selPort + "/wd/hub"), capabilities);

		} catch (Exception e) {

			e.printStackTrace();
			remoteDriver = null;
		}

		return remoteDriver;
	}

}
