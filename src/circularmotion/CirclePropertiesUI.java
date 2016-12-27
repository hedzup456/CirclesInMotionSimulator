package circularmotion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by richa on 27/12/2016.
 */
public class CirclePropertiesUI {
    Stage circlePopUp = new Stage();
    SingleParticle particle;

    public CirclePropertiesUI(SingleParticle sp){
        particle = sp;
        setUpNewCirclePropertiesPopup();
    }
    public void show(){
        circlePopUp.show();
    }

    /**
     * Get the particle object the Circle properties are modifying.
     * @return the particle object.
     */
    public SingleParticle getParticle(){
        return particle;
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

    private void setUpNewCirclePropertiesPopup(){
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
                // TODO REMOVE EXIT WHEN DONE
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
}
