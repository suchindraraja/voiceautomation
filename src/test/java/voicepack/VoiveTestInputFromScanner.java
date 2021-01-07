package voicepack;

import java.util.Scanner;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VoiveTestInputFromScanner 
{

	public static void main(String[] args)
	{
		//enter text from keyboard
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Text to be converted into specch");
		String x=sc.nextLine();
		//Register to voice library(kevin voice directory is default voice library in freetts jar)
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		//choose a voice and generate
		VoiceManager vm=VoiceManager.getInstance();
		Voice v=vm.getVoice("kevin16");
		v.allocate();
		v.speak(x);
		v.deallocate();
  
	}

}
