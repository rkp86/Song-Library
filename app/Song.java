package app;
//Roma Patel - rkp86
//Omer Farooq - ofs9

//import view.SongLibController;

public class Song implements Comparable<Song> {

	//instance variables
	private String name;
	private String artist;
	private String album;
	private int year;
	
	
	
	//constructors
	public Song () {
		name = null;
		artist = null;
		album = null;
		year = 0;
	}
	
	public Song (String name, String artist) {
		this.name = name;
		this.artist = artist;
		album = null;
		year = 0;
	}
	
	public Song (String name, String artist, String album) {
		this.name = name;
		this.artist = artist;
		if (album.length() != 0)
			this.album = album;
		this.year = 0;
	}
	
	public Song (String name, String artist, int year) {
		this.name = name;
		this.artist = artist;
		this.album = null;
		this.year = year;
	}
	
	public Song (String name, String artist, String album, int year) {
		this.name = name;
		this.artist = artist;
		if (album.length() != 0)
			this.album = album;
		this.year = year;
	}

	
	
	//getters
	public String getName() {
		return name;
	}
	public String getArtist() {
		return artist;
	}
	public String getAlbum() {
		return album;
	}
	public int getYear() {
		return year;
	}
	
	//setters
	public void setName(String name) {
		this.name=name;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public void setYear(int year) {
		this.year = year;
	}

	
	
	@Override
	public int compareTo(Song o) {
		// TODO Auto-generated method stub
		int result = this.getName().compareTo(((Song) o).getName());
        if(result == 0) {
            result = this.getArtist().compareTo(((Song) o).getArtist()); 
        }
        return result;
	}
	
	
	
	  @Override
	    public String toString() {
	        return ("Song: "+ name + "\n" + "Artist: " + artist 
	        		+ "\n" + "Album: " + album + "\n" + "Year: " + year);
	  }
	  
	  
	 
}
