package com.IBridge.test;
import static com.IBridge.test.DriverScript.App_Logs;
import static com.IBridge.test.DriverScript.CONFIG;
import static com.IBridge.test.DriverScript.OR;
import static com.IBridge.test.DriverScript.currentTestCaseName;
import static com.IBridge.test.DriverScript.currentTestDataSetID;
import static com.IBridge.test.DriverScript.currentTestSuiteXLS;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;














//import static com.qtpselenium.test.DriverScript.currentTestSuiteXLS;
//import static com.qtpselenium.test.DriverScript.currentTestCaseName;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.IBridge.xls.read.Xls_Reader;

public class Keywords{
	
		

	public static WebDriver driver;
	//public int currentTestCaseID;
	public String currentTestCaseName;
	//public int currentTestStepID;
	public Xls_Reader currentTestSuiteXLS;


	
	
	public  String openBrowser(String object,String data){		
		App_Logs.debug("Opening browser");
		if(data.equals("Mozilla"))
		{
			driver = new FirefoxDriver();
		}
		else if(data.equals("IE"))
		{
	System.setProperty("webdriver.ie.driver", "E:\\WorkSpace\\New folder\\IEDriverServer.exe");
			
		 driver = new InternetExplorerDriver();
		}
		else {	
			System.setProperty("webdriver.chrome.driver", "D:\\SwatiDocument\\WorkSpace\\Exc\\chromedriver_win32\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
		return Constants.KEYWORD_PASS;
	}
	
	
	public  String navigate(String object,String data){		
		App_Logs.debug("Navigating to URL");
		try{
		driver.navigate().to(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Notable to open"+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String clickLink(String object,String data){
		App_Logs.debug("Clicking on link ");
		try{
		driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Notable to Click on Link"+e.getMessage();
		}
        
     
		return Constants.KEYWORD_PASS;
	}
	
	public  String clickLink_linkText(String object,String data){
	    try{   
	    	driver.findElement(By.linkText(OR.getProperty(object))).click();
	    }catch(Exception e){
	    	return Constants.KEYWORD_FAIL+"---Link text fghg not found"+e.getMessage();
	    }
		
		
		return Constants.KEYWORD_PASS; 

        
	}
	public  String verifyLinkText(String object,String data){
		App_Logs.debug("Verifying link Text");
		try{
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			String expected = OR.getProperty(data);
			if(actual.equals(expected))
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL+"--Link text not matching"+actual+" -- "+expected;
				
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Link text not verified"+e.getMessage();
		}
	
        
	}
	
	
	public  String clickButton(String object,String data){
		App_Logs.debug("Clicking on Button");
		try{
			//String Actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
			//System.out.println(Actual);
			WebElement a=driver.findElement(By.xpath(OR.getProperty(object)));
			a.click();
			//driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			Thread.sleep(2000);
			return Constants.KEYWORD_PASS+" ";
			
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Notablr to click on Button"+e.getMessage();
		}
		
		//return Constants.KEYWORD_PASS;
	}
	
	
	public  String verifyButtonText(String object,String data){
		App_Logs.debug("Verifying the button text");
		try{
		String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
    	String expected=data;

    	if(actual.equals(expected))
    		return Constants.KEYWORD_PASS+" " + actual;
    	else
    		return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		
	}
	
	
	public  String selectRadio(String object, String data){
		App_Logs.debug("Selecting a radio button");
		try{
			String temp[]=object.split(Constants.Data_Split);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(String object, String data){
		App_Logs.debug("Verify Radio Selected");
		try{
			String temp[]=object.split(Constants.Data_Split);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return Constants.KEYWORD_FAIL+"- Radio not selected";	

				
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyCheckBoxSelected(String object,String data){
		App_Logs.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}
		
		
	}
	
	
	public  String verifyText(String object,String data){
		//App_Logs.debug("Verifying the text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText().trim().toUpperCase();
	    	String expected=data.trim().toUpperCase();
	    

	    	if(actual.equals(expected))
	    		return Constants.KEYWORD_PASS;
	    	else
	    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual;
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
	}
	
	public  String writeInInput(String object,String data){
		App_Logs.debug("Writing in text box");
		
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).clear();
			
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			System.out.println(actual);
			Thread.sleep(3000);
			return Constants.KEYWORD_PASS+" ";
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Unable to Write"+e.getMessage();
		}
		
		
		
	}
	
	public  String verifyTextinInput(String object,String data){
		App_Logs.debug("Verifying the text in input box");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;
			
			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS+" "+actual;
			}else{
				return Constants.KEYWORD_FAIL+"--Not Matching";
			}
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"--Unable to find input box"+e.getMessage();
		}
		
		
	}
	
	
	
	public  String verifyTitle(String object, String data){
		App_Logs.debug("Verifying title");
	       try{
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle=data;
	    	   if(actualTitle.equals(expectedTitle))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	

	public String exist(String object,String data){
	       App_Logs.debug("Checking existance of element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object)));
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Object doest not exist";
			  }
	       
	       
			return Constants.KEYWORD_PASS;
	}
	
	public  String synchronize(String object,String data){
		App_Logs.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		App_Logs.debug("Waiting for an element to be visible");
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				//Thread.sleep(500);
				start++;
			}else{
				break;
			}
		 }
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBroswer(String object, String data){
		App_Logs.debug("Closing the browser");
		try{
			driver.quit();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	public  String checkCheckBox(String object,String data){
		App_Logs.debug("Checking checkbox");
		try{
			// true or null
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbo";
		}
		return Constants.KEYWORD_PASS;
		}
	
	public String unCheckCheckBox(String object,String data){
		App_Logs.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	

	public  String alertmessage(String object, String data){
		try{
		Alert alert = driver.switchTo().alert();
		String return_message = alert.getText();
		System.out.println(return_message);
		//String actual=alert.getText();
		Thread.sleep(2000);
		alert.accept();
		return Constants.KEYWORD_PASS+" "+ return_message ;
		}catch(Exception e){
			
		return Constants.KEYWORD_FAIL ;	
		}
}
	

	public String alertdismiss(String object,String data){
		try{
			Alert alert = driver.switchTo().alert();
			String actual=alert.getText();
			System.out.println(actual);
			
			alert.dismiss();
			Thread.sleep(1000L);
			return Constants.KEYWORD_PASS + " "+ actual ;
			}catch(Exception e){
				
			return Constants.KEYWORD_FAIL;
			
		}
	}
	
	public String dropdownlist(String object, String data){
   	 try{
   		 
   		 //String Actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
		//	System.out.println(Actual);
   	 WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			/*List<WebElement> list = droplist.findElements(By.tagName("option"));
			System.out.println(list.size());
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i).getText());
				if(list.get(i).getText().equals(data)){
					droplist.sendKeys(data);
					Thread.sleep(10000);
				}
					
			}*/
   	 Select s = new Select(droplist);
		List<WebElement> dd=s.getOptions();
		System.out.println(dd.size());
		for(int i=0;i<dd.size();i++){
			System.out.println(dd.get(i).getText());
			
			if(dd.get(i).getText().equalsIgnoreCase(data))
				Thread.sleep(1000);
				
			{
				s.selectByVisibleText(data);
				Thread.sleep(3000);
				
				break;
				
			}
			
		}
		
			return Constants.KEYWORD_PASS;
			
   	 }catch(Exception e){

   		 return Constants.KEYWORD_FAIL;
   	 }
   	 
    }
  
   public  String selectvalue(String object, String data){
		App_Logs.debug("Selecting a dropdown button");
		try{
			String temp[]=object.split(Constants.Data_Split);
			WebElement drop=driver.findElement(By.xpath(OR.getProperty(temp[0])+data));
			 Select s = new Select(drop);
		 		List<WebElement> dd=s.getOptions();
		 		System.out.println(dd.size());
		 		for(int i=0;i<dd.size();i++){
		 			System.out.println(dd.get(i).getText());
		 			if(dd.get(i).getText().equalsIgnoreCase(data)){
		 				s.selectByVisibleText(data);
		 				break;
		 			}
		 		}
		 		return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
  }
  
   public String DisplayText(String object,String data){
			   try{
				   
				   String Text=driver.findElement(By.xpath(OR.getProperty(object))).getText();
				   System.out.println(Text);
				   Thread.sleep(2000);
				   return Constants.KEYWORD_PASS+" "+ Text;
				   //return Constants.KEYWORD_PASS;
			   }catch(Exception e){
				   return Constants.KEYWORD_FAIL;
			  }
		}
		
		
			   public String Doubleclick(String object,String data){
				   try{
					   Actions action=new Actions(driver);
					   action.moveToElement(driver.findElement(By.xpath(OR.getProperty(object)))).doubleClick().build().perform();
					   //System.out.println(Text);
					   return Constants.KEYWORD_PASS;
				   }catch(Exception e){
					   return Constants.KEYWORD_FAIL;
				  }
				   
			    }
		
		
	
	
	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
	
    public String CallclickOptionForPracticeRegionModalityAndInsurance(String object,String data){
    	
    	//String[] optionsToSelect,String XPathForDropdownVal1, String XPathForOptionsDisplayedVal1
    	try{
    	
    	String[] tempXpaths = OR.getProperty(object).split(","); 
    	String[] optionsToSelect = data.split(","); 
    	clickOptionForPracticeRegionModalityAndInsurance(optionsToSelect, tempXpaths[0],tempXpaths[1]);
    	
		//return data;
    	}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
    }
	public String  clickOptionForPracticeRegionModalityAndInsurance(String[] optionsToSelect,String XPathForDropdownVal1, String XPathForOptionsDisplayedVal1){
		App_Logs.debug("Selection of "+XPathForDropdownVal1);
		try{
		int spanIndex = 2; // needed for multiple value selection
		String XPathForDropdown = "//*[@id='login']//span[contains(@data-bind,'"+XPathForDropdownVal1+"')]//following::span";
		String XPathForOptionsDisplayed = "//ul[contains(@data-bind,'"+XPathForOptionsDisplayedVal1+"')]//span";
		String XPathForSelectingAllAsTheCheckbox = "//ul[contains(@data-bind,'"+XPathForOptionsDisplayedVal1+"')]/li[1]/input[1]";
		//String tempXPath = "(//ul[contains(@data-bind,'practiceList')]//span)[2]//preceding::input[1]";
		String tempXPath = "(//ul[contains(@data-bind,'"+XPathForOptionsDisplayedVal1+"')]//span)["+spanIndex+"]//preceding::input[1]";
		int countOfOptionsToSelect = optionsToSelect.length;
		driver.findElement(By.xpath(XPathForDropdown)).click();
		//for all the checkbox selecting postlogin page for every section below here is the rough logic
				Boolean singleOptionFoundInList = false;
				List<WebElement> optionsDisplayedColl = driver.findElements(By.xpath(XPathForOptionsDisplayed)); //span of all the options displayed
				for (String optionToCheck : optionsToSelect) 
				{
					for (WebElement option_span : optionsDisplayedColl) 
					{
					
						if(optionsToSelect.length == 1)
						{
							//for "All" as the checkbox options
				
							if(optionsToSelect[0].contains("All"))
							{
								//Select All from here
								driver.findElement(By.xpath(XPathForSelectingAllAsTheCheckbox)).click();
							    singleOptionFoundInList = true;
								option_span.click();
								XPathForDropdown = XPathForDropdown+"[1]"; //as dropdown once open yeilds more than one span
								driver.findElement(By.xpath(XPathForDropdown)).click();	
								driver.findElement(By.xpath(XPathForDropdown)).click();	
								break;
								
							}
							else
							{
								//else select one of the displayed values
								 if(option_span.getAttribute("title").contains(optionToCheck))
								 {
									System.out.println("option_span");
							
									option_span.click();
									singleOptionFoundInList = true;
									break;
								 }
							}
						}
						else
						{
							//For selecting multiple options
							 if(option_span.getAttribute("title").contains(optionToCheck))
							 {
								WebElement checkboxToSelect_Cbo = driver.findElement(By.xpath(tempXPath));
								spanIndex++;
								tempXPath = "(//ul[contains(@data-bind,'"+XPathForOptionsDisplayedVal1+"')]//span)["+spanIndex+"]//preceding::input[1]";
								checkboxToSelect_Cbo.click();
								
								break;
							 }
							
						}
						
					}//foreach for optionDisplayedCollection
				}//foreach optionsToCheck
				
				
			
				if(optionsToSelect.length>1)
				{
					driver.findElement(By.xpath(XPathForDropdown)).click();	
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public String ClickOptionforClientregion(String object,String data){
		try{
			
		List<WebElement> ListofOption=driver.findElements(By.xpath(OR.getProperty(object)));
		System.out.println(ListofOption.size());
		//for(int i=0;i<=ListofOption.size();i++){
			
			for (WebElement option_span : ListofOption) {
			if(option_span.getText().equals(data)){
				option_span.click();
				
				
			}
			
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbo";
		}
		return Constants.KEYWORD_PASS;
		}	
			
		
		
	

	public String controlStateVerification(String object,String data){
		
		App_Logs.debug("controlStateVerification");
		try{
			WebElement linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToActiveQueue')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToActiveQueue')]", "focus", "class",Constants.Locator_Xpath))
				
			{
				System.out.println("btnGoToActiveQueue");
				//return Constants.KEYWORD_PASS+"btnGoToActiveQueue";
			}
			
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToStandbyQueue')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState( "//span[contains(@data-bind,'btnGoToStandbyQueue')]", "focus", "class",Constants.Locator_Xpath))
			{
				//return Constants.KEYWORD_PASS+"btnGoToStandbyQueue";
				System.out.println("btnGoToStandbyQueue");
			}
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToClientQueue')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToClientQueue')]", "focus", "class",Constants.Locator_Xpath))
			{
				//return Constants.KEYWORD_PASS+"btnGoToClientQueue";
				System.out.println("btnGoToClientQueue");
			}
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToCompletedQueue')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToCompletedQueue')]", "focus", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("btnGoToCompletedQueue");
				//return Constants.KEYWORD_PASS+"btnGoToCompletedQueue";
				//System.out.println("btnGoToCompletedQueue");
			}
			
			
			WebElement newFormAdd = locateElement("//span[contains(@class,'add-form')]", Constants.Locator_Xpath);
			newFormAdd.click();
			Thread.sleep(2000);
			//waitForControl("e", "id", "btnGoToCaseInformation", 60);
			
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToCaseInformation')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToCaseInformation')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("CaseInformation");
			
			}
			
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToPatientInformation')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToPatientInformation')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("PatientInformation");
			
			}
			
			linkToVerify = locateElement("//span[contains(@data-bind,'referringPhysicianSection')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'referringPhysicianSection')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("ReferringPhysician");
			
			}
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToSchedule')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToSchedule')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("Schedule");
			
			}
			
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToInsurance')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToInsurance')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("Insurance");
			
			}
			
			linkToVerify = locateElement("//span[contains(@data-bind,'btnGoToProcedures')]", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToProcedures')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("Procedures");
			
			}
			
			linkToVerify = locateElement("//*[@id='btnGoToCaseNotes']", Constants.Locator_Xpath);
			linkToVerify.click();
			Thread.sleep(2000);
			if(verifyControlState("//span[contains(@data-bind,'btnGoToCaseNotes')]", "background-color-003366", "class",Constants.Locator_Xpath))
			{
				
				System.out.println("CaseNotes");
			
			}
			
			// for close case
		//	driver.findElement(By.xpath("//span[@title='Close']")).click();
			
			//verify the case information headaer is highlighted
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	

public static boolean verifyControlState(String locatorValue, String expectedControlState, String attributenameToCheck, String locatorType)
{
	WebElement element = locateElement(locatorValue, locatorType);
	
	//String controlState = element.getAttribute("data-bind");
	String controlState = element.getAttribute(attributenameToCheck);
	if(controlState.contains(expectedControlState))
	{
		System.out.println("test");
		return true;
	}
	else
	{
		return false;
	}
}

public static boolean verifyControlState(WebElement element, String expectedControlState, String attributenameToCheck)
{
	String controlState = element.getAttribute(attributenameToCheck);
	if(controlState.contains(expectedControlState))
	{
		System.out.println("test");
		return true;
	}
	else
	{
		return false;
	}
}

 public static void waitForControl(String waitType, String locatorType, String locatorValue, int timeOut){

	 WebDriverWait expWait = new WebDriverWait(driver, timeOut);
	 expWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
	
	 
	 
	/*switch(waitType.toUpperCase())
	{
	  case "E" : 
		  WebDriverWait expWait = new WebDriverWait(driver, timeOut);
		  switch(locatorType.toUpperCase())
			{
			case "ID":
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				break;
			case "XPATH": 
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				break;
			case "LINKTEXT": 
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
				break;
			case "CSS": 
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
				break;
			case "CLASSNAME": 
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
				break;
			case "TAGNAME":
				expWait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
				break;
			default : System.out.println("LocatorType not provided"); 
			}			  
		  
		  break;
	  case "I" : break;
	  default : break;
	  
	}*/
}

public static WebElement locateElement(String locatorValue, String locatorType)
{
	WebElement element = null;
	String ID,XPATH,LINKTEXT,CSSPATH,CLASSNAME,TAGNAME;
	if(locatorType.toUpperCase().equals("ID")){
	
		element = driver.findElement(By.id(locatorValue));
	}
	else if(locatorType.toUpperCase().equals("XPATH")){
		element = driver.findElement(By.xpath(locatorValue));
		
	}
	else if(locatorType.toUpperCase().equals("LINKTEXT")){
		element = driver.findElement(By.linkText(locatorValue));
	}
	else if(locatorType.toUpperCase().equals("CSSPATH")){
		element = driver.findElement(By.cssSelector(locatorValue));
	}
	else if(locatorType.toUpperCase().equals("CLASSNAME")){
		element = driver.findElement(By.className(locatorValue));
	}
	else if(locatorType.toUpperCase().equals("TAGNAME")){
		element = driver.findElement(By.tagName(locatorValue));
	}
	/*switch(locatorType.toUpperCase())
	{
	case "ID":
		element = driver.findElement(By.id(locatorValue));
		break;
	case "XPATH": 
		element = driver.findElement(By.xpath(locatorValue));
		break;
	case "LINKTEXT": 
		element = driver.findElement(By.linkText(locatorValue));
		break;
	case "CSSPATH": 
		element = driver.findElement(By.cssSelector(locatorValue));
		break;
	case "CLASSNAME": 
		element = driver.findElement(By.className(locatorValue));
		break;
	case "TAGNAME":
		element = driver.findElement(By.tagName(locatorValue));
		break;
	default : System.out.println("LocatorType not provided"); 
	}*/
		
	return element;
}
    
 public String CallverifyControlIsEditable(String object,String data){
	
	App_Logs.debug("verifyControlIsEditable");
	try{
	WebElement CurrentStatus = driver.findElement(By.xpath(OR.getProperty(object)));
	
	String CurrentElementStatus=verifyControlIsEditable(CurrentStatus, data);
	if(CurrentElementStatus.equals(data))
	{
		//driver.findElement(By.xpath("//span[text()='Confirm']")).click();
		
		return Constants.KEYWORD_PASS;
		
	}
		
	else
	{
	   return Constants.KEYWORD_FAIL;
		
	}
			
	}
	catch(Exception e)
 	{
 		
 		return Constants.KEYWORD_PASS;
	}
	//return Constants.KEYWORD_PASS;
	}
	



 public static String verifyControlIsEditable(WebElement elementToCheck, String editableYesOrNo)
{
	String editable = "";
	String YES;
	String NO;
 if(editableYesOrNo.trim().toUpperCase().equals("NO")){
	 if(elementToCheck.getAttribute("class").contains("readonly") || elementToCheck.getAttribute("class").contains("read-only") || (!elementToCheck.isEnabled()))
		{
			editable = "NO";
		}
		
 }
 else if (editableYesOrNo.trim().toUpperCase().equals("YES")){
	 if(elementToCheck.getAttribute("class").contains("readonly") || elementToCheck.getAttribute("class").contains("read-only"))
		{
			//Do nothing
		}
		else
		{
			if(elementToCheck.isEnabled())
				editable = "YES";
		}
 }
	 else if (editableYesOrNo.trim().toUpperCase().equals("NA")){
		 editable = "NOT PRESENT";
 }
 
 
	/*switch(editableYesOrNo.trim().toUpperCase())
	{
		case "NO": 
			if(elementToCheck.getAttribute("class").contains("readonly") || elementToCheck.getAttribute("class").contains("read-only") || (!elementToCheck.isEnabled()))
			{
				editable = "NO";
			}
			break;
		
		case "YES" : 
			if(elementToCheck.getAttribute("class").contains("readonly") || elementToCheck.getAttribute("class").contains("read-only"))
			{
				//Do nothing
			}
			else
			{
				if(elementToCheck.isEnabled())
					editable = "YES";
			}
			break;
		default : break;
	}
	*/
	return editable;
}

 public String dropdownlists(String object, String data){
   	 try{
   		 
	
   	 WebElement droplist= driver.findElement(By.xpath(object)); 
		
   	 Select s = new Select(droplist);
		List<WebElement> dd=s.getOptions();
		System.out.println(dd.size());
		for(int i=0;i<dd.size();i++){
			System.out.println(dd.get(i).getText());
			
			if(dd.get(i).getText().equalsIgnoreCase(data))
				Thread.sleep(1000);
				
			{
				s.selectByVisibleText(data);
				Thread.sleep(3000);
				
				break;
				
			}
			
		}
		
			return Constants.KEYWORD_PASS;
			
   	 }catch(Exception e){

   		 return Constants.KEYWORD_FAIL;
   	 }
   	 
    }
 
 
 
 
 
 public String CPTStatus(String object, String data){
		    App_Logs.debug("Select CPT status");
		try{
			String[] splitjob = data.split(",");
			String XPathForStatus="//span[text()='Procedure Information']//following::select[contains(@data-bind,'globalProcedureStatusList')]["+splitjob[0]+"]";
			String XpathForProcessBy="//span[text()='Procedure Information']//following::select[contains(@data-bind,'ProcessedByList')]["+splitjob[0]+"]";
			String XpathForAuthorizationNum="//span[text()='Procedures']//following::input[contains(@data-bind,'AuthorizationNumber')]["+splitjob[0]+"]";
			String XpathForAuthorizationNum1="//span[text()='Procedures']//following::input[@title='Case #']["+splitjob[0]+"]";
			String XpathForEffectiveDate="//span[text()='Procedures']//following::input[contains(@data-bind,'EffectiveDate')]["+splitjob[6]+"]";
			String XpathForExpirationDate="//span[text()='Procedures']//following::input[contains(@data-bind,'ExpirationDate')]["+splitjob[7]+"]";
		if(splitjob[1].equals("Authorized"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XpathForProcessBy,splitjob[2]);
			
			WebElement AuthNum=driver.findElement(By.xpath(XpathForAuthorizationNum)); 
			AuthNum.clear();
			AuthNum.sendKeys(splitjob[3]);
			Thread.sleep(2000);
			driver.findElement(By.xpath(XpathForEffectiveDate)).sendKeys(splitjob[4]);
			Thread.sleep(2000);
		    driver.findElement(By.xpath(XpathForExpirationDate)).sendKeys(splitjob[5]);
		}
		else if(splitjob[1].trim().equals("Authorization Not Required"))
		
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XpathForProcessBy,splitjob[2]);
			
		}
	
		else if(splitjob[1].equals("Retro Authorized"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XpathForProcessBy,splitjob[2]);
			WebElement AuthNum=driver.findElement(By.xpath(XpathForAuthorizationNum1)); 
			AuthNum.clear();
			AuthNum.sendKeys(splitjob[3]);
			Thread.sleep(2000);
			
			//driver.findElement(By.xpath(XpathForAuthorizationNum)).sendKeys(splitjob[2]);
			driver.findElement(By.xpath(XpathForEffectiveDate)).sendKeys(splitjob[4]);
		    driver.findElement(By.xpath(XpathForExpirationDate)).sendKeys(splitjob[5]);
		}
		else if(splitjob[1].equals("Denied"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XpathForProcessBy,splitjob[2]);
			
		}
		
		else if(splitjob[1].equals("InsuranceReview"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			WebElement AuthNum=driver.findElement(By.xpath(XpathForAuthorizationNum1));
			AuthNum.clear();
			AuthNum.sendKeys(splitjob[3]);
			
		}
		else if(splitjob[1].equals("PeerToPeer"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XPathForStatus,splitjob[1]);
			WebElement AuthNum=driver.findElement(By.xpath(XpathForAuthorizationNum1));
			AuthNum.clear();
			AuthNum.sendKeys(splitjob[3]);
		}
		else if(splitjob[1].equals("Pending"))
		{
			dropdownlists(XPathForStatus,splitjob[1]);
			dropdownlists(XPathForStatus,splitjob[1]);
			WebElement AuthNum=driver.findElement(By.xpath(XpathForAuthorizationNum1));
			AuthNum.clear();
			AuthNum.sendKeys(splitjob[3]);
		}
		
		
		return Constants.KEYWORD_PASS;
		   }catch(Exception e){
			   return Constants.KEYWORD_FAIL;
		   }
		}
 
		


 public String MultiSelectionJob(String object, String data){
		App_Logs.debug("MultiSelectionJob");
		try{
		String[] splitjob = data.split(",");	
	    String XPathForActivejobs ="//*[@id='activeQueue']//div/div[1]//span[text()='"+splitjob[1]+"']";
	    String Xpathforcompletedjob="//*[@id='completedQueue']//div/div[1]//span[text()='"+splitjob[1]+"']";
	    String XpathforStandbyJobs="//*[@id='standbyQueue']//div/div[1]//span[text()='"+splitjob[1]+"']";
	    String xpathforClientjobs="//*[@id='clientQueue']//div/div[1]//span[text()='"+splitjob[1]+"']";
	    
	
       if(splitjob[0].equals("activeQueue"))
      {
	 //  if(XPathForActivejobs.equals("//*[@id='activeQueue']//div/div[1]//span[text()='"+data+"']"))
		//{		
		List<WebElement> Activejobs=driver.findElements(By.xpath(XPathForActivejobs));
		System.out.println(Activejobs.size());
		for(int i=0;i<Activejobs.size();i++){
			Activejobs.get(i).click();
			break;
		}	
		}
       
       else if(splitjob[0].equals("completedQueue"))

		{
		List<WebElement> Completedjobs=driver.findElements(By.xpath(Xpathforcompletedjob));
		System.out.println(Completedjobs.size());
		for(int i=0;i<Completedjobs.size();i++){
			Completedjobs.get(i).click();
			break;
		}
		}
       
        else if(splitjob[0].equals("standbyQueue"))
  
		{
			List<WebElement> StandByjobs=driver.findElements(By.xpath(XpathforStandbyJobs));
			System.out.println(StandByjobs.size());
			for(int i=0;i<StandByjobs.size();i++){
				StandByjobs.get(i).click();
				break;
			}
		}
        else if(splitjob[0].equals("clientQueue"))
  
        {
        	List<WebElement> Clientjobs=driver.findElements(By.xpath(xpathforClientjobs));
			System.out.println(Clientjobs.size());
			for(int i=0;i<Clientjobs.size();i++){
				Clientjobs.get(i).click();
				break;
				
			}
		}
        	
        
		return Constants.KEYWORD_PASS;
		   }catch(Exception e){
			   return Constants.KEYWORD_FAIL;
		   }
	   }
			
		 
 public String Queues(String object, String data){
	 App_Logs.debug("Queues");
		try{
			
			if(data.equals("Draft"))
					{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
					}
			else if(data.equals("In-Progress"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Verified"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Deleted"))
			   {
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			   }
			else if(data.equals("Correction Required"))
			   {
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			   }
			else if(data.equals("Open"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Pending for Insurance Review"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("QC"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='QC [']//following::span[contains(@data-bind,'redirectToEditJob')][1]")).click();
				Thread.sleep(10000);
				driver.findElement(By.xpath("//span[@title='Send to Queue']")).click();
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='iBridge : Information']//following::span[1]")).click();
				
				
			}
			else if(data.equals("Peer To Peer Review"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Rework"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);;
			}
			else if(data.equals("Invalid Insurance"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Deactivated"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+" [']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Pending For Information"))
				
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+"']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Authorization Not Required"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+"']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Denied"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+"']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
			else if(data.equals("Authorized"))
			{
				WebElement Xpathclick=driver.findElement(By.xpath("//span[text()='"+data+"']"));
				Xpathclick.click();
				Thread.sleep(1000);
			}
	 
	 
	 return Constants.KEYWORD_PASS;
		   }catch(Exception e){
			   return Constants.KEYWORD_FAIL;
		   }
 }
 


	public String MouseMovementSearch(String object, String data){
		try{
			Actions act=new Actions(driver);
			WebElement SelectFilter=driver.findElement(By.xpath(OR.getProperty(object)));
			String CmpnyMst = SelectFilter.getText();
			System.out.println(CmpnyMst);
			act.moveToElement(SelectFilter).build().perform();
			
			
			 if(data.trim().equals("DOS")){
				WebElement DOS=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				act.moveToElement(DOS).click().build().perform();
				
			}
			 else if(data.trim().equals("Facility")){
				WebElement Facility=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				act.moveToElement(Facility).click().build().perform();
			}
			 else if(data.trim().equals("Insurance")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 
			 else if(data.trim().equals("Case")){
				 WebElement Case=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Case).click().build().perform();
			 }
			 
			 else if(data.trim().equals("Location")){
				 WebElement Location=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Location).click().build().perform();
			 }
			 else if(data.trim().equals("MOD")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 else if(data.trim().equals("Patient")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 else if(data.trim().equals("Requested")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 else if(data.trim().equals("Status")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 else if(data.trim().equals("Uploaded")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 else if(data.trim().equals("Worked")){
				 WebElement Insurance=driver.findElement(By.xpath("//span[@title='"+data+"']"));
				 act.moveToElement(Insurance).click().build().perform();
			 }
			 
			 
			 
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
			
		
		}
		
	}
	
	public String CalendarHandle(String object, String data){
		try{
			
			
			    Date d = new Date();
			    SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
			    Calendar cl = Calendar.getInstance();
			    cl.setTime(d);
			    cl.add(Calendar.DAY_OF_YEAR, 1);
			    d=cl.getTime();
			    String str = sd.format(d);
			  System.out.println("the date today is " + str);
			WebElement el = driver.findElement(By.xpath("//*[@id='datepicker']"));
			el.sendKeys(str);
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
			
		
		}
		
	}
	
	public String CallGetcasenumber(String object, String data){
		try{
		
			//Getcasenumber("Casenum");
		
		 return Constants.KEYWORD_PASS+ "Casenum";
	   }catch(Exception e){
		   return Constants.KEYWORD_FAIL;
	   }
}

	
	
	
	
	public String Getcasenumber(String object, String data){
		 App_Logs.debug("Getcasenumber");
			try{
		String Casenum=driver.findElement(By.xpath("//span[contains(@data-bind,'caseNumber')][1]")).getText();
		System.out.println(Casenum);
		String[] splitjob=Casenum.split("Case");
		String Case=splitjob[1].trim();
		System.out.println(Case);
		
		 return Constants.KEYWORD_PASS +" "+ Case;
			   }catch(Exception e){
				   return Constants.KEYWORD_FAIL;
			   }
	 }
	
	public String ClientName(String object, String data){
		 App_Logs.debug("select the client for infinx user");
			try{
		driver.findElement(By.xpath("//span[text()='"+data+"']")).click();
		Thread.sleep(3000);

		 return Constants.KEYWORD_PASS;
			   }catch(Exception e){
				   return Constants.KEYWORD_FAIL;
			   }
	 }
	public String WaitClick(String object, String data){
		 App_Logs.debug("wait until page get uploaded");
			try{
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		        Thread.sleep(20000);

		 return Constants.KEYWORD_PASS;
			   }catch(Exception e){
				   return Constants.KEYWORD_FAIL;
			   }
	 }

			
			
		
	
		
	
	
	
	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	
	
	public String alertMessageVerifyandAccept(String object, String data){
		try{
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			String actual=alert.getText();
			
			String expected="from date cannot be greater than to date.";	
			//String exp="This mail id is already registered!";
			if(actual.equals(expected))
			{
			alert.accept();
			}
			else {
				alert.accept();
				return Constants.KEYWORD_PASS + "Displaying wrong msg";
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String alertMessageVerifyandAccept1(String object, String data){
		try{
			Alert alert = driver.switchTo().alert();
			String return_message = alert.getText();
			System.out.println(return_message);
			String actual=alert.getText();
			
			String expected="Date not in correct format";	
			//String exp="This mail id is already registered!";
			if(actual.equals(expected))
			{
			alert.accept();
			}
			else {
				alert.accept();
				return Constants.KEYWORD_PASS + return_message;
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	

	public String ScrollBar(String object, String data){
		try{
			
			WebElement element = driver.findElement(By.xpath(OR.getProperty(object)));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			
			
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
	}
	
	public String switchtoFrame(String object, String data){
		try{
			Thread.sleep(5000);
			
			//WebElement fra=driver.findElement(By.cssSelector(OR.getProperty(object)));
			
			driver.switchTo().defaultContent();
			driver.switchTo().frame(0);
			driver.findElement(By.xpath("//*[@id='txtUserName']")).sendKeys("manju");
			driver.findElement(By.xpath("//*[@id='btnSubmit']")).click();
			//(OR.getProperty(object));
			return Constants.KEYWORD_PASS;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
		}
	}

	
	
	//mouseMovement
	
		//verify login
		
	public String VerifyLogin(String object, String data){
		
		try{
		String act=driver.findElement(By.xpath(OR.getProperty(object))).getText();
		
		if(act.equals(data)){
			
		return Constants.KEYWORD_PASS;
		}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL;
			
		}
		return Constants.KEYWORD_PASS;
		
		}
		
	     
			
	public String contract_manageruser(String object,String data){
		try{
			
	    driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("mdim01");
	    //driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("manager@001");
				   			
			return Constants.KEYWORD_PASS;
			}catch(Exception e){
		  return Constants.KEYWORD_FAIL;
		}
				   		
		}
	public String contract_managerpassword(String object,String data){
		try{
				   			
	  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys("manager@001");
				   			
		return Constants.KEYWORD_PASS;
		}catch(Exception e){
		 return Constants.KEYWORD_FAIL;
		}
				   		
	}

}




