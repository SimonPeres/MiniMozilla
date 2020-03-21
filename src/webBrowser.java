/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Hp
 */
public class webBrowser extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("webBrows.fxml"));
        
        Scene scene = new Scene( root);
//        stage.setFullScreen(true);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("Mini Mozilla");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static double getVisualScreenWidth() {
	return Screen.getPrimary().getVisualBounds().getWidth();
    }
    
    public static double getVisualScreenHeight() {
	return Screen.getPrimary().getVisualBounds().getHeight();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
