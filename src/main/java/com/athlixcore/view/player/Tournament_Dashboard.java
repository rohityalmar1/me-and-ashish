

package com.athlixcore.view.player;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Tournament_Dashboard extends VBox {

    public Tournament_Dashboard(BorderPane mainLayout) {
        this.setSpacing(20);
        this.setPadding(new Insets(40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        Label title = new Label("Tournament Dashboard");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label subtitle = new Label("Tournament features are under construction.");
        subtitle.setStyle("-fx-font-size: 16px; -fx-text-fill: #6b7280;");

        this.getChildren().addAll(title, subtitle);
    }
}