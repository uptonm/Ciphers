package CaesarShiftCipher;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

/**
 * @version 1.0 - 12/9/17
 * @author Mike Upton
 */
public class CipherGUI extends Application
{
    private int iterator = 0;
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        Label title = new Label("Caesar-Shift Cipher");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font("Verdana", 50));
        title.setStyle("-fx-stroke: rgb(0.0, 184.0, 222.0)");

        HBox fieldHolder = new HBox(10);
        fieldHolder.setAlignment(Pos.CENTER);
        VBox fields = new VBox(10);
        VBox titles = new VBox(45);
        titles.setPadding(new Insets(10));
        titles.setAlignment(Pos.CENTER);

        Label messageTitle = new Label("Message");
        TextField message = new TextField();
        message.setPromptText("Message");
        message.setPrefWidth(540);

        Label keyTitle = new Label("Key");
        TextField key = new TextField();
        key.setPromptText("0 to 2,147,483,647");
        key.setPrefWidth(540);

        titles.getChildren().addAll(messageTitle, keyTitle);
        fields.getChildren().addAll(message, key);
        fieldHolder.getChildren().addAll(titles, fields);

        TextArea output = new TextArea();
        output.setWrapText(true);

        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        message.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                root.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });

        root.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ESCAPE))
                {
                    root.requestFocus();
                }
            }
        });

        Button encrypt = new Button("Encrypt");
        encrypt.setOnAction(e -> {
            if(!message.getText().equals("") && !key.getText().equals(""))
            {
                if(Integer.parseInt(key.getText()) >= 0) {
                    output.clear();
                    output.setText("" + Cipher.encrypt(message.getText(), Integer.parseInt(key.getText())));
                    message.clear();
                    key.clear();
                }
                else
                {
                    output.clear();
                    output.setText("Check your input fields");
                }
            }
            else
            {
                output.clear();
                output.setText("Check your input fields");
            }
        });

        Button decrypt = new Button("Decrypt");
        decrypt.setOnAction(e -> {
            if(!message.getText().equals("") && !key.getText().equals(""))
            {
                if(Integer.parseInt(key.getText()) >= 0) {
                    output.clear();
                    output.setText("" + Cipher.decrypt(message.getText(), Integer.parseInt(key.getText())));
                    message.clear();
                    key.clear();
                }
                else
                {
                    output.clear();
                    output.setText("Check your input fields");
                }
            }
            else
            {
                output.clear();
                output.setText("Check your input fields");
            }
        });

        Button breakCipher = new Button("Break");
        breakCipher.setOnAction(e -> {
        if(!message.getText().equals("") && key.getText().equals(""))
        {
            output.requestFocus();
            output.clear();
            String[] possibilities = Cipher.breakCipher(message.getText());
            iterator = 0;
            output.setText("Possibility " + (iterator + 1) + ": " + possibilities[iterator]);
            output.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke)
                {
                    switch(ke.getCode())
                    {
                        case RIGHT:
                            iterator++;
                            if(iterator < 0)
                                iterator = 25;
                            else if(iterator > 25)
                                iterator = 0;
                            output.clear();
                            output.setText("Possibility " + (iterator + 1) + ": " + possibilities[iterator]);
                            break;
                        case LEFT:
                            iterator--;
                            if(iterator < 0)
                                iterator = 25;
                            else if(iterator > 25)
                                iterator = 0;
                            output.clear();
                            output.setText("Possibility " + (iterator + 1) + ": " + possibilities[iterator]);
                            break;
                    }
                }
            });
        }
        else if(!key.getText().equals(""))
        {
            output.clear();
            output.setText("Cannot break a cipher if you already have the key!");
        }
        else
        {
            output.clear();
            output.setText("Check your input fields");
        }
    });

        HBox buttonHolder = new HBox(10);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.getChildren().addAll(encrypt, decrypt, breakCipher);
        root.getChildren().addAll(title, fieldHolder,new Separator(), output, buttonHolder);

        Scene scene = new Scene(root, 700, 450);
        primaryStage.getIcons().add(new Image("Resources/RosettaStone.png"));

        scene.setUserAgentStylesheet("Resources/flatter.css");
        primaryStage.setTitle("Caesar-Shift Cipher");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
