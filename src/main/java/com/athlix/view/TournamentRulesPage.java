package com.athlix.view;

import javafx.geometry.Insets;
//import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TournamentRulesPage {

    public Node getView() {
        VBox rulesBox = new VBox(25);
        rulesBox.setPadding(new Insets(30));
        rulesBox.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        // Header Title with attractive styling matching the theme
        Label title = new Label("Tournament Rules & Guidelines 📜");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        VBox rulesList = new VBox(20);
        rulesList.getChildren().addAll(
            createRuleItem("1. Match Format & Overs", "All matches will be played using a standard tennis ball. Each innings consists of 20 overs per side, with strict over-rate penalties applied for delays."),
            createRuleItem("2. Umpire's Decision", "The decision of the on-field umpires is final and binding. Any form of dissent, argument, or aggressive behavior will lead to an immediate warning or disqualification."),
            createRuleItem("3. Team Reporting & Squad", "Teams must report to the venue at least 30 minutes before the scheduled start time. A maximum of 16 squad members are registered, with 11 playing on the field."),
            createRuleItem("4. Player Eligibility", "A player can only represent a single team throughout the tournament. Dual-representation will result in immediate match forfeiture for the violating team."),
            createRuleItem("5. Equipment & Attire", "Players must wear appropriate sports shoes and team jerseys. Only tournament-authorized tennis balls provided by the committee will be utilized during matches.")
        );

        rulesBox.getChildren().addAll(title, rulesList);

        ScrollPane scrollPane = new ScrollPane(rulesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }

    private VBox createRuleItem(String title, String desc) {
        VBox itemBox = new VBox(8);
        itemBox.setPadding(new Insets(18));
        itemBox.setStyle("-fx-background-color: #f8fafc; -fx-background-radius: 8; -fx-border-color: #e2e8f0; -fx-border-radius: 8;");
        
        Label lblTitle = new Label(title);
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #10b981;"); // Emerald green theme accent
        
        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569; -fx-line-spacing: 4px;");
        lblDesc.setWrapText(true);
        
        itemBox.getChildren().addAll(lblTitle, lblDesc);
        return itemBox;
    }
}