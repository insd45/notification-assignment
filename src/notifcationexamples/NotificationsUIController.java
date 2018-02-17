/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button button1;
    
    @FXML
    private Button button2;
    
    @FXML
    private Button button3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null){
                    task2.end();
                    button2.setText("Start Task 2");
                }
                if (task3 != null){
                    task3.end();
                    button3.setText("Start Task 3");
                }
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            
            // Edit button text to say "End Task 1"
            // Use event to get button, but it requires a cast to button
            ((Button) event.getSource()).setText("End Task 1");
        } else {
            // Task is running, so it needs to end
            // Button's text needs to switch back to "Start Task 1"
            // Nullify so it can be started again
            task1.end();
            ((Button) event.getSource()).setText("Start Task 1");
            task1 = null;
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            endTask1Naturally();
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if(message == "Task2 done."){
                    task2.end();
                    endTask2Naturally();
                }
            });
            
            task2.start();
            
            // Edit button text to say "End Task 2"
            // Use event to get button, but it requires a cast to button
            ((Button) event.getSource()).setText("End Task 2");
        } else {
            // Task is running, so it needs to end
            // Button's text needs to switch back to "Start Task 2"
            // Nullify so it can be started again
            task2.end();
            ((Button) event.getSource()).setText("Start Task 2");
            task2 = null;
        }
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if(evt.getNewValue().equals("Task3 done.")){
                    task3.end();
                    endTask3Naturally();
                }
            });
            
            task3.start();
            
            // Edit button text to say "End Task 3"
            // Use event to get button, but it requires a cast to button
            ((Button) event.getSource()).setText("End Task 3");
        } else {
            // Task is running, so it needs to end
            // Button's text needs to switch back to "Start Task 3"
            // Nullify so it can be started again
            task3.end();
            ((Button) event.getSource()).setText("Start Task 3");
            task3 = null;
        }
    } 
    
    public void endTask1Naturally(){
        task1 = null;
        button1.setText("Start Task 1");
    }
    
    public void endTask2Naturally(){
        task2 = null;
        button2.setText("Start Task 2");
    }
    
    public void endTask3Naturally(){
        task3 = null;
        button3.setText("Start Task 3");
    }
}
