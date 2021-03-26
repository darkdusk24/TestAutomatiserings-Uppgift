package stepDefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {

	private WebDriver driver;

	@Given("I have entered <email> into the e-mail input box")
	public void i_have_entered_email_into_the_e_mail_input_box() {
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\chromedriver.exe");
		driver = new ChromeDriver(); // Starta chrome
		driver.manage().window().maximize(); // Maximera fönstret
		driver.get("https://login.mailchimp.com/signup/");
		elementFound(By.cssSelector("button#create-account"));
		
		
	}

	@Given("I have entered <username> into the username input box")
	public void i_have_entered_username_into_the_username_input_box() {

	}

	@Given("I have entered <password> into the password input box")
	public void i_have_entered_password_into_the_password_input_box() {

	}

	@When("I press the sign up button")
	public void i_press_the_sign_up_button() {

	}

	@Then("<outcome> should be visible on the screen")
	public void outcome_should_be_visible_on_the_screen() {

	}

	private void elementFound(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));

		driver.findElement(by);

	}

}
