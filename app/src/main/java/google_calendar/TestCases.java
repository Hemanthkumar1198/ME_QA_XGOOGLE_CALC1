package google_calendar;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    WebDriverWait wait;
    boolean status;
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://calendar.google.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() {
        System.out.println("Start Test case: testCase01");
        status = driver.getCurrentUrl().contains("calendar");
        if (status) {
            System.out.println("The URL of the Calendar homepage contains calendar");
        }
        System.out.println("end Test case: testCase01");
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.findElement(By.xpath("//div[@jsname='WjL7X']//span[text()='Month']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("li[data-viewkey='month']"))).click();
        WebElement monthview = driver.findElement(By.xpath("//button//span[text()='Month']"));
        if (monthview.isDisplayed() && monthview.getText().contains("Month")) {
            System.out.println("tab is switched to monthview");
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@role='gridcell'])[26]"))).click(); // date box
        driver.findElement(By.cssSelector("input[placeholder='Add title and time']"))
                .sendKeys("Crio INTV Task Automation");
        driver.findElement(By.xpath("//button[@class='nUt0vb zmrbhe']//div[@class='x5FT4e kkUTBb']")).click();
        driver.findElement(By.xpath("//textarea[@jsname='YPqjbf']")).sendKeys("Crio INTV Calendar Task Automation");
        driver.findElement(By.cssSelector("button[jsname='x8hlje']")).click();
        System.out.println("end Test case: testCase02");
    }

    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        List<WebElement> task1 = driver.findElements(By.xpath("(//div[@class='KF4T6b jKgTF QGRmIf'])"));
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='KF4T6b jKgTF QGRmIf'])[13]")))
                .click();
        int count = task1.size();
        System.out.println(count);
        // for(int i=0; i<=task.size(); i++){
        // task.get(count).click();
        // }
        // task.click();
        wait.until((ExpectedConditions.elementToBeClickable(By.xpath("(//button[@jsname='DyVDA'])[1]")))).click();
        wait.until((ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@jsname='YPqjbf']")))).sendKeys(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        wait.until((ExpectedConditions.elementToBeClickable(By.cssSelector("button[jsname='x8hlje']")))).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Crio INTV Task Automation']"))).click();
        WebElement descriptionText = driver
                .findElement(By.xpath("//div[contains(text(), 'Crio INTV Calendar Task Automation')]"));
        status = descriptionText.getText().contains(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        if (status && descriptionText.isDisplayed()) {
            System.out.println("The task description is successfully updated");
        }
        driver.findElement(By.id("xDetDlgCloseBu")).click();
        System.out.println("end Test case: testCase03");
    }

    public void testCase04() {
        System.out.println("Start Test case: testCase04");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='KF4T6b jKgTF QGRmIf'])[13]")))
                .click();
        WebElement title = driver.findElement(By.id("rAECCd"));
        String text = title.getText();
        if (text.contains("Crio INTV Task Automation")) {
            System.out.println("verified the task title");
        }
        driver.findElement(By.cssSelector("button[aria-label='Delete task']")).click();
        WebElement deleteTask = driver.findElement(By.xpath("//div[@class='YrbPvc']//div[text()='Task deleted']"));
        String textalert = deleteTask.getText();
        if (textalert.equals("Task deleted")) {
            System.out.println("The task is successfully deleted, and the confirmation message indicates Task deleted");
        }
        System.out.println("end Test case: testCase04");
    }

}

