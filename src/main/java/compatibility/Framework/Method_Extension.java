package compatibility.Framework;

import java.awt.AWTException;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.core.Snapshot;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import compatibility.test.TestPack;

public class Method_Extension extends TestPack {
	
	public static String basedir = System.getProperty("user.dir");
	
	 static String fpath = basedir + "\\src\\main\\resources\\config\\xpath.properties";
	 static JavascriptExecutor js = (JavascriptExecutor) SDriver.get();
//	 static XSSFWorkbook wb;
	 
	
	public static String get_properties(String xpath) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream input1 = new FileInputStream(fpath);
		prop.load(input1);
		
		return prop.getProperty(xpath);

	}
	
	
	public static void Create_gspec(String scenario, String TestCaseID, String TestType) {
		clog.startTestCase("Starting BeforeMethod - Create_gspec - " +scenario);
		Scenario.set(scenario);
		TestData.set((Dictionary<?, ?>) Reuse.freaddata_diff(TestCaseID));
		URL.set(Reuse.getdata("Url"));
		Writer writer = null;
		try {
			InputStream input = new BufferedInputStream(
					new FileInputStream(basedir + "/TestInput/Thanos_Webpage.xlsx"));
			VF_gspec.set(basedir + "/Temp/" + TestType + ".gspec");
			File dest = new File(basedir + "/Temp/" + TestType + ".gspec");
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(input);
			System.out.println(wb.getNumberOfSheets());
			System.out.println(scenario);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				XSSFSheet sheet = wb.getSheetAt(i);
				if (wb.getSheetName(i).equals(scenario)) {
					writer = new BufferedWriter(new FileWriter(dest));
					Iterator<Row> rows = sheet.rowIterator();
					while (rows.hasNext()) {
						Row row = rows.next();
						Iterator<Cell> cells = row.cellIterator();

						while (cells.hasNext()) {
							Cell cell = cells.next();
							String excel_value = cell.getStringCellValue();
							if (excel_value != "") {
								writer.write(excel_value); 
							} else {
								writer.write("\t");
							}
						}
						writer.write("\n");
					}
				}
			}
			clog.info("Gspec file created " + TestType + ".gspec");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		clog.endTestCase("End BeforeMethod - Create_gspec - "+scenario);
	}
	
	public static File full_screenshot(String scenario, String browser) throws IOException, AWTException, InterruptedException {
//		 path = null;
		
		 String path = basedir + "\\Screenshot\\"+browser+"\\";
		Snapshot<?> snapshot = Shutterbug.shootPage(SDriver.get(), ScrollStrategy.BOTH_DIRECTIONS, 100, true).withName(scenario);
		snapshot.save(path);
		
		String image_location = basedir + "\\Screenshot\\"+browser+"\\"+scenario+".png";
		
		File img_path = new File(image_location);
			
		js.executeScript("window.scrollTo(0, 0)");
		return img_path;

	}
	
	public static void IE_site_restriction_link_handler() {
    	try {
			SDriver.get().navigate ().to ("javascript:document.getElementById('overridelink').click()");
		} catch (Exception e) {
			System.out.println("Fine to proceed -->> 'Continue to this website (not recommended).' in IE is not present");
		}
    }

	
	


	public static void Personal_Voice_Recharge() throws IOException, InterruptedException {
		Thread.sleep(5000);
		SDriver.get().findElement(By.xpath(get_properties("Credit_Recharge"))).click();
	}
	
	public static void Personal_Data_Recharge() throws IOException, InterruptedException {
		Thread.sleep(5000);
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Data_Recharge_No")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Data_Recharge"))).click();
		
		SDriver.get().findElement(By.xpath(get_properties("Data_Recharge_No"))).sendKeys("123");
		
		WebElement ShowMore_2 = SDriver.get().findElement(By.xpath(get_properties("Scroll_bold_text")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_2);
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Data_Recharge_Email"))).sendKeys("aravind");
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Data_Recharge_submit"))).click();
		
	}
	
	public static void Personal_Flex_Recharge() throws IOException, InterruptedException {
		Thread.sleep(5000);

		SDriver.get().findElement(By.xpath(get_properties("Flex_Recharge"))).click();
	}
	
	public static void Personal_AdvanceCredit() throws IOException, InterruptedException {
		Thread.sleep(5000);
		SDriver.get().findElement(By.xpath(get_properties("Advance_credit"))).click();
	}
	
	public static void Personal_CreditTransfer() throws IOException, InterruptedException {
		Thread.sleep(5000);
		SDriver.get().findElement(By.xpath(get_properties("Credit_Transfer"))).click();
		
	}
	
	public static void Consumer_Callertunes() throws IOException, InterruptedException {
		Thread.sleep(5000);
		SDriver.get().findElement(By.xpath(get_properties("Consumer_Callertunes"))).click();
		
	}
	
	public static void Personal_Int_Rts_Prepaid() throws IOException, InterruptedException {
		
		Thread.sleep(5000);
		Select dropdown = new Select(SDriver.get().findElement(By.xpath(get_properties("Personal_Int_Rts_Prepaid_drop1"))));
		dropdown.selectByValue("prePaid");
		
		Select dropdown_1 = new Select(SDriver.get().findElement(By.xpath(get_properties("Personal_Int_Rts_Prepaid_drop2"))));
		dropdown_1.selectByValue("Albania");

		SDriver.get().findElement(By.xpath(get_properties("Find_Rates_button"))).click();
		
	}
	
	public static void Personal_Int_Rts_Postpaid() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		Select dropdown_3 = new Select(SDriver.get().findElement(By.xpath(get_properties("Personal_Int_Rts_Prepaid_drop1"))));
		dropdown_3.selectByValue("postPaid");
		
		Select dropdown_4 = new Select(SDriver.get().findElement(By.xpath(get_properties("Personal_Int_Rts_Prepaid_drop2"))));
		dropdown_4.selectByValue("India");

		SDriver.get().findElement(By.xpath(get_properties("Find_Rates_button"))).click();
		
	}
	
	public static void Personal_Find_a_Store() throws IOException, InterruptedException {
		Thread.sleep(5000);

		SDriver.get().findElement(By.xpath(get_properties("Personal_Find_a_Store"))).click();
		
	}
	
	public static void Personal_Postpaid_Red() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_1")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Postpaid_Red_1"))).click();
		Thread.sleep(3000);
		WebElement ShowMore_2 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_2")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_2);
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Postpaid_Red_2"))).click();
		Thread.sleep(2000);
	}
	
	public static void Personal_Postpaid_Flex() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_3")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Postpaid_Flex_1"))).click();
		Thread.sleep(3000);
		WebElement ShowMore_2 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_2")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_2);
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Postpaid_Flex_2"))).click();
		Thread.sleep(2000);
	}
	public static void Personal_Prepaid_SIMS() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_4a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(5000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Prepaid_SIMS_1"))).click();
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Prepaid_SIMS_2"))).click();
		Thread.sleep(1000);
		WebElement ShowMore_1a = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_4b")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1a);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Prepaid_SIMS_3"))).click();
		Thread.sleep(3000);
		WebElement ShowMore_2 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_5")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_2);
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Prepaid_SIMS_4"))).click();
		Thread.sleep(2000);
	}
	
	public static void Personal_Star_Number() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_6a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Star_Number"))).click();
		Thread.sleep(2000);
	}
	
	public static void Personal_Star_Number_Order() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_7a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Star_Number_Order_Search"))).sendKeys("7777");
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Star_Number_Order_button"))).click();
		Thread.sleep(2000);
		WebElement ShowMore_1a = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_7b")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1a);
		Thread.sleep(3000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Star_Number_Order_select"))).click();
	}
	
	public static void Business_Premium_Login() throws IOException, InterruptedException {
		Thread.sleep(2000);
		SDriver.get().findElement(By.xpath(get_properties("Premium_business_username"))).sendKeys("aravindc@maveric-systems.com");
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Premium_business_password"))).clear();
		SDriver.get().findElement(By.xpath(get_properties("Premium_business_password"))).sendKeys("123456");
		Thread.sleep(1000);
		Actions actions = new Actions(SDriver.get());
		WebElement Login_button = SDriver.get().findElement(By.xpath(get_properties("Premium_business_submit")));
		  actions.moveToElement(Login_button);
		  Login_button.click();
//		SDriver.get().findElement(By.xpath(get_properties("Premium_business_submit"))).submit();
		Thread.sleep(5000);
	}
	
	public static void Personal_Arabic_Star_Number() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_6a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(1000);
		SDriver.get().findElement(By.xpath(get_properties("Personal_Star_Number"))).click();
		Thread.sleep(2000);
	}
	
	public static void Buisness_Roaming() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_8a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(5000);
		
		SDriver.get().findElement(By.xpath(get_properties("Show_More1GB_Data_Roming"))).click();
		Thread.sleep(1000);
	}
	
	public static void Personal_Support() throws IOException, InterruptedException {
		Thread.sleep(5000);
		
		WebElement ShowMore_1 = SDriver.get().findElement(By.xpath(get_properties("Scroll_Text_9a")));
		js.executeScript("arguments[0].scrollIntoView();", ShowMore_1);
		Thread.sleep(2000);
		
		SDriver.get().findElement(By.xpath(get_properties("consumer_support_calls_related_info"))).click();
	}
}

