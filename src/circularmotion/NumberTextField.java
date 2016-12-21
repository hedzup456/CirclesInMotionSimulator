/**
 * 
 */
package circularmotion;

import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;

import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Random;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		4 Dec 2016
 *
 */
public class NumberTextField extends TextField {
	public NumberTextField(){
	    this(0.0);
    }
    // NumberTextField as suggested by @Burkhard at http://stackoverflow.com/a/18959399
	// With modifications as necessary.
    private boolean dp = true;
    @Override
    public void replaceText(int start, int end, String text){
        if (validate(text)){
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }
    public double getValue(){
        String text = this.getText();
        return Double.parseDouble(text);
    }

    /**
     * Simple method to play a warning bell to the user.
     *
     * Customise the URL String in the AudioClip constructor to use different sounds.
     */
    public void bell(){
        Random rng = new Random(); // Added for entertainment purposes when debugging. Will be removed, along with it's
        // use on line +3
        String sndstrm= getClass().getClassLoader().getResource("./res/dss.wav").toString();
        String beepsfx = getClass().getClassLoader().getResource("./res/beep-02.wav").toString();
        AudioClip bell = new AudioClip(((rng.nextBoolean()? sndstrm : beepsfx)));   // Randomly decides whether to use
        // the beep sound effect or Darude - Sandstorm.
        System.out.println("The system has belled.");
        bell.play();
    }

    private boolean validate(String text) {
        if (text.length() == 0){
            String contents = getText();
            dp = !(contents.charAt(contents.length() - 1) == '.');
            return true;
        }
        if (text.matches("\\.") && !dp){    // If the character is a decimal point and etc
            dp = true;
            return true;
        } else if (text.matches("\\d")){    // If the character is a digit
            return true;
        } else {
            bell();
            return false;
        }
    }
    public void setText(double contents){
    	setText(Double.toString(contents));
    }
    public NumberTextField(double contents){
    	super(Double.toString(contents));
    }
}