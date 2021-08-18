package compatibility.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import compatibility.test.TestPack;
import io.appium.java_client.android.AndroidDriver;

@SuppressWarnings("rawtypes")
public class AndroidWebDriver extends TestPack implements NewDriver {

	//public static ThreadLocal<Dictionary> Data = new ThreadLocal<Dictionary>();

	AndroidDriver remoteDriver = null;

	@Override
	public AndroidDriver getNewDriver() {
		remoteDriver = getAppiumDriver();
		return remoteDriver;
	}
	
	public String ReadMobileproperties(String fname, String propname) throws IOException {
		String fpath = basedir + "\\src\\main\\resources\\config\\" + fname + ".properties";
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(fpath);

		prop.load(input);

		return prop.getProperty(propname);
	}

	public AndroidDriver getAppiumDriver() {

		try {
			System.out.println("SET CAps");
			String Mobile = TLDeviceName.get();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", ReadMobileproperties(Mobile, "Browser"));
			capabilities.setCapability("deviceName", ReadMobileproperties(Mobile, "Device"));
			capabilities.setCapability("udid", ReadMobileproperties(Mobile, "DeviceName"));
			capabilities.setCapability("platformVersion", ReadMobileproperties(Mobile, "version"));
//			capabilities.setCapability("bootstrapPort", ReadMobileproperties(Mobile, "bootstrapport"));
			capabilities.setCapability("platformName", "ANDROID");
			
			capabilities.setCapability("appActivity", "qa.vodafone.mcare.VFActivity");
			remoteDriver = new AndroidDriver(
					new URL("http://0.0.0.0:"+ReadMobileproperties(Mobile, "appiumport")+"/wd/hub"),capabilities);
				
		} catch (Exception e) {

			e.printStackTrace();
			remoteDriver = null;
		}

		return remoteDriver;
	}

}
