
public class VideoData {
	private String title;
	private String id;
	
	public VideoData() {
		title = "";
		id = "";
	}
	
	public VideoData(String title, String id) {
		this.title = title;
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setID(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getID() {
		return id;
	}
}
