/**
 * 
 */
package circularmotion;

import javafx.scene.control.TextField;
import javafx.scene.media.AudioClip;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		4 Dec 2016
 *
 */
public class NumberTextField extends TextField {
	// NumberTextField as suggested by @Burkhard at http://stackoverflow.com/a/18959399
	// With modifications as necessary.
    private boolean dp = false;
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
     * Simple method to play a warning bell to the user.
     *
     * Customise the URL String in the AudioClip constructor to use different sounds.
     */
    private void bell(){
        AudioClip bell = new AudioClip("http://localhost:8000/beep-02.wav");    // Use python -m http.server in
        // res folder.
        bell.play();
    }

    private boolean validate(String text) {
        if (text.matches("\\.") && !dp){
            dp = true;
            return true;
        } else if (text.matches("\\d")){
            return true;
        } else {
            if (text.contains("\\b")) bell();
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