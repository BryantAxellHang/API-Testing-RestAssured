package selenium.locator;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class SeleniumIntroductionTest {
    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\bryant.axell\\Downloads\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("prefs", Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false
        ));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        System.out.println("ChromeDriver berhasil dijalankan");
    }

    @BeforeMethod
    public void login() {
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void fullCheckoutFlow() throws InterruptedException {
        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        assert driver.getCurrentUrl().contains("inventory.html");

        // Tambah produk ke cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("shopping_cart_container")).click();

        assert driver.getCurrentUrl().contains("cart");

        // Checkout
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).sendKeys("Bryant");
        driver.findElement(By.id("last-name")).sendKeys("Axell");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();

        assert driver.getCurrentUrl().contains("checkout-step-two");

        driver.findElement(By.id("finish")).click();

        WebElement successMsg = driver.findElement(By.className("complete-header"));
        assert successMsg.getText().equals("Thank you for your order!");
        System.out.println("Pembayaran berhasil: " + successMsg.getText());
    }

    @Test
    public void loginGagalTest() {
        // Login salah
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        assert errorMsg.isDisplayed();
        assert errorMsg.getText().contains("Username and password do not match");

        System.out.println("Login gagal diverifikasi dengan pesan: " + errorMsg.getText());
    }
    @Test
        public void hapusDariKeranjangTest() throws InterruptedException {
        // Login
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        assert driver.getCurrentUrl().contains("inventory.html");

        // Tambah produk ke keranjang
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("shopping_cart_container")).click();

        assert driver.getCurrentUrl().contains("cart");

        // Hapus produk dari keranjang
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();

        // Verifikasi produk sudah tidak ada
        boolean isCartEmpty = driver.findElements(By.className("cart_item")).isEmpty();
        assert isCartEmpty;

        System.out.println("Produk berhasil dihapus. Keranjang kosong.");
    }

    @Test
public void tambahBeberapaProdukKeKeranjangTest() {
    // Login
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();
    assert driver.getCurrentUrl().contains("inventory.html");

    // Tambah beberapa produk
    driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
    driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
    driver.findElement(By.id("shopping_cart_container")).click();

    // Verifikasi dua produk ditambahkan
    int jumlahProdukDiCart = driver.findElements(By.className("cart_item")).size();
    assert jumlahProdukDiCart == 2;

    System.out.println("Berhasil menambahkan 2 produk ke keranjang.");
}



    @AfterMethod
    public void logoutIfNeeded() throws InterruptedException {
        // Cek apakah user sedang login (berada di halaman inventory)
        if (driver.getCurrentUrl().contains("inventory.html") ||
            driver.getCurrentUrl().contains("checkout") ||
            driver.getCurrentUrl().contains("cart")) {

            try {
                driver.findElement(By.id("react-burger-menu-btn")).click();
                Thread.sleep(1000); // Tunggu menu terbuka
                driver.findElement(By.id("logout_sidebar_link")).click();
                System.out.println("Logout berhasil");
            } catch (Exception e) {
                System.out.println("Logout gagal: " + e.getMessage());
            }
        }
    }
    @Test
public void cekDetailProdukTest() {
    // Login
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();
    assert driver.getCurrentUrl().contains("inventory.html");

    // Klik nama produk
    driver.findElement(By.linkText("Sauce Labs Backpack")).click();

    // Verifikasi halaman detail produk
    WebElement title = driver.findElement(By.className("inventory_details_name"));
    assert title.getText().equals("Sauce Labs Backpack");

    System.out.println("Berhasil masuk ke halaman detail produk: " + title.getText());
}


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
