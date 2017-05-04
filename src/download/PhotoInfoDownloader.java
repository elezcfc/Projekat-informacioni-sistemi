package download;

import java.io.IOException;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import photo.Picture;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.JSONLoader;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photosets.PhotosetsInterface;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PhotoInfoDownloader {

	private static String apiKey = "dcd577b951223768ca2e186a0562345d";
	private static String secret = "0b4ab0f7e4a06490";
	private static String idSet = "72157640120139333";

	public static void createJson(List<Picture> pictures) throws FlickrException {
		//Picture p = picture;
		try {
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter writter = new FileWriter("data/pictureInfo.json");
			writter.write(gson.toJson(pictures));
			writter.close();			
			System.out.println("JSON file kreiran");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Picture vratiSliku(String id) throws FlickrException{
		Transport t = new REST();
		Flickr f = new Flickr(apiKey, secret, t);
		PhotosInterface photosInterface = f.getPhotosInterface();
		Photo slika = photosInterface.getInfo(id, secret);
		Picture p = new Picture(slika.getId(), slika.getTitle());
		for(int i = 0; i < slika.getTags().size(); i++){
			if(((ArrayList) slika.getTags()).get(i) != null){
				p.tags.add(((ArrayList) slika.getTags()).get(i));
			}	
		}
		Date dates [] = new Date[]{slika.getDatePosted(), slika.getDateTaken()};
		p.setDates(dates);
		return p;
	}
	
	private static List<Picture> getPictures(String photosetId) throws FlickrException{
		Transport t = new REST();
		Flickr f = new Flickr(apiKey, secret, t);
		List<Picture> photoset = new ArrayList();
		//String photosetId = "72157640120139333";
	    PhotosetsInterface psi = f.getPhotosetsInterface();
	    Collection<Photo> photos = psi.getPhotos(photosetId,111, 1);
	    for(Photo p : photos){
	        Picture picture = vratiSliku(p.getId());
	        photoset.add(picture);
	    }
		return photoset;		
	}
	/*private static LinkedList<Picture> loadFromJson() throws IOException{
		LinkedList<Picture> pictures = new LinkedList<Picture>();
		String id;
		String title;
		ArrayList tags;
		Date[] dates;
		
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("data/pictureInfo.json"));
		//pictures = gson.fromJson(reader, Picture.class);
		JsonArray ceoNiz = (JsonArray) new JsonParser().parse(reader);
		
		for (JsonElement jsonElement : ceoNiz) {
			JsonObject photo = jsonElement.getAsJsonObject();
			id = photo.getAsJsonObject("id").toString();
			title  = photo.getAsJsonObject("title").toString();
			tags = new ArrayList();
			JsonArray tagovi = photo.getAsJsonArray();
			
			for (JsonElement jsonTag : tagovi) {
				JsonObject tag = (JsonObject) jsonTag;
				String value = tag.getAsJsonObject("value").toString();
				tags.add(value);
			}
			/*JsonArray datumi = photo.getAsJsonArray();
			for(int z = 0; z < datumi.size(); z++){
				JsonObject datum = datumi.get(i).getAsJsonObject();
				Date prvi = (Date)datum.getAsJsonObject().toString();
			}
			Picture p = new Picture(id, title);
			System.out.println(p.toString());
		}
		}
		return pictures;
	}*/

	public void getPhotoInfo(PhotoList<Photo> list) throws FlickrException {
		// TODO Auto-generated method stub
		Flickr f = new Flickr(apiKey, secret, new REST());
		List<Picture> pictures = new ArrayList<Picture>();
 		for (Photo photo : list) {
			String id = photo.getId();
			PhotosInterface pi = f.getPhotosInterface();
			Photo slika = pi.getInfo(id, secret);
			Picture p = new Picture(slika.getId(), slika.getTitle());
			for(int i = 0; i < slika.getTags().size(); i++){
				if(((ArrayList) slika.getTags()).get(i) != null){
					p.tags.add(((ArrayList) slika.getTags()).get(i));
				}	
			}
			Date dates [] = new Date[]{slika.getDatePosted(), slika.getDateTaken()};
			p.setDates(dates);
			pictures.add(p);
		}
 		createJson(pictures);
	}
}
