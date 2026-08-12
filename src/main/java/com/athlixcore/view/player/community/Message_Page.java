package com.athlixcore.view.player.community;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Message_Page extends VBox {

    public Message_Page(BorderPane mainLayout) {
        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        Button backBtn = new Button("\u2190 Back to Group");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 0;");
        backBtn.setOnAction(e -> {
            mainLayout.setCenter(new Group_Details(mainLayout));
        });

        Label title = new Label("Messages");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label placeholder = new Label("Your conversation with the group will appear here.");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: #6b7280;");

        this.getChildren().addAll(backBtn, title, placeholder);
    }
}