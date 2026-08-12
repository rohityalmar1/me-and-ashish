package com.athlixcore.view.player.community;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Create_Challenge_Page extends VBox {

    private BorderPane mainLayout;

    public Create_Challenge_Page(BorderPane mainLayout) {
        this.mainLayout = mainLayout;

        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        Button backBtn = new Button("\u2190 Back to Events");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold; -fx-font-size: 15px; -fx-padding: 0;");
        backBtn.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));

        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("Create Match Challenge");
        pageTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label pageSubTitle = new Label("Fill in the details to invite teams for a match.");
        pageSubTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(pageTitle, pageSubTitle);

        VBox formCard = new VBox(20);
        formCard.setPadding(new Insets(30));
        formCard.setMaxWidth(800);
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);");

        formCard.getChildren().add(buildInputRow("Challenge Title", "e.g. Sunday T20 Friendly", false));

        HBox locationRow = new HBox(20);
        VBox groundInput = buildInputRow("Ground Name", "e.g. Shivaji Park", false);
        HBox.setHgrow(groundInput, Priority.ALWAYS);
        
        VBox mapBox = new VBox(5);
        Label mapLabel = new Label("Location Map");
        mapLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        StackPane mapPlaceholder = new StackPane();
        mapPlaceholder.setPrefSize(200, 40);
        mapPlaceholder.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 8;");
        mapPlaceholder.getChildren().add(new Label("\ud83d\uddfa\ufe0f Select on Map"));
        mapBox.getChildren().addAll(mapLabel, mapPlaceholder);
        
        locationRow.getChildren().addAll(groundInput, mapBox);
        formCard.getChildren().add(locationRow);

        HBox dateTimeRow = new HBox(20);
        VBox dateBox = new VBox(5);
        Label dateLbl = new Label("Match Date");
        dateLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        DatePicker datePicker = new DatePicker();
        datePicker.setStyle("-fx-font-size: 14px; -fx-background-color: #f9fafb;");
        datePicker.setPrefWidth(250);
        dateBox.getChildren().addAll(dateLbl, datePicker);
        HBox.setHgrow(dateBox, Priority.ALWAYS);

        VBox timeBox = buildInputRow("Time", "e.g. 09:00 AM", false);
        HBox.setHgrow(timeBox, Priority.ALWAYS);
        dateTimeRow.getChildren().addAll(dateBox, timeBox);
        formCard.getChildren().add(dateTimeRow);

        HBox detailsRow = new HBox(20);
        VBox oversBox = new VBox(5);
        Label oversLbl = new Label("Overs");
        oversLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        ComboBox<String> oversCombo = new ComboBox<>();
        oversCombo.getItems().addAll("T10", "T20", "30 Overs", "40 Overs", "50 Overs", "Test Match");
        oversCombo.setPromptText("Select Format");
        oversCombo.setStyle("-fx-font-size: 14px; -fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8;");
        oversCombo.setPrefWidth(200);
        oversBox.getChildren().addAll(oversLbl, oversCombo);
        HBox.setHgrow(oversBox, Priority.ALWAYS);

        VBox prizeBox = buildInputRow("Prize Pool (\u20b9)", "e.g. 5000", false);
        HBox.setHgrow(prizeBox, Priority.ALWAYS);
        VBox playersBox = buildInputRow("Required Players", "e.g. 11", false);
        HBox.setHgrow(playersBox, Priority.ALWAYS);
        
        detailsRow.getChildren().addAll(oversBox, prizeBox, playersBox);
        formCard.getChildren().add(detailsRow);

        VBox descBox = buildInputRow("Description / Rules", "Add any specific rules, entry fees, or contact details...", true);
        formCard.getChildren().add(descBox);

        Button publishBtn = new Button("Publish Challenge");
        publishBtn.setCursor(Cursor.HAND);
        publishBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 8; -fx-padding: 12 30 12 30;");
        publishBtn.setMaxWidth(Double.MAX_VALUE);
        
        publishBtn.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));

        formCard.getChildren().add(publishBtn);

        ScrollPane scroll = new ScrollPane(formCard);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        this.getChildren().addAll(backBtn, titleBox, scroll);
    }

    private VBox buildInputRow(String labelText, String placeholder, boolean isTextArea) {
        VBox box = new VBox(5);
        Label label = new Label(labelText);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: #374151; -fx-font-size: 14px;");
        
        if (isTextArea) {
            TextArea input = new TextArea();
            input.setPromptText(placeholder);
            input.setPrefRowCount(3);
            input.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8; -fx-font-size: 14px;");
            box.getChildren().addAll(label, input);
        } else {
            TextField input = new TextField();
            input.setPromptText(placeholder);
            input.setStyle("-fx-background-color: #f9fafb; -fx-border-color: #e5e7eb; -fx-border-radius: 8; -fx-padding: 8 12 8 12; -fx-font-size: 14px;");
            box.getChildren().addAll(label, input);
        }
        return box;
    }
}