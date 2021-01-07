package voicepack;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VoiceInputToGoogle 
{

	public static void main(String[] args) throws Exception 
	{
		//enter text from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Text to be converted into specch");
		String x=sc.nextLine();
		//launch site
		WebDriverManager.chromedriver().setup();
		ChromeOptions co=new ChromeOptions();
		co.addArguments("--use-fake-ui-for-media-stream");
		ChromeDriver driver=new ChromeDriver(co);
		driver.get("http://www.google.co.in");
		driver.manage().window().maximize();
		WebDriverWait w=new WebDriverWait(driver,10);
		w.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@aria-label='Search by voice']"))).click();
		//Register to voice library(kevin voice directory is default voice library in freetts jar)
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		//choose a voice and generate
		VoiceManager vm=VoiceManager.getInstance();
		Voice v=vm.getVoice("kevin16");
		v.allocate();
		v.speak(x);
		v.deallocate();
		Thread.sleep(3000);
		String y=driver.getTitle();
		y=y.toLowerCase();
		x=x.toLowerCase();
		if(y.contains(x))
		{
			System.out.println("Test passed");
		}
		else
		{
			System.out.println("Test failed");
		}
		//close site 
		driver.close();
		
	}

}
