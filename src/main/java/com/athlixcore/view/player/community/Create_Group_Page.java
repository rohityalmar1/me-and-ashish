package com.athlixcore.view.player.community;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Create_Group_Page extends VBox {

    public Create_Group_Page(BorderPane mainLayout) {
        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        Button backBtn = new Button("\u2190 Back to Groups");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 0;");
        backBtn.setOnAction(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));

        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("Create a New Cricket Group");
        pageTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        titleBox.getChildren().add(pageTitle);

        VBox formCard = new VBox(20);
        formCard.setPadding(new Insets(30));
        formCard.setMaxWidth(800);
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1;");

        VBox nameBox = new VBox(5);
        Label nameLbl = new Label("Group Name");
        nameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8; -fx-padding: 8 12 8 12; -fx-font-size: 14px;");
        nameBox.getChildren().addAll(nameLbl, nameInput);

        VBox privacyBox = new VBox(5);
        Label privacyLbl = new Label("Group Privacy");
        privacyLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        ComboBox<String> privacyCombo = new ComboBox<>();
        privacyCombo.getItems().addAll("Public", "Private");
        privacyCombo.setValue("Public");
        privacyCombo.setStyle("-fx-font-size: 14px; -fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");
        privacyBox.getChildren().addAll(privacyLbl, privacyCombo);

        VBox descBox = new VBox(5);
        Label descLbl = new Label("Description");
        descLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        TextArea descInput = new TextArea();
        descInput.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8; -fx-font-size: 14px;");
        descBox.getChildren().addAll(descLbl, descInput);

        Button submitBtn = new Button("Create Group");
        submitBtn.setCursor(Cursor.HAND);
        submitBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 8; -fx-padding: 12 30 12 30;");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.setOnAction(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));

        formCard.getChildren().addAll(nameBox, privacyBox, descBox, submitBtn);

        ScrollPane scroll = new ScrollPane(formCard);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        this.getChildren().addAll(backBtn, titleBox, scroll);
    }
}