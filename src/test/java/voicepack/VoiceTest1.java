package voicepack;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class VoiceTest1 
{

	public static void main(String[] args) 
	{
		//Register voice library(kevin voice library is default library in freetts jar)
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		VoiceManager vm=VoiceManager.getInstance();
		Voice[] voices=vm.getVoices();
		for(Voice voice:voices)
		{
			System.out.println(voice.getName()+"-->"+voice.getDescription());
		}
	}
}
