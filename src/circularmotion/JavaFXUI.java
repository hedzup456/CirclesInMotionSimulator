package circularmotion;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXUI extends Application {
    private int height = 600;
    private int width = 800;
    private SingleParticle particle = new SingleParticle(1, true, 10, 1);
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
	    
	    root.getChildren().add(canvas);
	    root.getChildren().add(makeMenuBar());
	    primStg = primaryStage;
	    primaryStage.show();
    }
	private Canvas getCanvasFromStage(){
		Group group = (Group) primStg.getScene().getRoot();
		return (Canvas) group.getChildren().get(0);
		//	Canvas is the first item added.
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
    }
    
    /**
     * Method to create the terms for the File menu. 
     * <p> 
     * Moved out of the makeMenuBar method to allow both folding of unneeded code and to make reuse of code easier should it be needed.
     * 
     * @param menu the Menu object to add items to.
     */
    private void makeFileMenu(Menu menu){
    	MenuItem exit = new MenuItem("Exit");
    	exit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
			public void handle(ActionEvent event) {
      			Platform.exit(); // Close the program.
				}
			});	    	
    	menu.getItems().addAll(exit);
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
				circlePopUp.hide();				
			}
		});
    	Button okay = new Button("Okay");
    	okay.setTooltip(new Tooltip("This will save your changes and reflect them in the main display."));
    	okay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("ok");
				circlePopUp.hide();
			}
		});
    	cancelOkay.getChildren().addAll(cancel, okay);
    	
    	components.getChildren().addAll(lblRadius, radius, lblPeriodOrFreq, periodOrFrequencyBox, periodField, lblAcc, acc, cancelOkay);

    	Scene stageScene = new Scene(components, 300, 300);
    	circlePopUp.setScene(stageScene);
    	circlePopUp.setAlwaysOnTop(true);
    	circlePopUp.show();	
    	// acceleration
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
