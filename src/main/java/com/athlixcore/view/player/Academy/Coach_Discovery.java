package com.athlixcore.view.player.Academy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class Coach_Discovery extends VBox {

    private Runnable onCoachSelected;

    // Class-level UI components so our buttons can interact with them
    private FlowPane coachGrid;
    private ComboBox<String> specCombo;
    private ComboBox<String> expCombo;
    private ComboBox<String> ratingCombo;
    private Button aiBtn;

    public Coach_Discovery(BorderPane mainLayout, Runnable onCoachSelected) {
        this.onCoachSelected = onCoachSelected;
        
        this.setSpacing(25);
        this.setStyle("-fx-background-color: #f8fafc;");

        // 1. Premium Dark Banner
        HBox banner = buildEliteBanner();

        // 2. Filter Row
        HBox filterRow = buildFilterRow();

        // 3. Main Split Content (Left: Coach Grid, Right: AI Sidebar)
        HBox mainSplit = new HBox(30);
        VBox.setVgrow(mainSplit, Priority.ALWAYS);

        // --- Left Column: Recommended Coaches ---
        VBox leftColumn = new VBox(20);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        
        HBox sectionHeader = new HBox();
        Label sectionTitle = new Label("Recommended for You");
        sectionTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #0f172a;");
        sectionHeader.getChildren().add(sectionTitle);

        coachGrid = new FlowPane(20, 20);
        
        // Load initial coaches
        applyFilters();

        leftColumn.getChildren().addAll(sectionHeader, coachGrid);

        // --- Right Column: AI Matcher & Widgets ---
        VBox rightSidebar = new VBox(20);
        rightSidebar.setPrefWidth(300);
        rightSidebar.setMinWidth(300);
        rightSidebar.getChildren().addAll(
            buildAiMatcherWidget(),
            buildAvailabilityWidget(),
            buildWeatherWidget()
        );

        mainSplit.getChildren().addAll(leftColumn, rightSidebar);

        this.getChildren().addAll(banner, filterRow, mainSplit);
    }

    private HBox buildEliteBanner() {
        HBox banner = new HBox(30);
        banner.setPadding(new Insets(35, 40, 35, 40));
        banner.setAlignment(Pos.CENTER_LEFT);
        banner.setStyle("-fx-background-color: #0f172a; -fx-background-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.15), 15, 0, 0, 5);");

        VBox textStack = new VBox(15);
        Label badge = new Label("PREMIUM DISCOVERY");
        badge.setStyle("-fx-background-color: #1e293b; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 11px; -fx-padding: 4 10; -fx-background-radius: 8;");
        
        Label title1 = new Label("Master Your Craft");
        title1.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 32px;");
        Label title2 = new Label("with Elite Coaching");
        title2.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 32px;");
        VBox titles = new VBox(-5, title1, title2);

        Label desc = new Label("Access professional ICC-certified mentors and performance\nspecialists worldwide. Precision training for the next generation.");
        desc.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-line-spacing: 4px;");

        HBox buttons = new HBox(15);
        Button findBtn = new Button("\ud83d\udd0d Find Coach");
        findBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20;");
        findBtn.setCursor(Cursor.HAND);
        
        aiBtn = new Button("\u2728 AI Recommendation");
        aiBtn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20; -fx-border-color: #334155; -fx-border-radius: 8;");
        aiBtn.setCursor(Cursor.HAND);
        
        // --- FUNCTIONALITY: FIND COACH BUTTON ---
        findBtn.setOnAction(e -> applyFilters());

        // --- FUNCTIONALITY: AI RECOMMENDATION BUTTON ---
        aiBtn.setOnAction(e -> {
            // The AI noticed a dip in Power Hitting (Batting), so it auto-filters for you!
            specCombo.setValue("Batting");
            expCombo.setValue("20+ Years"); // Wants the best coaches for you
            applyFilters();
            aiBtn.setText("\u2714 AI Filters Applied!");
            aiBtn.setStyle("-fx-background-color: #064e3b; -fx-text-fill: #34d399; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20; -fx-border-color: #059669; -fx-border-radius: 8;");
        });
        
        buttons.getChildren().addAll(findBtn, aiBtn);
        textStack.getChildren().addAll(badge, titles, desc, buttons);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox statsBox = new HBox(25);
        statsBox.setPadding(new Insets(20, 30, 20, 30));
        statsBox.setStyle("-fx-background-color: #1e293b; -fx-background-radius: 12;");
        statsBox.getChildren().addAll(
            createBannerStat("2,450+", "Total Coaches"),
            createBannerStat("850+", "Verified"),
            createBannerStat("120", "Online Now")
        );

        banner.getChildren().addAll(textStack, spacer, statsBox);
        return banner;
    }

    private VBox createBannerStat(String val, String title) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        Label v = new Label(val);
        v.setStyle("-fx-text-fill: #10b981; -fx-font-size: 24px; -fx-font-weight: bold;");
        Label t = new Label(title);
        t.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 12px;");
        box.getChildren().addAll(v, t);
        return box;
    }

    private HBox buildFilterRow() {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        Button filterIconBtn = new Button("\u2699 Filters");
        filterIconBtn.setStyle("-fx-background-color: #0f172a; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 16;");
        
        specCombo = new ComboBox<>();
        specCombo.getItems().addAll("All Roles", "Batting", "Bowling", "Fielding", "High Performance");
        specCombo.setValue("All Roles");
        styleFilterCombo(specCombo);

        expCombo = new ComboBox<>();
        expCombo.getItems().addAll("All Experience", "5+ Years", "10+ Years", "15+ Years", "20+ Years");
        expCombo.setValue("All Experience");
        styleFilterCombo(expCombo);

        ratingCombo = new ComboBox<>();
        ratingCombo.getItems().addAll("All Ratings", "4.5+", "4.8+", "5.0");
        ratingCombo.setValue("All Ratings");
        styleFilterCombo(ratingCombo);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label clearLbl = new Label("Clear All");
        clearLbl.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 13px;");
        clearLbl.setCursor(Cursor.HAND);

        Button applyBtn = new Button("Apply");
        applyBtn.setStyle("-fx-background-color: #0f172a; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8 20;");
        applyBtn.setCursor(Cursor.HAND);

        // --- FUNCTIONALITY: FILTER ACTIONS ---
        applyBtn.setOnAction(e -> applyFilters());
        
        clearLbl.setOnMouseClicked(e -> {
            specCombo.setValue("All Roles");
            expCombo.setValue("All Experience");
            ratingCombo.setValue("All Ratings");
            
            // Reset the AI button visually if it was clicked
            aiBtn.setText("\u2728 AI Recommendation");
            aiBtn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10 20; -fx-border-color: #334155; -fx-border-radius: 8;");
            
            applyFilters();
        });

        row.getChildren().addAll(filterIconBtn, specCombo, expCombo, ratingCombo, spacer, clearLbl, applyBtn);
        return row;
    }

    private void styleFilterCombo(ComboBox<String> combo) {
        combo.setStyle("-fx-background-color: white; -fx-border-color: #e2e8f0; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 2 10; -fx-text-fill: #475569; -fx-font-weight: bold;");
        combo.setCursor(Cursor.HAND);
    }

    // --- FUNCTIONALITY: THE FILTERING ENGINE ---
    private void applyFilters() {
        coachGrid.getChildren().clear();
        
        String reqSpec = specCombo.getValue();
        String reqExp = expCombo.getValue();
        String reqRat = ratingCombo.getValue();

        // 1. Check Rahul Dravid
        if (matchesCriteria("Batting", "20+ Years", 4.9, reqSpec, reqExp, reqRat)) {
            coachGrid.getChildren().add(buildCoachCard("Rahul Dravid", "Batting Specialist", "20+ Years", "4.9", true, "#10b981", "R"));
        }
        
        // 2. Check Zaheer Khan
        if (matchesCriteria("Bowling", "15+ Years", 4.8, reqSpec, reqExp, reqRat)) {
            coachGrid.getChildren().add(buildCoachCard("Zaheer Khan", "Pace Bowling", "15+ Years", "4.8", true, "#10b981", "Z"));
        }
        
        // 3. Check Sandeep Patil
        if (matchesCriteria("High Performance", "30+ Years", 5.0, reqSpec, reqExp, reqRat)) {
            coachGrid.getChildren().add(buildCoachCard("Sandeep Patil", "High Performance", "30+ Years", "5.0", false, "#f59e0b", "S"));
        }
        
        // 4. Check Jonty Rhodes
        if (matchesCriteria("Fielding", "15+ Years", 4.9, reqSpec, reqExp, reqRat)) {
            coachGrid.getChildren().add(buildCoachCard("Jonty Rhodes", "Fielding Specialist", "15+ Years", "4.9", true, "#10b981", "J"));
        }
        
        // 5. Check Lasith Malinga
        if (matchesCriteria("Bowling", "10+ Years", 4.6, reqSpec, reqExp, reqRat)) {
            coachGrid.getChildren().add(buildCoachCard("Lasith Malinga", "Pace Bowling", "10+ Years", "4.6", false, "#f59e0b", "L"));
        }

        // Show message if no coaches found
        if (coachGrid.getChildren().isEmpty()) {
            Label noRes = new Label("No coaches found matching these exact filters. Try clicking 'Clear All'.");
            noRes.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-padding: 20;");
            coachGrid.getChildren().add(noRes);
        }
    }

    private boolean matchesCriteria(String coachRole, String coachExp, double coachRat, String reqSpec, String reqExp, String reqRat) {
        // Check Specialization
        boolean specMatch = reqSpec.equals("All Roles") || coachRole.contains(reqSpec);
        
        // Check Experience (Simple logic for the UI prototype)
        boolean expMatch = reqExp.equals("All Experience") || coachExp.equals(reqExp) 
                || (reqExp.equals("5+ Years") && true) 
                || (reqExp.equals("10+ Years") && (coachExp.contains("10") || coachExp.contains("15") || coachExp.contains("20") || coachExp.contains("30")))
                || (reqExp.equals("15+ Years") && (coachExp.contains("15") || coachExp.contains("20") || coachExp.contains("30")));

        // Check Rating
        boolean ratMatch = reqRat.equals("All Ratings") 
                || (reqRat.equals("5.0") && coachRat >= 5.0)
                || (reqRat.equals("4.8+") && coachRat >= 4.8)
                || (reqRat.equals("4.5+") && coachRat >= 4.5);

        return specMatch && expMatch && ratMatch;
    }

    private VBox buildCoachCard(String name, String role, String exp, String rating, boolean isVerified, String badgeColor, String initial) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefWidth(220);
        card.setAlignment(Pos.TOP_CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.03), 10, 0, 0, 2);");

        HBox topRow = new HBox();
        if (isVerified) {
            Label badge = new Label("\u2714 VERIFIED");
            badge.setStyle("-fx-background-color: #dcfce3; -fx-text-fill: #166534; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 6;");
            topRow.getChildren().add(badge);
        } else {
            Label badge = new Label("\u2605 FEATURED");
            badge.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #b45309; -fx-font-size: 10px; -fx-font-weight: bold; -fx-padding: 4 8; -fx-background-radius: 6;");
            topRow.getChildren().add(badge);
        }
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label rLbl = new Label("\u2605 " + rating);
        rLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: #0f172a;");
        topRow.getChildren().addAll(sp, rLbl);

        Label avatar = new Label(initial);
        avatar.setPrefSize(80, 80);
        avatar.setAlignment(Pos.CENTER);
        avatar.setStyle("-fx-background-color: #1e293b; -fx-text-fill: white; -fx-background-radius: 40; -fx-font-weight: bold; -fx-font-size: 32px;");

        VBox textStack = new VBox(3);
        textStack.setAlignment(Pos.CENTER);
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #0f172a;");
        Label roleLbl = new Label(role);
        roleLbl.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");
        Label expLbl = new Label("Exp: " + exp);
        expLbl.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 11px;");
        textStack.getChildren().addAll(nameLbl, roleLbl, expLbl);

        Button viewBtn = new Button("View Profile / Select");
        viewBtn.setCursor(Cursor.HAND);
        viewBtn.setMaxWidth(Double.MAX_VALUE);
        viewBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #0f172a; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 8;");
        
        // This triggers the switch over to My_Coach_View via the Dashboard
        viewBtn.setOnAction(e -> {
            if (onCoachSelected != null) {
                onCoachSelected.run();
            }
        });

        card.getChildren().addAll(topRow, avatar, textStack, viewBtn);
        return card;
    }

    private VBox buildAiMatcherWidget() {
        VBox widget = new VBox(15);
        widget.setPadding(new Insets(25));
        widget.setStyle("-fx-background-color: #0f172a; -fx-background-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.1), 10, 0, 0, 4);");

        HBox header = new HBox(5);
        Label icon = new Label("\u2728");
        Label title = new Label("ATHLIX AI");
        title.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold; -fx-font-size: 12px; -fx-letter-spacing: 1px;");
        header.getChildren().addAll(icon, title);

        Label subtitle = new Label("Coach Matcher");
        subtitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");

        Label desc = new Label("Based on your recent matches, we identified a 15% dip in Power Hitting against off-spin.");
        desc.setWrapText(true);
        desc.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 13px; -fx-line-spacing: 4px;");

        Button focusBtn = new Button("Apply Suggested Focus");
        focusBtn.setMaxWidth(Double.MAX_VALUE);
        focusBtn.setStyle("-fx-background-color: #1e293b; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 10; -fx-border-color: #334155; -fx-border-radius: 8;");
        focusBtn.setCursor(Cursor.HAND);
        
        // Clicking this does the exact same thing as the top AI button
        focusBtn.setOnAction(e -> aiBtn.fire());

        widget.getChildren().addAll(header, subtitle, desc, focusBtn);
        return widget;
    }

    private VBox buildAvailabilityWidget() {
        VBox widget = new VBox(15);
        widget.setPadding(new Insets(20));
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label title = new Label("TODAY'S AVAILABILITY");
        title.setStyle("-fx-text-fill: #94a3b8; -fx-font-weight: bold; -fx-font-size: 11px; -fx-letter-spacing: 1px;");

        widget.getChildren().addAll(
            title,
            createTimeSlot("09:00 - 11:00", "12 Slots", "#10b981"),
            createTimeSlot("14:00 - 16:00", "Full", "#ef4444"),
            createTimeSlot("17:00 - 19:00", "3 Slots", "#f59e0b")
        );
        return widget;
    }

    private HBox createTimeSlot(String time, String status, String color) {
        HBox row = new HBox();
        Label t = new Label(time);
        t.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569; -fx-font-weight: bold;");
        Region sp = new Region(); HBox.setHgrow(sp, Priority.ALWAYS);
        Label s = new Label(status);
        s.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + color + ";");
        row.getChildren().addAll(t, sp, s);
        return row;
    }

    private HBox buildWeatherWidget() {
        HBox widget = new HBox(15);
        widget.setPadding(new Insets(20));
        widget.setAlignment(Pos.CENTER_LEFT);
        widget.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12;");

        Label icon = new Label("\u2600");
        icon.setStyle("-fx-font-size: 32px; -fx-text-fill: #10b981;");

        VBox text = new VBox(2);
        Label temp = new Label("28\u00b0C");
        temp.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #0f172a;");
        Label desc = new Label("Perfect for outdoor play");
        desc.setStyle("-fx-text-fill: #64748b; -fx-font-size: 11px;");
        text.getChildren().addAll(temp, desc);

        widget.getChildren().addAll(icon, text);
        return widget;
    }
}