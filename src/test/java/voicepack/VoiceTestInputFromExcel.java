package voicepack;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class VoiceTestInputFromExcel 
{
	public static void main(String[] args) throws Exception
	{
		//connect to excel file
		File f=new File("voiceinput.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		Sheet sh=wb.getSheet("Sheet1");
		int nour=sh.getPhysicalNumberOfRows();
		int nouc=sh.getRow(0).getLastCellNum();
		//create heading for headings column
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		String cname="reslut on "+sf.format(dt);
		sh.getRow(0).createCell(nouc).setCellValue(cname);
		sh.autoSizeColumn(nouc);
		for(int i=1;i<nour;i++)
		{
			try
			{
				DataFormatter df=new DataFormatter();
				String input=df.formatCellValue(sh.getRow(i).getCell(0));
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
				v.speak(input);
				v.deallocate();
				Thread.sleep(3000);
				String y=driver.getTitle();
				y=y.toLowerCase();
				input=input.toLowerCase();
				if(y.contains(input))
				{
					sh.getRow(i).createCell(nouc).setCellValue("Test passed");
					sh.autoSizeColumn(nouc);
				}
				else
				{
					sh.getRow(i).createCell(nouc).setCellValue("Test failed");
					sh.autoSizeColumn(nouc);
				}
			}
			catch(Exception ex)
			{
				sh.getRow(i).createCell(nouc).setCellValue(ex.getMessage());
			}
		}
	}

}
