package stepDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bytebuddy.utility.RandomString;

public class StepDefinitions {

	private WebDriver driver;
	
	@Before
	public void startUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\chromedriver.exe");
		driver = new ChromeDriver(); // Starta chrome
		driver.manage().window().maximize(); // Maximera fönstret
		driver.get("https://login.mailchimp.com/signup/");
		elementFound(By.cssSelector("button#onetrust-accept-btn-handler"));
		WebElement cookiesAccept = driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler"));
		elementClickable(By.cssSelector("button#onetrust-accept-btn-handler"));
		Thread.sleep(1000);
		cookiesAccept.click();
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
	public void i_have_entered_password_into_the_password_input_box(String password) throws InterruptedException {
		WebElement passwordInputBox = driver.findElement(By.cssSelector("input#new_password"));
		passwordInputBox.sendKeys(password);
		Thread.sleep(1000);
	}

	@When("I press the sign up button")
	public void i_press_the_sign_up_button() {
		WebElement signUpButton = driver.findElement(By.cssSelector("button#create-account"));
		signUpButton.click();
	}

	@Then("{string} should be visible on the screen")
	public void This_should_be_visible_on_the_screen(String outcome) throws InterruptedException {
		result(outcome);
		Thread.sleep(1000);
		driver.quit();
	}

	private void elementFound(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		driver.findElement(by);

	}

	private void elementClickable(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));

		driver.findElement(by);

	}

	private String usernameCreator(String usernameType) {
		String input = "";

//		switch(usernameType) {
//		
//		}

		if (usernameType.equalsIgnoreCase("functional")) {
			input = RandomString.make(50);
		} else if (usernameType.equalsIgnoreCase("OneHundredOne")) {
			input = RandomString.make(101);
		} else if (usernameType.equalsIgnoreCase("alreadyInUse")) {
			input = "Testing123";
		}

		return input;
	}
	
	private void result(String outcome) {
		if (outcome.equalsIgnoreCase("Account created")) {
			elementFound(By.cssSelector("h1[class^='!margin-bottom--lv3'"));
			WebElement textBanner = driver.findElement(By.cssSelector("h1[class^='!margin-bottom--lv3'"));
			assertEquals("Check your email", textBanner.getText());
		} else if (outcome.equalsIgnoreCase("Too long username")) {
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			WebElement errorText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals("Enter a value less than 100 characters long", errorText.getText());
		} else if (outcome.equalsIgnoreCase("Username already in use")) {
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			WebElement errorText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"));
			assertEquals("Another user with this username already exists. Maybe it's your evil twin. Spooky.", errorText.getText());
		} else if (outcome.equalsIgnoreCase("No email inputted")) {
			elementFound(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			WebElement errorText = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[1]/div/span"));
			assertEquals("Please enter a value", errorText.getText());
		}
	}

}
