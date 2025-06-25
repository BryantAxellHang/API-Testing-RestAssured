package test.Selenium.locator;
// import model.ObjectUpdateRequest;

import.junit.Test;

public class seleniumintro {
    
    @Test
    public void loginscenario() {
        System.setProperties(webdriver.chrome.driver, "C:\\Users\\axell\\Downloads\\chromedriver_win32\\chromedriver.exe");

        webdriver.ChromeDriver driver = new webdriver.ChromeDriver();
        driver.get("https://rahulshettyacademy.com/locatorspractice/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        Thread.sleep(2000);

        webelement username = driver.findElement(By.id("inputUsername"));
    }

}
