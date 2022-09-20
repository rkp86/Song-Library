package view;
//Roma Patel - rkp86
//Omer Farooq - ofs9


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import app.Song;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Controller {
	
 	@FXML
    private TextField addalbum;

    @FXML
    private TextField addartist;

    @FXML
    private Button addbutton;

    @FXML
    private TextField addname;

    @FXML
    private TextField addyear;

    @FXML
    private Button deletebutton;

    @FXML
    private TextField editalbum;

    @FXML
    private TextField editartist;

    @FXML
    private Button editbutton;

    @FXML
    private TextField editname;

    @FXML
    private TextField edityear;

	public List<Song> storesongs;  //to organize the songs have
	
    private ObservableList<String> songlist;  //to put the songs we have into a readable format
	
    @FXML
    private ListView<String> library;  //to take the readable format and make it visible to the user 
    
    private int selectedID;
    

    @FXML
    void addSong(MouseEvent event) throws IOException {
    	if (addname.getText().isBlank() || addartist.getText().isBlank()) {
    		cannotAdd(); // shows a dialog to the user to say they cant add the song
    	}
    	else if (addname.getText().indexOf("|") >= 0 || addartist.getText().indexOf("|") > 0 || addalbum.getText().indexOf("|") >= 0) {
    		cannotAdd2();
    	}
    	else if (!isNumber(addyear.getText()))
    		cannotAddYear();
    	else {
    		if (addalbum.getText().isBlank() && addyear.getText().isBlank()) {
	    		Song s = new Song(addname.getText().trim(), addartist.getText().trim());
	    		checkDuplicate(s);
    		}
	    	else if (addyear.getText().isBlank()) {
	    		Song s = new Song(addname.getText().trim(), addartist.getText().trim(), addalbum.getText().trim(), 0);
	    		checkDuplicate(s);
	    	}
	    	else if (addalbum.getText().isBlank()) {
	    		Song s = new Song(addname.getText().trim(), addartist.getText().trim(), Integer.parseInt(addyear.getText().trim()));
	    		checkDuplicate(s);
	    	}
	    	else {
	    		Song s = new Song(addname.getText().trim(), addartist.getText().trim(), addalbum.getText().trim(), Integer.parseInt(addyear.getText()));
	    		checkDuplicate(s);
	    	}
	  	}
    }

    
    public void checkDuplicate(Song song) throws IOException {
    	boolean check = false;
    	for (Song s : storesongs) {
    		if (s.getName().toLowerCase().equals(song.getName().toLowerCase().trim()) && s.getArtist().toLowerCase().equals(song.getArtist().toLowerCase().trim())) {
    			check = true;
    			duplicateWarning();  //dialog to tell user they cant add a duplicate song
    		}
    	}   	
    	if (!check) {
    		
    		doYouWannaAdd(song);  //dialog to ask user for confirmation
    	}
    }
    
    
    private void doYouWannaAdd (Song song) throws IOException {             
		Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to add this song to the library ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
			storesongs.add(song);
			updateLibrary();  
			selectedID = storesongs.indexOf(song);
			library.getSelectionModel().select(selectedID);
			showSongInfo(selectedID);
			addname.setText("");
			addartist.setText("");
			addalbum.setText("");
			addyear.setText("");
		}
    }

    
    
    private void duplicateWarning () {             
    	
		Alert alert = new Alert(AlertType.ERROR, "This song is already in the song library.", ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {		
		}
    }
    
    private void cannotAdd () {             
    	
		Alert alert = new Alert(AlertType.ERROR, "You must input the song name and artist.", ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {		
		}
    }
    
    private void cannotAdd2 () {             
    	
		Alert alert = new Alert(AlertType.ERROR, "This song cannot be added. (has the character |)", ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {		
		}
    }
    
    private boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (Character.isDigit(s.charAt(i)) == false)
                return false;
 
        return true;
    }
    
    private void cannotAddYear () {             
    	
		Alert alert = new Alert(AlertType.ERROR, "This song has an invalid year.", ButtonType.OK);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {		
		}
    }
 
    @FXML
    void removeSong(MouseEvent event) throws IOException {
        int selectedID = library.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this song?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {		
			storesongs.remove(selectedID);
	        library.getItems().remove(selectedID);
	        updateLibrary();
		}
		if (selectedID == library.getItems().size())
				library.getSelectionModel().select(selectedID-1);
		else  library.getSelectionModel().select(selectedID);
		

    }
    
    @FXML
    void editSong(MouseEvent event) throws IOException {
        
    	if (editname.getText().isBlank() || editartist.getText().isBlank()) {
    		cannotAdd(); // shows a dialog to the user to say they cant add the song
    		if (editname.getText().isBlank())
    			editname.setText(storesongs.get(selectedID).getName());
    		if(editartist.getText().isBlank())
    			editartist.setText(storesongs.get(selectedID).getArtist());
    	}
    	else if (!isNumber(edityear.getText()))
    		cannotAddYear();
    	else {
	        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to edit this song?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES) {		
				storesongs.get(selectedID).setName(editname.getText());
		        storesongs.get(selectedID).setArtist(editartist.getText());
		        storesongs.get(selectedID).setAlbum(editalbum.getText());
		        if (!edityear.getText().isBlank())
		        	storesongs.get(selectedID).setYear(Integer.parseInt(edityear.getText()));
		        updateLibrary();
			}
		}
    	library.getSelectionModel().select(selectedID);
    }
    
    public void readSongs() throws IOException, FileNotFoundException  {
    	
 	   File myFile = new File("src/songs.txt");  
 	   storesongs = new ArrayList<Song>();

 	   String input = "";
 	   int count = 1;
 	   int year = 0;
 	   String name = "", artist = "", album= "";
    	try (FileReader fileReader = new FileReader(myFile);
            Scanner scanner=new Scanner(fileReader)){
            while (scanner.hasNext()) {
        		
            	if(scanner.hasNext()) {
            		 if (scanner.hasNextInt()) {
                 		year = scanner.nextInt();
                     } 
            		String c = scanner.next();
            		if (c.equals("~")) {
                    	if (count == 1) name = input;
                    	if (count == 2) artist = input;
                    	if (count == 3) album = input;                
                   		input = "";
                   		count ++;
                 	} 
                    else if (c.equals("|")) {
                    	if (count == 2) { artist = input; year = 0; }
                    	if (count == 3) { album = input; year = 0; }
                    	count = 1 ;
                    	input ="";
                    	if (year != 0) 	storesongs.add(new Song(name.trim(),artist.trim(),album,year)); 
                    	else storesongs.add(new Song(name.trim(),artist.trim(),album));
                    	name = "" ; artist = ""; album = ""; year = 0;
                    }
                    
                    else {
                    	input += c + " ";
                    }
                }
            	
            }
            
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	songlist = FXCollections.observableArrayList();
    	updateLibrary();
    	   
    	
    }
    
    public void updateLibrary() throws IOException {
    	Collections.sort(storesongs);
    	library.getItems().clear();

    	if (storesongs.size() >=2) {
	    	for (int i = 0; i <= storesongs.size() - 2 ; i++) { //order the lowercase and uppercase abc order
		    	for (int j = i+1; j <= storesongs.size()-1; j++) {	
	    			int compare = storesongs.get(i).getName().toLowerCase().compareTo(storesongs.get(j).getName().toLowerCase());
	    			if (compare > 0) {
	    				storesongs.add(i, storesongs.get(j));
	    				storesongs.remove(j+1);
	    			}   
	    			if (compare < 0) {
	    				storesongs.add(i+1, storesongs.get(j));
	    				storesongs.remove(j+1);
	    			}   
	    			if (compare == 0) { 
	    				int cmp = storesongs.get(i).getArtist().toLowerCase().compareTo(storesongs.get(j).getArtist().toLowerCase());
		    			
		    			if (cmp > 0) {
		    				storesongs.add(i, storesongs.get(j));
		    				storesongs.remove(j+1);

		    			}   
	    			}
		    	}
	    	}
    	}
    	
  
    	    	
		for (Song song : storesongs) {
			songlist.add(song.getName() + " - " + song.getArtist());
		}		
		library.setItems(songlist);
		
		writeFile();
    }
    
    public void writeFile() throws IOException {
    	//File songs = new File ("src/songs.txt");
    	//if(songs.exists()) 
		FileWriter writer = new FileWriter("src/songs.txt");
		BufferedWriter buffer = new BufferedWriter(writer);
    	for (Song s: storesongs) {
    		buffer.write(s.getName() + " ~ " + s.getArtist());

	    		if (s.getAlbum() != null) {
	    			buffer.write(" ~ " + s.getAlbum());
	    		}
	    			
	    		if (s.getYear() != 0)
	    			buffer.write(" ~ " + s.getYear());
	    		
	    		buffer.write(" | \n");
    	}		
	    		buffer.close();
	    		
	    	
    	
    }
    
    public void start(Stage primaryStage) throws Exception {
    	readSongs();

		//selected but should it display the first songs info?
		library.getSelectionModel().select(0);
		showSongInfo();

		// set listener for the items
		library
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSongInfo()
				);		
		
		primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
    
    public void showSongInfo() {
    	if (library.getSelectionModel().getSelectedIndex() != -1)
    		selectedID = library.getSelectionModel().getSelectedIndex();
    	if (storesongs.size() == 0) {
    		editname.setText("");
    		editartist.setText("");
    		editalbum.setText("");
    		edityear.setText("");;
    		editname.setEditable(false);
	    	editartist.setEditable(false);
	    	editalbum.setEditable(false);
	    	edityear.setEditable(false);
    	}
    	else {
	    	editname.setText(storesongs.get(selectedID).getName());
	    	editartist.setText(storesongs.get(selectedID).getArtist());
	    	editalbum.setText(storesongs.get(selectedID).getAlbum());
	    	String year = String.valueOf(storesongs.get(selectedID).getYear());
	    	if (storesongs.get(selectedID).getYear() == 0 ) edityear.setText("");
	    	else edityear.setText(year);
	    
	    	editname.setEditable(true);
	    	editartist.setEditable(true);
	    	editalbum.setEditable(true);
	    	edityear.setEditable(true);
    	}
    }

    public void showSongInfo(int index) {
    	
	    	editname.setText(storesongs.get(selectedID).getName());
	    	editartist.setText(storesongs.get(selectedID).getArtist());
	    	editalbum.setText(storesongs.get(selectedID).getAlbum());
	    	String year = String.valueOf(storesongs.get(selectedID).getYear());
	    	if (storesongs.get(selectedID).getYear() == 0 ) edityear.setText("");
	    	else edityear.setText(year);
    	
    }
}