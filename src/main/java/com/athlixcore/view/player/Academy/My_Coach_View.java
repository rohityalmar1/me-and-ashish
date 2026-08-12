package com.athlixcore.view.player.Academy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class My_Coach_View extends VBox {

    public My_Coach_View(BorderPane mainLayout, Runnable onBack) {
        this.setSpacing(20);

        // --- NEW BACK BUTTON ---
        Button backBtn = new Button("\u2190 Back to Coach Directory");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 0;");
        
        // Trigger the back action when clicked
        backBtn.setOnAction(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });

        Label title = new Label("Your Training Program");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #111827;");

        // The Assigned Coach Banner
        HBox coachBanner = new HBox(20);
        coachBanner.setPadding(new Insets(25));
        coachBanner.setAlignment(Pos.CENTER_LEFT);
        coachBanner.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e5e7eb; -fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.04), 10, 0, 0, 4);");

        Label avatar = new Label("R");
        avatar.setPrefSize(80, 80);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: #f59e0b; -fx-text-fill: white; -fx-background-radius: 40; -fx-font-weight: bold; -fx-font-size: 32px;");

        VBox info = new VBox(5);
        Label nameLbl = new Label("Rahul Dravid");
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 22px; -fx-text-fill: #111827;");
        Label roleLbl = new Label("Head Coach \u2022 Batting Specialist");
        roleLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        info.getChildren().addAll(nameLbl, roleLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button chatBtn = new Button("Message Coach");
        chatBtn.setCursor(Cursor.HAND);
        chatBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20;");

        coachBanner.getChildren().addAll(avatar, info, spacer, chatBtn);

        // Current Tasks Section
        VBox tasksBox = new VBox(10);
        Label taskTitle = new Label("Current Training Tasks");
        taskTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");
        
        Label task1 = new Label("\u2714 500 balls against spin machine (Completed)");
        task1.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
        
        Label task2 = new Label("\u23f3 Footwork agility drills (Pending)");
        task2.setStyle("-fx-text-fill: #f59e0b; -fx-font-weight: bold;");

        tasksBox.getChildren().addAll(taskTitle, task1, task2);

        // Add the back button to the top of the view
        this.getChildren().addAll(backBtn, title, coachBanner, tasksBox);
    }
}