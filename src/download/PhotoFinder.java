package download;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;

public class PhotoFinder {

	private  String apiKey = "dcd577b951223768ca2e186a0562345d";
	private  String secret = "0b4ab0f7e4a06490";
	private  String idSet = "72157640120139333";

	
	public PhotoFinder(){
		
	}
	
	public PhotoList<Photo> searchForPhotos(String param) throws FlickrException{
		Transport t = new REST();
		Flickr f = new Flickr(apiKey, secret, t);
		PhotosInterface photosInterface = f.getPhotosInterface();
		SearchParameters sp = new SearchParameters();
		sp.setText(param);
		PhotoList<Photo> list = photosInterface.search(sp, 0, 0);
		return list;
	}
}
