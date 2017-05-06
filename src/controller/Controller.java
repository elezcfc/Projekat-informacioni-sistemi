package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.netlib.util.doubleW;

import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.tags.Tag;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import domen.Picture;
import download.PhotoFinder;
import download.PhotoInfoDownloader;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Attribute;
import weka.core.SparseInstance;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

import com.google.gson.reflect.TypeToken;

public class Controller {
	private static Controller instance;

	private Controller() {

	}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	public void searchPhotos(String searchParam) throws FlickrException {
		// TODO Auto-generated method stub
		PhotoFinder pf = new PhotoFinder();
		PhotoList<Photo> list = pf.searchForPhotos(searchParam);
		for (Photo photo : list) {
			System.out.println(photo);
		}
		getPhotoInfo(list);
		// createJSON();
	}

	private void getPhotoInfo(PhotoList list) throws FlickrException {
		// TODO Auto-generated method stub
		PhotoInfoDownloader pid = new PhotoInfoDownloader();
		pid.getPhotoInfo(list);
	}

	private Picture[] readFromJson() throws JsonSyntaxException,
			JsonIOException, FileNotFoundException {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Picture[] pictures = gson.fromJson(new FileReader(
				"data/pictureInfo.json"), Picture[].class);
		for (Picture picture : pictures) {
			System.out.println(picture);
		}
		return pictures;
	}

	public void createDS() throws JsonSyntaxException, JsonIOException,
			ParseException, IOException {
		Picture[] pictures = readFromJson();
		FastVector atts;
		Instances data;
		double[] vals;
		// creating attributes
		atts = new FastVector();
		atts.add(new Attribute("title", (FastVector) null));
		atts.add(new Attribute("tags", (FastVector) null));
		atts.add(new Attribute("dates", (FastVector) null));
		System.out.println(atts.size());
		data = new Instances("Dataset", atts, 0);
		for (int i = 0; i < pictures.length; i++) {
			Picture p = pictures[i];
			vals = new double[data.numAttributes()];
			// setting values
			vals[0] = data.attribute("title").addStringValue(p.getTitle());
			String tags = "";
			for (int j = 0; j < p.getTags().size(); j++) {
				tags = tags + ", " + p.getTags().get(j).getValue();
			}
			vals[1] = data.attribute("tags").addStringValue(tags);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			String dates = "";
			for (int j = 0; j < p.getDates().length; j++) {
				String date = sdf.format(p.getDates()[j]);
				dates = dates + ", " + date;
			}
			vals[2] = data.attribute("dates").addStringValue(dates);

			data.add(new DenseInstance(1.0, vals));

		}
		System.out.println(data);
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File("./data/test.arff"));
		saver.writeBatch();

	}

	public String izvrsiKlasterizaciju() throws Exception {
		// TODO Auto-generated method stub
		String fileName = "./data/test.arff";
		DataSource loader = new DataSource(fileName);
		Instances data = loader.getDataSet();
		
		SimpleKMeans kMeansCLusterer = new SimpleKMeans();
		kMeansCLusterer.setNumClusters(5);
		kMeansCLusterer.setDisplayStdDevs(true);
		
		kMeansCLusterer.buildClusterer(data);
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(kMeansCLusterer);
		eval.evaluateClusterer(data);

		return eval.clusterResultsToString();
	}
}
