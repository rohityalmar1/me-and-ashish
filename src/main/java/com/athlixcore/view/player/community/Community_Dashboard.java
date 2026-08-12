package com.athlixcore.view.player.community;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.util.Optional;

public class Community_Dashboard extends VBox {

    private BorderPane mainLayout;
    private VBox postList;

    // Carousel
    private HBox adBox;
    private int currentAdIndex = 0;
    private final int AD_WIDTH = 700;
    private final int TOTAL_ADS = 4;

    public Community_Dashboard(BorderPane mainLayout) {
        this.mainLayout = mainLayout;

        setSpacing(0);
        setPadding(new Insets(0));
        setStyle("-fx-background-color: #f5f7fb;");

        VBox page = new VBox(0);

        HBox topMenu = buildTopMenu();

        HBox mainSplit = new HBox(28);
        mainSplit.setPadding(new Insets(24, 35, 30, 35));

        VBox feedColumn = buildFeedColumn();
        HBox.setHgrow(feedColumn, Priority.ALWAYS);

        VBox rightSidebar = buildRightSidebar();

        mainSplit.getChildren().addAll(feedColumn, rightSidebar);
        page.getChildren().addAll(topMenu, mainSplit);

        ScrollPane pageScroll = new ScrollPane(page);
        pageScroll.setFitToWidth(true);
        pageScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pageScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        pageScroll.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        VBox.setVgrow(pageScroll, Priority.ALWAYS);
        getChildren().add(pageScroll);
    }

    private HBox buildTopMenu() {
        HBox menu = new HBox(8);
        menu.setAlignment(Pos.CENTER_LEFT);
        menu.setPadding(new Insets(18, 35, 14, 35));
        menu.setStyle("-fx-background-color: white; -fx-border-color: #e5e7eb; -fx-border-width: 0 0 1 0;");

        Label communityLabel = new Label("Community");
        communityLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111827; -fx-padding: 0 25 0 0;");

        Button homeBtn = createMenuTab("⌂  Home", true);
        Button followingBtn = createMenuTab("♡  Following", false);
        Button groupsBtn = createMenuTab("◈  Groups", false);
        Button eventsBtn = createMenuTab("◷  Events", false);

        // --- RESTORED CONNECTIVITY ---
        groupsBtn.setOnAction(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));
        eventsBtn.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));
        followingBtn.setOnAction(e -> mainLayout.setCenter(new Community_Following_Page(mainLayout)));

        menu.getChildren().addAll(communityLabel, homeBtn, followingBtn, groupsBtn, eventsBtn);
        return menu;
    }

    private Button createMenuTab(String text, boolean active) {
        Button button = new Button(text);
        button.setCursor(Cursor.HAND);

        if (active) {
            button.setStyle("-fx-background-color: #eef2ff; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10; -fx-padding: 10 18;");
        } else {
            button.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10; -fx-padding: 10 18;");
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10; -fx-padding: 10 18;"));
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 10; -fx-padding: 10 18;"));
        }
        return button;
    }

    private VBox buildFeedColumn() {
        VBox feed = new VBox(20);
        feed.setPrefWidth(700);
        feed.setMinWidth(550);

        VBox welcomeCard = new VBox(5);
        welcomeCard.setPadding(new Insets(22, 25, 22, 25));
        welcomeCard.setStyle("-fx-background-color: linear-gradient(to right, #172554, #2563eb); -fx-background-radius: 18; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.12), 18, 0, 0, 5);");

        Label small = new Label("ATHLIX COMMUNITY");
        small.setStyle("-fx-text-fill: #bfdbfe; -fx-font-size: 11px; -fx-font-weight: bold;");

        Label title = new Label("Connect. Play. Improve. 🏏");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 25px; -fx-font-weight: bold;");

        Label subtitle = new Label("Connect with athletes, discover cricket events and share your journey.");
        subtitle.setStyle("-fx-text-fill: #dbeafe; -fx-font-size: 13px;");

        welcomeCard.getChildren().addAll(small, title, subtitle);

        StackPane carousel = buildAdvertisementCarousel();
        HBox createPost = buildCreatePostTrigger();

        postList = new VBox(16);
        postList.getChildren().addAll(
                buildFeedPost("Virat Kohli", "Looking for a practice match this weekend! Anyone interested?", "2 min ago", "VK", "#4338ca"),
                buildFeedPost("Rohit Sharma", "Great win today in the tournament. Proud of the team's performance! 🏆", "18 min ago", "RS", "#047857"),
                buildFeedPost("Hardik Pandya", "Training session done. Time to get better every single day. 💪", "42 min ago", "HP", "#7c3aed")
        );

        ScrollPane feedScroll = new ScrollPane(postList);
        feedScroll.setFitToWidth(true);
        feedScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        feedScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        feedScroll.setStyle("-fx-background-color: transparent; -fx-background-insets: 0; -fx-padding: 0;");
        feedScroll.getStylesheets().add("data:text/css,.scroll-pane > .viewport { -fx-background-color: transparent; }");

        VBox.setVgrow(feedScroll, Priority.ALWAYS);
        feed.getChildren().addAll(welcomeCard, carousel, createPost, feedScroll);

        return feed;
    }

    private StackPane buildAdvertisementCarousel() {
        StackPane container = new StackPane();
        container.setPrefHeight(150);
        container.setMinHeight(150);
        container.setMaxHeight(150);

        Rectangle clip = new Rectangle(AD_WIDTH, 150);
        clip.setArcWidth(22);
        clip.setArcHeight(22);

        PaneWrapper wrapper = new PaneWrapper();
        wrapper.setPrefSize(AD_WIDTH, 150);
        wrapper.setClip(clip);

        adBox = new HBox(0);
        adBox.getChildren().addAll(
                createAdCard("Live Tournament", "Watch the City Finals this Sunday.", "#fef3c7", "#b45309", "🏆"),
                createAdCard("Cricket Gear Sale", "Up to 50% off on premium cricket gear.", "#e0e7ff", "#4338ca", "🏏"),
                createAdCard("Pro Academy", "Train with professional cricket coaches.", "#dcfce7", "#047857", "🎯"),
                createAdCard("AthliX Challenge", "Join this week's player challenge.", "#fce7f3", "#be185d", "🔥")
        );

        wrapper.getChildren().add(adBox);

        Button previous = createCarouselButton("‹");
        Button next = createCarouselButton("›");

        previous.setOnAction(e -> moveCarousel(-1));
        next.setOnAction(e -> moveCarousel(1));

        StackPane.setAlignment(previous, Pos.CENTER_LEFT);
        StackPane.setAlignment(next, Pos.CENTER_RIGHT);
        StackPane.setMargin(previous, new Insets(0, 0, 0, 12));
        StackPane.setMargin(next, new Insets(0, 12, 0, 0));

        container.getChildren().addAll(wrapper, previous, next);

        Timeline autoSlide = new Timeline(new KeyFrame(Duration.seconds(5), e -> moveCarousel(1)));
        autoSlide.setCycleCount(Timeline.INDEFINITE);
        autoSlide.play();

        return container;
    }

    private void moveCarousel(int direction) {
        int newIndex = currentAdIndex + direction;
        if (newIndex < 0) newIndex = TOTAL_ADS - 1;
        if (newIndex >= TOTAL_ADS) newIndex = 0;

        currentAdIndex = newIndex;

        TranslateTransition transition = new TranslateTransition(Duration.millis(450), adBox);
        transition.setToX(-currentAdIndex * AD_WIDTH);
        transition.play();
    }

    private Button createCarouselButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(38, 38);
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-background-color: rgba(255,255,255,0.95); -fx-text-fill: #111827; -fx-font-size: 25px; -fx-font-weight: bold; -fx-background-radius: 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.18), 8, 0, 0, 2);");
        return button;
    }

    private StackPane createAdCard(String title, String subtitle, String background, String textColor, String icon) {
        StackPane card = new StackPane();
        card.setPrefSize(AD_WIDTH, 150);
        card.setMinSize(AD_WIDTH, 150);
        card.setCursor(Cursor.HAND);
        card.setStyle("-fx-background-color: " + background + "; -fx-background-radius: 18;");

        HBox layout = new HBox(25);
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(20, 50, 20, 30));

        StackPane iconBox = new StackPane();
        iconBox.setPrefSize(95, 95);
        iconBox.setMinSize(95, 95);
        iconBox.setStyle("-fx-background-color: rgba(255,255,255,0.65); -fx-background-radius: 20;");

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 45px;");
        iconBox.getChildren().add(iconLabel);

        VBox textBox = new VBox(7);
        Label badge = new Label("FEATURED");
        badge.setStyle("-fx-text-fill: " + textColor + "; -fx-font-size: 10px; -fx-font-weight: bold;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: " + textColor + "; -fx-font-size: 22px; -fx-font-weight: bold;");

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-text-fill: " + textColor + "; -fx-font-size: 13px;");

        textBox.getChildren().addAll(badge, titleLabel, subtitleLabel);
        layout.getChildren().addAll(iconBox, textBox);
        card.getChildren().add(layout);

        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: derive(" + background + ", -5%); -fx-background-radius: 18; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.10), 12, 0, 0, 3);"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: " + background + "; -fx-background-radius: 18;"));

        return card;
    }

    private HBox buildCreatePostTrigger() {
        HBox box = new HBox(15);
        box.setPadding(new Insets(16, 20, 16, 20));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setCursor(Cursor.HAND);
        box.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-border-color: #e5e7eb; -fx-border-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.05), 10, 0, 0, 2);");

        StackPane avatar = createAvatar("VM", "#2563eb", 42);

        Label prompt = new Label("Share something with the community...");
        prompt.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button createButton = new Button("+ Create Post");
        createButton.setCursor(Cursor.HAND);
        createButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 9; -fx-padding: 9 18;");

        createButton.setOnAction(e -> showCreatePostModal());
        box.setOnMouseClicked(e -> showCreatePostModal());

        box.getChildren().addAll(avatar, prompt, spacer, createButton);
        return box;
    }

    private void showCreatePostModal() {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(getScene().getWindow());
        modal.setTitle("Create Post");

        VBox root = new VBox(18);
        root.setPadding(new Insets(25));
        root.setPrefWidth(520);
        root.setStyle("-fx-background-color: #f8fafc;");

        Label title = new Label("Create a Community Post");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label subtitle = new Label("Share an update, achievement, question or training moment.");
        subtitle.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");

        TextArea input = new TextArea();
        input.setPromptText("What's happening in your cricket journey?");
        input.setPrefRowCount(6);
        input.setWrapText(true);
        input.setStyle("-fx-background-color: white; -fx-border-color: #dbe2ea; -fx-border-radius: 12; -fx-background-radius: 12; -fx-font-size: 14px;");

        HBox uploadRow = new HBox(12);
        uploadRow.setAlignment(Pos.CENTER_LEFT);

        Button uploadButton = new Button("📷 Add Photo");
        uploadButton.setCursor(Cursor.HAND);
        uploadButton.setStyle("-fx-background-color: #eef2ff; -fx-text-fill: #4338ca; -fx-font-weight: bold; -fx-background-radius: 9; -fx-padding: 9 15;");

        Label fileLabel = new Label("No photo selected");
        fileLabel.setStyle("-fx-text-fill: #64748b; -fx-font-size: 12px;");

        ImageView preview = new ImageView();
        preview.setFitWidth(430);
        preview.setFitHeight(220);
        preview.setPreserveRatio(true);
        preview.setVisible(false);

        final File[] selectedFile = {null};

        uploadButton.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose Post Image");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

            File file = chooser.showOpenDialog(modal);
            if (file != null) {
                selectedFile[0] = file;
                fileLabel.setText(file.getName());
                preview.setImage(new Image(file.toURI().toString()));
                preview.setVisible(true);
            }
        });

        uploadRow.getChildren().addAll(uploadButton, fileLabel);

        Button cancelButton = new Button("Cancel");
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setStyle("-fx-background-color: #e5e7eb; -fx-text-fill: #374151; -fx-font-weight: bold; -fx-background-radius: 9; -fx-padding: 10 22;");

        Button publishButton = new Button("Publish Post");
        publishButton.setCursor(Cursor.HAND);
        publishButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 9; -fx-padding: 10 22;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(12);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        bottom.getChildren().addAll(cancelButton, spacer, publishButton);

        cancelButton.setOnAction(e -> modal.close());

        publishButton.setOnAction(e -> {
            String content = input.getText().trim();

            if (content.isEmpty() && selectedFile[0] == null) {
                input.setPromptText("Please write something or upload an image.");
                return;
            }

            VBox newPost = buildFeedPostWithImage("Vikram Malhotra", content, "Just now", "VM", "#2563eb", selectedFile[0]);
            postList.getChildren().add(0, newPost);
            modal.close();
        });

        root.getChildren().addAll(title, subtitle, input, uploadRow, preview, bottom);

        Scene scene = new Scene(root);
        modal.setScene(scene);
        modal.setResizable(false);
        modal.show();
    }

    private VBox buildFeedPost(String user, String content, String time, String initials, String avatarColor) {
        return buildFeedPostWithImage(user, content, time, initials, avatarColor, null);
    }

    private VBox buildFeedPostWithImage(String user, String content, String time, String initials, String avatarColor, File imageFile) {
        VBox post = new VBox(15);
        post.setPadding(new Insets(20));
        post.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e5e7eb; -fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.05), 10, 0, 0, 3);");

        HBox header = new HBox(12);
        header.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = createAvatar(initials, avatarColor, 45);

        VBox userInfo = new VBox(3);
        Label name = new Label(user);
        name.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #111827;");

        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #94a3b8;");

        userInfo.getChildren().addAll(name, timeLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button moreButton = new Button("⋯");
        moreButton.setCursor(Cursor.HAND);
        moreButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-size: 20px;");

        header.getChildren().addAll(avatar, userInfo, spacer, moreButton);

        Text body = new Text(content);
        body.setStyle("-fx-font-size: 14px; -fx-fill: #334155;");
        body.setWrappingWidth(630);

        if (imageFile != null) {
            ImageView imageView = new ImageView(new Image(imageFile.toURI().toString()));
            imageView.setFitWidth(630);
            imageView.setPreserveRatio(true);

            Rectangle imageClip = new Rectangle(630, 350);
            imageClip.setArcWidth(14);
            imageClip.setArcHeight(14);
            imageView.setClip(imageClip);

            post.getChildren().addAll(header, body, imageView);
        } else {
            post.getChildren().addAll(header, body);
        }

        Region divider = new Region();
        divider.setPrefHeight(1);
        divider.setStyle("-fx-background-color: #f1f5f9;");

        HBox actions = new HBox(8);
        actions.setAlignment(Pos.CENTER_LEFT);

        Button likeButton = createPostActionButton("♡  Like  12");
        Button commentButton = createPostActionButton("💬  Comment  4");
        Button shareButton = createPostActionButton("↗  Share");

        likeButton.setOnAction(e -> {
            if (likeButton.getText().contains("♡")) {
                likeButton.setText("♥  Liked  13");
                likeButton.setStyle("-fx-background-color: #fee2e2; -fx-text-fill: #dc2626; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 12;");
            } else {
                likeButton.setText("♡  Like  12");
                likeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 12;");
            }
        });

        commentButton.setOnAction(e -> showCommentDialog(user, content));

        shareButton.setOnAction(e -> {
            String shareText = "Check out " + user + "'s post on AthliX:\n\n" + content;
            StringSelection selection = new StringSelection(shareText);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            shareButton.setText("✓  Copied");
        });

        actions.getChildren().addAll(likeButton, commentButton, shareButton);
        post.getChildren().addAll(divider, actions);

        return post;
    }

    private Button createPostActionButton(String text) {
        Button button = new Button(text);
        button.setCursor(Cursor.HAND);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 12;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f1f5f9; -fx-text-fill: #111827; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 12;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #64748b; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 7 12;"));
        return button;
    }

    private void showCommentDialog(String user, String postContent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Comment");
        dialog.setHeaderText("Comment on " + user + "'s post");
        dialog.setContentText("Your comment:");
        dialog.showAndWait();
    }

    private VBox buildRightSidebar() {
        VBox sidebar = new VBox(18);
        sidebar.setPrefWidth(310);
        sidebar.setMinWidth(310);

        sidebar.getChildren().add(buildProfileMiniCard());
        sidebar.getChildren().add(buildGroupsWidget());
        sidebar.getChildren().add(buildEventsWidget());
        sidebar.getChildren().add(buildTrendingWidget());
        sidebar.getChildren().add(buildActivePlayersWidget());

        return sidebar;
    }

    private VBox buildProfileMiniCard() {
        VBox card = createSidebarCard();
        HBox profile = new HBox(12);
        profile.setAlignment(Pos.CENTER_LEFT);
        StackPane avatar = createAvatar("VM", "#2563eb", 55);

        VBox info = new VBox(4);
        Label name = new Label("Vikram Malhotra");
        name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label role = new Label("All-Rounder • Pune");
        role.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748b;");
        info.getChildren().addAll(name, role);
        profile.getChildren().addAll(avatar, info);

        HBox stats = new HBox(25);
        stats.setPadding(new Insets(15, 0, 0, 0));
        stats.getChildren().addAll(
                createMiniStat("24", "Posts"),
                createMiniStat("1.2K", "Followers"),
                createMiniStat("86", "Following")
        );
        card.getChildren().addAll(profile, stats);
        return card;
    }

    private VBox createMiniStat(String value, String title) {
        VBox box = new VBox(2);
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #94a3b8;");
        box.getChildren().addAll(valueLabel, titleLabel);
        return box;
    }

    private VBox buildGroupsWidget() {
        VBox card = createSidebarCard();
        HBox titleRow = new HBox();
        Label title = createSidebarTitle("My Groups");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label viewAll = new Label("View all");
        viewAll.setCursor(Cursor.HAND);
        viewAll.setStyle("-fx-text-fill: #2563eb; -fx-font-size: 11px; -fx-font-weight: bold;");
        
        // --- RESTORED CONNECTIVITY ---
        viewAll.setOnMouseClicked(e -> mainLayout.setCenter(new Groups_Page(mainLayout)));
        
        titleRow.getChildren().addAll(title, spacer, viewAll);
        card.getChildren().add(titleRow);

        card.getChildren().add(createGroupRow("🏏", "Pune Cricket Club", "124 Members"));
        card.getChildren().add(createGroupRow("🔥", "Weekend Warriors", "86 Members"));
        card.getChildren().add(createGroupRow("🎯", "Batting Masters", "52 Members"));
        return card;
    }

    private HBox createGroupRow(String icon, String name, String members) {
        HBox row = new HBox(12);
        row.setPadding(new Insets(10, 0, 10, 0));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setCursor(Cursor.HAND);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 22px;");

        VBox text = new VBox(3);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label memberLabel = new Label(members);
        memberLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #94a3b8;");
        text.getChildren().addAll(nameLabel, memberLabel);

        row.getChildren().addAll(iconLabel, text);
        
        // --- RESTORED CONNECTIVITY ---
        row.setOnMouseClicked(e -> mainLayout.setCenter(new Group_Details(mainLayout)));
        
        return row;
    }

    private VBox buildEventsWidget() {
        VBox card = createSidebarCard();
        Label title = createSidebarTitle("Upcoming Events");
        card.getChildren().add(title);

        card.getChildren().add(createEventRow("18", "AUG", "City Cricket Finals", "Sunday • 10:00 AM"));
        card.getChildren().add(createEventRow("22", "AUG", "Player Meet & Greet", "Friday • 6:00 PM"));

        Button viewAll = new Button("View All Events →");
        viewAll.setMaxWidth(Double.MAX_VALUE);
        viewAll.setCursor(Cursor.HAND);
        viewAll.setStyle("-fx-background-color: #eff6ff; -fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-background-radius: 8; -fx-padding: 9;");
        
        // --- RESTORED CONNECTIVITY ---
        viewAll.setOnAction(e -> mainLayout.setCenter(new Community_Events_Page(mainLayout)));
        
        card.getChildren().add(viewAll);
        return card;
    }

    private HBox createEventRow(String day, String month, String eventName, String time) {
        HBox row = new HBox(12);
        row.setPadding(new Insets(10, 0, 10, 0));
        row.setAlignment(Pos.CENTER_LEFT);

        VBox date = new VBox(1);
        date.setPrefWidth(48);
        date.setAlignment(Pos.CENTER);
        date.setStyle("-fx-background-color: #eff6ff; -fx-background-radius: 9;");

        Label dayLabel = new Label(day);
        dayLabel.setStyle("-fx-text-fill: #2563eb; -fx-font-size: 17px; -fx-font-weight: bold;");
        Label monthLabel = new Label(month);
        monthLabel.setStyle("-fx-text-fill: #60a5fa; -fx-font-size: 9px; -fx-font-weight: bold;");
        date.getChildren().addAll(dayLabel, monthLabel);

        VBox text = new VBox(3);
        Label event = new Label(eventName);
        event.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label eventTime = new Label(time);
        eventTime.setStyle("-fx-font-size: 10px; -fx-text-fill: #94a3b8;");
        text.getChildren().addAll(event, eventTime);

        row.getChildren().addAll(date, text);
        return row;
    }

    private VBox buildTrendingWidget() {
        VBox card = createSidebarCard();
        card.getChildren().add(createSidebarTitle("Trending Now"));
        card.getChildren().add(createTrendingItem("#CityFinals", "2.4K posts"));
        card.getChildren().add(createTrendingItem("#CricketTraining", "1.8K posts"));
        card.getChildren().add(createTrendingItem("#AthliXChallenge", "1.2K posts"));
        card.getChildren().add(createTrendingItem("#WeekendCricket", "864 posts"));
        return card;
    }

    private HBox createTrendingItem(String hashtag, String count) {
        HBox row = new HBox();
        row.setPadding(new Insets(8, 0, 8, 0));
        VBox text = new VBox(3);
        Label tag = new Label(hashtag);
        tag.setStyle("-fx-text-fill: #2563eb; -fx-font-weight: bold; -fx-font-size: 12px;");
        Label posts = new Label(count);
        posts.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 10px;");
        text.getChildren().addAll(tag, posts);
        row.getChildren().add(text);
        return row;
    }

    private VBox buildActivePlayersWidget() {
        VBox card = createSidebarCard();
        card.getChildren().add(createSidebarTitle("Active Players"));
        card.getChildren().add(createOnlinePlayer("VK", "Virat Kohli", "#4338ca"));
        card.getChildren().add(createOnlinePlayer("RS", "Rohit Sharma", "#047857"));
        card.getChildren().add(createOnlinePlayer("HP", "Hardik Pandya", "#7c3aed"));
        return card;
    }

    private HBox createOnlinePlayer(String initials, String name, String color) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(8, 0, 8, 0));
        row.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = createAvatar(initials, color, 34);
        Circle online = new Circle(5);
        online.setStyle("-fx-fill: #22c55e; -fx-stroke: white; -fx-stroke-width: 2;");

        StackPane avatarBox = new StackPane(avatar, online);
        StackPane.setAlignment(online, Pos.BOTTOM_RIGHT);

        VBox info = new VBox(2);
        Label nameLabel = new Label(name);
        nameLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        Label status = new Label("Online now");
        status.setStyle("-fx-font-size: 10px; -fx-text-fill: #22c55e;");
        info.getChildren().addAll(nameLabel, status);

        row.getChildren().addAll(avatarBox, info);
        return row;
    }

    private VBox createSidebarCard() {
        VBox card = new VBox(12);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; -fx-border-color: #e5e7eb; -fx-border-radius: 16; -fx-effect: dropshadow(three-pass-box, rgba(15,23,42,0.05), 10, 0, 0, 3);");
        return card;
    }

    private Label createSidebarTitle(String text) {
        Label title = new Label(text);
        title.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #111827;");
        return title;
    }

    private StackPane createAvatar(String initials, String backgroundColor, double size) {
        StackPane avatar = new StackPane();
        avatar.setPrefSize(size, size);
        avatar.setMinSize(size, size);
        avatar.setMaxSize(size, size);
        avatar.setStyle("-fx-background-color: " + backgroundColor + "; -fx-background-radius: " + (size / 2) + ";");

        Label label = new Label(initials);
        label.setStyle("-fx-text-fill: white; -fx-font-size: " + Math.max(11, size / 3.2) + "px; -fx-font-weight: bold;");
        avatar.getChildren().add(label);
        return avatar;
    }

    private static class PaneWrapper extends StackPane {
        public PaneWrapper() {
            super();
        }
    }
}