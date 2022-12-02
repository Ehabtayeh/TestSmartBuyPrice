package stageing;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Cart {

	public WebDriver driver;
	public int numberOfTry = 10;
	SoftAssert softassert = new SoftAssert();

	@BeforeTest
	public void BeforeMyTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://smartbuy-me.com/smartbuystore");
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();

	}

	@Test()
	public void test_DELL_G15() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		for (int i = 0; i < numberOfTry; i++) {
			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			WebElement msg = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]"));
			// System.out.println(msg);
			if (msg.getText().contains("Sorry")) {
				numberOfTry = i;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();
				// System.out.println(i);

			} else {
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();
			}

		}
		driver.navigate().back();
		String price_One_Item = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();
		String[] price_After_Spilt = price_One_Item.split("JOD");
		String price_replace = price_After_Spilt[0].trim();
		String Update_Price = price_replace.replace(",", ".");
		double final_Price = Double.parseDouble(Update_Price);
		System.out.println(" the price After Discount " + " = " + final_Price);
		// softassert.assertEquals(final_Price * numberOfTry, 5.796, "");

		softassert.assertAll();
	}

	@Test()
	public void Check_Discount_Item_() {

		String price_Before_Discount = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[2]"))
				.getText();
		String[] price_Before_Discount_split = price_Before_Discount.split("JOD");
		String price_Before_Discount_replace = price_Before_Discount_split[0].trim();
		String price_Before_Discount_Update = price_Before_Discount_replace.replace(",", ".");
		double final_Price_Before_Discount = Double.parseDouble(price_Before_Discount_Update);

		System.out.println(" the price Before Discount " + " =" + final_Price_Before_Discount);
		String discountValue = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[3]/div/div[2]/div[2]/div/div/span[1]"))
				.getText();
		System.out.println("discountValue " + " = " + discountValue);
		String[] price_Discount_Value_Split = discountValue.split("%");
		String price_Discount_Value_replace = price_Discount_Value_Split[0].trim();

		double final_Price_Discount_Value = Double.parseDouble(price_Discount_Value_replace);
		System.out.println("discountValue After spilt % " + " = " + final_Price_Discount_Value);

	}

	
}
