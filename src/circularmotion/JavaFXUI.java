package circularmotion;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JavaFXUI extends Application {
    private int height = 600;
    private int width = 800;
    private SingleParticle particle = new SingleParticle(1, true, 10, 1);
    private Line radius;
    private Timeline timeline;
    Stage primStg;
    Image icon = new Image("https://cdn1.iconfinder.com/data/icons/science-filled-1/60/circles-connection-motion-128.png");
	@Override

	public void start(Stage primaryStage) {
	    Group root = new Group();
	    Scene scene = new Scene(root, width, height, Color.BLACK);
	    primaryStage.setScene(scene);
	    	    
	    primaryStage.setTitle("Circles in Motion");
	    primaryStage.getIcons().add(icon);
	    
	    Canvas canvas = new Canvas(width, height);
	    canvas.setId("Main Canvas");	// Allows check of object.
	    
	    makeCircle(canvas.getGraphicsContext2D());

	    root.getChildren().addAll(canvas, makeMenuBar(), radius);
	    root.getChildren().add(makeMenuBar());
	    primStg = primaryStage;
	    primaryStage.show();
	    popup("I am well aware this is all-but non-functional.", "I'm working on it. Lots of stuff going" +
				" on. In the meantime, though, feel free to peruse my source code and my beautiful javadoc. Also," +
				" be aware that the latest version of this code is currently available on github at the following url." +
				"\n\n" +
				"https://github.com/hedzup456/CirclesInMotionSimulators");
    }

	/**
	 * Simple method to create a popup window to alert the user of something.
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
	 * Calls the other popup method, passing a null value for header text.
	 * @param title The title of the window, as a String.
	 * @param content The content text of the popup window, as a String.
	 */
    private void popup(String title, String content){
		popup(title, null, content);
	}

	/**
	 * Method finds and returns the main canvas from the group.
	 * <p>
	 *     This uses the fact that the main canvas has an ID of (imaginatively) "Main Canvas". This allows the program
	 *     to identify the correct subnode in the Javafx graph.
	 * </p>
	 * @return The main Canvas object from the primary stage.
	 */
	// TODO Abstract this out to be finding any subnode, given an ID?
	private Canvas getCanvasFromStage(){
		Group group = (Group) primStg.getScene().getRoot();
		int i = 0;
		while (i>= 0){
			Node node = group.getChildren().get(i);
			if (node.getId().equals("Main Canvas")) return (Canvas) node;
			else i++;
		}
		return new Canvas();
		// Should never be reached, but oh well. It's neater than suppressing warnings.
	}
	private void changeBackgroundColour(Paint colour){
		primStg.getScene().setFill(colour);
	}
	private void changeForegroundColour(Paint colour){
		Canvas cvs = getCanvasFromStage();
		makeCircle(cvs.getGraphicsContext2D(), colour, cvs.getGraphicsContext2D().getLineWidth());
		// Get the same line width as before.
	}
	private void changeStrokeSize(double size){
		Canvas cvs = getCanvasFromStage();
		Paint colour = cvs.getGraphicsContext2D().getStroke();
		if (size < cvs.getGraphicsContext2D().getLineWidth()){
			/*	Needs to redraw for thinner size else it keeps the old circle visible.  This just draws the background
			 *	colour to hide any old lines. */
			makeCircle(cvs.getGraphicsContext2D(), primStg.getScene().getFill(), 1000);
		}
		makeCircle(cvs.getGraphicsContext2D(), colour, size);	// Keep same colour even if changing down a size.
	}
	private void makeCircle(GraphicsContext gc){
		makeCircle(gc, Color.AZURE, 2);
	}
    private void makeCircle(GraphicsContext gc, Paint colour, double strokeSize){
        gc.setStroke(colour);
        gc.setLineWidth(strokeSize);
        Point p = findDrawingStartLocations(500);
        gc.strokeOval(p.getX(), p.getY(), 500, 500);
		radius = new Line(p.getX()+250, p.getY()+250, p.getX()+250, p.getY());
		radius.setStroke(colour);
		radius.setStrokeWidth(strokeSize);
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
		KeyValue keyValue = new KeyValue(radius.rotateProperty(), 360);
		KeyFrame keyFrame = new KeyFrame(animationDuration, keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(1000);
		timeline.play();
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
     * Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * 
     * @param menu the Menu object to add items to.
     */
    private void makeViewMenu(Menu menu){
    	// Submenu for foreground colours.
    	Menu changeBGColours = new Menu("Background Colour");
    		MenuItem black = new MenuItem("Black");
    		MenuItem blue = new MenuItem("Blue");
    		MenuItem green = new MenuItem("Green");
    		MenuItem custom = new MenuItem("Custom colour");
    	
	    	black.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			changeBackgroundColour(Color.BLACK);    		
	    		}
			});
	    	blue.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			changeBackgroundColour(Color.BLUE);    		
	    		}
			});
	    	green.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			changeBackgroundColour(Color.GREEN);    		
	    		}
			});
	    	custom.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			// TODO Implement custom colour picking.
	    			changeBackgroundColour(Color.ORANGERED);    		
	    		}
			});
    	
    		changeBGColours.getItems().addAll(black, blue, green,custom);
    	// End submenu
    	// Submenu for foreground colours.
    	Menu changeFGColours = new Menu("Change Foreground Colour");
    		MenuItem whiteFG = new MenuItem("White");
    		MenuItem orangeFG = new MenuItem("Orange");
    		MenuItem redFG	= new MenuItem("Red");
    		MenuItem customFG = new MenuItem("Custom colour");
	    	
    		whiteFG.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event){
	    			changeForegroundColour(Color.WHITE);
	    		}
	    	});
	    	orangeFG.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event){
	    			changeForegroundColour(Color.ORANGE);
	    		}
	    	});
	    	redFG.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event){
	    			changeForegroundColour(Color.RED);
	    		}
	    	});
	    	customFG.setOnAction(new EventHandler<ActionEvent>() {
	    		@Override
	    		public void handle(ActionEvent event) {
	    			// TODO Implement custom colour picking.
	    			changeForegroundColour(Color.ORANGERED);		
	    		}
			});
   
	    	changeFGColours.getItems().addAll(whiteFG, orangeFG, redFG, customFG);
    	// End submenu
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
     * Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * 
     * @param menu the Menu object to add items to.
     */
    private void makeEditMenu(Menu menu){
    	MenuItem newCircleWindow = new MenuItem("Set up circle");
    	newCircleWindow.setAccelerator(KeyCombination.keyCombination("Ctrl+E"));
    	newCircleWindow.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			getNewCirclePropertiesPopup();
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
    private void getNewCirclePropertiesPopup(){
    	Stage circlePopUp = new Stage();
    	circlePopUp.setTitle("Set up Circle");
    	circlePopUp.initModality(Modality.APPLICATION_MODAL);
    	circlePopUp.getIcons().add(new Image("http://icons.iconarchive.com/icons/iconsmind/outline/512/Gears-icon.png"));
    	// Image is free for use.

    	VBox components = new VBox();
    	components.setAlignment(Pos.TOP_CENTER);
    	
    	Label lblRadius = new Label("Radius");
    	NumberTextField radius = new NumberTextField(particle.getRadius());
    	radius.setTooltip(new Tooltip("Set the desired radius of your circle."));
    	
       	NumberTextField periodField = new NumberTextField(particle.getPeriod());
    	Label lblPeriodOrFreq = new Label("Period or Frequecy");
    	HBox periodOrFrequencyBox = new HBox();
    	periodOrFrequencyBox.setAlignment(Pos.CENTER);
    	ToggleGroup perOrFreq = new ToggleGroup();
    	ToggleButton period = new ToggleButton("Period");
    	period.setToggleGroup(perOrFreq);
    	period.setSelected(true);
    	period.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				periodField.setText(particle.getPeriod());
			}
		});
    	ToggleButton frequency = new ToggleButton("Frequency");
    	frequency.setToggleGroup(perOrFreq);
    	frequency.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				periodField.setText(particle.getFrequency());
			}
		});
    	periodOrFrequencyBox.getChildren().addAll(period, frequency);
    	
    	Label lblAcc = new Label("Acceleration");
    	NumberTextField acc = new NumberTextField(particle.getAcceleration());
    	acc.setTooltip(new Tooltip("Set the desired acceleration of your particle."));
    	
    	HBox cancelOkay = new HBox();
    	cancelOkay.setAlignment(Pos.CENTER);
    	Button cancel = new Button("Cancel");
    	cancel.setTooltip(new Tooltip("This will discard your changes and close the edit window."));
    	cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit(); // Cancel and exit the program.
				// TODO REMOVE THIS WHEN DONE
				circlePopUp.hide();
			}
		});
    	Button okay = new Button("Okay");
    	okay.setTooltip(new Tooltip("This will save your changes and reflect them in the main display."));
    	okay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveCircleSettings(radius, period.isSelected(), periodField, acc);
				System.out.println("ok");
				circlePopUp.hide();
			}
		});
    	cancelOkay.getChildren().addAll(cancel, okay);
    	
    	components.getChildren().addAll(lblRadius, radius, lblPeriodOrFreq, periodOrFrequencyBox, periodField, lblAcc,
				acc, cancelOkay);

    	Scene stageScene = new Scene(components, 300, 300);
    	circlePopUp.setScene(stageScene);
    	circlePopUp.setAlwaysOnTop(true);
    	circlePopUp.show();	
    	// acceleration
    }
    private void saveCircleSettings(NumberTextField radius, boolean isPeriod, NumberTextField period, NumberTextField
			acc){
		particle.setRadius(radius.getValue());
		if (isPeriod) particle.setPeriod(period.getValue());
		else particle.setFrequency(period.getValue());
		particle.setAcceleration(acc.getValue());
    	System.out.println(particle.getRadius());
		System.out.println(particle.getPeriod());
		System.out.println(particle.getFrequency());
		System.out.println(particle.getAcceleration());
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
