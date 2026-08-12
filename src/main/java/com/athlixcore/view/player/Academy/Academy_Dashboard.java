package com.athlixcore.view.player.Academy;

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

public class Academy_Dashboard extends VBox {

    private BorderPane mainLayout;
    private HBox tabsContainer;
    private VBox contentContainer;

    private boolean hasCoachAssigned = false; 

    public Academy_Dashboard(BorderPane mainLayout) {
        this.mainLayout = mainLayout;
        this.setStyle("-fx-background-color: #f8fafc;");

        VBox innerContent = new VBox(25);
        innerContent.setPadding(new Insets(30, 40, 30, 40));

        VBox headerBox = buildAcademyHeader();

        tabsContainer = new HBox(15);
        tabsContainer.setAlignment(Pos.CENTER_LEFT);
        tabsContainer.setPadding(new Insets(10, 0, 10, 0));

        contentContainer = new VBox(25);
        VBox.setVgrow(contentContainer, Priority.ALWAYS);

        innerContent.getChildren().addAll(headerBox, tabsContainer, contentContainer);

        ScrollPane scrollPane = new ScrollPane(innerContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");
        
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        this.getChildren().add(scrollPane);
        
        loadTabContent("Overview");
    }

    private VBox buildAcademyHeader() {
        VBox headerContainer = new VBox(0); 
        headerContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 25, 0, 0, 10);");

        StackPane banner = new StackPane();
        banner.setPrefHeight(160);
        banner.setStyle("-fx-background-color: linear-gradient(to bottom right, #047857, #10b981); -fx-background-radius: 20 20 0 0;");
        
        Label bannerText = new Label("\ud83c\udfc6 Elite Strikers Cricket Academy");
        bannerText.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 2);");
        banner.getChildren().add(bannerText);

        HBox infoBox = new HBox(20);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(25, 30, 30, 30));

        Label logo = new Label("ES");
        logo.setPrefSize(70, 70);
        logo.setAlignment(Pos.CENTER);
        logo.setStyle("-fx-background-color: #ecfdf5; -fx-text-fill: #047857; -fx-background-radius: 16; -fx-font-weight: bold; -fx-font-size: 24px; -fx-border-color: #d1fae5; -fx-border-radius: 16; -fx-border-width: 2;");

        VBox textInfo = new VBox(8);
        Label academyName = new Label("Elite Strikers Academy");
        academyName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label academyStats = new Label("\ud83d\udc65 85 Active Players  \u2022  \ud83c\udfd4\ufe0f 3 Turf Wickets  \u2022  \ud83d\udccd Maharashtra");
        academyStats.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px; -fx-font-weight: bold;");
        textInfo.getChildren().addAll(academyName, academyStats);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button enrollBtn = new Button("Enroll Now");
        enrollBtn.setCursor(Cursor.HAND);
        enrollBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 25; -fx-font-size: 14px;");
        
        Button contactBtn = new Button("Contact Coach");
        contactBtn.setCursor(Cursor.HAND);
        contactBtn.setStyle("-fx-background-color: white; -fx-text-fill: #047857; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 25; -fx-font-size: 14px; -fx-border-color: #a7f3d0; -fx-border-radius: 25;");

        infoBox.getChildren().addAll(logo, textInfo, spacer, contactBtn, enrollBtn);
        headerContainer.getChildren().addAll(banner, infoBox);

        return headerContainer;
    }

    private void loadTabContent(String tabName) {
        tabsContainer.getChildren().clear();
        
        Button overviewBtn = createTabButton("Overview", tabName.equals("Overview"));
        Button rosterBtn = createTabButton("Squad & Roster", tabName.equals("Squad & Roster"));
        Button coachesBtn = createTabButton("Coaching Staff", tabName.equals("Coaching Staff"));
        Button facilitiesBtn = createTabButton("Facilities", tabName.equals("Facilities"));

        overviewBtn.setOnAction(e -> loadTabContent("Overview"));
        rosterBtn.setOnAction(e -> loadTabContent("Squad & Roster"));
        coachesBtn.setOnAction(e -> loadTabContent("Coaching Staff"));
        facilitiesBtn.setOnAction(e -> loadTabContent("Facilities"));

        tabsContainer.getChildren().addAll(overviewBtn, rosterBtn, coachesBtn, facilitiesBtn);

        contentContainer.getChildren().clear();

        switch (tabName) {
            case "Squad & Roster" -> contentContainer.getChildren().add(buildRosterTab());
            case "Coaching Staff" -> {
                if (hasCoachAssigned) {
                    // --- WIRED THE BACK BUTTON ACTION HERE ---
                    contentContainer.getChildren().add(new My_Coach_View(mainLayout, () -> {
                        hasCoachAssigned = false; // Reset to unassigned
                        loadTabContent("Coaching Staff"); // Reload the tab
                    }));
                } else {
                    contentContainer.getChildren().add(new Coach_Discovery(mainLayout, () -> {
                        hasCoachAssigned = true; 
                        loadTabContent("Coaching Staff"); 
                    }));
                }
            }
            case "Facilities" -> contentContainer.getChildren().add(buildFacilitiesTab());
            default -> contentContainer.getChildren().add(buildOverviewTab());
        }
    }

    private Button createTabButton(String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setCursor(Cursor.HAND);
        if (isActive) {
            btn.setStyle("-fx-background-color: #d1fae5; -fx-text-fill: #047857; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 20; -fx-padding: 8 22;");
        } else {
            btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 22;");
        }
        return btn;
    }

    private HBox buildOverviewTab() {
        HBox split = new HBox(25);
        
        VBox leftSide = new VBox(20);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        
        VBox aboutCard = new VBox(10);
        aboutCard.setPadding(new Insets(25));
        aboutCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        
        Label aboutTitle = new Label("Academy Philosophy");
        aboutTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #111827;");
        Label aboutText = new Label("Elite Strikers Academy is dedicated to nurturing raw talent and transforming them into professional athletes. We focus on discipline, modern technique, and match-simulation training to prepare our players for top-tier competitive leagues.");
        aboutText.setWrapText(true);
        aboutText.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 14px; -fx-line-spacing: 5px;");
        aboutCard.getChildren().addAll(aboutTitle, aboutText);

        HBox statsRow = new HBox(20);
        statsRow.getChildren().addAll(
            createStatCard("Established", "2015", "#3b82f6"),
            createStatCard("Tournaments Won", "14", "#f59e0b"),
            createStatCard("Pro Players Produced", "26", "#10b981")
        );

        leftSide.getChildren().addAll(aboutCard, statsRow);

        VBox rightSide = new VBox(15);
        rightSide.setPrefWidth(320);
        rightSide.setMinWidth(320);
        rightSide.setPadding(new Insets(25));
        rightSide.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");

        Label noticeTitle = new Label("Notice Board \ud83d\udccc");
        noticeTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");

        rightSide.getChildren().addAll(
            noticeTitle,
            createNoticeItem("Weekend Trials", "Under-19 trials this Saturday at 7 AM.", "#ef4444"),
            createNoticeItem("Kit Distribution", "New match kits arriving next week. Clear your dues.", "#3b82f6"),
            createNoticeItem("Match Canceled", "Friendly vs Spartans canceled due to rain.", "#64748b")
        );

        split.getChildren().addAll(leftSide, rightSide);
        return split;
    }

    private VBox createStatCard(String title, String val, String color) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(20));
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px; -fx-font-weight: bold;");
        Label v = new Label(val);
        v.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 24px; -fx-font-weight: bold;");
        card.getChildren().addAll(t, v);
        return card;
    }

    private VBox createNoticeItem(String title, String desc, String badgeColor) {
        VBox item = new VBox(5);
        item.setPadding(new Insets(10, 0, 10, 0));
        item.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");
        
        HBox top = new HBox(10);
        Label tLbl = new Label(title);
        tLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 14px;");
        Label dot = new Label("\u2022");
        dot.setStyle("-fx-text-fill: " + badgeColor + "; -fx-font-size: 18px;");
        top.getChildren().addAll(dot, tLbl);

        Label dLbl = new Label(desc);
        dLbl.setWrapText(true);
        dLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px; -fx-padding: 0 0 0 20;");

        item.getChildren().addAll(top, dLbl);
        return item;
    }

    private VBox buildRosterTab() {
        VBox table = new VBox(0);
        table.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 8, 0, 0, 2);");
        
        HBox header = new HBox(10);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");
        header.setAlignment(Pos.CENTER_LEFT);

        header.getChildren().addAll(
            createHeaderLabel("Player Name", 250), 
            createHeaderLabel("Primary Role", 150), 
            createHeaderLabel("Batch / Age Group", 150), 
            createHeaderLabel("Status", 100)
        );

        table.getChildren().addAll(
            header,
            buildRosterRow("Vikram Malhotra", "All-Rounder", "Senior Pro (A-Team)", "Active", "#10b981"),
            buildRosterRow("Arjun Khanna", "Fast Bowler", "Under-19", "Active", "#10b981"),
            buildRosterRow("Siddharth Patel", "Wicketkeeper Batsman", "Senior Pro (A-Team)", "Injured", "#ef4444"),
            buildRosterRow("Rohan Desai", "Top Order Batsman", "Under-16", "Active", "#10b981"),
            buildRosterRow("Kabir Singh", "Left-Arm Spinner", "Under-19", "On Leave", "#f59e0b")
        );

        return table;
    }

    private Label createHeaderLabel(String text, double width) {
        Label lbl = new Label(text);
        lbl.setPrefWidth(width);
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #6b7280; -fx-font-size: 12px;");
        return lbl;
    }

    private HBox buildRosterRow(String name, String role, String batch, String status, String statusColor) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(15, 20, 15, 20));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-border-color: #f3f4f6; -fx-border-width: 0 0 1 0;");

        HBox playerBox = new HBox(15);
        playerBox.setPrefWidth(250);
        playerBox.setAlignment(Pos.CENTER_LEFT);
        Label avatar = new Label(name.substring(0, 1));
        avatar.setPrefSize(35, 35);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: #e0e7ff; -fx-text-fill: #4338ca; -fx-background-radius: 17.5; -fx-font-weight: bold;");
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: #111827; -fx-font-size: 14px;");
        playerBox.getChildren().addAll(avatar, nameLbl);

        Label roleLbl = new Label(role);
        roleLbl.setPrefWidth(150);
        roleLbl.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px;");

        Label batchLbl = new Label(batch);
        batchLbl.setPrefWidth(150);
        batchLbl.setStyle("-fx-text-fill: #4b5563; -fx-font-size: 13px;");

        Label statusBadge = new Label(status);
        statusBadge.setPrefWidth(100);
        statusBadge.setStyle("-fx-text-fill: " + statusColor + "; -fx-font-weight: bold; -fx-font-size: 13px;");

        row.getChildren().addAll(playerBox, roleLbl, batchLbl, statusBadge);
        return row;
    }

    private VBox buildFacilitiesTab() {
        VBox box = new VBox(20);
        Label title = new Label("Academy Training Facilities");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: #111827;");
        
        Label desc = new Label("Our academy provides state-of-the-art infrastructure to ensure players get the best possible environment to train and recover.");
        desc.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");

        HBox facilitiesGrid = new HBox(20);
        facilitiesGrid.getChildren().addAll(
            createFacilityCard("\ud83c\udfd4\ufe0f", "3 Turf Wickets", "Match-simulation quality pitches."),
            createFacilityCard("\u26be", "Bowling Machines", "High-speed pace and spin simulators."),
            createFacilityCard("\ud83c\udfcb\ufe0f", "Strength & Conditioning", "Fully equipped modern gym for athletes."),
            createFacilityCard("\ud83d\udcfa", "Video Analytics Room", "For post-match and swing technique reviews.")
        );

        box.getChildren().addAll(title, desc, facilitiesGrid);
        return box;
    }

    private VBox createFacilityCard(String icon, String title, String desc) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label iconLbl = new Label(icon);
        iconLbl.setStyle("-fx-font-size: 32px;");

        Label tLbl = new Label(title);
        tLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: #111827;");

        Label dLbl = new Label(desc);
        dLbl.setWrapText(true);
        dLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 13px;");

        card.getChildren().addAll(iconLbl, tLbl, dLbl);
        return card;
    }
}