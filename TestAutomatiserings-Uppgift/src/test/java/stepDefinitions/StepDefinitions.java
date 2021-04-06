package stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		driver.manage().window().maximize(); // Maximera fönstret
		driver.get("https://login.mailchimp.com/signup/");
		elementFound(By.cssSelector("button#onetrust-accept-btn-handler"));
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Given("I have entered {string} into the e-mail input box")
	public void i_have_entered_into_the_e_mail_input_box(String email) {
		WebElement emailInputBox = driver.findElement(By.cssSelector("input#email"));
		emailInputBox.sendKeys(email);
	}

	@Given("I have entered {string} into the username input box")
	public void i_have_entered_into_the_username_input_box(String usernameType) {
		WebElement usernameInputBox = driver.findElement(By.cssSelector("input#new_username"));
		usernameInputBox.sendKeys(usernameCreator(usernameType));
	}

	@Given("I have entered {string} into the password input box")
	public void i_have_entered_password_into_the_password_input_box(String password) {
		WebElement passwordInputBox = driver.findElement(By.cssSelector("input#new_password"));
		passwordInputBox.sendKeys(password);
	}

	@When("I press the sign up button")
	public void i_press_the_sign_up_button() {
		WebElement cookiesAccept = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
		elementClickable(By.cssSelector("button#onetrust-accept-btn-handler"));
		cookiesAccept.click();
		WebElement signUpButton = driver.findElement(By.cssSelector("button#create-account"));
		signUpButton.click();
	}

	@Then("{string} should be visible on the screen")
	public void This_should_be_visible_on_the_screen(String outcome) {
		result(outcome);
	}

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

	private Boolean result(String outcome) {
		Boolean successful = false;
		WebElement outcomeText;

		switch (outcome) {
		case "Check your email":
			elementFound(By.cssSelector("h1[class^='!margin-bottom--lv3'"));
			outcomeText = driver.findElement(By.cssSelector("h1[class^='!margin-bottom--lv3'"));
			assertEquals(outcome, outcomeText.getText());
			break;
		case "Enter a value less than 100 characters long":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			outcomeText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals(outcome, outcomeText.getText());
			break;
		case "Another user with this username already exists. Maybe it's your evil twin. Spooky.":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			outcomeText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals(outcome, outcomeText.getText());
			break;
		case "Please enter a value":
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			outcomeText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			assertEquals(outcome, outcomeText.getText());
			break;
		}
		return successful;
	}
	
	private void elementFound(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		driver.findElement(by);
	}

	private void elementClickable(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));

		driver.findElement(by);
	}

}
