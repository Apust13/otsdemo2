package com.apust.selenium.otsdemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by GUN
 * on 11.07.2016.
 */
public class CheckMailTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String login;
    private String password;
    private String testSite;
    private String titleOfTestSite;
    private Integer expectedCountLetter;



    @BeforeClass
    public void initWebDriver(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        login = "otsdemo";
        password = "otsdemo12345test";
        testSite = "https://www.tut.by";
        titleOfTestSite = "Белорусский портал TUT.BY";
        expectedCountLetter = 2;
    }

    @Test(priority = 0)
    public void openSiteTest(){
        driver.get(testSite);
        boolean checkSite = driver.getTitle().contains(titleOfTestSite);
        Assert.assertTrue(checkSite);

    }

    @Test(priority = 1)
    public void loginTest(){
        //driver.get(testSite);
        driver.findElement(By.xpath(".//*[@id='authorize']/div/a")).click();
        driver.findElement(By.name("login")).clear();
        driver.findElement(By.name("login")).sendKeys(login);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input.button.auth__enter")).click();

        boolean logedIn = driver.findElement(By.xpath("//*[@id='authorize']/div/*[@class=\"enter logedin\"]")).isDisplayed();

        Assert.assertTrue(logedIn);

    }



    @Test(priority = 2)
    public void mailTest() throws Exception {
       // driver.get(testSite);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='mainmenu']/ul/li[3]/a")));
        driver.findElement(By.xpath(".//*[@id='mainmenu']/ul/li[3]/a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='js-page']/div/div[5]//*[@title='Письма']")));

        String countDisplay = driver.findElement(By.xpath(".//*[@id='js-page']/div/div[5]/div/div[2]/div[2]/div/div/div[1]/div[1]/div/div[1]/span[1]/span/span")).getText();
        String[] split = countDisplay.split("/");
        Integer actualCountLetter = Integer.valueOf(split[1].trim());
        Assert.assertEquals(actualCountLetter, expectedCountLetter);

    }


//
//   boolean isElementPresent(By locator){
//        try {
//            driver.findElement(locator);
//            return true;
//        } catch (NoSuchElementException e){
//            return false;
//        }
//    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }


}
