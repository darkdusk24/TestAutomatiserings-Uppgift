package stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bytebuddy.utility.RandomString;

public class StepDefinitions {

	private WebDriver driver;

	@Before
	public void startUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\chromedriver.exe");
		driver = new ChromeDriver(); // Starta chrome
		driver.manage().window().maximize(); // Maximera f�nstret
		driver.get("https://login.mailchimp.com/signup/"); // G� till sidan
		elementFound(By.cssSelector("button#onetrust-accept-btn-handler")); // V�nta tills accept cookies knappen syns
	}

	@After
	public void tearDown() {
		driver.quit(); // St�nga chrome
	}

	@Given("I have entered {string} into the e-mail input box")
	public void i_have_entered_into_the_e_mail_input_box(String email) {
		sendKeys(By.cssSelector("input#email"), email); // Kalla p� sendKeys metoden f�r att skriva in variabeln email i email f�ltet
	}

	@Given("I have entered {string} into the username input box")
	public void i_have_entered_into_the_username_input_box(String usernameType) {
		sendKeys(By.cssSelector("input#new_username"), usernameCreator(usernameType)); // Kalla p� sendKeys metoden f�r att skriva in ett randomiserat username i username f�ltet
	}

	@Given("I have entered {string} into the password input box")
	public void i_have_entered_password_into_the_password_input_box(String password) {
		sendKeys(By.cssSelector("input#new_password"), password); // Kalla p� sendKeys metoden f�r att skriva in variabeln l�senord i password f�ltet
	}

	@When("I press the sign up button")
	public void i_press_the_sign_up_button() {
		click(By.cssSelector("button#onetrust-accept-btn-handler")); // Kalla p� click metoden f�r att klicka p� accept cookies
		click(By.cssSelector("button#create-account")); // Kalla p� click metoden f�r att klicka p� sign up knappen
	}

	@Then("{string} should be visible on the screen")
	public void This_should_be_visible_on_the_screen(String outcome) {
		Boolean successful = result(outcome); // Kalla p� result metoden f�r att f� ett boolean v�rde f�r om testet fungerade som planerat
		assertEquals(true, successful); // Anv�nd assertEquals f�r att avg�ra om testet fungerade som planerat
	}
	
	// Skapa username baserat p� typen speciferat i feature filen
	private String usernameCreator(String usernameType) {
		String input = "";

		switch (usernameType) {
		case "functional":
			input = RandomString.make(50);
			break;
		case "OneHundredOne":
			input = RandomString.make(101);
			break;
		case "alreadyInUse":
			input = "Testing123";
			break;
		}
		return input;
	}
	
	// Kolla om testet f�r det f�rv�ntade resultatet och i s� fall skickar den tillbaka en boolean med v�rdet true
	private Boolean result(String outcome) {
		Boolean successful = false;

		switch (outcome) {
		case "Check your email":
			elementFound(By.cssSelector("h1[class^='!margin-bottom--lv3'"));
			assertEquals(outcome, getText(By.cssSelector("h1[class^='!margin-bottom--lv3'")));
			if(outcome.equals(getText(By.cssSelector("h1[class^='!margin-bottom--lv3'"))))
				successful = true;
			break;
		case "Enter a value less than 100 characters long":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals(outcome, getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")));
			if(outcome.equals(getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))))
				successful = true;
			break;
		case "Another user with this username already exists. Maybe it's your evil twin. Spooky.":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals(outcome, getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span")));
			if(outcome.equals(getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))))
				successful = true;
			break;
		case "Please enter a value":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			assertEquals(outcome, getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span")));
			if(outcome.equals(getText(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"))))
				successful = true;
			break;
		}
		return successful;
	}
	
	// V�nta tills elementet �r hittat.
	// Om 10 sekunder har g�tt utan att elementet �r hittat slutar testet.
	private void elementFound(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		driver.findElement(by);
	}
	
	// V�nta tills elementet �r klickbart och klickar p� det.
	// Om 10 sekunder har g�tt utan att elementet �r klickbart slutar testet.
	private void click(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));

		driver.findElement(by).click();
	}
	
	// V�nta tills elementet �r hittat och skicka d� in text i elementet.
	// Om 10 sekunder har g�tt utan att elementet �r hittat slutar testet.
	private void sendKeys(By by, String keys) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		driver.findElement(by).sendKeys(keys);
	}
	
	// V�nta tills elementet �r hittat och returnera elementets text.
	// Om 10 sekunder har g�tt utan att elementet �r hittat slutar testet.
	private String getText(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		return driver.findElement(by).getText();
	}

}
