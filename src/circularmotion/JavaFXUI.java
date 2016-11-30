package circularmotion;

import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class JavaFXUI extends Application {
    private int height = 600;
    private int width = 800;
    Stage primStg;
	@Override

	public void start(Stage primaryStage) {
	    Group root = new Group();
	    Scene scene = new Scene(root, width, height, Color.BLACK);
	    primaryStage.setScene(scene);
	    
	    primaryStage.setTitle("Circles in Motion");
	    
	    
	    Canvas canvas = new Canvas(800, 600);
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
    private MenuBar makeMenuBar(){
    	MenuBar mb = new MenuBar();
    	Menu fileMenu = new Menu("File");
	    // Menu items for the File menu
    	MenuItem exit = new MenuItem("Exit");
    	exit.setAccelerator(KeyCombination.keyCombination("Alt+F4"));
        exit.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
			public void handle(ActionEvent event) {
    			System.out.println(event);
    			Platform.exit();
				}
			});
    	//	End of menu items.	    	
    	fileMenu.getItems().addAll(exit);
    	
    	Menu editMenu = new Menu("Edit");
    	// Menu items for the Edit menu
    	// Submenu for foreground colours.
    	Menu changeBGColours = new Menu("Change Background Colour");
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
	    			changeForegroundColour(Color.ORANGERED);		
	    		}
			});
   
	    	changeFGColours.getItems().addAll(whiteFG, orangeFG, redFG, customFG);
    	// End submenu
	    // Submenu for stroke size.
	    Menu strokeSize = new Menu("Change Stroke Size");
		ToggleGroup sizeToggleGroup = new ToggleGroup();
	    	RadioMenuItem[] sizes = new RadioMenuItem[9];
	    	for (int i = 0; i < sizes.length; i++){
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
    	editMenu.getItems().addAll(changeBGColours, changeFGColours, strokeSize);
    	
    	Menu viewMenu = new Menu("View");
    	// Menu items for the View menu
    	// End of menu items.
    	mb.getMenus().addAll(fileMenu, editMenu, viewMenu);
    	return mb;
    }
    private Point findDrawingStartLocations(int radius){
    	Point point = new Point();
    	point.setX((width-radius)/2);
    	point.setY((height-radius)/2);
    	return point;
    }
	public static void main(String[] args) {
		SingleParticle sp = new SingleParticle(0, true, 1, 500);
		launch(args);
	}
	

}
