package compatibility.Driver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import compatibility.test.TestPack;

public class DesktopWebDriver extends TestPack implements NewDriver {

	WebDriver remoteDriver = null;

	@Override
	public WebDriver getNewDriver() {
		remoteDriver = getRemoteDriver();
		return remoteDriver;
	}

	public WebDriver getRemoteDriver() {
		DesiredCapabilities desiredCap = null;
		String browser = Brower.get();
		try {
			switch (browser.toUpperCase()) {
			case "FIREFOX":
				clog.info("FIREFOX is going to started:::");
				System.setProperty("webdriver.gecko.driver", "./Drivers/geckodriver.exe");
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setPreference("network.proxy.type", 0);
				firefoxProfile.setAcceptUntrustedCertificates(true);
				firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
				remoteDriver = new FirefoxDriver();
				break;
			case "CHROME":
				if(Device.get().equalsIgnoreCase("win")) {
					System.out.println("Luncing in Windows machine");
				}else if(Device.get().equalsIgnoreCase("mac")){
					System.out.println("Luncing in MAC machine");
				}else {
					System.out.println("Luncing in Default machine");
				}
				clog.info("CHROME is going to started:::");
				System.setProperty("webdriver.chrome.driver", basedir + "/Drivers/chromedriver.exe");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("useAutomationExtension", false);
				options.addArguments("--disable-extensions");
				options.setExperimentalOption("prefs", prefs);
				remoteDriver = new ChromeDriver(options);
				break;

			case "IE":
				clog.info("IE is going to started:::");
				System.setProperty("webdriver.ie.driver", basedir + "/Drivers/IEDriverServer.exe");
				desiredCap = DesiredCapabilities.internetExplorer();
				desiredCap.setCapability("ignoreZoomSetting", true);
				desiredCap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				desiredCap.setCapability("requireWindowFocus", true);
				desiredCap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
//				desiredCap.setCapability("ACCEPT_SSL_CERTS", "true");
				
				remoteDriver = new InternetExplorerDriver();
				break;
			case "SAFARI":
				clog.info("SAFARI is going to started:::");
//				desiredCap = DesiredCapabilities.safari();
//				desiredCap.setBrowserName("safari");
//				desiredCap.setCapability("webdriver.safari.noinstall", "false");
//				desiredCap.setCapability("ACCEPT_SSL_CERTS", "true");
				SafariOptions options1 = new SafariOptions();
				remoteDriver = new SafariDriver(options1);
				
				break;
			default:
				clog.info("Default browser is going to started:::");
				desiredCap = DesiredCapabilities.firefox();
				break;
			}
			//System.out.println("Desired cap " + desiredCap);
			// remoteDriver = new RemoteWebDriver(new
			// URL("https://www.vodafone.qa/en/"),desiredCap);
			//remoteDriver.get("https://www.vodafone.qa/en/");
			//remoteDriver.manage().deleteAllCookies();
			//remoteDriver.manage().window().maximize();
			//remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			clog.info("Browser Initiated successfully");
		} catch (Exception e) {
			e.printStackTrace();
			clog.info("Browser Initiation Failed :" + e.getMessage());
		}
		return remoteDriver;
	}
}