package com.athlix.view;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.util.function.Consumer;

public class CurrentTournamentTeams {

    private Consumer<String> onViewSquadAction;

    // Added constructor to accept the action
    public CurrentTournamentTeams(Consumer<String> onViewSquadAction) {
        this.onViewSquadAction = onViewSquadAction;
    }

    public Node getView() {
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20, 0, 20, 0));

        Label sectionTitle = new Label("🛡️ Participating Teams");
        sectionTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");

        FlowPane cardsContainer = new FlowPane();
        cardsContainer.setHgap(20);
        cardsContainer.setVgap(20);
        cardsContainer.setAlignment(Pos.TOP_LEFT);

        cardsContainer.getChildren().addAll(
            createTeamCard("Chennai Super Kings", "CSK", "#eab308", "Ruturaj Gaikwad", "24 Players"),
            createTeamCard("Mumbai Indians", "MI", "#3b82f6", "Hardik Pandya", "25 Players"),
            createTeamCard("Kolkata Knight Riders", "KKR", "#8b5cf6", "Shreyas Iyer", "22 Players"),
            createTeamCard("Royal Challengers", "RCB", "#ef4444", "Faf du Plessis", "25 Players"),
            createTeamCard("Sunrisers Hyderabad", "SRH", "#f97316", "Pat Cummins", "23 Players"),
            createTeamCard("Delhi Capitals", "DC", "#0284c7", "Rishabh Pant", "24 Players"),
            createTeamCard("Rajasthan Royals", "RR", "#db2777", "Sanju Samson", "22 Players"),
            createTeamCard("Punjab Kings", "PBKS", "#dc2626", "Shikhar Dhawan", "24 Players")
        );

        mainContainer.getChildren().addAll(sectionTitle, cardsContainer);
        return mainContainer;
    }

    private VBox createTeamCard(String teamName, String shortName, String colorHex, String captain, String squadSize) {
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25, 20, 25, 20));
        card.setPrefWidth(240); 
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");

        StackPane logoPane = new StackPane();
        Circle logoCircle = new Circle(32, Color.web(colorHex));
        Label logoLbl = new Label(shortName);
        logoLbl.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");
        logoPane.getChildren().addAll(logoCircle, logoLbl);

        Label nameLbl = new Label(teamName);
        nameLbl.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0f172a;");
        nameLbl.setWrapText(true);
        nameLbl.setAlignment(Pos.CENTER);

        Label captLbl = new Label("👑 " + captain);
        captLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        Label squadLbl = new Label("👥 " + squadSize);
        squadLbl.setStyle("-fx-font-size: 13px; -fx-text-fill: #64748b;");

        Button viewSquadBtn = new Button("View Squad");
        viewSquadBtn.setMaxWidth(Double.MAX_VALUE);
        viewSquadBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;");
        
        viewSquadBtn.setOnMouseEntered(e -> viewSquadBtn.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));
        viewSquadBtn.setOnMouseExited(e -> viewSquadBtn.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #10b981; -fx-font-weight: bold; -fx-background-radius: 6; -fx-padding: 8 16; -fx-cursor: hand;"));

        // WIRE UP THE BUTTON CLICK
        viewSquadBtn.setOnAction(e -> {
            if (onViewSquadAction != null) {
                onViewSquadAction.accept(teamName); // Pass the team name to the view
            }
        });

        card.getChildren().addAll(logoPane, nameLbl, captLbl, squadLbl, viewSquadBtn);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(200), card);
        scaleIn.setToX(1.03); scaleIn.setToY(1.03);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(200), card);
        scaleOut.setToX(1.0); scaleOut.setToY(1.0);

        card.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #10b981; -fx-border-radius: 12; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.08), 10, 0, 0, 5);");
        });
        card.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e2e8f0; -fx-border-radius: 12;");
        });

        return card;
    }
}