package com.example;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class AppTest {
    
    WebDriver driver;
    String searchbook;
    XSSFWorkbook workbook;
    XSSFSheet sheet;
    ExtentTest test;
    ExtentSparkReporter spark;
    ExtentReports reports;
     WebDriverWait wait;
     Logger log;




    @BeforeTest
    public void BeforeTestExecution() throws IOException
    {

        String path = "C:\\Users\\91936\\OneDrive\\Desktop\\testingreport\\src\\Excel\\Data.xlsx";
        FileInputStream fs = new FileInputStream(path);

        workbook = new XSSFWorkbook(fs);
        sheet = workbook.getSheetAt(0);
       

        searchbook = sheet.getRow(1).getCell(0).getStringCellValue();
        // password = sheet.getRow(1).getCell(1).getStringCellValue();

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        driver.get("https://www.barnesandnoble.com/");
       
    }
    @Test(priority = 1)
     public void test1() throws IOException, InterruptedException{
        
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[1]/a")).click();
        Thread.sleep(6000);
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[1]/div/a[2]")).click();
        Thread.sleep(4000);
        // searching
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/div[2]/div/input[1]")).sendKeys(searchbook);
        Thread.sleep(6000);
       
        // clicking
        driver.findElement(By.xpath("//*[@id='rhf_header_element']/nav/div/div[3]/form/div/span/button")).click();


        String ExptName = "chetan bhagat";
        String OriginalName = driver.findElement(By.xpath("//*[@id=\"searchGrid\"]/div/section[1]/section[1]/div/div[1]/div[1]/h1/span")).getText();
        
        if(ExptName.equals(OriginalName)){
            System.out.println("String Matches");
            test.log(Status.PASS, "Match Passed");
        }
        else{
            test.log(Status.FAIL, "Match Failed");
        }

    }


    @Test(priority = 2)
    public void test2() throws InterruptedException
    {
        Actions actions=new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id='rhfCategoryFlyout_Audiobooks']"))).perform();
        Thread.sleep(7000);
        driver.findElement(By.xpath("//*[@id='navbarSupportedContent']/div/ul/li[5]/div/div/div[1]/div/div[2]/div[1]/dd/a[1]")).click();

        Thread.sleep(6000);

        // clicking the first book;
        driver.findElement(By.xpath("//*[@id=\"product-shelf-2940159543998\"]/div/div[2]/div[1]/h3/a")).click();
        Thread.sleep(6000);

        driver.findElement(By.xpath("//*[@id=\"otherAvailFormats\"]/div/div/div[3]")).click();

        //Add the first book to the cart
        driver.findElement(By.xpath("/html/body/main/div[2]/div[2]/section/div[2]/div/div[3]/div[2]/div[3]/form[1]/input[11]")).sendKeys("ADD TO CART");;
        
        // //Verify if the book is added to the cart
        Thread.sleep(10000);
        assertTrue(driver.findElement(By.xpath("/html/body/div[37]/div/div/div[2]/div[3]/div/div[1]")).getText().equals("Item Successfully Added To Your Cart"));
        assertTrue(true);
    }



        @Test(priority = 3)
        public void verifyingPopup()throws Exception{
        driver.get("https://www.barnesandnoble.com/");
        
        driver.findElement(By.xpath("//*[@id=\"footer\"]/div/dd/div/div/div[1]/div/a[5]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"rewards-modal-link\"]")).click();
        Thread.sleep(2000);

        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = "./Screenshot/shot2.png";
        FileUtils.copyFile(screen, new File(path));

       
    }

    @AfterTest
    public void end() throws InterruptedException{
        System.out.println("End");
        reports.flush();
        
        driver.quit();
    }
}
