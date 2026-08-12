
package com.athlix.view.Scorecard;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ScorecardCreateButton {

    // Custom functional interface to handle 4 parameters (Team1, Team2, Overs, TossResult)
    public interface MatchSubmitListener {
        void onSubmit(String team1, String team2, String overs, String tossResult);
    }

    private Runnable onBackAction;
    private MatchSubmitListener onSubmitMatch;
    private String currentTossResult = "Match has just begun!"; // Default text

    public ScorecardCreateButton(Runnable onBackAction, MatchSubmitListener onSubmitMatch) {
        this.onBackAction = onBackAction;
        this.onSubmitMatch = onSubmitMatch;
    }

    public Node getView() {
        VBox mainContainer = new VBox(25);
        mainContainer.setPadding(new Insets(30, 30, 80, 30));
        mainContainer.setStyle("-fx-background-color: #f8fafc;");

        // --- 1. HEADER SECTION ---
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("❮  Back to Dashboard");
        backBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #3b82f6; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 8 18; -fx-background-radius: 20; -fx-border-color: #e2e8f0; -fx-border-radius: 20; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            if (onBackAction != null) onBackAction.run();
        });

        VBox titleBox = new VBox(5);
        Label pageTitle = new Label("⚙️ Create New Match");
        pageTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        Label subtitle = new Label("Enter the details of the competing teams and match length.");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");
        titleBox.getChildren().addAll(pageTitle, subtitle);

        header.getChildren().addAll(backBtn, titleBox);

        // --- 2. FORM CONTAINER ---
        VBox formCard = new VBox(25);
        formCard.setPadding(new Insets(30));
        formCard.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.03), 10, 0, 0, 4);");
        formCard.setMaxWidth(700);

        // Team 1 Input
        VBox team1Box = new VBox(8);
        Label team1Lbl = new Label("Team 1 Name:");
        team1Lbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextField team1Field = new TextField();
        team1Field.setPromptText("Enter Team 1 Name...");
        team1Field.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-font-size: 14px;");
        team1Box.getChildren().addAll(team1Lbl, team1Field);

        // VS Separator
        HBox vsBox = new HBox();
        vsBox.setAlignment(Pos.CENTER);
        Label vsLbl = new Label("VS");
        vsLbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #94a3b8;");
        vsBox.getChildren().add(vsLbl);

        // Team 2 Input
        VBox team2Box = new VBox(8);
        Label team2Lbl = new Label("Team 2 Name :");
        team2Lbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextField team2Field = new TextField();
        team2Field.setPromptText("Enter Team 2 Name...");
        team2Field.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-font-size: 14px;");
        team2Box.getChildren().addAll(team2Lbl, team2Field);

        // Match Overs Input
        VBox oversBox = new VBox(8);
        Label oversLbl = new Label("Total Overs:");
        oversLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #475569;");
        TextField oversField = new TextField();
        oversField.setPromptText("e.g. 20");
        oversField.setStyle("-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-font-size: 14px;");
        oversBox.getChildren().addAll(oversLbl, oversField);

        // --- 3. AI TOSS PREDICTOR ---
        VBox tossBox = new VBox(12);
        tossBox.setPadding(new Insets(20));
        tossBox.setStyle("-fx-background-color: #f0fdf4; -fx-border-color: #bbf7d0; -fx-border-radius: 8; -fx-background-radius: 8;");
        
        Label tossHeading = new Label("🪙 AI Toss Predictor");
        tossHeading.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #166534;");

        HBox tossControls = new HBox(15);
        tossControls.setAlignment(Pos.CENTER_LEFT);
        
        Label callLbl = new Label("Team Call:");
        callLbl.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #15803d;");
        
        ComboBox<String> callCombo = new ComboBox<>();
        callCombo.getItems().addAll("Heads", "Tails");
        callCombo.getSelectionModel().selectFirst();
        callCombo.setStyle("-fx-background-color: white; -fx-border-color: #bbf7d0; -fx-border-radius: 6; -fx-padding: 4;");

        Button predictBtn = new Button("Predict Toss ✨");
        predictBtn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-background-radius: 6; -fx-cursor: hand;");
        
        Label resultLbl = new Label("Waiting for prediction...");
        resultLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #166534; -fx-font-style: italic;");
        
        // Toss Prediction Logic
        predictBtn.setOnAction(e -> {
            String t1 = team1Field.getText().trim().isEmpty() ? "Team 1" : team1Field.getText().trim();
            String t2 = team2Field.getText().trim().isEmpty() ? "Team 2" : team2Field.getText().trim();
            String t1Call = callCombo.getValue();
            
            // AI determines result
            boolean isHeads = Math.random() < 0.5;
            String actualResult = isHeads ? "Heads" : "Tails";
            
            String winner;
            String decision;
            
            if (t1Call.equals(actualResult)) {
                winner = t1;
                decision = "bat"; // Force team 1 to bat first based on UI design
            } else {
                winner = t2;
                decision = "bowl"; // Force team 2 to bowl first based on UI design
            }
            
            currentTossResult = "🪙 " + actualResult + "! " + winner + " won the toss and chose to " + decision + " first.";
            resultLbl.setText(currentTossResult);
            resultLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #15803d; -fx-font-weight: bold;");
        });

        tossControls.getChildren().addAll(callLbl, callCombo, predictBtn);
        tossBox.getChildren().addAll(tossHeading, tossControls, resultLbl);

        // --- 4. ACTIONS ---
        HBox actionBox = new HBox(15);
        actionBox.setAlignment(Pos.CENTER_RIGHT);
        actionBox.setPadding(new Insets(20, 0, 0, 0));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #475569; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> { if (onBackAction != null) onBackAction.run(); });

        Button submitBtn = new Button("Submit & Start Match");
        submitBtn.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10 24; -fx-background-radius: 8; -fx-cursor: hand; -fx-effect: dropshadow(three-pass-box, rgba(59,130,246,0.3), 6, 0, 0, 2);");
        
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), submitBtn);
        scaleIn.setToX(1.03); scaleIn.setToY(1.03);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), submitBtn);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);
        
        submitBtn.setOnMouseEntered(e -> scaleIn.playFromStart());
        submitBtn.setOnMouseExited(e -> scaleOut.playFromStart());

        // Dynamic Submit Action
        submitBtn.setOnAction(e -> {
            String t1 = team1Field.getText().trim();
            String t2 = team2Field.getText().trim();
            String overs = oversField.getText().trim();
            
            // Reset styles
            String defaultStyle = "-fx-background-color: #f8fafc; -fx-border-color: #cbd5e1; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-font-size: 14px;";
            String errorStyle = "-fx-background-color: #fef2f2; -fx-border-color: #ef4444; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 10; -fx-font-size: 14px;";
            
            team1Field.setStyle(defaultStyle);
            team2Field.setStyle(defaultStyle);
            oversField.setStyle(defaultStyle);

            if (!t1.isEmpty() && !t2.isEmpty() && !overs.isEmpty()) {
                if (onSubmitMatch != null) {
                    // Send Team1, Team2, Overs, and the Toss Result back
                    onSubmitMatch.onSubmit(t1, t2, overs, currentTossResult); 
                }
                if (onBackAction != null) {
                    onBackAction.run();
                }
            } else {
                if (t1.isEmpty()) team1Field.setStyle(errorStyle);
                if (t2.isEmpty()) team2Field.setStyle(errorStyle);
                if (overs.isEmpty()) oversField.setStyle(errorStyle);
            }
        });

        actionBox.getChildren().addAll(cancelBtn, submitBtn);

        // Assemble Form
        formCard.getChildren().addAll(team1Box, vsBox, team2Box, oversBox, tossBox, actionBox);
        mainContainer.getChildren().addAll(header, formCard);

        ScrollPane scrollPane = new ScrollPane(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f8fafc;");
        scrollPane.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        return scrollPane;
    }
}