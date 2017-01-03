/**
 * 
 */
package circularmotion;

import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;
import java.util.Random;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		4 Dec 2016
 *
 */
public class NumberTextField extends TextField {
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

    /**
     * Gets the value of the {@link NumberTextField} and returns it as a double.
     * @return The value of the {@link NumberTextField} as a double.
     */
    public double getValue(){
        String text = this.getText();
        return Double.parseDouble(text);
    }


    /**
     * Simple method to play a warning bell to the user.
     *
     * Customise the resource locations (relative to the bin folder) for different sounds.
     * This means the res folder must be on the same level as the package folder.
     */
    public void bell(){
        Random rng = new Random(); // Added for entertainment purposes when debugging. Will be removed, along with it's
        // use on line +3
        try {
            String weAren1 = getClass().getClassLoader().getResource("./res/WANO.wav").toString();  // We ar number one
            String sndstrm = getClass().getClassLoader().getResource("./res/dss.wav").toString();   // Darude
            String beepsfx = getClass().getClassLoader().getResource("./res/beep-02.wav").toString();   // Beep
            AudioClip bell = new AudioClip(((rng.nextBoolean()? sndstrm : weAren1)));   // Randomly decides whether to use
            // TODO probably should remove the fact the We Are Number One is a valid bell sound.
            // the beep sound effect or Darude - Sandstorm.
            bell.play();
        } catch (NullPointerException npe){
            System.out.println("File not found. It looks like something has happened to the audio files.");
        } finally {
            System.out.println("The system has belled.");
        }
    }

    /**
     * Determine whether the entered text (Generally character by character) is valid.
     *
     * @param text The text to check
     * @return whether or not the entered text is valid.
     */
    private boolean validate(String text) {
        if (text.length() == 0){    // Special case to allow backspace to work.
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

    /**
     * Set the contents of the {@link NumberTextField}
     * <p>
     *     Sets the contents of the {@link NumberTextField} from a double rather than the default String.
     * </p>
     *
     * @param contents The value to set the {@link NumberTextField} contents to.
     */
    public void setText(double contents){
    	setText(Double.toString(contents));
    }
    /**
     * Create a {@link NumberTextField} with initial contents.
     */
    public NumberTextField(double contents){
    	super(Double.toString(contents));
    }

    /**
     * Create a {@link NumberTextField} with no intial contents (ie, 0.0)
     */
    public NumberTextField(){
        this(0.0);
    }
}