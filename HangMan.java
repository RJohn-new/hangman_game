// Robert Johnson, 4/28/17
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;

public class HangMan extends Application {

    @Override
    public void start(Stage primaryStage) {
        Random rdm = new Random();
        Pane pane = new Pane();
        Text txtGuess = new Text("Guess a word: ");
        Text txtOver = new Text("The word is: ");
        Text txtMissed = new Text("Missed Letters: ");
        Text txtMissedLetters = new Text();
        Text txtCorrectGuess = new Text();
        Text txtAnswer = new Text();
        Text txtReplay = new Text("To continue the game, press ENTER");

        ArrayList<Shape> bodyParts = new ArrayList<>();
        String[] words = { "write", "program", "that", "receive", "positive",
                "excellent", "linger", "violin", "strange", "holiday", "twilight",
                "school", "teacher", "tutor", "mother", "code", "laptop", "repository",
                "dentist", "chocolate"};
        String answer = words[rdm.nextInt(words.length)];
        ArrayList<Character>  answerArr = new ArrayList<>();
        ArrayList<Character> guesses = new ArrayList<>();
        ArrayList<Character> misses = new ArrayList<>();

        for (Character c : answer.toCharArray()) {
            answerArr.add(c);
        }

        Arc base = new Arc(150,420,50,20,0,180);
        base.setFill(Color.TRANSPARENT);
        base.setStroke(Color.BLACK);
        Line post = new Line(150,400,150,200);
        Line beam = new Line(150,200,226,200);
        pane.getChildren().add(base);
        pane.getChildren().add(post);
        pane.getChildren().add(beam);

        bodyParts.add(new Line(225,200,225,230));
        Circle head = new Circle(225,245,20);
        head.setFill(Color.WHITE);
        head.setStroke(Color.BLACK);
        bodyParts.add(head);
        bodyParts.add(new Line(225,265,225,325));
        bodyParts.add(new Line(225,285,260,300));
        bodyParts.add(new Line(225,285,190,300));
        bodyParts.add(new Line(225,325,260,355));
        bodyParts.add(new Line(225,325,190,355));

        txtGuess.setX(225);
        txtGuess.setY(400);
        txtOver.setX(225);
        txtOver.setY(400);

        txtMissed.setX(225);
        txtMissed.setY(425);
        txtReplay.setX(225);
        txtReplay.setY(425);
        pane.getChildren().add(txtGuess);
        pane.getChildren().add(txtMissed);

        txtMissedLetters.setX(310);
        txtMissedLetters.setY(425);
        txtCorrectGuess.setX(310);
        txtCorrectGuess.setY(400);

        txtAnswer.setText(answer);
        txtAnswer.setX(310);
        txtAnswer.setY(400);

        for (int i = 0; i < answerArr.size(); i++) {
            txtCorrectGuess.setText(txtCorrectGuess.getText() + "*");
        }
        pane.getChildren().add(txtCorrectGuess);
        pane.getChildren().add(txtMissedLetters);

        pane.setOnKeyPressed(e -> {
            if (!bodyParts.isEmpty() && !misses.contains(Character.toLowerCase(e.getCode().getName().charAt(0)))
                    && !guesses.contains(Character.toLowerCase(e.getCode().getName().charAt(0))) && e.getCode().isLetterKey()) {

                if (answerArr.contains(Character.toLowerCase(e.getCode().getName().charAt(0)))) {
                    guesses.add(Character.toLowerCase(e.getCode().getName().charAt(0)));
                    txtCorrectGuess.setText("");
                    for (int i = 0; i < answerArr.size(); i++) {
                        if (guesses.contains(answerArr.get(i))) {
                            txtCorrectGuess.setText(txtCorrectGuess.getText() + answerArr.get(i));
                        } else {
                            txtCorrectGuess.setText(txtCorrectGuess.getText() + "*");
                        }
                    }
                } else {
                    pane.getChildren().add(bodyParts.get(misses.size()));
                    misses.add(Character.toLowerCase(e.getCode().getName().charAt(0)));
                    txtMissedLetters.setText("");
                    for (int i = 0; i < misses.size(); i++) {
                        txtMissedLetters.setText(txtMissedLetters.getText() + misses.get(i));
                    }
                }

                if (guesses.containsAll(answerArr) && answerArr.containsAll(guesses)) {
                    pane.getChildren().remove(txtGuess);
                    pane.getChildren().add(txtOver);
                    pane.getChildren().remove(txtCorrectGuess);
                    pane.getChildren().add(txtAnswer);
                    pane.getChildren().removeAll(txtMissedLetters, txtMissed);
                    pane.getChildren().add(txtReplay);
                    pane.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            primaryStage.close();
                            start(new Stage());
                        }
                    });
                }

                if (misses.size() == 7) {
                    pane.getChildren().remove(txtGuess);
                    pane.getChildren().add(txtOver);
                    pane.getChildren().remove(txtCorrectGuess);
                    pane.getChildren().add(txtAnswer);
                    pane.getChildren().removeAll(txtMissedLetters, txtMissed);
                    pane.getChildren().add(txtReplay);

                    Arc swing = new Arc(225,205,12,10,220,100);
                    swing.setFill(Color.TRANSPARENT);
                    swing.setStroke(Color.BLACK);
                    //pane.getChildren().add(swing);

                    bodyParts.get(0).translateXProperty().addListener((observable, oldValue, newValue) -> {
                        bodyParts.get(1).setTranslateX(newValue.doubleValue());
                        bodyParts.get(2).setTranslateX(newValue.doubleValue());
                        bodyParts.get(3).setTranslateX(newValue.doubleValue());
                        bodyParts.get(4).setTranslateX(newValue.doubleValue());
                        bodyParts.get(5).setTranslateX(newValue.doubleValue());
                        bodyParts.get(6).setTranslateX(newValue.doubleValue());
                    });

                    bodyParts.get(0).translateYProperty().addListener((observable, oldValue, newValue) -> {
                        bodyParts.get(1).setTranslateY(newValue.doubleValue());
                        bodyParts.get(2).setTranslateY(newValue.doubleValue());
                        bodyParts.get(3).setTranslateY(newValue.doubleValue());
                        bodyParts.get(4).setTranslateY(newValue.doubleValue());
                        bodyParts.get(5).setTranslateY(newValue.doubleValue());
                        bodyParts.get(6).setTranslateY(newValue.doubleValue());
                    });

                    PathTransition pt = new PathTransition(Duration.seconds(2.5),swing,bodyParts.get(0));
                    pt.setCycleCount(Timeline.INDEFINITE);
                    pt.setAutoReverse(true);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.play();

                    pane.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            primaryStage.close();
                            start(new Stage());
                        }
                    });
                }
            }
        });


        Scene scene = new Scene(pane, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Exercise20_07");
        primaryStage.show();

        pane.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}