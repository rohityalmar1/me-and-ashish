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
import javafx.scene.layout.VBox;

public class Groups_Page extends VBox {

    private BorderPane mainLayout;

    public Groups_Page(BorderPane mainLayout) {
        this.mainLayout = mainLayout;

        this.setSpacing(20);
        this.setPadding(new Insets(20, 40, 20, 40));
        this.setStyle("-fx-background-color: #fbf8f8;");

        HBox topMenu = buildTopMenu();

        HBox headerBox = new HBox();
        headerBox.setAlignment(Pos.CENTER_LEFT);
        
        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("Community Groups");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label pageSubTitle = new Label("Discover, join, or create your own cricket communities.");
        pageSubTitle.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        titleBox.getChildren().addAll(pageTitle, pageSubTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button createGroupBtn = new Button("+ Create Group");
        createGroupBtn.setCursor(Cursor.HAND);
        createGroupBtn.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 20 10 20; -fx-font-size: 14px;");
        createGroupBtn.setOnAction(e -> mainLayout.setCenter(new Create_Group_Page(mainLayout)));

        headerBox.getChildren().addAll(titleBox, spacer, createGroupBtn);

        VBox groupsList = new VBox(15);
        groupsList.getChildren().addAll(
            buildGroupCard("Pune Cricket Club", "124 Members \u2022 Public", "A community for all local Pune players.", true),
            buildGroupCard("Mumbai Elite Strikers", "89 Members \u2022 Private", "Invite-only group for division A players.", false),
            buildGroupCard("Weekend Warriors XI", "15 Members \u2022 Public", "Casual Sunday cricket organizers.", false)
        );

        ScrollPane scrollPane = new ScrollPane(groupsList);
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
        Button groupsBtn = createMenuTab("Groups", true); 
        Button eventsBtn = createMenuTab("Events", false);

        homeBtn.setOnAction(e -> mainLayout.setCenter(new Community_Dashboard(mainLayout)));
        followingBtn.setOnAction(e -> mainLayout.setCenter(new Community_Following_Page(mainLayout)));
        eventsBtn.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));

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

    private HBox buildGroupCard(String name, String stats, String desc, boolean isJoined) {
        HBox card = new HBox(20);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);");

        Label icon = new Label(name.substring(0, 1));
        icon.setPrefSize(60, 60);
        icon.setAlignment(Pos.CENTER);
        icon.setStyle("-fx-background-color: #dbeafe; -fx-text-fill: #1d4ed8; -fx-background-radius: 30; -fx-font-weight: bold; -fx-font-size: 24px;");

        VBox details = new VBox(5);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #111827;");
        
        Label statsLbl = new Label(stats);
        statsLbl.setStyle("-fx-text-fill: #4f46e5; -fx-font-weight: bold; -fx-font-size: 13px;");
        
        Label descLbl = new Label(desc);
        descLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 14px;");
        
        details.getChildren().addAll(nameLbl, statsLbl, descLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button actionBtn = new Button(isJoined ? "View Group" : "Join Group");
        actionBtn.setCursor(Cursor.HAND);
        
        if (isJoined) {
            actionBtn.setStyle("-fx-background-color: white; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-border-color: #d1d5db; -fx-border-radius: 20; -fx-padding: 8 20 8 20;");
            actionBtn.setOnAction(e -> mainLayout.setCenter(new Group_Details(mainLayout)));
        } else {
            actionBtn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 8 20 8 20;");
        }

        card.getChildren().addAll(icon, details, spacer, actionBtn);
        return card;
    }
}