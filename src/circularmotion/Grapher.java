package circularmotion;

import com.sun.org.apache.xerces.internal.impl.dv.dtd.NMTOKENDatatypeValidator;
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


    int num = 360;
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
        xAxis.setAutoRanging(true);
        xAxis.setForceZeroInRange(false);
        xAxis.setMinorTickCount(0);


        LineChart<Number, Number> lineChart= new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setId("Graph " + grapherIDNumber + " graph");
        lineChart.setCreateSymbols(false); // Ugly-ass dots, get outta here

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Pregenerated Values");

        for (int i = 0; i <= 360; i++){
            dataSeries.getData().add(new XYChart.Data<Number, Number>(i, Math.sin( Math.toRadians(i) ) ) );
        }

        lineChart.getData().add(dataSeries);

        Button addNew = new Button("ASDF");
        addNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle (ActionEvent event) {
                addNewDataPointToGraph();
            }
        });
        addNew.setDefaultButton(true);

        Group root = new Group();
        root.getChildren().addAll(lineChart, addNew);
        Scene scene = new Scene(root, 800, 600);
        grapher.setScene(scene);
    }

    /**
     * This is a method for testing only. Science.
     */
    private void addNewDataPointToGraph(){
        LineChart<Number, Number> lc = (LineChart<Number, Number>) grapher.getScene().getRoot().getChildrenUnmodifiable().get(0);
        XYChart.Series data = lc.getData().get(0);    // Get the data set.

        data.getData().remove(0);
        data.getData().add( new XYChart.Data<Number, Number>(num, Math.sin( Math.toRadians(num) ) ) );
        num++;
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
