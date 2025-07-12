        package stepdefinitions;

        import io.cucumber.java.en.*;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.chrome.ChromeDriver;
        import org.openqa.selenium.chrome.ChromeOptions;
        import org.testng.Assert;
        import pages.SauceDemoPage;
        import io.cucumber.java.After;
        import java.util.HashMap;
        import java.util.Map;


        public class StepDefinitionsSln {


            WebDriver driver;
            SauceDemoPage sauceDemo;

            public StepDefinitionsSln() {
            ChromeOptions options = new ChromeOptions();

            // Gunakan profile kosong (harus path yang tidak dipakai Chrome lain!)
            options.addArguments("user-data-dir=C:/temp/empty-profile"); // Buat folder manual jika belum ada

            // Tambahan flags
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-blink-features=PasswordManager");
            options.addArguments("--incognito"); // Extra isolasi: mode incognito

            // Nonaktifkan password manager via prefs
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            // Jalankan Chrome
            driver = new ChromeDriver(options);
            sauceDemo = new SauceDemoPage(driver);
            }
            
            // LOGIN Steps
            @Given("User is on the Saucedemo login page")
            public void user_is_on_the_saucedemo_login_page() {
                sauceDemo.openLoginPage();
            }

            @When("User enters valid username and password")
            public void user_enters_valid_username_and_password() {
                sauceDemo.login("standard_user", "secret_sauce");
            }

            @When("User enters invalid username and password")
            public void user_enters_invalid_username_and_password() {
                sauceDemo.login("invalid_user", "invalid_pass");
            }

            @And("User clicks the login button")
            public void user_clicks_the_login_button() {
                // Sudah termasuk di dalam method login()
                // Jadi bisa dikosongkan atau dihapus jika tidak diperlukan
            }

            @Then("User should be redirected to the inventory page")
            public void user_should_be_redirected_to_the_inventory_page() {
                Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
            }

            @Then("A login error message should be displayed")
            public void a_login_error_message_should_be_displayed() {
                Assert.assertTrue(driver.getPageSource().contains("Epic sadface"));
            }

            @Then("A checkout error message should be displayed")
            public void a_checkout_error_message_should_be_displayed() {
                Assert.assertTrue(sauceDemo.isCheckoutErrorDisplayed(), "Expected checkout error message not shown");
            }


            // CHECKOUT Steps
            @Given("User is logged in with standard credentials")
            public void user_is_logged_in_with_standard_credentials() {
                sauceDemo.openLoginPage();
                sauceDemo.login("standard_user", "secret_sauce");
                Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
            }

            @And("User adds a product to the cart")
            public void user_adds_a_product_to_the_cart() {
                sauceDemo.addToCart();
            }

            @And("User navigates to the cart page")
            public void user_navigates_to_the_cart_page() {
                sauceDemo.goToCart();   
            }

            @And("User proceeds to the checkout page")
            public void user_proceeds_to_the_checkout_page() {
                sauceDemo.proceedToCheckout();
            }

            @And("User fills in checkout information")
            public void user_fills_in_checkout_information() {
                sauceDemo.fillCheckoutInfo("Bryant", "Hang", "12345");
            }
                

            @And("User clicks the finish button")
            public void user_clicks_the_finish_button() {
                sauceDemo.finishCheckout();
            }

            @Then("User should see the confirmation message")
            public void user_should_see_the_confirmation_message() {
                Assert.assertTrue(sauceDemo.isConfirmationDisplayed());
            }
                // LOGOUT steps
            @When("User clicks the menu button")
            public void user_clicks_the_menu_button() {
                sauceDemo.openMenu();
            }

            @And("User clicks the logout link")
            public void user_clicks_the_logout_link() {
                sauceDemo.clickLogout();
            }

            @Then("User should be redirected to the login page")
            public void user_should_be_redirected_to_the_login_page() {
                // Cek presence dari username input field sebagai bukti halaman login
                Assert.assertTrue(sauceDemo.isOnLoginPage(), "User not redirected to login page.");
            }


            // REMOVE PRODUCT steps
            @When("User removes the product from the cart")
            public void user_removes_the_product_from_the_cart() {
                sauceDemo.removeProductFromCart();
            }

            @Then("The cart should be empty")
                public void the_cart_should_be_empty() {
                Assert.assertTrue(sauceDemo.isCartEmpty());
            }

            // CHECKOUT ERROR steps
            @When("User fills in checkout info with missing first name")
            public void user_fills_in_checkout_info_with_missing_first_name() {
                sauceDemo.fillCheckoutInfoMissingFirstName("Hang", "12345");
            }

            @When("User fills in checkout info with missing postal code")
            public void user_fills_in_checkout_info_with_missing_postal_code() {
                sauceDemo.fillCheckoutInfoMissingPostalCode("Bryant", "Hang");
            }


            @After
            public void tearDown() {    
                if (driver != null) {
                    driver.quit();
                }
            }
        }
