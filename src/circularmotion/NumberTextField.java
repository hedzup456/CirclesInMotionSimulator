/**
 * 
 */
package circularmotion;

import javafx.scene.control.TextField;

/**
 * @author 		Richard Henry (richardhenry602@gmail.com)
 * @since 		4 Dec 2016
 *
 */
public class NumberTextField extends TextField {
	// NumberTextField as suggested by @Burkhard at http://stackoverflow.com/a/18959399
	// With modifications as necessary.
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
    
    private boolean validate(String text) {
        return text.matches("\\d+\\.?\\d*");
    }
    public void setText(double contents){
    	setText(Double.toString(contents));
    }
    public NumberTextField(double contents){
    	super(Double.toString(contents));
    }
}