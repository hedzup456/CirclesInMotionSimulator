package circularmotion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

// This is literally done for no reason other than more class files = better.
// Okay also because it's neater to have it as a separate object, and it's easier to modify and stuff.
// But shush.

/**
 * Created by richa on 27/12/2016.
 */
public class Grapher {
    static int numberOfGraphers = 0;
    int grapherIDNumber;
    Stage grapher = new Stage();
    /**
     * Character data points to store the variable stored on x and y.
     */
    char x;
    char y;

    /**
     * Constructor for the grapher object. Given no axis, the grapher automatically plots displacement against time.
     */
    public Grapher(){
        this('t','s');
    }
    /**
     * Constructor for the Grapher object.
     */
    public  Grapher(char x, char y) {
        grapherIDNumber = numberOfGraphers;
        numberOfGraphers++;
        this.x = x;
        this.y = y;
        setUpGrapher();
    }

    public void close(){
        numberOfGraphers--;
    }
    /**
     *  Method to call the Stage's show method.
     */
    public void show(){
        grapher.show();
    }

    public void setUpGrapher() {
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel( getVariableAsFullName(y) );
        yAxis.setId("Graph " + grapherIDNumber + " y axis");

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel( getVariableAsFullName(x) );
        xAxis.setId("Graph " + grapherIDNumber + " x axis");

        LineChart<Number, Number> lineChart= new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setId("Graph " + grapherIDNumber + " graph");
        lineChart.setCreateSymbols(false); // Ugly-ass dots, get outta here

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Pregenerated Values");

        lineChart.getData().add(dataSeries);

        Scene scene = new Scene(lineChart, 800, 600);
        grapher.setScene(scene);
    }

    private String getVariableAsFullName(char var) {
        // TODO CASING
        switch (var){
            case 't':
                return "Time";
            case 's':
                return "Displacement";
            default:
                return "" + var; // Return the letter if no name found
        }
    }
}
