package service;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;


import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tests {

	private WebDriver driver;
	String url;
	private String firstName;
	private String lastName;
	private String email;
	private String sessionName;
	private String startTime;
	private String endTime;
	private String date;
	private int maxPeople;
	private String description;
	private String location;
	private String category;
	
	@Before
	public void setup(){
		
		System.setProperty("webdriver.chrome.driver", "C://Users//Tom Stockmans//Documents//web3//chromedriver.exe");
		// windows: gebruik dubbele \\ om pad aan te geven
		// hint: zoek een werkende test op van web 2 ...

	driver = new HtmlUnitDriver();

	firstName = "";
	lastName = "";
	email = "";
	sessionName = "";
	startTime = "";
	endTime = "";
	date = "";
	maxPeople = 0;
	description = "";
	location = "";
	category = "";

	System.out.println("ok");
	}
	
	@After
	public void quit(){
		driver.quit();
	}

	@Given("^a link to the overview page$")
	public void a_link_to_the_overview_page() throws Throwable {
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tom Stockmans\\Documents\\web3\\chromedriver.exe");
		// windows: gebruik dubbele \\ om pad aan te geven
		// hint: zoek een werkende test op van web 2 ...
		//driver = new ChromeDriver();
	    url = "http://localhost:8080/OpenClass-11/Controller?action=getOpleidingenOverzicht";
	}

	@When("^i click the link$")
	public void i_click_the_link() throws Throwable {
		
	    driver.get("http://localhost:8080/OpenClass-11/Controller?action=getOpleidingenOverzicht");
	}

	@Then("^all the \"([^\"]*)\" with their \"([^\"]*)\" are displayed$")
	public void all_the_with_there_are_displayed(String department, String education) throws Throwable {
		java.util.List<WebElement> el = driver.findElements(By.id(department));
		
		boolean found = false;
		for (WebElement dep: el) {
			System.out.println(dep.getText());
			if (dep.getText().contains(education)) {
				found = true;
			}
		}
		assertTrue(found);
	}
	

	@Given("^a user that is on the education overview page$")
	public void a_user_that_is_on_the_education_overview_page() throws Throwable {
	    driver.get("http://localhost:8080/OpenClass-11/Controller?action=getOpleidingenOverzicht");

	}

	@When("^he chooses a the education \"([^\"]*)\"$")
	public void he_chooses_a_the_education(String education) throws Throwable {
	    WebElement el = driver.findElement(By.linkText(education));
	    el.click();
	}

	@Then("^the overview of \"([^\"]*)\" with open days for that education is shown$")
	public void the_overview_of_with_open_days_for_that_education_is_shown(String datum) throws Throwable {
	    WebElement el = driver.findElement(By.className("row"));
	    String date = "";
	    BufferedReader bufReader = new BufferedReader(new StringReader(el.getText()));
	    for(int i = 0; i < 3; i++){
	    	date += bufReader.readLine().trim() + " ";
	    }
	    date = date.trim();
	    System.out.println(date + "1");
	    assertEquals(datum, date);


	    //assertEquals(h1.getText(), "Overzicht Openlesdagen");
	}

	@Given("^a user that is on an open campus days page$")
	public void a_user_that_is_on_the_open_campus_days_page(String arg1) throws Throwable {
	    driver.get("http://localhost:8080/OpenClass-11/Controller?action=overviewOpendays&id=1&afdeling=Lerarenopleiding");
	}

	@When("^he chooses the day on \"([^\"]*)\"$")
	public void he_chooses_the_day_on(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the overview of every available \"([^\"]*)\" is shown$")
	public void the_overview_of_every_available_is_shown(String arg1) throws Throwable {
	    List<WebElement> el = driver.findElements(By.className("panel-heading"));
	    
	    boolean correct = false;
	    for( WebElement e : el) {
	    		if(e.getText().contains(arg1))
	    			correct = true;
	    }
	    assertTrue(correct);
	}

	@Given("^a user with first name \"([^\"]*)\", last name \"([^\"]*)\" and an email address \"([^\"]*)\"$")
	public void a_user_with_first_name_last_name_and_an_email_address(String arg1, String arg2, String arg3) throws Throwable {
	    
	    
	    firstName = arg1;
	    lastName = arg2;
	    email = arg3;
	    
	    
	    
	}

	
	@When("^he applies for a session$")
	public void he_applies_for_a_session() throws Throwable {
		
		WebElement registerButton = driver.findElement(By.linkText("Schrijf in"));
		
		registerButton.click();
		
		WebElement firstNameField = driver.findElement(By.cssSelector("input[name=\"firstName\"]"));
	    WebElement lastNameField = driver.findElement(By.cssSelector("input[name=\"lastName\"]"));
	    WebElement emailField = driver.findElement(By.cssSelector("input[name=\"email\"]"));
	    
	    firstNameField.sendKeys(firstName);
	    lastNameField.sendKeys(lastName);
	    emailField.sendKeys(email);
	    
	    WebElement submit = driver.findElement(By.cssSelector("button[type=\"submit\"]"));
	    submit.click();
	}

	@Then("^He is registered for that session$")
	public void he_is_registered_for_that_session() throws Throwable {
	    
	}

	@Given("^an administrator that is logged in$")
	public void an_administrator_that_is_logged_in() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^a session with name \"([^\"]*)\" starting at \"([^\"]*)\" and ending at \"([^\"]*)\" on \"([^\"]*)\"$")
	public void a_session_with_name_starting_at_and_ending_at_on(String arg1, String arg2, String arg3, String arg4) throws Throwable {
	    sessionName = arg1;
	    startTime = arg2;
	    endTime = arg3;
	    date = arg4;
	    
	}

	@Given("^with a description \"([^\"]*)\" and location \"([^\"]*)\" and category \"([^\"]*)\" and max inschrijvingen (\\d+)$")
	public void with_a_description_and_location_and_category_and_max_inschrijvingen(String arg1, String arg2, String arg3, int arg4) throws Throwable {
	   description = arg1;
	   location = arg2;
	   category = arg3;
	   maxPeople = arg4;
	}
	

	@When("^he submits this session$")
	public void he_submits_this_session_on_the_add_a_session_page() throws Throwable {
		WebElement submit = driver.findElement(By.cssSelector("button[type=\"submit\"]"));
	    submit.click();
	}

	@Then("^the session is added$")
	public void the_session_is_added() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^an error message is shown$")
	public void an_error_message_is_shown() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^he clicks on Overzicht inschrijvingen$")
	public void he_clicks_on() throws Throwable {
	    WebElement overzicht = driver.findElement(By.linkText("Overzicht inschrijvingen"));
	    overzicht.click();
	}

	@Then("^an overview of registered students is shown$")
	public void an_overview_of_registered_students_is_shown() throws Throwable {
	    WebElement header = driver.findElement(By.cssSelector("h2"));
	    assertTrue(header.getText().contains("Inschrijvingen"));
	}

	@Then("^he receives a confirmation email$")
	public void he_receives_a_confirmation_email() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Given("^He is on the registration overview page of \"([^\"]*)\" on \"([^\"]*)\"$")
	public void he_is_on_the_registration_overview_page_of_on(String arg1, String arg2) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@When("^he clicks on \"([^\"]*)\" next to a student$")
	public void he_clicks_on_next_to_a_student(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^he can edit the details for that student$")
	public void he_can_edit_the_details_for_that_student() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the student is removed from the registrations$")
	public void the_student_is_removed_from_the_registrations() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
	@Given("^he is on a sessions overview page$")
	public void he_is_on_a_sessions_overview_page() throws Throwable {
	    driver.get("http://localhost:8080/OpenClass-11/Controller?action=sessionoverview");
	}




	
}
