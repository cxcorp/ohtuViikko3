package ohtu;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class Stepdefs {
    WebDriver driver = new ChromeDriver();
    String baseUrl = "http://localhost:4567";
    Random random = new Random();

    @Given("^login is selected$")
    public void login_selected() throws Throwable {
        driver.get(baseUrl);
        clickLinkWithText("login");
    }

    @Given("^register is selected$")
    public void registerIsSelected() throws Throwable {
        driver.get(baseUrl);
        goToRegistration();
    }

    @Given("^user has just registered and is on main page$")
    public void userHasJustRegisteredAndIsOnMainPage() throws Throwable {
        driver.get(baseUrl);
        goToRegistration();
        registerWith("arto" + random.nextInt(100000), "legitpassword1", "legitpassword1");
        skipWelcomeScreen();
    }

    @When("^username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_and_password_are_given(String username, String password) throws Throwable {
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    @Then("^system will respond \"([^\"]*)\"$")
    public void system_will_respond(String pageContent) throws Throwable {
        assertTrue(driver.getPageSource().contains(pageContent));
    }

    @When("^logout is clicked$")
    public void logoutIsClicked() throws Throwable {
        WebElement element = driver.findElement(By.linkText("logout"));
        element.click();
    }

    @When("^correct username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void username_correct_and_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^correct username \"([^\"]*)\" and incorrect password \"([^\"]*)\" are given$")
    public void username_and_incorrect_password_are_given(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^incorrect username \"([^\"]*)\" and password \"([^\"]*)\" are given$")
    public void incorrectUsernameAndPasswordAreGiven(String username, String password) throws Throwable {
        logInWith(username, password);
    }

    @When("^a valid username \"([^\"]*)\" and password \"([^\"]*)\" and matching password confirmation are entered$")
    public void aValidUsernameAndPasswordAndMatchingPasswordConfirmationAreEntered(
        String username,
        String password
    ) throws Throwable {
        registerWith(
            username,
            password,
            password
        );
    }

    @When("^too short of a password \"([^\"]*)\" and valid password \"([^\"]*)\" are entered$")
    public void tooShortOfAPasswordAndValidPasswordAreEntered(
        String username,
        String password
    ) throws Throwable {
        registerWith(
            username,
            password,
            password
        );
    }

    @When("^a valid username \"([^\"]*)\" and too short of a password \"([^\"]*)\" are entered$")
    public void aValidUsernameAndTooShortOfAPasswordAreEntered(
        String username,
        String password
    ) throws Throwable {
        registerWith(
            username,
            password,
            password
        );
    }

    @When("^a valid username \"([^\"]*)\" and valid password \"([^\"]*)\" but incorrect confirmation \"([^\"]*)\" is entered$")
    public void aValidUsernameAndValidPasswordButIncorrectConfirmationIsEntered(
        String username,
        String password,
        String passwordConfirmation
    ) throws Throwable {
        registerWith(
            username,
            password,
            passwordConfirmation
        );
    }

    @Then("^user is logged in$")
    public void user_is_logged_in() throws Throwable {
        pageHasContent("Ohtu Application main page");
    }

    @Then("^user is not logged in and error message is given$")
    public void user_is_not_logged_in_and_error_message_is_given() throws Throwable {
        pageHasContent("invalid username or password");
        pageHasContent("Give your credentials to login");
    }

    @Then("^a new user is created")
    public void userIsShownTheWelcomePage() throws Throwable {
        pageHasContent("Welcome to Ohtu Application!");
    }

    @Then("^user is logged out$")
    public void userIsLoggedOut() throws Throwable {
        pageHasContent("Ohtu App");
        pageHasContent("login");
        pageHasContent("register new user");
    }

    @Then("^user is not created and error \"([^\"]*)\" is reported$")
    public void userIsNotCreatedAndErrorIsReported(String error) throws Throwable {
        pageHasContent(error);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */

    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void goToRegistration() {
        clickLinkWithText("register new user");
    }

    private void skipWelcomeScreen() {
        WebElement element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();
    }

    private void logInWith(String username, String password) {
        assertTrue(driver.getPageSource().contains("Give your credentials to login"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("login"));
        element.submit();
    }

    private void registerWith(String username, String password, String passwordConfirmation) {
        assertTrue(driver.getPageSource().contains("Create username and give password"));
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys(username);
        element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys(passwordConfirmation);
        element = driver.findElement(By.name("signup"));
        element.submit();
    }

    private void clickLinkWithText(String text) {
        int trials = 0;
        while (trials++ < 5) {
            try {
                WebElement element = driver.findElement(By.linkText(text));
                element.click();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
