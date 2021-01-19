package com.e7.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.e7.util.ExcelReader;
import com.e7.util.ExtentManager;
import com.e7.util.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase {
	
	public static WebDriver dr;
	public static Properties conf = new Properties();
	public static Properties or = new Properties();
	public static FileInputStream fis ;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report = ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	
	
	@BeforeSuite
	public void setUp() {
		
		if(dr ==null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				conf.load(fis);
				log.debug("Config file loaded");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				or.load(fis);
				log.debug("OR file loaded");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//Jenkins parameter setup 
			if(System.getenv("browser")!=null  && System.getenv("browser").isEmpty() ) {
				
				browser = System.getProperty("browser");
			}else {
				browser = conf.getProperty("browser");
			}
			
			conf.setProperty("browser", browser);
			
			if(conf.getProperty("browser").equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				//WebDriverManager.firefoxdriver().setup();
				dr= new FirefoxDriver();
				
			}else if(conf.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				dr= new ChromeDriver();
				log.debug("Chrome Launched");
			}else if(conf.getProperty("browser").equals("ie")) {
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				dr= new InternetExplorerDriver();
			}
			
			dr.get(conf.getProperty("testsiteurl"));
			log.debug("Navigated to :" +conf.getProperty("testsiteurl"));
			dr.manage().window().maximize();
			dr.manage().timeouts().implicitlyWait(Integer.parseInt(conf.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(dr, 5);
		}
	}
	
	public void click(String locator) {
		if(locator.endsWith("_CSS")) {
		dr.findElement(By.cssSelector(or.getProperty(locator))).click();
		}else if (locator.endsWith("_XPATH")) {
			dr.findElement(By.xpath(or.getProperty(locator))).click();
		}else if (locator.endsWith("_ID")) {
			dr.findElement(By.id(or.getProperty(locator))).click();
		}else if (locator.endsWith("_NAME")) {
			dr.findElement(By.name(or.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on :"+locator);
	}
	
	public void type(String locator, String value) {
		if(locator.endsWith("_CSS")) {
		dr.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			dr.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);	
		}else if(locator.endsWith("_ID")) {
			dr.findElement(By.id(or.getProperty(locator))).sendKeys(value);	
		}else if(locator.endsWith("_NAME")) {
			dr.findElement(By.name(or.getProperty(locator))).sendKeys(value);	
		}
		test.log(LogStatus.INFO, "Typing in  :"+locator +"Entered value as :"+value);
	}
	
	 static WebElement dropdown;
	
		public void select(String locator,String value) {

		if (locator.endsWith("_CSS")) {
			dropdown = dr.findElement(By.cssSelector(or.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = dr.findElement(By.xpath(or.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = dr.findElement(By.id(or.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);

		test.log(LogStatus.INFO, "Selecting from dropdown :" + locator + " value as " + value);

	}
	
	public boolean  isElementPresent(By by) {
		
		try {
			dr.findElement(by);
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
	}
	
	public static void verifyEquals(String expected,String actual) throws IOException {
		
		try {
			
			Assert.assertEquals(actual, expected);
		}catch(Throwable t) {
			TestUtil.captureScreenShot();
			Reporter.log("<br>" +"Verification failure : " + t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\"href="+TestUtil.screenShotName+"> <img src="+TestUtil.screenShotName+" height=200 width=200></img> </a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			test.log(LogStatus.FAIL,  "Verification failed with exception : "+t.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenShotName));
			
		}
	}

	
	
	
	@AfterSuite
	public void tearDown() {
		
		if(dr!=null) {
			dr.close();
			dr.quit();
			log.debug("Test execution complete ");
		}
		
		
	}

}
