package ohtu;

import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tester {
    private static final Random rand = new Random();

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:4567");

            sleep(2);

            WebElement element = driver.findElement(By.linkText("login"));
            element.click();

            // epäonnistunut kirjautuminen: oikea käyttäjätunnus, väärä salasana
            sleep(1);
            element = driver.findElement(By.name("username"));
            element.sendKeys("pekka");
            element = driver.findElement(By.name("password"));
            element.sendKeys("asdasdasdasdasd");
            element = driver.findElement(By.name("login"));
            element.submit();

            // epäonnistunut kirjautuminen: ei-olemassaoleva käyttäjätunnus
            sleep(1);
            element = driver.findElement(By.name("username"));
            element.sendKeys("asddsadasdasd");
            element = driver.findElement(By.name("password"));
            element.sendKeys("asdasdasdasdasd");
            element = driver.findElement(By.name("login"));
            element.submit();

            // uuden käyttäjätunnuksen luominen

            String newUsername = "arto" + rand.nextInt(1000000);
            String newPassword = "arto_the_slayer1337";

            sleep(1);
            driver.get("http://localhost:4567");
            element = driver.findElement(By.linkText("register new user"));
            element.click();
            sleep(1);
            element = driver.findElement(By.name("username"));
            element.sendKeys(newUsername);
            element = driver.findElement(By.name("password"));
            element.sendKeys(newPassword);
            element = driver.findElement(By.name("passwordConfirmation"));
            element.sendKeys(newPassword);
            element = driver.findElement(By.name("signup"));
            element.click();

            // uuden käyttäjätunnuksen luomisen jälkeen tapahtuva ulkoskirjautuminen sovelluksesta
            sleep(1);
            element = driver.findElement(By.linkText("continue to application mainpage"));
            element.click();
            sleep(1);
            element = driver.findElement(By.linkText("logout"));
            element.click();

            // ok login
            sleep(1);
            element = driver.findElement(By.linkText("login"));
            element.click();
            sleep(1);
            element = driver.findElement(By.name("username"));
            element.sendKeys("pekka");
            element = driver.findElement(By.name("password"));
            element.sendKeys("akkep");
            element = driver.findElement(By.name("login"));
            element.submit();
        } catch (Throwable ex) {
            driver.quit();
        }
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n * 1000);
        } catch (Exception e) {
        }
    }
}
