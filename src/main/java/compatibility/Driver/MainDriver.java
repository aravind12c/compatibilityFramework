package compatibility.Driver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class MainDriver {
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws FilloException {

		String basedir = System.getProperty("user.dir");
		File tempfold = new File(basedir + "/Temp");
		if ((!tempfold.exists()))
			tempfold.mkdir();

		Fillo nfillo = new Fillo();
		Connection connection = nfillo.getConnection(basedir + "/TestInput/ScenarioSheet.xlsx");
		String strQuery = "Select * from ScenariosPicker where RunControl = 'YES' ORDER BY SeqNo ASC";
		Recordset rs = connection.executeQuery(strQuery);
		rs.moveFirst();
		ArrayList<String> HeaderNames = rs.getFieldNames();
		rs.close();

		int headercount = HeaderNames.size();
		System.out.println("Get Total Headers count : " + (headercount - 4));

		List<XmlSuite> suites = new ArrayList<XmlSuite>();


		for (int Ts = 4; Ts < headercount; Ts++) {
			String Device = HeaderNames.get(Ts);
			String set[] = Device.split("_");
			strQuery = "Select Scenario,TestCaseID," + Device + " from ScenariosPicker where " + Device
					+ " = 'YES' ORDER BY SeqNo ASC";
			try {
				Recordset res = connection.executeQuery(strQuery);
				res.moveFirst();
				int testcaseCount = res.getCount();
				System.out.println("Get TestCases count : " + testcaseCount);

				XmlSuite suite = new XmlSuite();
				suite.setName(Device);
				// suite.setParallel(XmlSuite.ParallelMode.TESTS);

				XmlTest test = null;
				List<XmlClass> classes = null;
				XmlClass nclass = null;
				List<XmlInclude> nmethods = null;
				

				String Sapratetest = null, SaprateClass = null;
				
				String TCList_Scn_Excel = "";
				String SCList_Scn_Excel = "";

				for (int Tm = 1; Tm <= testcaseCount; Tm++) {

					String tc = res.getField("TestCaseID").toString();
					Connection conn = nfillo.getConnection(basedir + "/TestInput/TestDataSheet.xlsx");
					String str = "Select * from Datatype where TestCaseID = \'" + tc + "\'";
					Recordset re = conn.executeQuery(str);
					re.moveFirst();
					Sapratetest = re.getField(Device).toString();
					
					if (!(re.getField(Device).toString().equals(Sapratetest))||!(Sapratetest.equals(SaprateClass))||(SaprateClass == null) ) {
						if (SaprateClass != null) {
							nclass.setIncludedMethods(nmethods);
							classes.add(nclass);
							test.setXmlClasses(classes);
						}
						test = new XmlTest(suite);
						test.setName(Sapratetest);
						test.addParameter("Devicetype", set[0]);
						test.addParameter("browser", set[1]);
						test.addParameter("Testtype", Device);
						test.addParameter("DeviceName", Sapratetest);
						
						classes = new ArrayList<XmlClass>();
						nclass = new XmlClass();
						nclass.setName("com.vodafone.Framework.TestPack");
						nmethods = new ArrayList<XmlInclude>();
					}

					
					if (res.getField(Device).toString().equalsIgnoreCase("yes")) {
						
						TCList_Scn_Excel =TCList_Scn_Excel+","+ res.getField("TestCaseID");
						SCList_Scn_Excel = SCList_Scn_Excel+","+ res.getField("Scenario");
					}
					
					if (Tm == testcaseCount) {
						
						XmlInclude meth = new XmlInclude("Validation");
						meth.addParameter("TestCaseID", TCList_Scn_Excel);
						meth.addParameter("Scenario", SCList_Scn_Excel);
						meth.addParameter("TestType", Device);
						nmethods.add(meth);
						
						nclass.setIncludedMethods(nmethods);
						classes.add(nclass);
						test.setXmlClasses(classes);
					}

					if (Sapratetest != null) {
						SaprateClass = Sapratetest;
					}

					if (res.hasNext()) {
						res.moveNext();
					}
					re.close();
				}

				res.close();
				//createXmlFile(suite);
				suites.add(suite);
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("No record found for " + Device);
			}

		}
		connection.close();
		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.setSuiteThreadPoolSize(10);
		createSuteFile(suites);
		tng.run();
	}

	public static void createXmlFile(XmlSuite mSuite) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("Temp_TestNG.xml"));
			writer.write(mSuite.toXml());
			writer.flush();
			writer.close();
			System.out.println(new File("Temp_TestNG.xml").getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createSuteFile(List<XmlSuite> suites) {
		FileWriter writer;
		try {
			writer = new FileWriter(new File("Temp_TestNG.xml"));
			int x = suites.size();
			System.out.println(x);
			for (int i = 0; i < x; i++) {
				writer.write(suites.get(i).toXml());
			}
			writer.flush();
			writer.close();
			System.out.println(new File("Temp_TestNG.xml").getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
