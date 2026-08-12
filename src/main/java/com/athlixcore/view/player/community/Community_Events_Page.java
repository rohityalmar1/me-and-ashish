package com.athlixcore.view.player.community;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Community_Events_Page extends VBox {

    private BorderPane mainLayout;

    public Community_Events_Page(BorderPane mainLayout) {
        this.mainLayout = mainLayout;

        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        HBox topMenu = buildTopMenu();

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("Match Challenges & Events");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label pageSubTitle = new Label("Find matches, challenge other teams, and join local events.");
        pageSubTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(pageTitle, pageSubTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button createEventBtn = new Button("+ Create Challenge");
        createEventBtn.setCursor(Cursor.HAND);
        createEventBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20 10 20; -fx-font-size: 14px;");
        
        createEventBtn.setOnAction(e -> {
            mainLayout.setCenter(new Create_Challenge_Page(mainLayout));
        });

        headerBox.getChildren().addAll(titleBox, spacer, createEventBtn);

        VBox eventsList = new VBox(20);
        eventsList.getChildren().addAll(
            buildEventCard("Sunday League Match", "Pune Cricket Club", "12 Aug 2024 \u2022 09:00 AM", "Alandi Ground, Pune", "20 Overs", "\u20b9 5,000"),
            buildEventCard("Friendly T20 Challenge", "Mumbai Elite Strikers", "15 Aug 2024 \u2022 04:00 PM", "Shivaji Park, Mumbai", "T20 Match", "No Entry Fee")
        );

        ScrollPane scrollPane = new ScrollPane(eventsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        this.getChildren().addAll(topMenu, headerBox, scrollPane);
    }

    private HBox buildTopMenu() {
        HBox menu = new HBox(20);
        menu.setAlignment(Pos.CENTER_LEFT);
        menu.setPadding(new Insets(0, 0, 10, 0));
        menu.setStyle("-fx-border-color: #d1d5db; -fx-border-width: 0 0 1 0;");

        Button homeBtn = createMenuTab("Home", false);
        Button followingBtn = createMenuTab("Following", false);
        Button groupsBtn = createMenuTab("Groups", false);
        Button eventsBtn = createMenuTab("Events", true); 

        homeBtn.setOnAction(e -> mainLayout.setCenter(new Community_Dashboard(mainLayout)));
        followingBtn.setOnAction(e -> mainLayout.setCenter(new Community_Following_Page(mainLayout)));
        groupsBtn.setOnAction(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));

        menu.getChildren().addAll(homeBtn, followingBtn, groupsBtn, eventsBtn);
        return menu;
    }

    private Button createMenuTab(String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setCursor(Cursor.HAND);
        if (isActive) {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 16px; -fx-border-color: #2563eb; -fx-border-width: 0 0 3 0; -fx-padding: 5 10 5 10;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10;");
            btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10; -fx-background-radius: 8;"));
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10;"));
        }
        return btn;
    }

    private VBox buildEventCard(String title, String host, String dateTime, String location, String format, String prize) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);");

        HBox topSection = new HBox(20);
        
        VBox detailsBox = new VBox(8);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);
        
        Label titleLbl = new Label(title);
        titleLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        
        Label hostLbl = new Label("Hosted by: " + host);
        hostLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #4b5563;");

        HBox badgesBox = new HBox(10);
        Label formatBadge = new Label(format);
        formatBadge.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #b45309; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-font-size: 12px; -fx-font-weight: bold;");
        Label prizeBadge = new Label("\ud83c\udfc6 Prize: " + prize);
        prizeBadge.setStyle("-fx-background-color: #dcfce3; -fx-text-fill: #166534; -fx-padding: 4 10 4 10; -fx-background-radius: 12; -fx-font-size: 12px; -fx-font-weight: bold;");
        badgesBox.getChildren().addAll(formatBadge, prizeBadge);

        Label dateTimeLbl = new Label("\ud83d\udcc5 " + dateTime);
        dateTimeLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #374151;");
        
        Label locationLbl = new Label("\ud83d\udccd " + location);
        locationLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #374151;");

        detailsBox.getChildren().addAll(titleLbl, hostLbl, badgesBox, dateTimeLbl, locationLbl);

        StackPane mapPlaceholder = new StackPane();
        mapPlaceholder.setPrefSize(250, 120);
        mapPlaceholder.setStyle("-fx-background-color: #e5e7eb; -fx-background-radius: 8;");
        Label mapLbl = new Label("\ud83d\uddfa\ufe0f Map View");
        mapLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #6b7280; -fx-font-size: 16px;");
        mapPlaceholder.getChildren().add(mapLbl);

        topSection.getChildren().addAll(detailsBox, mapPlaceholder);

        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER_LEFT);
        actionBox.setPadding(new Insets(10, 0, 0, 0));
        actionBox.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 1 0 0 0;");

        // --- THE FIX: Define all styles here ---
        String intDefault = "-fx-background-color: white; -fx-text-fill: #2563eb; -fx-border-color: #2563eb; -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-weight: bold; -fx-padding: 8 20 8 20;";
        String intActive = "-fx-background-color: #dbeafe; -fx-text-fill: #1d4ed8; -fx-border-color: #2563eb; -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-weight: bold; -fx-padding: 8 20 8 20;";
        
        String accDefault = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 20 8 20;";
        String accActive = "-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 20 8 20;"; 
        
        String rejDefault = "-fx-background-color: white; -fx-text-fill: #ef4444; -fx-border-color: #ef4444; -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-weight: bold; -fx-padding: 8 20 8 20;";
        String rejActive = "-fx-background-color: #fee2e2; -fx-text-fill: #b91c1c; -fx-border-color: #ef4444; -fx-border-radius: 20; -fx-background-radius: 20; -fx-font-weight: bold; -fx-padding: 8 20 8 20;";

        // Create the buttons
        Button interestedBtn = new Button("Interested");
        Button acceptBtn = new Button("Accept");
        Button rejectBtn = new Button("Reject");

        interestedBtn.setCursor(Cursor.HAND);
        interestedBtn.setStyle(intDefault);
        
        acceptBtn.setCursor(Cursor.HAND);
        acceptBtn.setStyle(accDefault);
        
        rejectBtn.setCursor(Cursor.HAND);
        rejectBtn.setStyle(rejDefault);

        // --- THE FIX: Update logic to reset other buttons when one is clicked ---
        
        interestedBtn.setOnAction(e -> {
            if (interestedBtn.getText().equals("Interested")) {
                interestedBtn.setText("Interested \u2713");
                interestedBtn.setStyle(intActive);
                // Reset others
                acceptBtn.setText("Accept");
                acceptBtn.setStyle(accDefault);
                rejectBtn.setText("Reject");
                rejectBtn.setStyle(rejDefault);
            } else {
                interestedBtn.setText("Interested");
                interestedBtn.setStyle(intDefault);
            }
        });

        acceptBtn.setOnAction(e -> {
            if (acceptBtn.getText().equals("Accept")) {
                acceptBtn.setText("Accepted \u2713");
                acceptBtn.setStyle(accActive);
                // Reset others
                interestedBtn.setText("Interested");
                interestedBtn.setStyle(intDefault);
                rejectBtn.setText("Reject");
                rejectBtn.setStyle(rejDefault);
            } else {
                acceptBtn.setText("Accept");
                acceptBtn.setStyle(accDefault);
            }
        });

        rejectBtn.setOnAction(e -> {
            if (rejectBtn.getText().equals("Reject")) {
                rejectBtn.setText("Rejected \u2715");
                rejectBtn.setStyle(rejActive);
                // Reset others
                interestedBtn.setText("Interested");
                interestedBtn.setStyle(intDefault);
                acceptBtn.setText("Accept");
                acceptBtn.setStyle(accDefault);
            } else {
                rejectBtn.setText("Reject");
                rejectBtn.setStyle(rejDefault);
            }
        });

        actionBox.getChildren().addAll(interestedBtn, acceptBtn, rejectBtn);
        card.getChildren().addAll(topSection, actionBox);

        return card;
    }
}