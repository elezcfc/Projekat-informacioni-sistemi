package photo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Picture {

	private String id;
	private String owner;
	private String title;
	public ArrayList tags;
	private String location;
	private Date [] dates;
	
	public Picture (String i, String o, String t, String l){
		id = i;
		owner = o;
		title = t;
		tags = new ArrayList();
		location = l;
		dates = new Date[2];
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date[] getDates() {
		return dates;
	}

	public void setDates(Date[] dates) {
		this.dates = dates;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Picture [id=" + id + ", owner=" + owner + ", title=" + title
				+ ", tags=" + tags + ", location=" + location + ", dates="
				+ Arrays.toString(dates) + "]";
	}
	
	
}
