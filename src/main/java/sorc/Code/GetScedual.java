package sorc.Code;

/**
 * 
 * This class is the web scraper.
 * 
 * TODO: Clean up this method. (I'll comment this when i do that.)
 * 
 */

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetScedual {

	public String[] getScedual(String pUsername, String pPassword, String[] SecurityQuestions,
			String[] SecurityAnswers) {

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,

		String[] Schedule = new String[28];

		System.setProperty("webdriver.gecko.driver", "WindowsDriver\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.get("https://mysite.starbucks.com/MySchedule/Schedule.aspx");

		// Enters Username
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_UserIDView_txtUserid"));
		element.sendKeys(pUsername);
		// Submits username
		WebElement element2 = driver
				.findElement(By.name("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$btnSubmit"));

		element2.sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement element3 = driver
				.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_lblKBQ1"));
		if (element3.getText().equals(SecurityQuestions[0])) {

			WebElement element4 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_tbxKBA1"));
			element4.sendKeys(SecurityAnswers[0]);
			WebElement element5 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_btnSubmit"));
			element5.sendKeys(Keys.RETURN);
		} else if (element3.getText().equals(SecurityQuestions[1])) {
			WebElement element4 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_tbxKBA1"));
			element4.sendKeys(SecurityAnswers[1]);
			WebElement element5 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_btnSubmit"));
			element5.sendKeys(Keys.RETURN);

		}
		WebElement element6 = driver.findElement(By.name("password"));
		element6.sendKeys(pPassword);
		WebElement element7 = driver.findElement(By.id("submitbutton"));
		element7.sendKeys(Keys.RETURN);

		// WebElement el =
		wait.until(ExpectedConditions.elementToBeClickable(By.id("showNextWeek")));
		new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id("showNextWeek")));
		List<WebElement> Day = driver.findElements(By.className("scheduleDayTitle"));
		List<WebElement> Shift = driver.findElements(By.className("scheduleShiftTime"));
		int i = 0;
		int f = 0;
		int j = 0;
		while (i < 14) {
			if (i % 2 == 0) {
				Schedule[i] = Day.get(f).getText();
				f++;
			} else {
				Schedule[i] = Shift.get(j).getText();
				j++;
			}
			i++;
			// System.out.println("i: " + i + "(Day.size()*2) -1: " + ((Day.size()) - 1));
		}
		WebElement element8 = driver.findElement(By.id("showNextWeek"));
		element8.click();
		while (i < 28) {
			if (i % 2 == 0) {
				Schedule[i] = Day.get(f).getText();
				f++;
			} else {
				Schedule[i] = Shift.get(j).getText();
				j++;
			}
			i++;
			// System.out.println("i: " + i + "(Day.size()*2) -1: " + ((Day.size()) - 1));
		}
		// Close the browser
		driver.quit();
		return Schedule;

	}

	public String[] getScedualWithProgress(String pUsername, String pPassword, String[] SecurityQuestions,
			String[] SecurityAnswers, int pProgress) {

		// Create a new instance of the Firefox driver
		// Notice that the remainder of the code relies on the interface,

		String[] Schedule = new String[14];

		System.setProperty("webdriver.gecko.driver",
				"C:\\Users\\Anthony\\Downloads\\geckodriver-v0.19.1-win64\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		driver.get("https://mysite.starbucks.com/MySchedule/Schedule.aspx");

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_UserIDView_txtUserid"));
		element.sendKeys(pUsername);

		WebElement element2 = driver
				.findElement(By.name("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$btnSubmit"));

		element2.sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement element3 = driver
				.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_lblKBQ1"));
		if (element3.getText().equals(SecurityQuestions[0])) {

			WebElement element4 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_tbxKBA1"));
			element4.sendKeys(SecurityAnswers[0]);
			WebElement element5 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_btnSubmit"));
			element5.sendKeys(Keys.RETURN);
		} else if (element3.getText().equals(SecurityQuestions[1])) {
			WebElement element4 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_tbxKBA1"));
			element4.sendKeys(SecurityAnswers[1]);
			WebElement element5 = driver
					.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_btnSubmit"));
			element5.sendKeys(Keys.RETURN);

		}
		WebElement element6 = driver.findElement(By.name("password"));
		element6.sendKeys(pPassword);
		WebElement element7 = driver.findElement(By.id("submitbutton"));
		element7.sendKeys(Keys.RETURN);

		/*WebElement el =*/ wait.until(ExpectedConditions.elementToBeClickable(By.id("showNextWeek")));

		List<WebElement> Day = driver.findElements(By.className("scheduleDayTitle"));
		List<WebElement> Shift = driver.findElements(By.className("scheduleShiftTime"));
		int i = 0;
		int f = 0;
		int j = 0;
		while (i < 14) {
			if (i % 2 == 0) {
				Schedule[i] = Day.get(f).getText();
				f++;
			} else {
				Schedule[i] = Shift.get(j).getText();
				j++;
			}
			i++;
			// System.out.println("i: " + i + "(Day.size()*2) -1: " + ((Day.size()) - 1));
		}

		// Close the browser
		driver.quit();
		return Schedule;

	}

	public String prob(String pUsername) {
		String s = "";
		System.setProperty("webdriver.gecko.driver", "WindowsDriver\\geckodriver.exe");
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);

		WebDriver driver = new FirefoxDriver(options);

		driver.get("https://mysite.starbucks.com/MySchedule/Schedule.aspx");

		// Enters Username
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebElement element = driver.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_UserIDView_txtUserid"));
		element.sendKeys(pUsername);

		// Submits username
		WebElement element2 = driver
				.findElement(By.name("ctl00$ContentPlaceHolder1$MFALoginControl1$UserIDView$btnSubmit"));

		element2.sendKeys(Keys.RETURN);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement element3 = driver
				.findElement(By.id("ContentPlaceHolder1_MFALoginControl1_KBARegistrationView_lblKBQ1"));
		s = element3.getText();

		driver.quit();
		return s;
	}

	public static String loadingUpdate(int updatePercent, String Bar) {
		String updateBar = Bar;
		for (int i = 0; i < updatePercent / 2; i++) {
			updateBar += "=";
		}
		System.out.println(updateBar);
		return updateBar;
	}

}
