package circularmotion;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        
        MenuBar menubar = makeMenuBar();
        
        
        Canvas canvas = new Canvas(800, 600);
        
        makeCircle(canvas.getGraphicsContext2D());
        
        root.getChildren().add(canvas);
        root.getChildren().add(menubar);
        primStg = primaryStage;
        primaryStage.show();
    }
	private void changeBackgroundColour(Paint colour){
		primStg.getScene().setFill(colour);
	}
	
    private void makeCircle(GraphicsContext gc){
        gc.setStroke(Color.AZURE);
        gc.setLineWidth(1);
        Point p = findDrawingStartLocations(500);
        gc.strokeOval(p.getX(), p.getY(), 500, 500);
    }
    private MenuBar makeMenuBar(){
    	MenuBar mb = new MenuBar();
    	
    	Menu fileMenu = new Menu("File");
 
    	MenuItem exit = new MenuItem("Exit");
	    	exit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Platform.exit();
				}
			});
	    	
    	fileMenu.getItems().addAll(exit);
    	Menu editMenu = new Menu("Edit");
    	Menu changeColours = new Menu("Change Background Colour"); //	Submenu
    	MenuItem black = new MenuItem("Black");
    	MenuItem blue = new MenuItem("Blue");
    	MenuItem green = new MenuItem("Green");
    	
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
    	
    	changeColours.getItems().addAll(black, blue, green);
    	
    	editMenu.getItems().add(changeColours);
    	
    	
    	Menu viewMenu = new Menu("View");
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
