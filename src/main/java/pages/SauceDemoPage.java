package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SauceDemoPage {
    WebDriver driver;

    public SauceDemoPage(WebDriver driver) {
        this.driver = driver;
    }

    // ====  Login Page ====
    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By loginButton = By.id("login-button");

    public void openLoginPage() {
        driver.get("https://www.saucedemo.com/");
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    // ==== Inventory Page ====
    By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    By cartIcon = By.className("shopping_cart_link");

    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    // ==== Cart Page ====
    By checkoutButton = By.id("checkout");

    public void proceedToCheckout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        driver.findElement(checkoutButton).click();
    }

    // ==== Checkout Info Page ====
    By firstName = By.id("first-name");
    By lastName = By.id("last-name");
    By postalCode = By.id("postal-code");
    By continueButton = By.id("continue");

    public void fillCheckoutInfo(String fname, String lname, String postal) {
        driver.findElement(firstName).sendKeys(fname);
        driver.findElement(lastName).sendKeys(lname);
        driver.findElement(postalCode).sendKeys(postal);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("continue")));
        continueBtn.click();
    }

    // ==== Overview Page ====
    By finishButton = By.id("finish");

    public void finishCheckout() {
        driver.findElement(finishButton).click();
    }

    // ==== Complete Page ====
    By confirmationMessage = By.className("complete-header");

    public boolean isConfirmationDisplayed() {
        return driver.findElement(confirmationMessage).isDisplayed();
    }
        // ==== Logout Related ====
    By menuButton = By.id("react-burger-menu-btn");
    By logoutLink = By.id("logout_sidebar_link");
    By lastNameField = By.id("last-name");

    public void openMenu() {
        driver.findElement(menuButton).click();
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    // ==== Remove Product ====
    By removeButton = By.id("remove-sauce-labs-backpack");

    public void removeProductFromCart() {
        driver.findElement(removeButton).click();
    }

    public boolean isCartEmpty() {
        return driver.findElements(removeButton).isEmpty();
    }

    // ==== Checkout Error (Missing First Name) ====
    By errorMessage = By.cssSelector("h3[data-test='error']");

    public void fillCheckoutInfoMissingFirstName(String lastName, String postal) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.lastNameField)).sendKeys(lastName);
        driver.findElement(postalCode).sendKeys(postal);
        driver.findElement(continueButton).click(); // otomatis submit
    }

    public boolean isCheckoutErrorDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            return error.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().equals("https://www.saucedemo.com/") &&
            driver.findElement(By.id("user-name")).isDisplayed();
    }

    public void fillCheckoutInfoMissingPostalCode(String firstName, String lastName) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        // Kosongkan postal code
        driver.findElement(By.id("continue")).click();
    }

}
