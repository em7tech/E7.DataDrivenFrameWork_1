package com.e7.testcases;
import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.e7.base.TestBase;


public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		
	
		verifyEquals("abc", "xyz");
		Thread.sleep(2000);
		log.debug("Inside login test");
		
		click("bmlBtn_CSS");
		Thread.sleep(2000);
		Assert.assertTrue(isElementPresent(By.xpath(or.getProperty("addCustBtn_XPATH"))),"Login not sucessful");
		log.debug("Login SucessTst ");
		Reporter.log("Login SucessTst");
		
		//Assert.fail("Login not sucess");
		
		
	}

}
