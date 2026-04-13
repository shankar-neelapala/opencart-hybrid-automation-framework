package testbase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

@SuppressWarnings("deprecation")
public class BaseClass {

	private static  ThreadLocal<WebDriver> driver = new ThreadLocal<>();
		
	public static WebDriver getDriver() {
	    return driver.get();
	}

	public static void setDriver(WebDriver driverInstance) {
	    driver.set(driverInstance);
	}

	public Logger logger;
	public Properties prop;
	
	@BeforeClass(groups = {"smoke", "regression"})
	@Parameters({"os", "browser"})
	public void setUp(String os, String browser) throws IOException {
		
		prop = new Properties();
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		prop.load(file);
		logger=(Logger) LogManager.getLogger(this.getClass());
		
		if(prop.getProperty("execution-env").equalsIgnoreCase("remote")) {
			DesiredCapabilities cap = new DesiredCapabilities();
			switch(os.toLowerCase()) {
			case "windows" : cap.setPlatform(Platform.WIN11);break;
			case "mac" : cap.setPlatform(Platform.MAC);break;
			case "linux" : cap.setPlatform(Platform.LINUX);break;
			default : logger.info("Invalid platform name");return;
			}
			
			switch (browser.toLowerCase()) {
			case "chrome" : cap.setBrowserName("chrome");break;
			case "edge" : cap.setBrowserName("MicrosoftEdge");break;
			case "firefox" : cap.setBrowserName("firefox");break;
			default : logger.info("Invalid browser name");return;
			}
			
			setDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap));
		}
		
		if(prop.getProperty("execution-env").equalsIgnoreCase("local")) {
			switch (browser.toLowerCase()) {
			case "chrome" : setDriver(new ChromeDriver());logger.info("Executing on chrome");break;
			case "edge" : setDriver(new EdgeDriver());logger.info("Executing on edge");break;
			case "firefox" : setDriver(new FirefoxDriver());logger.info("Executing on firefox");break;
			default:logger.info("Invalid Browser");return;
			}
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("appurl"));//reading url from properties file
	}
	
	@AfterClass(groups = {"smoke", "regression"})
	public void tearDown() {
		getDriver().quit();
	}
	
	public String getRandomString() {
		return RandomStringUtils.randomAlphabetic(5).toUpperCase();
	}
	
	public String getRandomAlphaNumeric() {
	    String letters = RandomStringUtils.randomAlphabetic(5);
	    String digit = RandomStringUtils.randomNumeric(1);
	    return letters + digit;
	}
	
	public String getRandomNumeric() {
		return RandomStringUtils.randomNumeric(10);
	}
	
	public static String captureScreenshot(String name) {
		TakesScreenshot ts = (TakesScreenshot) getDriver();
		File src = ts.getScreenshotAs(OutputType.FILE);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		String filename = System.getProperty("user.dir")+"\\screenshots\\"+name+"-"+timeStamp+".png";
		File target = new File(filename);
		src.renameTo(target);
		return filename;
	}

}
