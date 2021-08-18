package compatibility.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.codoid.products.exception.FilloException;
import com.galenframework.api.Galen;
import com.galenframework.config.GalenConfig;
import com.galenframework.config.GalenProperty;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.speclang2.pagespec.SectionFilter;

import compatibility.Driver.AndroidWebDriver;
import compatibility.Driver.DesktopWebDriver;
import compatibility.Driver.IOSWebDriver;
import compatibility.Framework.Method_Extension;
import compatibility.Framework.Reuse;
import compatibility.Utils.*;

@SuppressWarnings("rawtypes")
public class TestPack extends Galen{

	protected static logs clog = new logs();
	public static ThreadLocal<WebDriver> SDriver = new ThreadLocal<WebDriver>();
	public static ThreadLocal<String> VF_gspec = new ThreadLocal<String>();
	public static String basedir = System.getProperty("user.dir");
	public static final String Result_FLD = System.getProperty("user.dir") + "/Result";
	public static final String Input_FLD = System.getProperty("user.dir") + "/TestInput";
	public static final String Temp_FLD = System.getProperty("user.dir") + "/Temp";

	public static DateFormat For = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	public static Calendar cal = Calendar.getInstance();
	public static String ExecutionStarttime = For.format(cal.getTime()).toString();
	public static ThreadLocal<LayoutReport> Galen_Test = new ThreadLocal<LayoutReport>();;
	public static ThreadLocal<GalenTestInfo> test = new ThreadLocal<GalenTestInfo>();
	List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
	
	// Device related info
	public static ThreadLocal<String> Scenario = new ThreadLocal<String>();
	public static ThreadLocal<String> DeviceType = new ThreadLocal<String>();
	public static ThreadLocal<String> TestType = new ThreadLocal<String>();
	public static ThreadLocal<String> Brower = new ThreadLocal<String>();
	public static ThreadLocal<String> Device = new ThreadLocal<String>();
	public static ThreadLocal<String> TLDeviceName = new ThreadLocal<String>();
	public static ThreadLocal<String> URL = new ThreadLocal<String>();
	private static final List<String> EMPTY_TAGS = Collections.emptyList();
	private static final Properties EMPTY_PROPERTIES = new Properties();
	private static final Map<String, Object> EMPTY_VARS = Collections.emptyMap();

	public static ThreadLocal<Dictionary> TestData = new ThreadLocal<Dictionary>(); 

	public static File resfold = null;
	public static String trfold = "";
	public static String timefold = "";

	static ExtentReports extent;
	static ExtentHtmlReporter htmlReporter;
	static ExtentTest test1;
	
	

	@BeforeSuite
	public void initialize() {
		Reuse.createtimestampfold();
		extent = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(trfold + "\\Master.html");
		extent.attachReporter(htmlReporter);
		System.setProperty("logfilename", trfold + "\\Logs");
		DOMConfigurator.configure("log4j.xml");
		clog.info("Starting execution at :" + ExecutionStarttime);
	}

	@Parameters({ "Devicetype", "browser", "Testtype", "DeviceName" })
	@BeforeTest
	public void launchdriver(String Devicetype, String browser, String Testtype, String DeviceName)
			throws IOException, FilloException, InterruptedException {
		System.out.println(DeviceName);
		clog.startTestCase("Starting BeforeTest - launchdriver");
		Brower.set(browser);
		DeviceType.set(Devicetype);
		TestType.set(Testtype);
		TLDeviceName.set(DeviceName);
		switch (Devicetype) {
		case "Desktop":
			Device.set(DeviceName);
			SDriver.set(new DesktopWebDriver().getNewDriver());
			Thread.sleep(5000);
			clog.info("launched Desktop driver");
			break;
		case "AndroidMobile":
			Device.set(DeviceName);
			SDriver.set(new AndroidWebDriver().getNewDriver());
			clog.info("launched Andriod driver");
			break;
		case "AndroidTab":
			Device.set(DeviceName);
			SDriver.set(new AndroidWebDriver().getNewDriver());
			clog.info("launched Andriod driver");
			break;
		case "IOSMobile":
		case "IOSTab":
			Device.set(DeviceName);
			SDriver.set(new IOSWebDriver().getNewDriver());
			clog.info("launched IOS driver");
			break;
		}
		String Desc = "Layout check for Vodafone web page on - " + DeviceName + ":" + browser + " Browser";
		test.set(GalenTestInfo.fromString(Desc));
		clog.endTestCase("End BeforeTest - launchdriver");
		return;
	}
	

	@Test
	@Parameters({ "Scenario", "TestCaseID", "TestType" })
	public void Validation(String scenarios, String TestCaseIDs, String TestType) throws IOException, InterruptedException {
		String scenario[] = scenarios.split(",");
		String TestCaseID[] = TestCaseIDs.split(",");
		GalenConfig.getConfig().setProperty(GalenProperty.SCREENSHOT_FULLPAGE, "true");
		GalenConfig.getConfig().setProperty(GalenProperty.SCREENSHOT_FULLPAGE_SCROLLWAIT, "1000");
//		GalenConfig.getConfig().setProperty(GalenProperty.GALEN_BROWSER_VIEWPORT_ADJUSTSIZE, "true");
		int count = TestCaseID.length;
		
		for(int i=1;i<count;i++) {
			
			try {
			Method_Extension.Create_gspec(scenario[i],TestCaseID[i],TestType);
					
			clog.startTestCase("Starting Method - "+scenario[i]);
			SDriver.get().navigate().to(URL.get());
//			SDriver.get().manage().deleteAllCookies();
			SDriver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			List<String> includedTags = null;
			
			if(TestType.equalsIgnoreCase("Desktop_IE"))
			{
				if(i==1) {
				Method_Extension.IE_site_restriction_link_handler();
				
				
				}
			}
					
			if(TestType.contains("Desktop")) {
				SDriver.get().manage().window().maximize();
				
//				Thread.sleep(1000);
				
				 ((JavascriptExecutor) SDriver.get()).executeScript("$('.toaster').css('position', 'relative');");
				 ((JavascriptExecutor) SDriver.get()).executeScript("document.getElementsByClassName('js-navigation')[2].style.position = 'relative'");
				 
				 Thread.sleep(1000);
			}
			else {
				
//				Thread.sleep(1000);
				((JavascriptExecutor) SDriver.get()).executeScript("$('.toaster').css('position', 'relative');");
				 ((JavascriptExecutor) SDriver.get()).executeScript("document.getElementsByClassName('js-navigation')[2].style.position = 'relative'");
				 Thread.sleep(1000);
			}
			
		
			
			if(scenario[i].equalsIgnoreCase("Personal_Voice_Recharge"))
			{
				Method_Extension.Personal_Voice_Recharge();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Data_Recharge"))
			{
				Method_Extension.Personal_Data_Recharge();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Flex_Recharge"))
			{
				Method_Extension.Personal_Flex_Recharge();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Credit Transfer"))
			{
				Method_Extension.Personal_CreditTransfer();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Advance Credit"))
			{
				Method_Extension.Personal_AdvanceCredit();
			}
			else if(scenario[i].equalsIgnoreCase("Consumer_Callertunes"))
			{
				Method_Extension.Consumer_Callertunes();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Int Rts_Prepaid"))
			{
				Method_Extension.Personal_Int_Rts_Prepaid();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Int Rts_Postpaid"))
			{
				Method_Extension.Personal_Int_Rts_Postpaid();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Find a Store"))
			{
				Method_Extension.Personal_Find_a_Store();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Postpaid_Red"))
			{
				Method_Extension.Personal_Postpaid_Red();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Postpaid_Flex"))
			{
				Method_Extension.Personal_Postpaid_Flex();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Prepaid_SIMS"))
			{
				Method_Extension.Personal_Prepaid_SIMS();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Star Number"))
			{
				Method_Extension.Personal_Star_Number();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Star Number_Order"))
			{
				Method_Extension.Personal_Star_Number_Order();
			}
			else if(scenario[i].equalsIgnoreCase("Business_Premium_Login"))
			{
				Method_Extension.Business_Premium_Login();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Arabic_Star Number"))
			{
				Method_Extension.Personal_Arabic_Star_Number();
			}
			else if( (scenario[i].equalsIgnoreCase("Buisness_Roaming")) ) 
			{
				Method_Extension.Buisness_Roaming();
			}
			else if( (scenario[i].equalsIgnoreCase("Personal_Support")) ) 
			{
				Method_Extension.Personal_Support();
			}
			else if(scenario[i].equalsIgnoreCase("Personal_Arabic_Postpaid_Red"))
			{
				Method_Extension.Personal_Postpaid_Red();
			}

			
			
			if(TestType.equalsIgnoreCase("Desktop_Chrome"))
			{
				includedTags = Arrays.asList("Desktop_Chrome");
//				SDriver.get().manage().window().maximize();
				
				clog.startTestCase("Starting Galen Validation - "+scenario[i]);
//				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), includedTags));
				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), new SectionFilter(includedTags, EMPTY_TAGS),EMPTY_PROPERTIES,EMPTY_VARS,Method_Extension.full_screenshot(scenario[i],TestType)));
				clog.endTestCase("End Galen Validation - "+scenario[i]);
			}
			else if(TestType.equalsIgnoreCase("Desktop_Firefox"))
					{
				includedTags = Arrays.asList("Desktop_Firefox");
//				SDriver.get().manage().window().maximize();
				clog.startTestCase("Starting Galen Validation - "+scenario[i]);
				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), new SectionFilter(includedTags, EMPTY_TAGS),EMPTY_PROPERTIES,EMPTY_VARS,Method_Extension.full_screenshot(scenario[i], TestType)));
				clog.endTestCase("End Galen Validation - "+scenario[i]);
					}
			else if(TestType.equalsIgnoreCase("AndroidMobile_Chrome"))
			{
				includedTags = Arrays.asList("Mobile");
				clog.startTestCase("Starting Galen Validation - "+scenario[i]);
				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), includedTags));
				clog.endTestCase("End Galen Validation - "+scenario[i]);
			}
			else if(TestType.equalsIgnoreCase("Desktop_IE"))
			{
				
				includedTags = Arrays.asList("Desktop_Chrome");
//				SDriver.get().manage().window().maximize();
				clog.startTestCase("Starting Galen Validation - "+scenario[i]);
				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), new SectionFilter(includedTags, EMPTY_TAGS),EMPTY_PROPERTIES,EMPTY_VARS,Method_Extension.full_screenshot(scenario[i], TestType)));
//				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), includedTags));
				clog.endTestCase("End Galen Validation - "+scenario[i]);
			}
			else if(TestType.equalsIgnoreCase("AndroidTab_Chrome"))
			{
				includedTags = Arrays.asList("Tab");
				clog.startTestCase("Starting Galen Validation - "+scenario[i]);
				Galen_Test.set(Galen.checkLayout(SDriver.get(), VF_gspec.get(), includedTags));
				clog.endTestCase("End Galen Validation - "+scenario[i]);
			}
			
				
			
		
			clog.endTestCase("End Method - "+scenario[i]);
			
			clog.startTestCase("Starting Report Adding - "+scenario[i]);
			try {
				
				String Reportlayout = "Check layout on Vodafone " + Scenario.get() + " page";
				test.get().getReport().layout(Galen_Test.get(), Reportlayout);
			} catch (Exception e) {
				e.printStackTrace();
			}
			clog.endTestCase("End Report Adding - "+scenario[i]);
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
	
	
	@AfterTest
	public void GalenReportUpdate() throws IOException {
		clog.startTestCase("Starting AfterTest - GalenReportUpdate" );
		tests.add(test.get());
		SDriver.get().close();
		clog.endTestCase("End AfterTest - GalenReportUpdate");
	}

	@AfterSuite
	public void DeleteGspecs() throws IOException {
		clog.startTestCase("Starting AfterSuite - deleteGspecs");
		new HtmlReportBuilder().build(tests, trfold + "/" + TestType.get());
		File Gspecfile = new File(VF_gspec.get());
		if (Gspecfile.delete()) {
			System.out.println("Gspec file deleted successfully");
		}
		clog.endTestCase("End AfterSuite - deleteGspecs");
	}
}
