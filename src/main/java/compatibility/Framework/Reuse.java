package compatibility.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import compatibility.test.TestPack;

public class Reuse extends TestPack {
	public static void createtimestampfold() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();

		try {

			resfold = new File(Result_FLD + "/" + dateFormat.format(cal.getTime()) + "/");
			if ((!resfold.exists()))
				resfold.mkdir();

			timefold = ExecutionStarttime.replace(":", "-").replace(" ", "_");
			File tresfold = new File(resfold + "/" + timefold + "/");
			if ((!tresfold.exists()))
				tresfold.mkdir();
			/*
			 * File screenshotfold = new File(resfold + "/" + timefold + "/screenshots"); if
			 * ((!screenshotfold.exists())) screenshotfold.mkdir();
			 */
			trfold = tresfold.toString();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String takescreenshot() {
		String destDir = trfold + "/screenshots";
		File scrFile = ((TakesScreenshot) SDriver.get()).getScreenshotAs(OutputType.FILE);
		// Set date format to set It as screenshot file name.
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		// Create folder under project with name "screenshots" provided to destDir.
		new File(destDir).mkdirs();
		// Set file name using current date time.
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			// Copy paste file at destination folder location
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
			// Thread.sleep(100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile;
	}

	public String ReadMobileproperties(String fname, String propname) throws IOException {
		String fpath = basedir + "\\src\\test\\resources\\config\\" + fname + ".properties";
		Properties prop = new Properties();
		FileInputStream input = new FileInputStream(fpath);
		prop.load(input);
		return prop.getProperty(propname);
	}

	public static Object freaddata_diff(String Testcase) {
		try {
			Dictionary<String, String> dict = new Hashtable<String, String>();

			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(Input_FLD + "/TestDataSheet.xlsx");
			String strQuery = "Select * from Datatype where TestCaseID = \'" + Testcase + "\'";

			Recordset rs = connection.executeQuery(strQuery);
			int noOfColumns = rs.getFieldNames().size();
			ArrayList<String> fieldnames = rs.getFieldNames();
			rs.moveNext();
			for (int readloop = 0; readloop < noOfColumns; readloop++) {
				String colname = fieldnames.get(readloop);
				String dat = rs.getField(readloop).value();
				if (dat == null) {
					dict.put(colname, "");
				} else {
					dict.put(colname, dat);
				}
			}
			rs.close();
			connection.close();
			return dict;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getdata(String colname) {
		String c = "";
		try {
			c = TestData.get().get(colname).toString();
			return c;
		} catch (Exception e) {
			return c;
		}
	}
}
