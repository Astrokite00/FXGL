/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */
package s07particles;

import com.almasb.fxgl.animation.EasingInterpolator;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.effect.ParticleControl;
import com.almasb.fxgl.effect.ParticleEmitter;
import com.almasb.fxgl.effect.ParticleEmitters;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.settings.GameSettings;
import javafx.collections.FXCollections;
import javafx.geometry.Point2D;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Spinner;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Example of using particles.
 */
public class ParticleSystemSample extends GameApplication {

    private GameEntity particleEntity;
    private ParticleEmitter emitter;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(700 + 300);
        settings.setHeight(600);
        settings.setTitle("ParticleSystemSample");
        settings.setVersion("0.1");
        settings.setFullScreen(false);
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);
        settings.setProfilingEnabled(false);
        settings.setCloseConfirmation(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initInput() {

    }

    @Override
    protected void initGame() {
        Entities.builder()
                .viewFromNode(new Rectangle(getWidth() - 300, getHeight()))
                .buildAndAttach();

        emitter = ParticleEmitters.newFireEmitter();

        particleEntity = Entities.builder()
                .at((getWidth() - 300) / 2, getHeight() / 2)
                .with(new ParticleControl(emitter))
                .buildAndAttach();
    }

    @Override
    protected void initUI() {

        Spinner<Integer> spinnerNumParticles = new Spinner<>(0, 100, 15, 1);
        emitter.numParticlesProperty().bind(spinnerNumParticles.valueProperty());

        Spinner<Double> spinnerRate = new Spinner<>(0.0, 1.0, 1.0, 0.05);
        emitter.emissionRateProperty().bind(spinnerRate.valueProperty());

        Spinner<Double> spinnerMinSize = new Spinner<>(1.0, 100.0, 9.0, 1);
        emitter.minSizeProperty().bind(spinnerMinSize.valueProperty());

        Spinner<Double> spinnerMaxSize = new Spinner<>(1.0, 100.0, 12.0, 1);
        emitter.maxSizeProperty().bind(spinnerMaxSize.valueProperty());

        Spinner<Double> spinnerVelX = new Spinner<>(-500.0, 500.0, 0.0, 10);
        Spinner<Double> spinnerVelY = new Spinner<>(-500.0, 500.0, -30.0, 10);

        spinnerVelX.setPrefWidth(65);
        spinnerVelY.setPrefWidth(65);

        spinnerVelX.valueProperty().addListener((observable, oldValue, newValue) -> {
            emitter.setVelocityFunction((i, x, y) -> new Point2D(newValue, spinnerVelY.getValue()));
        });

        spinnerVelY.valueProperty().addListener((observable, oldValue, newValue) -> {
            emitter.setVelocityFunction((i, x, y) -> new Point2D(spinnerVelX.getValue(), newValue));
        });

        Spinner<Double> spinnerAccelX = new Spinner<>(-150.0, 150.0, 0.0, 10);
        Spinner<Double> spinnerAccelY = new Spinner<>(-150.0, 150.0, 0.0, 10);

        spinnerAccelX.setPrefWidth(65);
        spinnerAccelY.setPrefWidth(65);

        spinnerAccelX.valueProperty().addListener((observable, oldValue, newValue) -> {
            emitter.setAccelerationFunction(() -> new Point2D(newValue, spinnerAccelY.getValue()));
        });

        spinnerAccelY.valueProperty().addListener((observable, oldValue, newValue) -> {
            emitter.setAccelerationFunction(() -> new Point2D(spinnerAccelX.getValue(), newValue));
        });

        ChoiceBox<BlendMode> choiceBlend = getUIFactory().newChoiceBox(
                FXCollections.observableArrayList(BlendMode.values())
        );

        emitter.blendModeProperty().bind(choiceBlend.valueProperty());
        choiceBlend.setValue(BlendMode.ADD);

        ChoiceBox<EasingInterpolator> choiceInterpolator = getUIFactory().newChoiceBox(
                FXCollections.observableArrayList(Interpolators.values())
        );

        choiceInterpolator.setValue(Interpolators.LINEAR);

        choiceInterpolator.valueProperty().addListener((observable, oldValue, newValue) -> {
            emitter.setInterpolator(newValue.EASE_OUT());
        });

        ColorPicker startColor = new ColorPicker((Color) emitter.getStartColor());
        ColorPicker endColor = new ColorPicker((Color) emitter.getEndColor());

        emitter.startColorProperty().bind(startColor.valueProperty());
        emitter.endColorProperty().bind(endColor.valueProperty());

        VBox vbox = new VBox(10);
        vbox.setTranslateX(700);
        vbox.getChildren().addAll(
                new HBox(10, new Text("Vel X and Y"), spinnerVelX, spinnerVelY),
                new HBox(10, new Text("Acc X and Y"), spinnerAccelX, spinnerAccelY),
                new Text("Number of Particles:"), spinnerNumParticles,
                new Text("Min Size:"), spinnerMinSize,
                new Text("Max Size:"), spinnerMaxSize,
                new Text("Emission Rate:"), spinnerRate,
                new Text("Start Color:"), startColor,
                new Text("End Color:"), endColor,
                choiceBlend,
                choiceInterpolator
        );

        getGameScene().addUINode(vbox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
