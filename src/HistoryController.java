/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hp
 */
public class HistoryController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private TextArea historyTextArea ;
    
    @FXML
    private Button backButtonFromHistory ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            FileReader reader = new FileReader(new File("History.txt")) ;
            BufferedReader br = new BufferedReader(reader) ;
            
            String str ;
            while((str = br.readLine()) != null){
                historyTextArea.appendText(str);
                historyTextArea.appendText("\n");
        }
            br.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HistoryController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HistoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    
    public void backToBrowser(ActionEvent event) throws IOException{
        Parent newRoot = FXMLLoader.load(getClass().getResource("webBrows.fxml")) ;
        Scene newScene = new Scene(newRoot) ;
        
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;
//        newStage.setFullScreen(true);
        newStage.setMaximized(true);
        newStage.setScene(newScene);
        newStage.show();
    }
    
}
