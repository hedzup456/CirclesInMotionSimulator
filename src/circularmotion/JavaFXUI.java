package circularmotion;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JavaFXUI extends Application {
    private int height = 600;
    private int width = 800;
    private SingleParticle particle = new SingleParticle(1, true, 10, 1);
    private Group movingParts = new Group();
    private Circle circle;
    private Timeline timeline;
    Stage primStg;
    Image icon = new Image("https://cdn1.iconfinder.com/data/icons/science-filled-1/60/circles-connection-motion-128.png");
    // Image is free for use.

    @Override
	public void start(Stage primaryStage) {
    	particle.setCentre(new Point(300, 300));

	    Group root = new Group();
	    Scene scene = new Scene(root, width, height, Color.BLACK);
	    primaryStage.setScene(scene);
	    	    
	    primaryStage.setTitle("Circles in Motion");
	    primaryStage.getIcons().add(icon);

	    makeCircle(Color.WHITE, 2.0);
	    setUpMovingParts(particle.getCentre(), Color.WHITE, 2.0);
	    root.getChildren().addAll(circle, makeMenuBar(), movingParts);

	    primStg = primaryStage;
	    primaryStage.show();
	    /* popup("I am well aware this is all-but non-functional.", "I'm working on it. Lots of stuff going" +
				" on. In the meantime, though, feel free to peruse my source code and my beautiful javadoc. Also," +
				" be aware that the latest version of this code is currently available on github at the following url." +
				"\n\n" +
				"https://github.com/hedzup456/CirclesInMotionSimulators");
		*/
    }

	/**
	 * Create the circle. Circles are good.
	 * @param colour Take a wild fuckin guess buddy. (Paint or Paint subclass, please)
	 * @param width How fat do ya want ya line? As a double.
	 */
	private void makeCircle(Paint colour, double width){
		if (circle == null){
			circle = new Circle(particle.getCentre().getX(), particle.getCentre().getY(), 250);
			circle.setId("Circle");
			circle.setFill(Color.TRANSPARENT);
		}
		circle.setStroke(colour);
		circle.setStrokeWidth(width);
	}

	/**
	 * Simple method to create a popup window to alert the user of something.
	 *
	 * @param title The title of the window, as a String.
	 * @param header The header text of the popup window, as a String.
	 * @param content The content text of the popup window, as a String.
	 */
    private void popup(String title, String header, String content){
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}

	/**
	 * Simple method to create a popup window to alert the user of something.
	 * <p>
	 *		Calls the other popup method, passing a null value for header text.
	 * </p>
	 * @param title The title of the window, as a String.
	 * @param content The content text of the popup window, as a String.
	 */
    private void popup(String title, String content){
		popup(title, null, content);
	}

	// TODO Javadoc comments.
	private void changeBackgroundColour(Paint colour){
		primStg.getScene().setFill(colour);
	}
	private void changeForegroundColour(Paint colour){
		makeCircle(colour, circle.getStrokeWidth());
		setUpMovingParts(particle.getCentre(), colour, circle.getStrokeWidth());
	}
	private void changeStrokeSize(double size){
		makeCircle(circle.getStroke(), size);
		setUpMovingParts(particle.getCentre(), circle.getStroke(), size);
	}

	/**
	 * Method to handle setting up, resizing and colouring all of the moving parts
	 * @param centre Point object holding the coordinates of the centre.
	 * @param colour Paint object for the colour to fill the parts.
	 * @param strokeSize double representation of the stroke size to set the lines to.
	 */
	private void setUpMovingParts(Point centre, Paint colour, double strokeSize){
	    if (movingParts.getChildren().size() == 0) {
		    Line radius = new Line(centre.getX(), centre.getY(), centre.getX(), centre.getY() - 250);
		    radius.setStroke(colour);
		    radius.setStrokeWidth(strokeSize);
		    radius.setId("Radius");

		    Group arrowhead = new Group();
		    arrowhead.getChildren().addAll(
				    new Line(centre.getX(), centre.getY(), centre.getX() + 10, centre.getY() - 10),
				    new Line(centre.getX(), centre.getY(), centre.getX() - 10, centre.getY() - 10));
		    for ( Node part : arrowhead.getChildren() ) {
			    ((Line) part).setStroke(colour);
			    ((Line) part).setStrokeWidth(strokeSize);
		    }
		    arrowhead.setId("Arrowhead");

		    movingParts.getChildren().addAll(radius, arrowhead);
	    } else {    // If the objects already exist
		    for (Node node: movingParts.getChildren()) {
			    if (node.getId().equals("Radius")){
			    	// setStroke and sSW are not part of node, but are part of Line. Casting allows this without
				    // reallocation
				    ((Line) node).setStroke(colour);
				    ((Line) node).setStrokeWidth(strokeSize);
			    } else if (node.getId().equals("Arrowhead")){
					for (Node subnode: ((Group) node).getChildren()){
						((Line) subnode).setStroke(colour);
						((Line) subnode).setStrokeWidth(strokeSize);
					}
			    }
		    }
	    }
    }

	/**
	 * Method to handle the animation of the particle.
	 * <p>
	 *     This method will make the program display the force, acceleration, and current velocity of the particle.
	 * </p>
	 */
	private void playAnimation(){
		timeline = new Timeline();
		Duration animationDuration;
		/*
		The duration of the animation should probably be scaled. For an orbit in the order of five to thirty seconds,
		it is reasonable to have it act in realtime. For less than five seconds, a time dilation would be useful, and
		for thirty seconds or more a time acceleration would be of benefit to all involved.
		 */
		// TODO Allow the user to set cutoff values.
		int lowTime = 5;
		int highTime = 30;
		double orbitPeriod = particle.getPeriod();
		if (orbitPeriod < lowTime){
			// 1000 makes seconds, the 10 means one second takes ten seconds.
			animationDuration = new Duration(orbitPeriod*1000*10);
		} else if (lowTime <= orbitPeriod && orbitPeriod < highTime){
			animationDuration = new Duration( orbitPeriod*1000); // 1000 makes seconds.
		} else if (highTime <= orbitPeriod){
			// Ten seconds per second.
			animationDuration = new Duration(orbitPeriod*1000/10);
		} else animationDuration = new Duration(1); // Default case, should never occur

		Rotate rot = new Rotate(0.1, particle.getCentre().getX(), particle.getCentre().getY());

		movingParts.getTransforms().add(rot);

		KeyValue keyValue = new KeyValue(rot.angleProperty(), 360);
		KeyFrame keyFrame = new KeyFrame(animationDuration, keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	/**
	 * Method handles setting the relevant colours when a custom colour is requested.
	 * <p>
	 *     God help anyone who chooses to use this code again. It's bordering on abuse of pretty much everything I know.
	 *     I'm so sorry.
	 * </p>
	 *
	 * @param backgroundOrForeground String value to dictate whether the foreground or background is changed.
	 */
	private void getCustomColour(String backgroundOrForeground){
		// Looks like I'm going to have to create a new window.
		Stage colourPicker = new Stage();
		colourPicker.setTitle("Pick a colour");
		colourPicker.initModality(Modality.APPLICATION_MODAL);
		colourPicker.getIcons().add(new Image("http://icons.iconarchive.com/icons/iconsmind/outline/512/Gears-icon.png"));
		// Image is free for use.

		// Define a new colour picker with the current colour as the default.
		ColorPicker colPick = new ColorPicker (Color.valueOf( ((backgroundOrForeground.equals("Foreground"))?
				(circle.getStroke()) : (primStg.getScene().getFill())) .toString()));
		// Ternary operators are pretty cool.
		colPick.setId(backgroundOrForeground);	// Like I said, I'm sorry.
		colPick.setVisible(true);
		colPick.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (colPick.getId().contains("Foreground")){
					changeForegroundColour(colPick.getValue());
				} else if (colPick.getId().contains("Background")){
					changeBackgroundColour(colPick.getValue());
				}
			}
		});

		VBox vBox = new VBox();
		String text = "Select a colour to use as the ";
		if (backgroundOrForeground.contains("Background")){
			text = text + "background colour.";
		} else if (backgroundOrForeground.equals("Foreground")){
			text = text + "foreground colour.";
		}
		Label label = new Label(text);
		Button close = new Button("Close");
		close.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				colourPicker.close();
			}
		});

		vBox.getChildren().addAll(label, colPick, close);

		Scene stageScene = new Scene(vBox);
		colourPicker.setScene(stageScene);
		colourPicker.setAlwaysOnTop(true);
		colourPicker.show();
		// Well that was a fucking rollercoaster from start to finish.
		// I wonder why JavaFX doesn't support a ColorPicker in a MenuItem.
		// Oh well. It's only resulted in a disgrace for everyone involved.
	}

	/**
     * Method to create the terms for the File menu. 
     * <p> 
     * Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * 
     * @param menu the Menu object to add items to.
     */
    private void makeFileMenu(Menu menu){
    	MenuItem startAnim = new MenuItem("Start Animation");
    	startAnim.setAccelerator(KeyCombination.keyCombination("Ctrl+Enter"));
    	startAnim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Here we go..");
				playAnimation();
			}
		});
    	MenuItem stopAnim = new MenuItem("Stop Animation");
		stopAnim.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+Enter"));
		stopAnim.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				timeline.stop();
			}
		});
    	MenuItem exit = new MenuItem("Exit");
    	exit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
			public void handle(ActionEvent event) {
      			Platform.exit(); // Close the program.
				}
			});	    	
    	menu.getItems().addAll(startAnim, stopAnim, exit);
    }
	/**
     * Method to create the terms for the View menu. 
     * <p> 
     * 		Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * </p>
     * @param menu the Menu object to add items to.
     */
    private void makeViewMenu(Menu menu){
    	// Define an Event Handler to handle ALL colour changes.
		// Yay, reusable code!
		EventHandler<ActionEvent> colourChangeHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String sourceID = ((MenuItem) event.getSource()).getId();
				if (sourceID.contains("FG")){	// All logic relevant to foreground colours.
					if (sourceID.contains("White")) changeForegroundColour(Color.WHITE);
					else if (sourceID.contains("Orange")) changeForegroundColour(Color.ORANGE);
					else if (sourceID.contains("Red")) changeForegroundColour(Color.RED);
					else if (sourceID.contains("Custom")) getCustomColour("Foreground");
				} else if (sourceID.contains("BG")) {    // All logic relevant to background colours.
					if (sourceID.contains("Black")) changeBackgroundColour(Color.BLACK);
					else if (sourceID.contains("Blue")) changeBackgroundColour(Color.BLUE);
					else if (sourceID.contains("Green")) changeBackgroundColour(Color.GREEN);
					else if (sourceID.contains("Custom")) getCustomColour("Background");
				}
			}
		};

    	// Submenu for background colours.
    	Menu changeBGColours = new Menu("Background Colour");
    		MenuItem black = new MenuItem("Black");
    		MenuItem blue = new MenuItem("Blue");
    		MenuItem green = new MenuItem("Green");
    		MenuItem custom = new MenuItem("Custom colour");
			// Set the IDs for the EventHandler
			black.setId("Black BG");
			blue.setId("Blue BG");
			green.setId("Green BG");
			custom.setId("Custom BG");

		changeBGColours.getItems().addAll(black, blue, green,custom);
    	// End submenu
    	// Submenu for foreground colours.
		Menu changeFGColours = new Menu("Change Foreground Colour");
    		MenuItem whiteFG = new MenuItem("White");
    		MenuItem orangeFG = new MenuItem("Orange");
    		MenuItem redFG	= new MenuItem("Red");
    		MenuItem customFG = new MenuItem("Custom colour");
    		// Set the IDs for the EventHandler
	    	whiteFG.setId("White FG");
	    	orangeFG.setId("Orange FG");
	    	redFG.setId("Red FG");
	    	customFG.setId("Custom FG");

    	changeFGColours.getItems().addAll(whiteFG, orangeFG, redFG, customFG);
		// End submenu
		// Set the EventHandler for all the colour changing menu items.
		for (MenuItem option: changeFGColours.getItems()) option.setOnAction(colourChangeHandler);
		for (MenuItem option: changeBGColours.getItems()) option.setOnAction(colourChangeHandler);
	    // Multiline for loops are for losers.

		// Submenu for stroke size.
	    Menu strokeSize = new Menu("Change Stroke Size");
		ToggleGroup sizeToggleGroup = new ToggleGroup();
	    	RadioMenuItem[] sizes = new RadioMenuItem[4];
	    	for (int i = 0; i < sizes.length; i++){	// It's neater to just run over the array.
	    		RadioMenuItem size = sizes[i];
	    		double number = Math.pow(2, i);
	    		size = new RadioMenuItem(String.valueOf(number));
	    		size.setSelected((number == 2.0));
	    		size.setToggleGroup(sizeToggleGroup);
	    		size.setOnAction(new EventHandler<ActionEvent>() {
	    			@Override
	    			public void handle(ActionEvent e){
	    				changeStrokeSize(number);
	    			}
				});
	    		strokeSize.getItems().add(size);
	    	}
	    
	    // End of menu items.
    	menu.getItems().addAll(changeBGColours, changeFGColours, strokeSize);
    }
	/**
     * Method to create the terms for the Edit menu. 
     * <p> 
     * 		Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * </p>
	 *
     * @param menu the Menu object to add items to.
     */
    private void makeEditMenu(Menu menu){
    	MenuItem newCircleWindow = new MenuItem("Set up circle");
    	newCircleWindow.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
    	newCircleWindow.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			CirclePropertiesUI cpui = new CirclePropertiesUI(particle);
    			cpui.show();

    			particle = cpui.getParticle();
			}
		});
    	menu.getItems().addAll(newCircleWindow);
    }

    private MenuBar makeMenuBar(){
    	MenuBar mb = new MenuBar();
    	Menu file = new Menu("File");
	    Menu edit = new Menu("Edit");
    	Menu view = new Menu("View");
    	
    	makeFileMenu(file);
    	makeEditMenu(edit);
    	makeViewMenu(view);

    	mb.getMenus().addAll(file, edit, view);
    	return mb;
    }


    private Point findDrawingStartLocations(int radius){
    	Point point = new Point();
    	point.setX((width-radius)/2);
    	point.setY((height-radius)/2);
    	return point;
    }
	public static void main(String[] args) {
		launch(args);
	}
	

}
