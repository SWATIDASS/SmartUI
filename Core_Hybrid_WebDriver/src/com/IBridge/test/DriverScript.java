package com.IBridge.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.IBridge.xls.read.Xls_Reader;

public class DriverScript {

	public static Logger App_Logs;
	//suite.xlsx
	public Xls_Reader suiteXLS;
	public int currentSuiteID;
	public String currentTestSuite;
	
	// current test suite
	public static Xls_Reader currentTestSuiteXLS;
	public static int currentTestCaseID;
	public static String currentTestCaseName;
	public static int currentTestStepID;
	public static String currentKeyword;
	public static int currentTestDataSetID;
	public static Method method[];
	public static Keywords keywords;
	public static String keyword_execution_result;
	public static ArrayList<String> resultSet;                                                                                                                                                                                                                                                                     
	public static String data;
	public static String object;
	public static Method capturescreenShot_method;
	
	
	public static Properties CONFIG;
	public static Properties OR;
	
	public DriverScript() throws SecurityException, NoSuchMethodException{
		keywords = new Keywords();
		method = keywords.getClass().getMethods();
		capturescreenShot_method =keywords.getClass().getMethod("captureScreenshot",String.class,String.class);
	}
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, SecurityException, NoSuchMethodException {
		FileInputStream fs= new FileInputStream(System.getProperty("user.dir")+"//src//com//IBridge//config//config.properties");
		CONFIG=new Properties();
		CONFIG.load(fs);
		
		fs= new FileInputStream(System.getProperty("user.dir")+"//src//com//IBridge//config//OR.properties");
		OR=new Properties();
		OR.load(fs);
		
		
		DriverScript test = new DriverScript();
		
		test.start();
		//deleteXLSReports();
		System.exit(0);
		
	}
	
	
	public void start() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
{
		// initialize the app logs
		App_Logs = Logger.getLogger("devpinoyLogger");
		App_Logs.debug("Hello");
		App_Logs.debug("Properties loaded starting testting");
		
		// 1) check the runmode of test Suite
		// 2) Runmode of the test case in test suite
	    // 3) Execute keywords of the test case serially
		// 4) Execute Keywords as many times as
		// number of data sets - set to Y
		App_Logs.debug("Intialize Suite xlsx");
		suiteXLS = new Xls_Reader(System.getProperty("user.dir")+"//src//com//IBridge//xls//Suite.xlsx");
		
		
		for(currentSuiteID=2;currentSuiteID<=suiteXLS.getRowCount(Constants.Test_Suite_Sheet);currentSuiteID++)
		{
		
			App_Logs.debug(suiteXLS.getCellData(Constants.Test_Suite_Sheet, Constants.Test_Suite_ID, currentSuiteID)+" -- "+  suiteXLS.getCellData("Test Suite", "Runmode", currentSuiteID));
			// test suite name = test suite xls file having test cases
			currentTestSuite=suiteXLS.getCellData(Constants.Test_Suite_Sheet, Constants.Test_Suite_ID, currentSuiteID);
			if(suiteXLS.getCellData(Constants.Test_Suite_Sheet, Constants.RUNMODE, currentSuiteID).equals(Constants.Runmode_YES))
			{
				// execute the test cases in the suite
				App_Logs.debug("******Executing the Suite******"+suiteXLS.getCellData(Constants.Test_Suite_Sheet, Constants.Test_Suite_ID, currentSuiteID));
				currentTestSuiteXLS=new Xls_Reader(System.getProperty("user.dir")+"//src//com//IBridge//xls//"+currentTestSuite+".xlsx");
				keywords.currentTestSuiteXLS=currentTestSuiteXLS;
				// iterate through all the test cases in the suite
			
				for(currentTestCaseID=2;currentTestCaseID<=currentTestSuiteXLS.getRowCount("Test Cases");currentTestCaseID++)
				{		 
					//deleteXLSReports();			
					App_Logs.debug(currentTestSuiteXLS.getCellData(Constants.Test_Cases_Sheet, Constants.TCID, currentTestCaseID)+" -- "+currentTestSuiteXLS.getCellData("Test Cases", "Runmode", currentTestCaseID));
					currentTestCaseName=currentTestSuiteXLS.getCellData(Constants.Test_Cases_Sheet, Constants.TCID, currentTestCaseID);
						keywords.currentTestCaseName=currentTestCaseName;				
					if(currentTestSuiteXLS.getCellData(Constants.Test_Cases_Sheet, Constants.RUNMODE, currentTestCaseID).equals(Constants.Runmode_YES))
					{
						App_Logs.debug("Executing the test case -> "+currentTestCaseName);
					 if(currentTestSuiteXLS.isSheetExist(currentTestCaseName))
					 {
					  	// RUN as many times as number of test data sets with runmode Y
					  for(currentTestDataSetID=2;currentTestDataSetID<=currentTestSuiteXLS.getRowCount(currentTestCaseName);currentTestDataSetID++)	
					  {
						 // deleteXLSReport();
						resultSet= new ArrayList<String>();
						App_Logs.debug("Iteration number "+(currentTestDataSetID-1));
						// checking the runmode for the current data set
					   if(currentTestSuiteXLS.getCellData(currentTestCaseName, Constants.RUNMODE, currentTestDataSetID).equals(Constants.Runmode_YES)){
						
					    // iterating through all keywords	
						   executeKeywords(); // multiple sets of data
						   
					   }
					  
					   createXLSReport();
					  }
					 }else{
						// iterating through all keywords
						 resultSet= new ArrayList<String>(); //PATCH - Introduced later
						 executeKeywords();// no data with the test
						 createXLSReport();

					 }
				}
				}
			}

		}	
		
	}
	
	
	public void executeKeywords() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		
		// iterating through all keywords	
		for(currentTestStepID=2;currentTestStepID<=currentTestSuiteXLS.getRowCount(Constants.Test_Steps_Sheet);currentTestStepID++)
		{
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.TCID, currentTestStepID)))
			{
			data=currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.DATA, currentTestStepID);
			if(data.startsWith(Constants.Data_Start)){
				//read the actual data value from the corresponding column
				data=currentTestSuiteXLS.getCellData(currentTestCaseName, data.split(Constants.Data_Split)[1] , currentTestDataSetID);
				
				//createXLSReport();

			}else if(data.startsWith(Constants.CONFIG)){
				data=CONFIG.getProperty(data.split(Constants.Data_Split)[1]);
				//createXLSReport();
			}else{
				//by default read actual data value from or.properties
				data=OR.getProperty(data);
				//createXLSReport();
			}
			object=currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.OBJECT, currentTestStepID);
			// checking TCID
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.TCID, currentTestStepID)))
			{
			
				currentKeyword=currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.KEYWORD, currentTestStepID);
				App_Logs.debug(currentKeyword);
				// code to execute the keywords as well
			    // reflection API
				
				for(int i=0;i<method.length;i++){
					//System.out.println(method[i].getName());
					
					if(method[i].getName().equals(currentKeyword)){
						keyword_execution_result=(String)method[i].invoke(keywords,object,data);
					App_Logs.debug(keyword_execution_result);
					resultSet.add(keyword_execution_result);
					
					// capture screenshot
					capturescreenShot_method.invoke(keywords,
							currentTestSuite+"_"+currentTestCaseName+"_TS"+currentTestStepID+"_"+(currentTestDataSetID-1),
							keyword_execution_result);
					}
					
				}
			}	
		}
	}
}
	
	
	
	
public void createXLSReport(){
		
		String colName=Constants.RESULT +(currentTestDataSetID-1);
		boolean isColExist=false;
		
		for(int c=0;c<currentTestSuiteXLS.getColumnCount(Constants.Test_Steps_Sheet);c++){
			//System.out.println(currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet,c , 2));
			if(currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet,c , 1).equals(colName)){
				isColExist=true;
				break;
			}
		}
		
		if(!isColExist)
			currentTestSuiteXLS.addColumn(Constants.Test_Steps_Sheet, colName);
		int index=0;
		for(int i=2;i<=currentTestSuiteXLS.getRowCount(Constants.Test_Steps_Sheet);i++){
			
			if(currentTestCaseName.equals(currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet, Constants.TCID, i))){
				if(resultSet.size()==0)
					currentTestSuiteXLS.setCellData(Constants.Test_Steps_Sheet, colName, i, Constants.KEYWORD_SKIP);
				else	
					currentTestSuiteXLS.setCellData(Constants.Test_Steps_Sheet, colName, i, resultSet.get(index));
				index++;
			}
			
			
		}
		
		if(resultSet.size()==0){
			// skip
			currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_SKIP);
			return;
		}else{
			for(int i=0;i<resultSet.size();i++){
				if(!resultSet.get(i).equals(Constants.KEYWORD_PASS)){
					currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_FAIL);
					return;
				}
			}
		}
		currentTestSuiteXLS.setCellData(currentTestCaseName, Constants.RESULT, currentTestDataSetID, Constants.KEYWORD_PASS);
	//	if(!currentTestSuiteXLS.getCellData(currentTestCaseName, "Runmode",currentTestDataSetID).equals("Y")){}
		
	}

public void deleteXLSReports(){
	String colName=Constants.RESULT.toLowerCase();

	
	int b=currentTestSuiteXLS.getColumnCount(Constants.Test_Steps_Sheet);
	for(int c=2;c<=b;c++){
		String a=currentTestSuiteXLS.getCellData(Constants.Test_Steps_Sheet,c , 1).toLowerCase();
		System.out.println(a);
		if(c>6){
			
			currentTestSuiteXLS.removeColumn(Constants.Test_Steps_Sheet,c);
			
		
			
			
		}
	}
	
}
}
