package service;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.awt.List;
import java.util.ArrayList;


import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Tests {

	private WebDriver driver;
	String url;
	
	@Before
	public void setup(){
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Tom Stockmans\\Documents\\web3\\chromedriver.exe");
		// windows: gebruik dubbele \\ om pad aan te geven
		// hint: zoek een werkende test op van web 2 ...
	driver = new ChromeDriver();
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

	@Then("^all the \"([^\"]*)\" with there \"([^\"]*)\" are displayed$")
	public void all_the_with_there_are_displayed(String department, String education) throws Throwable {
		WebElement ul_elements = driver.findElement(By.id(department));
		ArrayList<WebElement> departments = (ArrayList<WebElement>) ul_elements.findElement(By.tagName("li"));
		boolean found = false;
		for (WebElement dep:departments) {
			if (dep.getText().contains(education)) {
				found = true;
			}
		}
		assertTrue(found);
	}
	

	@Given("^an education where i can click on$")
	public void an_education_where_i_can_click_on() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the overview of the open campusdays of that particular education is shown$")
	public void the_overview_of_the_open_campusdays_of_that_particular_education_is_shown() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}

	@Then("^the overview contains each day this bachelor keeps an open campus day$")
	public void the_overview_contains_each_day_this_bachelor_keeps_an_open_campus_day() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    throw new PendingException();
	}
	
}
