// package com.athlixcore.view.player.community;

// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Cursor;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.layout.BorderPane;
// import javafx.scene.layout.HBox;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.Region;
// import javafx.scene.layout.StackPane;
// import javafx.scene.layout.VBox;
// import javafx.scene.text.Text;

// public class Community_Following_Page extends VBox {

//     private BorderPane mainLayout;
//     private VBox postList;

//     public Community_Following_Page(BorderPane mainLayout) {
//         this.mainLayout = mainLayout;
        
//         this.setSpacing(10);
//         this.setPadding(new Insets(20, 40, 20, 40));
//         this.setStyle("-fx-background-color: #fbf8f8;");

//         HBox topMenu = buildTopMenu();
//         HBox mainSplit = new HBox(30);
//         mainSplit.setPadding(new Insets(20, 0, 0, 0));
//         VBox.setVgrow(mainSplit, Priority.ALWAYS);

//         VBox feedColumn = buildFeedColumn();
//         HBox.setHgrow(feedColumn, Priority.ALWAYS);

//         VBox rightSidebar = buildRightSidebar();

//         mainSplit.getChildren().addAll(feedColumn, rightSidebar);
//         this.getChildren().addAll(topMenu, mainSplit);
//     }

//     private HBox buildTopMenu() {
//         HBox menu = new HBox(20);
//         menu.setAlignment(Pos.CENTER_LEFT);
//         menu.setPadding(new Insets(0, 0, 10, 0));
//         menu.setStyle("-fx-border-color: #d1d5db; -fx-border-width: 0 0 1 0;");

//         Button homeBtn = createMenuTab("Home", false);
//         Button followingBtn = createMenuTab("Following", true);
//         Button groupsBtn = createMenuTab("Groups", false);
//         Button eventsBtn = createMenuTab("Events", false);

//         homeBtn.setOnAction(e -> mainLayout.setCenter(new Community_Dashboard(mainLayout)));
//         groupsBtn.setOnAction(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));
//         eventsBtn.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));

//         menu.getChildren().addAll(homeBtn, followingBtn, groupsBtn, eventsBtn);
//         return menu;
//     }

//     private Button createMenuTab(String text, boolean isActive) {
//         Button btn = new Button(text);
//         btn.setCursor(Cursor.HAND);
//         if (isActive) {
//             btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 16px; -fx-border-color: #2563eb; -fx-border-width: 0 0 3 0; -fx-padding: 5 10 5 10;");
//         } else {
//             btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10;");
//             btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f3f4f6; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10; -fx-background-radius: 8;"));
//             btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #6b7280; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5 10 5 10;"));
//         }
//         return btn;
//     }

//     private VBox buildFeedColumn() {
//         VBox feed = new VBox(20);
//         feed.setPrefWidth(650);

//         HBox storiesBar = new HBox(15);
//         storiesBar.setPadding(new Insets(15));
//         storiesBar.setAlignment(Pos.CENTER_LEFT);
//         storiesBar.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);");
        
//         storiesBar.getChildren().addAll(
//             buildStoryBubble("Virat K.", "#2563eb"),
//             buildStoryBubble("Rohit S.", "#10b981"),
//             buildStoryBubble("Jasprit B.", "#f59e0b"),
//             buildStoryBubble("Coach Sandeep", "#ef4444")
//         );

//         postList = new VBox(15);
//         postList.getChildren().addAll(
//             buildFeedPost("Jasprit Bumrah", "Morning bowling drills session at the academy! \ud83c\udfcf\u26a1"),
//             buildFeedPost("Coach Sandeep Patil", "Great intensity shown by the team in today's net practice.")
//         );
        
//         ScrollPane scroll = new ScrollPane(postList);
//         scroll.setFitToWidth(true);
//         scroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
//         scroll.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");
//         VBox.setVgrow(scroll, Priority.ALWAYS);

//         feed.getChildren().addAll(storiesBar, scroll);
//         return feed;
//     }

//     private VBox buildStoryBubble(String name, String colorHex) {
//         VBox story = new VBox(6);
//         story.setAlignment(Pos.CENTER);
//         story.setCursor(Cursor.HAND);
        
//         StackPane circle = new StackPane();
//         circle.setPrefSize(55, 55);
//         circle.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 27.5;");
        
//         Label initial = new Label(name.substring(0, 1));
//         initial.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
//         circle.getChildren().add(initial);

//         Label nameLbl = new Label(name);
//         nameLbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #374151; -fx-font-weight: bold;");

//         story.getChildren().addAll(circle, nameLbl);
//         return story;
//     }

//     private VBox buildFeedPost(String user, String content) {
//         VBox post = new VBox(15);
//         post.setPadding(new Insets(20));
//         post.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);");

//         Label author = new Label(user);
//         author.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");
        
//         Text body = new Text(content);
//         body.setStyle("-fx-font-size: 14px; -fx-fill: #374151;");
//         body.setWrappingWidth(590);

//         post.getChildren().addAll(author, body);

//         HBox actions = new HBox(20);
//         Button likeBtn = new Button("\u2661 Like");
//         likeBtn.setCursor(Cursor.HAND);
//         String likeDefault = "-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold;";
//         String likeActive = "-fx-background-color: #dbeafe; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-background-radius: 6;";
//         likeBtn.setStyle(likeDefault);
        
//         likeBtn.setOnAction(e -> {
//             if (likeBtn.getText().equals("\u2661 Like")) {
//                 likeBtn.setText("\u2764 Liked");
//                 likeBtn.setStyle(likeActive);
//             } else {
//                 likeBtn.setText("\u2661 Like");
//                 likeBtn.setStyle(likeDefault);
//             }
//         });

//         Button commentBtn = new Button("\ud83d\udcac Comment");
//         commentBtn.setCursor(Cursor.HAND);
//         commentBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold;");
        
//         Button shareBtn = new Button("\u27A1 Share");
//         shareBtn.setCursor(Cursor.HAND);
//         shareBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #4b5563; -fx-font-weight: bold;");

//         actions.getChildren().addAll(likeBtn, commentBtn, shareBtn);
//         post.getChildren().add(actions);
//         return post;
//     }

//     private VBox buildRightSidebar() {
//         VBox sidebar = new VBox(20);
//         sidebar.setPrefWidth(300);
//         sidebar.setMinWidth(300);

//         String widgetStyle = "-fx-background-color: white; -fx-background-radius: 12; -fx-border-color: #e5e7eb; -fx-border-radius: 12; -fx-border-width: 1; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.04), 8, 0, 0, 2);";

//         VBox suggestionsWidget = new VBox(15);
//         suggestionsWidget.setPadding(new Insets(20));
//         suggestionsWidget.setStyle(widgetStyle);
        
//         Label suggTitle = new Label("Suggested For You");
//         suggTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #111827;");
//         suggestionsWidget.getChildren().add(suggTitle);

//         suggestionsWidget.getChildren().addAll(
//             buildSuggestedUserRow("MS Dhoni", "Wicketkeeper \u2022 Icon"),
//             buildSuggestedUserRow("Ravindra Jadeja", "All-Rounder")
//         );

//         sidebar.getChildren().add(suggestionsWidget);
//         return sidebar;
//     }

//     private HBox buildSuggestedUserRow(String name, String role) {
//         HBox row = new HBox(10);
//         row.setAlignment(Pos.CENTER_LEFT);

//         Label avatar = new Label(name.substring(0, 1));
//         avatar.setPrefSize(38, 38);
//         avatar.setAlignment(Pos.CENTER);
//         avatar.setStyle("-fx-background-color: #eef2ff; -fx-text-fill: #4f46e5; -fx-background-radius: 19; -fx-font-weight: bold;");

//         VBox info = new VBox(2);
//         Label nameLbl = new Label(name);
//         nameLbl.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: #111827;");
//         Label roleLbl = new Label(role);
//         roleLbl.setStyle("-fx-text-fill: #6b7280; -fx-font-size: 12px;");
//         info.getChildren().addAll(nameLbl, roleLbl);

//         Region spacer = new Region();
//         HBox.setHgrow(spacer, Priority.ALWAYS);

//         Button followBtn = new Button("Follow");
//         followBtn.setCursor(Cursor.HAND);
//         String followDefault = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 5 14 5 14; -fx-font-size: 12px;";
//         String followActive = "-fx-background-color: #e5e7eb; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-background-radius: 15; -fx-padding: 5 14 5 10; -fx-font-size: 12px;";
//         followBtn.setStyle(followDefault);

//         followBtn.setOnAction(e -> {
//             if (followBtn.getText().equals("Follow")) {
//                 followBtn.setText("Following");
//                 followBtn.setStyle(followActive);
//             } else {
//                 followBtn.setText("Follow");
//                 followBtn.setStyle(followDefault);
//             }
//         });

//         row.getChildren().addAll(avatar, info, spacer, followBtn);
//         return row;
//     }
// }


package com.athlixcore.view.player.community;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Optional;

public class Community_Following_Page extends VBox {

    private BorderPane mainLayout;

    public Community_Following_Page(BorderPane mainLayout) {

        this.mainLayout = mainLayout;

        setPadding(new Insets(0));
        setSpacing(0);

        setStyle(
                "-fx-background-color: #f6f8fc;"
        );

        VBox page = new VBox(0);

        HBox topNavigation = buildTopNavigation();

        HBox mainContent = new HBox(25);

        mainContent.setPadding(
                new Insets(25, 35, 30, 35)
        );

        VBox centerContent = buildCenterContent();

        HBox.setHgrow(
                centerContent,
                Priority.ALWAYS
        );

        VBox rightSidebar = buildRightSidebar();

        mainContent.getChildren().addAll(
                centerContent,
                rightSidebar
        );

        page.getChildren().addAll(
                topNavigation,
                mainContent
        );

        ScrollPane scroll = new ScrollPane(page);

        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scroll.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-padding: 0;"
        );

        scroll.getStylesheets().add(
                "data:text/css," +
                ".scroll-pane > .viewport {" +
                "-fx-background-color: transparent;" +
                "}"
        );

        VBox.setVgrow(
                scroll,
                Priority.ALWAYS
        );

        getChildren().add(scroll);
    }

    // =========================================================
    // TOP NAVIGATION
    // =========================================================

    private HBox buildTopNavigation() {

        HBox navigation = new HBox(8);

        navigation.setAlignment(
                Pos.CENTER_LEFT
        );

        navigation.setPadding(
                new Insets(18, 35, 14, 35)
        );

        navigation.setStyle(
                "-fx-background-color: white;" +
                "-fx-border-color: #e5e7eb;" +
                "-fx-border-width: 0 0 1 0;"
        );

        Label title =
                new Label("Community");

        title.setStyle(
                "-fx-font-size: 22px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;" +
                "-fx-padding: 0 25 0 0;"
        );

        Button home =
                createTab(
                        "Home",
                        false
                );

        Button following =
                createTab(
                        "Following",
                        true
                );

        Button groups =
                createTab(
                        "Groups",
                        false
                );

        Button events =
                createTab(
                        "Events",
                        false
                );

        home.setOnAction(e ->
                mainLayout.setCenter(
                        new Community_Dashboard(
                                mainLayout
                        )
                )
        );

        groups.setOnAction(e ->
                mainLayout.setCenter(
                        new Groups_Page(
                                mainLayout
                        )
                )
        );

        events.setOnAction(e ->
                mainLayout.setCenter(
                        new Community_Events_Page(
                                mainLayout
                        )
                )
        );

        navigation.getChildren().addAll(
                title,
                home,
                following,
                groups,
                events
        );

        return navigation;
    }

    private Button createTab(
            String text,
            boolean active
    ) {

        Button button =
                new Button(text);

        button.setCursor(
                Cursor.HAND
        );

        if (active) {

            button.setStyle(
                    "-fx-background-color: #eef2ff;" +
                    "-fx-text-fill: #2563eb;" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 10 18;"
            );

        } else {

            button.setStyle(
                    "-fx-background-color: transparent;" +
                    "-fx-text-fill: #64748b;" +
                    "-fx-font-size: 14px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-radius: 10;" +
                    "-fx-padding: 10 18;"
            );

            button.setOnMouseEntered(e ->
                    button.setStyle(
                            "-fx-background-color: #f1f5f9;" +
                            "-fx-text-fill: #111827;" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10 18;"
                    )
            );

            button.setOnMouseExited(e ->
                    button.setStyle(
                            "-fx-background-color: transparent;" +
                            "-fx-text-fill: #64748b;" +
                            "-fx-font-size: 14px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10 18;"
                    )
            );
        }

        return button;
    }

    // =========================================================
    // CENTER CONTENT
    // =========================================================

    private VBox buildCenterContent() {

        VBox center = new VBox(18);

        center.setPrefWidth(760);

        // Header
        VBox header = new VBox(5);

        header.setPadding(
                new Insets(22, 25, 22, 25)
        );

        header.setStyle(
                "-fx-background-color: linear-gradient(to right, #172554, #2563eb);" +
                "-fx-background-radius: 18;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.12), 15, 0, 0, 4);"
        );

        Label small =
                new Label(
                        "YOUR COMMUNITY"
                );

        small.setStyle(
                "-fx-text-fill: #bfdbfe;" +
                "-fx-font-size: 10px;" +
                "-fx-font-weight: bold;"
        );

        Label heading =
                new Label(
                        "Following"
                );

        heading.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: 27px;" +
                "-fx-font-weight: bold;"
        );

        Label description =
                new Label(
                        "Stay connected with players, coaches and cricket creators you follow."
                );

        description.setStyle(
                "-fx-text-fill: #dbeafe;" +
                "-fx-font-size: 13px;"
        );

        header.getChildren().addAll(
                small,
                heading,
                description
        );

        // Following players
        VBox followingCard =
                buildFollowingPlayers();

        // Posts
        VBox posts =
                new VBox(16);

        posts.getChildren().addAll(

                buildPost(
                        "Jasprit Bumrah",
                        "JB",
                        "#f59e0b",
                        "2 min ago",
                        "Morning bowling drills session at the academy! Ready to improve every day. ⚡",
                        "18",
                        "5"
                ),

                buildPost(
                        "Coach Sandeep Patil",
                        "CS",
                        "#ef4444",
                        "21 min ago",
                        "Great intensity shown by the team in today's net practice. Keep pushing! 💪",
                        "26",
                        "8"
                ),

                buildPost(
                        "Rohit Sharma",
                        "RS",
                        "#10b981",
                        "1 hour ago",
                        "Good preparation today. Looking forward to the next tournament match. 🏏",
                        "42",
                        "12"
                )
        );

        ScrollPane postScroll =
                new ScrollPane(posts);

        postScroll.setFitToWidth(true);

        postScroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        postScroll.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-background-insets: 0;" +
                "-fx-padding: 0;"
        );

        postScroll.getStylesheets().add(
                "data:text/css," +
                ".scroll-pane > .viewport {" +
                "-fx-background-color: transparent;" +
                "}"
        );

        VBox.setVgrow(
                postScroll,
                Priority.ALWAYS
        );

        center.getChildren().addAll(
                header,
                followingCard,
                postScroll
        );

        return center;
    }

    // =========================================================
    // FOLLOWING PLAYER STRIP
    // =========================================================

    private VBox buildFollowingPlayers() {

        VBox card =
                createCard();

        HBox titleRow =
                new HBox();

        Label title =
                new Label(
                        "People You Follow"
                );

        title.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label count =
                new Label(
                        "24 following"
                );

        count.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-text-fill: #64748b;"
        );

        titleRow.getChildren().addAll(
                title,
                spacer,
                count
        );

        HBox players =
                new HBox(25);

        players.setPadding(
                new Insets(10, 0, 0, 0)
        );

        players.setAlignment(
                Pos.CENTER_LEFT
        );

        players.getChildren().addAll(

                createFollowingPlayer(
                        "VK",
                        "Virat K.",
                        "#2563eb"
                ),

                createFollowingPlayer(
                        "RS",
                        "Rohit S.",
                        "#10b981"
                ),

                createFollowingPlayer(
                        "JB",
                        "Jasprit B.",
                        "#f59e0b"
                ),

                createFollowingPlayer(
                        "CS",
                        "Coach S.",
                        "#ef4444"
                ),

                createFollowingPlayer(
                        "+",
                        "View All",
                        "#e5e7eb"
                )
        );

        card.getChildren().addAll(
                titleRow,
                players
        );

        return card;
    }

    private VBox createFollowingPlayer(
            String initials,
            String name,
            String color
    ) {

        VBox player =
                new VBox(7);

        player.setAlignment(
                Pos.CENTER
        );

        player.setCursor(
                Cursor.HAND
        );

        StackPane avatar =
                createAvatar(
                        initials,
                        color,
                        55
                );

        Label nameLabel =
                new Label(name);

        nameLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #334155;"
        );

        player.getChildren().addAll(
                avatar,
                nameLabel
        );

        return player;
    }

    // =========================================================
    // POST
    // =========================================================

    private VBox buildPost(
            String user,
            String initials,
            String color,
            String time,
            String content,
            String likes,
            String comments
    ) {

        VBox post =
                createCard();

        // Header
        HBox header =
                new HBox(12);

        header.setAlignment(
                Pos.CENTER_LEFT
        );

        StackPane avatar =
                createAvatar(
                        initials,
                        color,
                        46
                );

        VBox userInfo =
                new VBox(3);

        Label userName =
                new Label(user);

        userName.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        Label timeLabel =
                new Label(time);

        timeLabel.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-text-fill: #94a3b8;"
        );

        userInfo.getChildren().addAll(
                userName,
                timeLabel
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button more =
                new Button("⋯");

        more.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #64748b;" +
                "-fx-font-size: 20px;"
        );

        header.getChildren().addAll(
                avatar,
                userInfo,
                spacer,
                more
        );

        // Content
        Text body =
                new Text(content);

        body.setWrappingWidth(
                680
        );

        body.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-fill: #334155;"
        );

        // Divider
        Region divider =
                new Region();

        divider.setPrefHeight(1);

        divider.setStyle(
                "-fx-background-color: #f1f5f9;"
        );

        // Actions
        HBox actions =
                new HBox(8);

        Button like =
                createActionButton(
                        "♡  Like  " + likes
                );

        Button comment =
                createActionButton(
                        "💬  Comment  " + comments
                );

        Button share =
                createActionButton(
                        "↗  Share"
                );

        like.setOnAction(e -> {

            if (like.getText().contains("♡")) {

                like.setText(
                        "♥  Liked  " +
                        (Integer.parseInt(likes) + 1)
                );

                like.setStyle(
                        "-fx-background-color: #fee2e2;" +
                        "-fx-text-fill: #dc2626;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 7 12;"
                );

            } else {

                like.setText(
                        "♡  Like  " + likes
                );

                like.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #64748b;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 7 12;"
                );
            }
        });

        comment.setOnAction(e ->
                showCommentDialog(user)
        );

        share.setOnAction(e -> {

            String text =
                    "Check out " +
                    user +
                    "'s post on AthliX:\n\n" +
                    content;

            StringSelection selection =
                    new StringSelection(text);

            Toolkit.getDefaultToolkit()
                    .getSystemClipboard()
                    .setContents(
                            selection,
                            null
                    );

            share.setText(
                    "✓  Copied"
            );
        });

        actions.getChildren().addAll(
                like,
                comment,
                share
        );

        post.getChildren().addAll(
                header,
                body,
                divider,
                actions
        );

        return post;
    }

    // =========================================================
    // RIGHT SIDEBAR
    // =========================================================

    private VBox buildRightSidebar() {

        VBox sidebar =
                new VBox(18);

        sidebar.setPrefWidth(300);
        sidebar.setMinWidth(300);

        sidebar.getChildren().add(
                buildSuggestedCard()
        );

        sidebar.getChildren().add(
                buildTrendingCard()
        );

        sidebar.getChildren().add(
                buildActivityCard()
        );

        return sidebar;
    }

    // =========================================================
    // SUGGESTED PLAYERS
    // =========================================================

    private VBox buildSuggestedCard() {

        VBox card =
                createCard();

        HBox header =
                new HBox();

        Label title =
                new Label(
                        "Suggested For You"
                );

        title.setStyle(
                "-fx-font-size: 16px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Label more =
                new Label(
                        "See all"
                );

        more.setStyle(
                "-fx-text-fill: #2563eb;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;"
        );

        header.getChildren().addAll(
                title,
                spacer,
                more
        );

        card.getChildren().add(header);

        card.getChildren().add(
                createSuggestedPlayer(
                        "M",
                        "MS Dhoni",
                        "Wicketkeeper • Icon",
                        "#6366f1"
                )
        );

        card.getChildren().add(
                createSuggestedPlayer(
                        "R",
                        "Ravindra Jadeja",
                        "All-Rounder",
                        "#10b981"
                )
        );

        card.getChildren().add(
                createSuggestedPlayer(
                        "S",
                        "Shubman Gill",
                        "Batsman",
                        "#f59e0b"
                )
        );

        return card;
    }

    private HBox createSuggestedPlayer(
            String initials,
            String name,
            String role,
            String color
    ) {

        HBox row =
                new HBox(10);

        row.setAlignment(
                Pos.CENTER_LEFT
        );

        row.setPadding(
                new Insets(8, 0, 8, 0)
        );

        StackPane avatar =
                createAvatar(
                        initials,
                        color,
                        42
                );

        VBox info =
                new VBox(3);

        Label nameLabel =
                new Label(name);

        nameLabel.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        Label roleLabel =
                new Label(role);

        roleLabel.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-text-fill: #94a3b8;"
        );

        info.getChildren().addAll(
                nameLabel,
                roleLabel
        );

        Region spacer =
                new Region();

        HBox.setHgrow(
                spacer,
                Priority.ALWAYS
        );

        Button follow =
                new Button("Follow");

        follow.setCursor(
                Cursor.HAND
        );

        follow.setStyle(
                "-fx-background-color: #2563eb;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 15;" +
                "-fx-padding: 7 13;"
        );

        follow.setOnAction(e -> {

            if (follow.getText().equals("Follow")) {

                follow.setText(
                        "Following"
                );

                follow.setStyle(
                        "-fx-background-color: #dcfce7;" +
                        "-fx-text-fill: #15803d;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 7 13;"
                );

            } else {

                follow.setText(
                        "Follow"
                );

                follow.setStyle(
                        "-fx-background-color: #2563eb;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 7 13;"
                );
            }
        });

        row.getChildren().addAll(
                avatar,
                info,
                spacer,
                follow
        );

        return row;
    }

    // =========================================================
    // TRENDING
    // =========================================================

    private VBox buildTrendingCard() {

        VBox card =
                createCard();

        card.getChildren().add(
                createSidebarTitle(
                        "Trending Cricket"
                )
        );

        card.getChildren().add(
                createTrend(
                        "#CityFinals",
                        "2.4K posts"
                )
        );

        card.getChildren().add(
                createTrend(
                        "#CricketTraining",
                        "1.8K posts"
                )
        );

        card.getChildren().add(
                createTrend(
                        "#AthliXChallenge",
                        "1.2K posts"
                )
        );

        card.getChildren().add(
                createTrend(
                        "#WeekendCricket",
                        "864 posts"
                )
        );

        return card;
    }

    private HBox createTrend(
            String hashtag,
            String count
    ) {

        HBox row =
                new HBox();

        row.setPadding(
                new Insets(8, 0, 8, 0)
        );

        VBox text =
                new VBox(2);

        Label tag =
                new Label(hashtag);

        tag.setStyle(
                "-fx-text-fill: #2563eb;" +
                "-fx-font-size: 12px;" +
                "-fx-font-weight: bold;"
        );

        Label posts =
                new Label(count);

        posts.setStyle(
                "-fx-text-fill: #94a3b8;" +
                "-fx-font-size: 10px;"
        );

        text.getChildren().addAll(
                tag,
                posts
        );

        row.getChildren().add(text);

        return row;
    }

    // =========================================================
    // ACTIVITY
    // =========================================================

    private VBox buildActivityCard() {

        VBox card =
                createCard();

        card.getChildren().add(
                createSidebarTitle(
                        "Recent Activity"
                )
        );

        card.getChildren().add(
                createActivity(
                        "🏆",
                        "City Finals",
                        "You joined a tournament",
                        "2h ago"
                )
        );

        card.getChildren().add(
                createActivity(
                        "👥",
                        "Pune Cricket Club",
                        "New group activity",
                        "5h ago"
                )
        );

        card.getChildren().add(
                createActivity(
                        "🔥",
                        "AthliX Challenge",
                        "Your rank improved",
                        "Yesterday"
                )
        );

        return card;
    }

    private HBox createActivity(
            String icon,
            String title,
            String subtitle,
            String time
    ) {

        HBox row =
                new HBox(10);

        row.setPadding(
                new Insets(8, 0, 8, 0)
        );

        Label iconLabel =
                new Label(icon);

        iconLabel.setStyle(
                "-fx-font-size: 18px;"
        );

        VBox text =
                new VBox(2);

        Label titleLabel =
                new Label(title);

        titleLabel.setStyle(
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        Label sub =
                new Label(subtitle);

        sub.setStyle(
                "-fx-font-size: 10px;" +
                "-fx-text-fill: #64748b;"
        );

        Label timeLabel =
                new Label(time);

        timeLabel.setStyle(
                "-fx-font-size: 9px;" +
                "-fx-text-fill: #94a3b8;"
        );

        text.getChildren().addAll(
                titleLabel,
                sub,
                timeLabel
        );

        row.getChildren().addAll(
                iconLabel,
                text
        );

        return row;
    }

    // =========================================================
    // COMMON CARD
    // =========================================================

    private VBox createCard() {

        VBox card =
                new VBox(14);

        card.setPadding(
                new Insets(20)
        );

        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 16;" +
                "-fx-border-color: #e5e7eb;" +
                "-fx-border-radius: 16;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.05), 10, 0, 0, 3);"
        );

        return card;
    }

    // =========================================================
    // SIDEBAR TITLE
    // =========================================================

    private Label createSidebarTitle(
            String text
    ) {

        Label label =
                new Label(text);

        label.setStyle(
                "-fx-font-size: 15px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #111827;"
        );

        return label;
    }

    // =========================================================
    // ACTION BUTTON
    // =========================================================

    private Button createActionButton(
            String text
    ) {

        Button button =
                new Button(text);

        button.setCursor(
                Cursor.HAND
        );

        button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: #64748b;" +
                "-fx-font-size: 11px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8;" +
                "-fx-padding: 8 12;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #f1f5f9;" +
                        "-fx-text-fill: #111827;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 12;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: transparent;" +
                        "-fx-text-fill: #64748b;" +
                        "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 8 12;"
                )
        );

        return button;
    }

    // =========================================================
    // AVATAR
    // =========================================================

    private StackPane createAvatar(
            String initials,
            String color,
            double size
    ) {

        StackPane avatar =
                new StackPane();

        avatar.setPrefSize(
                size,
                size
        );

        avatar.setMinSize(
                size,
                size
        );

        avatar.setMaxSize(
                size,
                size
        );

        avatar.setStyle(
                "-fx-background-color: " +
                color +
                ";" +
                "-fx-background-radius: " +
                (size / 2) +
                ";"
        );

        Label label =
                new Label(initials);

        label.setStyle(
                "-fx-text-fill: white;" +
                "-fx-font-size: " +
                Math.max(11, size / 3.2) +
                "px;" +
                "-fx-font-weight: bold;"
        );

        avatar.getChildren().add(
                label
        );

        return avatar;
    }

    // =========================================================
    // COMMENT
    // =========================================================

    private void showCommentDialog(
            String user
    ) {

        TextInputDialog dialog =
                new TextInputDialog();

        dialog.setTitle(
                "Comment"
        );

        dialog.setHeaderText(
                "Comment on " +
                user +
                "'s post"
        );

        dialog.setContentText(
                "Your comment:"
        );

        Optional<String> result =
                dialog.showAndWait();

        result.ifPresent(comment -> {

            if (!comment.trim().isEmpty()) {

                System.out.println(
                        "Comment: " +
                        comment
                );
            }
        });
    }
}