package com.e7.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import com.e7.base.TestBase;
import com.e7.util.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String,String>  data) throws InterruptedException {

		if (!(TestUtil.isTestRunnable("OpenAccountTest", excel))) {

			throw new SkipException("Skipping the test" + "OpenAccountTest".toUpperCase() + " as the runmode is no ");

		}

		click("openaccount_CSS");
		select("customer_CSS", data.get("customer"));
		select("currency_CSS", data.get("currency"));
		click("process_CSS");

		Thread.sleep(2000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		alert.accept();

	}

}
