package compatibility.Utils;

import org.apache.log4j.Logger;



public class logs {
	
	private Logger Log = Logger.getLogger(logs.class.getName());//

	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite

	public void startTestCase(String sTestCaseName) {

		Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName + " $$$$$$$$$$$$$$$$$$$$$$$$$");

	}

	// This is to print log for the ending of the test case

	public void endTestCase(String sTestCaseName) {

		Log.info("XXXXXXXXXXXXXXXXXXXXXXX " + sTestCaseName + " XXXXXXXXXXXXXXXXXXXXXX");
		
	}

	// Need to create these methods, so that they can be called

	public void info(String message) {

		Log.info(message+"\r\n");

	}

	public void warn(String message) {

		Log.warn(message);

	}

	public void error(String message) {

		Log.error(message);

	}

	public void fatal(String message) {

		Log.fatal(message);

	}

	public void debug(String message) {

		Log.debug(message);

	}


}
