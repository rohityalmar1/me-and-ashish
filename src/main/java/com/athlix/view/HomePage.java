// package com.athlix.view;

// import javafx.animation.FadeTransition;
// import javafx.animation.Interpolator;
// import javafx.application.Application;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Cursor;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListCell;
// import javafx.scene.control.ListView;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;
// import javafx.util.Duration;

// public class HomePage extends Application {

//     public static Stage homePageStage;
//   //  private Scene homePageScene;
//     private BorderPane borderPane;
//     private VBox defaultContentBox;

//     @Override
//     public void start(Stage stage) throws Exception {
//         homePageStage = stage;
//         borderPane = new BorderPane();
//         borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #eef2ff, #f8fafc);");

//         // --- TOP BAR ---
//         HBox topBar = new HBox(24);
//         topBar.setPadding(new Insets(22, 28, 22, 28));
//         topBar.setAlignment(Pos.CENTER_LEFT);
//         topBar.setStyle("-fx-background-color: rgba(255,255,255,0.97); -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 18, 0, 0, 2);");

//         Label logoBadge = new Label("A");
//         logoBadge.setAlignment(Pos.CENTER);
//         logoBadge.setPrefSize(46, 46);
//         logoBadge.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(to bottom right, #6366f1, #2563eb); -fx-background-radius: 16px;");

//         Label title = new Label("Athlix");
//         title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");  

//         VBox logoBox = new VBox(2, title);
//         logoBox.setAlignment(Pos.CENTER_LEFT);

//         HBox brandBox = new HBox(12, logoBadge, logoBox);
//         brandBox.setAlignment(Pos.CENTER_LEFT);

//         TextField searchField = new TextField();
//         searchField.setPromptText("Search athletes, events, drills...");
//         searchField.setPrefWidth(420);
//         searchField.setStyle("-fx-background-color: white; -fx-background-radius: 24; -fx-border-radius: 24; -fx-border-color: #d1d5db; -fx-border-width: 1; -fx-padding: 12 16 12 16; -fx-prompt-text-fill: #9ca3af; -fx-font-size: 14px;");

//         Button searchButton = new Button("Search");
//         searchButton.setPrefSize(120, 44);
//         searchButton.setCursor(Cursor.HAND);
//         searchButton.setStyle("-fx-background-color: linear-gradient(to right, #4f46e5, #2563eb); -fx-text-fill: white; -fx-background-radius: 22; -fx-border-radius: 22; -fx-font-weight: 600; -fx-font-size: 14px;");

//         Button notificationButton = new Button("🔔");
//         notificationButton.setCursor(Cursor.HAND);
//         notificationButton.setStyle("-fx-background-color: #eef2ff; -fx-background-radius: 20; -fx-text-fill: #1d4ed8; -fx-font-size: 14px; -fx-padding: 10 14 10 14;");

//         Button profileButton = new Button("P");
//         profileButton.setCursor(Cursor.HAND);
//         profileButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px; -fx-padding: 10 14 10 14; -fx-font-weight: bold;");

//         HBox searchBox = new HBox(10, searchField, searchButton);
//         searchBox.setAlignment(Pos.CENTER);
//         searchBox.setMaxWidth(560);
//         HBox.setHgrow(searchField, Priority.ALWAYS);

//         Region leftSpacer = new Region();
//         Region rightSpacer = new Region();
//         HBox.setHgrow(leftSpacer, Priority.ALWAYS);
//         HBox.setHgrow(rightSpacer, Priority.ALWAYS);

//         HBox rightActions = new HBox(12, notificationButton, profileButton);
//         rightActions.setAlignment(Pos.CENTER_RIGHT);

//         topBar.getChildren().addAll(brandBox, leftSpacer, searchBox, rightSpacer, rightActions);
//         borderPane.setTop(topBar);

//         // --- SIDE BAR ---
//         VBox sideBar = new VBox(10);
//         sideBar.setPadding(new Insets(18));
//         sideBar.setPrefWidth(240);
//         sideBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ead8d8; -fx-border-width: 0 1 0 0;");

//         Label menuLabel = new Label("Main Menu");
//         menuLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #6b6b6b;");

//         ListView<String> listView = new ListView<>();
//         listView.getItems().addAll("Dashboard", "Community", "Tournament", "Scorecard", "Academy", "Tranning", "Leaderboard", "Notification", "Message", "Profile", "Setting", "Logout");
//         listView.setFixedCellSize(44);
//         listView.setPrefHeight(listView.getItems().size() * 44 + 16);
//         listView.setStyle("-fx-background-color: transparent; -fx-control-inner-background: #ffffff; -fx-padding: 4px; -fx-border-color: transparent;");

//         listView.setCellFactory(lv -> {
//             ListCell<String> cell = new ListCell<>() {
//                 @Override
//                 protected void updateItem(String item, boolean empty) {
//                     super.updateItem(item, empty);
//                     if (empty || item == null) {
//                         setText(null);
//                         setStyle("");
//                     } else {
//                         setText(item);
//                         if (isSelected()) {
//                             setStyle("-fx-padding: 10 14 10 14; -fx-background-color: linear-gradient(to right, #eff6ff, #dbeafe); -fx-background-radius: 14; -fx-text-fill: #1d4ed8; -fx-font-weight: bold; -fx-font-size: 15px;");
//                         } else {
//                             setStyle("-fx-padding: 10 14 10 14; -fx-background-color: transparent; -fx-text-fill: #1f2937; -fx-font-size: 15px;");
//                         }
//                     }
//                 }
//             };
//             cell.hoverProperty().addListener((obs, wasHovered, isHovered) -> {
//                 if (!cell.isEmpty() && !cell.isSelected()) {
//                     cell.setStyle(isHovered
//                             ? "-fx-padding: 10 14 10 14; -fx-background-color: rgba(59,130,246,0.08); -fx-background-radius: 14; -fx-text-fill: #1d4ed8; -fx-font-size: 15px;"
//                             : "-fx-padding: 10 14 10 14; -fx-background-color: transparent; -fx-text-fill: #1f2937; -fx-font-size: 15px;");
//                 }
//             });
//             return cell;
//         });

//         sideBar.getChildren().addAll(menuLabel, listView);
//         borderPane.setLeft(sideBar);

//         // --- DEFAULT CONTENT AREA ---
//         Label contentTitle = new Label("Dashboard Page");
//         contentTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");
//         Text contentText = new Text("Welcome to Athlix dashboard.");
//         contentText.setStyle("-fx-font-size: 16px; -fx-fill: #4b5563;");

//         defaultContentBox = new VBox(20);
//         defaultContentBox.setPadding(new Insets(40));
//         defaultContentBox.setAlignment(Pos.TOP_LEFT);
//         defaultContentBox.setStyle("-fx-background-color: #fbf8f8;");
//         defaultContentBox.getChildren().addAll(contentTitle, contentText);
//         borderPane.setCenter(defaultContentBox);

//         // Navigation logic for changing center view
//         listView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, selectedItem) -> {
//             if (selectedItem == null) return;

//             if (selectedItem.equals("Tournament")) {
//                 // Switch to Tournament Page layout dynamically without opening a new stage
//                 borderPane.setCenter(new TournamentPage().getView());
//             } else {
//                 // For all other pages, reuse default view and update text
//                 borderPane.setCenter(defaultContentBox);
//                 switch (selectedItem) {
//                     case "Dashboard" -> {
//                         contentTitle.setText("Dashboard Page");
//                         contentText.setText("Welcome to your dashboard overview.");
//                     }
//                     case "Community" -> {
//                         contentTitle.setText("Community Page");
//                         contentText.setText("Connect with other users and join discussions.");
//                     }
//                     default -> {
//                         contentTitle.setText(selectedItem + " Page");
//                         contentText.setText("You are viewing the " + selectedItem.toLowerCase() + " section.");
//                     }
//                 }
//                 animateContent(contentTitle, contentText);
//             }
//         });

//         listView.getSelectionModel().select("Dashboard");

//         Scene scene = new Scene(borderPane, 1500, 750);
//         stage.setScene(scene);
//         stage.setTitle("Athlix Core");
//         stage.show();
//     }

//     private void animateContent(Label title, Text body) {
//         FadeTransition fadeTitle = new FadeTransition(Duration.millis(420), title);
//         fadeTitle.setFromValue(0);
//         fadeTitle.setToValue(1);
//         fadeTitle.setInterpolator(Interpolator.EASE_OUT);

//         FadeTransition fadeBody = new FadeTransition(Duration.millis(520), body);
//         fadeBody.setFromValue(0);
//         fadeBody.setToValue(1);
//         fadeBody.setInterpolator(Interpolator.EASE_OUT);
//         fadeBody.setDelay(Duration.millis(120));

//         fadeTitle.play();
//         fadeBody.play();
//     }
// }

package com.athlix.view;

import com.athlix.view.Scorecard.ScorecardPage; // Added import for the new Scorecard Page
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePage extends Application {

    public static Stage homePageStage;
    private BorderPane borderPane;
    private VBox defaultContentBox;

    @Override
    public void start(Stage stage) throws Exception {
        homePageStage = stage;
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #eef2ff, #f8fafc);");

        // --- TOP BAR ---
        HBox topBar = new HBox(24);
        topBar.setPadding(new Insets(22, 28, 22, 28));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: rgba(255,255,255,0.97); -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.06), 18, 0, 0, 2);");

        Label logoBadge = new Label("A");
        logoBadge.setAlignment(Pos.CENTER);
        logoBadge.setPrefSize(46, 46);
        logoBadge.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: linear-gradient(to bottom right, #6366f1, #2563eb); -fx-background-radius: 16px;");

        Label title = new Label("Athlix");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #111827;");  

        VBox logoBox = new VBox(2, title);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        HBox brandBox = new HBox(12, logoBadge, logoBox);
        brandBox.setAlignment(Pos.CENTER_LEFT);

        TextField searchField = new TextField();
        searchField.setPromptText("Search athletes, events, drills...");
        searchField.setPrefWidth(420);
        searchField.setStyle("-fx-background-color: white; -fx-background-radius: 24; -fx-border-radius: 24; -fx-border-color: #d1d5db; -fx-border-width: 1; -fx-padding: 12 16 12 16; -fx-prompt-text-fill: #9ca3af; -fx-font-size: 14px;");

        Button searchButton = new Button("Search");
        searchButton.setPrefSize(120, 44);
        searchButton.setCursor(Cursor.HAND);
        searchButton.setStyle("-fx-background-color: linear-gradient(to right, #4f46e5, #2563eb); -fx-text-fill: white; -fx-background-radius: 22; -fx-border-radius: 22; -fx-font-weight: 600; -fx-font-size: 14px;");

        Button notificationButton = new Button("🔔");
        notificationButton.setCursor(Cursor.HAND);
        notificationButton.setStyle("-fx-background-color: #eef2ff; -fx-background-radius: 20; -fx-text-fill: #1d4ed8; -fx-font-size: 14px; -fx-padding: 10 14 10 14;");

        Button profileButton = new Button("P");
        profileButton.setCursor(Cursor.HAND);
        profileButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px; -fx-padding: 10 14 10 14; -fx-font-weight: bold;");

        HBox searchBox = new HBox(10, searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setMaxWidth(560);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        HBox rightActions = new HBox(12, notificationButton, profileButton);
        rightActions.setAlignment(Pos.CENTER_RIGHT);

        topBar.getChildren().addAll(brandBox, leftSpacer, searchBox, rightSpacer, rightActions);
        borderPane.setTop(topBar);

        // --- SIDE BAR ---
        VBox sideBar = new VBox(10);
        sideBar.setPadding(new Insets(18));
        sideBar.setPrefWidth(240);
        sideBar.setStyle("-fx-background-color: #ffffff; -fx-border-color: #ead8d8; -fx-border-width: 0 1 0 0;");

        Label menuLabel = new Label("Main Menu");
        menuLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #6b6b6b;");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Dashboard", "Community", "Tournament", "Scorecard", "Academy", "Tranning", "Leaderboard", "Notification", "Message", "Profile", "Setting", "Logout");
        listView.setFixedCellSize(44);
        listView.setPrefHeight(listView.getItems().size() * 44 + 16);
        listView.setStyle("-fx-background-color: transparent; -fx-control-inner-background: #ffffff; -fx-padding: 4px; -fx-border-color: transparent;");

        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        if (isSelected()) {
                            setStyle("-fx-padding: 10 14 10 14; -fx-background-color: linear-gradient(to right, #eff6ff, #dbeafe); -fx-background-radius: 14; -fx-text-fill: #1d4ed8; -fx-font-weight: bold; -fx-font-size: 15px;");
                        } else {
                            setStyle("-fx-padding: 10 14 10 14; -fx-background-color: transparent; -fx-text-fill: #1f2937; -fx-font-size: 15px;");
                        }
                    }
                }
            };
            cell.hoverProperty().addListener((obs, wasHovered, isHovered) -> {
                if (!cell.isEmpty() && !cell.isSelected()) {
                    cell.setStyle(isHovered
                            ? "-fx-padding: 10 14 10 14; -fx-background-color: rgba(59,130,246,0.08); -fx-background-radius: 14; -fx-text-fill: #1d4ed8; -fx-font-size: 15px;"
                            : "-fx-padding: 10 14 10 14; -fx-background-color: transparent; -fx-text-fill: #1f2937; -fx-font-size: 15px;");
                }
            });
            return cell;
        });

        sideBar.getChildren().addAll(menuLabel, listView);
        borderPane.setLeft(sideBar);

        // --- DEFAULT CONTENT AREA ---
        Label contentTitle = new Label("Dashboard Page");
        contentTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Text contentText = new Text("Welcome to Athlix dashboard.");
        contentText.setStyle("-fx-font-size: 16px; -fx-fill: #4b5563;");

        defaultContentBox = new VBox(20);
        defaultContentBox.setPadding(new Insets(40));
        defaultContentBox.setAlignment(Pos.TOP_LEFT);
        defaultContentBox.setStyle("-fx-background-color: #fbf8f8;");
        defaultContentBox.getChildren().addAll(contentTitle, contentText);
        borderPane.setCenter(defaultContentBox);

        // Navigation logic for changing center view
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, selectedItem) -> {
            if (selectedItem == null) return;

            if (selectedItem.equals("Tournament")) {
                borderPane.setCenter(new TournamentPage().getView());
            } else if (selectedItem.equals("Scorecard")) {
                // ✅ INTEGRATED ROUTING FOR SCORECARD
                borderPane.setCenter(new ScorecardPage().getView());
            } else {
                // For all other pages, reuse default view and update text
                borderPane.setCenter(defaultContentBox);
                switch (selectedItem) {
                    case "Dashboard" -> {
                        contentTitle.setText("Dashboard Page");
                        contentText.setText("Welcome to your dashboard overview.");
                    }
                    case "Community" -> {
                        contentTitle.setText("Community Page");
                        contentText.setText("Connect with other users and join discussions.");
                    }
                    default -> {
                        contentTitle.setText(selectedItem + " Page");
                        contentText.setText("You are viewing the " + selectedItem.toLowerCase() + " section.");
                    }
                }
                animateContent(contentTitle, contentText);
            }
        });

        listView.getSelectionModel().select("Dashboard");

        Scene scene = new Scene(borderPane, 1500, 750);
        stage.setScene(scene);
        stage.setTitle("Athlix Core");
        stage.show();
    }

    private void animateContent(Label title, Text body) {
        FadeTransition fadeTitle = new FadeTransition(Duration.millis(420), title);
        fadeTitle.setFromValue(0);
        fadeTitle.setToValue(1);
        fadeTitle.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fadeBody = new FadeTransition(Duration.millis(520), body);
        fadeBody.setFromValue(0);
        fadeBody.setToValue(1);
        fadeBody.setInterpolator(Interpolator.EASE_OUT);
        fadeBody.setDelay(Duration.millis(120));

        fadeTitle.play();
        fadeBody.play();
    }
}