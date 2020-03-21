/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author Hp
 */
public class webBrowserController implements Initializable {
    
    @FXML
    private TextField urlSearchBar ;
    
    @FXML
    private Button urlSearButton ;
    
    @FXML
    private TextField contentSearchBar ;
    
    @FXML
    private Button contentSearButton ;
    
    @FXML
    private Button forwardButton ;
    
    @FXML
    private Button backButton ;
    
    @FXML
    private Button reloadButton ;
    
    @FXML
    private Button historyButton ;
    
    @FXML
    private Button backFromHistoryButton ;
    
    @FXML
    private TextArea historyTextArea ;
    
    @FXML
    private ComboBox<String> providerBox ;
    
    @FXML
    private WebView web ;
    
    WebEngine engine ;
    
    WebHistory history ;
    
    ObservableList<WebHistory.Entry> historyEntryList;
    
    FileWriter fileWriter ;
    
    
    @FXML
    public void showHistory(ActionEvent event) throws IOException{
        Parent newRoot = FXMLLoader.load(getClass().getResource("histor.fxml")) ;
        Scene newScene = new Scene(newRoot) ;
        
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow() ;

//        newStage.setFullScreen(true);
        newStage.setMaximized(true);
        
        newStage.setScene(newScene);
        newStage.show();
        
    }
    
    public void googleSearch(){
        engine.load("https://www.google.com/");
    }
    
    public void youtubeSearch(){
        engine.load("https://www.youtube.com/");
    }
    
    public void facebookSearch(){
        engine.load("https://www.facebook.com/");
    }
    
    public void yahooSearch(){
        engine.load("https://www.yahoo.com/");
    }
    
    public void wikipediaSearch(){
        engine.load("https://www.wikipedia.org/");
    }
    
    public void twitterSearch(){
        engine.load("https://www.twitter.com/");
    }
    
    public void amazonSearch(){
        engine.load("https://www.amazon.com/");
    }
    
    public void urlSearch(){
        String httLink = "https://" ;
        
        engine.load(httLink + urlSearchBar.getText());
    }
    
    public String getSearchEngineSearchUrl(String searchProvider) {
	
	switch (searchProvider.toLowerCase()) {
	
        case "google": //then google
	    return "https://www.google.com/search?q=";
        default:
            return "https://search.yahoo.com/search?p=";
	}
    }
    
    public void contentSearch(){
        
        try {
            engine.load(getSearchEngineSearchUrl(providerBox.getSelectionModel().getSelectedItem())
                    + URLEncoder.encode(contentSearchBar.getText(), "UTF-8") ) ;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(webBrowserController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    
    
    public void goBack() {
	history.go(historyEntryList.size() > 1 && history.getCurrentIndex() > 0 ? -1 : 0);
	System.out.println(history.getCurrentIndex() + "," + historyEntryList.size());
    }
    
    public void goForward() {
	history.go(historyEntryList.size() > 1 && history.getCurrentIndex() < historyEntryList.size() - 1 ? 1 : 0);
	//System.out.println(history.getCurrentIndex() + "," + historyEntryList.size())
    }
    
    public void reload(){
        if (history.getEntries().isEmpty())
		engine.load("about:home");
	    else
		engine.reload();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        
        
    // home-page
        engine = web.getEngine() ;
        engine.setJavaScriptEnabled(false);
        engine.load("https://www.google.com/");
        
    //default provider is set to google
        providerBox.getSelectionModel().selectFirst();

    //history
        history = engine.getHistory() ;
        historyEntryList = history.getEntries() ;
        SimpleListProperty<WebHistory.Entry> list = new SimpleListProperty<>(historyEntryList);
        historyEntryList.addListener(new ListChangeListener() {
 
            @Override
            public void onChanged(ListChangeListener.Change change) {
                try {
                   
                    String str = String.valueOf(historyEntryList.get(historyEntryList.size() - 1)) ;
                    int lastPoint = str.indexOf(',');
                    String strFinal = str.substring( 6, lastPoint) ;
                    
                    fileWriter = new FileWriter("History.txt", true) ;
                    BufferedWriter writer = new BufferedWriter(fileWriter) ;
                    
                    writer.append(strFinal);
                    writer.newLine();
                    
                    writer.flush();
                    writer.close();
                    fileWriter.close();
                    
                  //  System.out.println(historyEntryList.get(historyEntryList.size() - 1));
                    System.out.println(history.getCurrentIndex());
                    
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(webBrowserController.class.getName()).log(Level.SEVERE, null, ex);
                
                } catch (IOException ex) {
                    Logger.getLogger(webBrowserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        // back button disable property
        backButton.disableProperty().bind(history.currentIndexProperty().isEqualTo(0));
        
//         forward button disable property
        forwardButton.disableProperty()
		.bind(history.currentIndexProperty().greaterThanOrEqualTo(list.sizeProperty().subtract(1)));
//        
//        else
//            backButton.setDisable(false);
    }

    
    
}
