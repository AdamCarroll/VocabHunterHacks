/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.vocabhunter.gui.main;

import io.github.vocabhunter.gui.common.Placement;
import io.github.vocabhunter.gui.controller.*;
import io.github.vocabhunter.gui.hack.HackService;
import io.github.vocabhunter.gui.model.FilterSettingsTool;
import io.github.vocabhunter.gui.model.MainModel;
import io.github.vocabhunter.gui.services.PlacementManager;
import io.github.vocabhunter.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class VocabHunterGui {
    private static final Logger LOG = LoggerFactory.getLogger(VocabHunterGui.class);

    @Inject
    private FXMLLoader mainLoader;

    @Inject
    private MainController mainController;

    @Inject
    private PlacementManager placementManager;

    @Inject
    private GuiFileHandler guiFileHandler;

    @Inject
    private TitleHandler titleHandler;

    @Inject
    private MainModel model;

    @Inject
    private FilterHandler filterHandler;

    @Inject
    private ExitRequestHandler exitRequestHandler;

    @Inject
    private SessionStateHandler sessionStateHandler;

    @Inject
    private FilterSettingsTool filterSettingsTool;

    @Inject
    private HackService hackService;

    public void start(final Stage stage, final Instant start) {
        Parent root = ViewFxml.MAIN.loadNode(mainLoader);

        initialise(stage);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(this::handleKeyEvent);
        stage.setOnCloseRequest(mainController.getCloseRequestHandler());

        Placement placement = placementManager.getMainWindow();

        stage.setScene(scene);
        stage.setWidth(placement.getWidth());
        stage.setHeight(placement.getHeight());
        if (placement.isPositioned()) {
            stage.setX(placement.getX());
            stage.setY(placement.getY());
        }
        stage.show();
        LOG.info("User interface started");
        hackService.beginHack(start);

        // We delay starting the async filtering to allow the GUI to start quickly
        filterSettingsTool.beginAsyncFiltering();
    }

    private void initialise(final Stage stage) {
        mainController.initialise(stage);
        guiFileHandler.initialise(stage);
        exitRequestHandler.initialise(stage);
        titleHandler.initialise();
        filterHandler.initialise();

        stage.titleProperty().bind(model.titleProperty());
    }

    private void handleKeyEvent(final KeyEvent event) {
        sessionStateHandler.getSessionActions()
            .map(SessionActions::getKeyPressHandler)
            .ifPresent(k -> k.handle(event));
    }
}
