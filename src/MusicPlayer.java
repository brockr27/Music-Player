import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.IOException;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

/**
 * This is the main class for the Music Player project. This is where the actual program will run.
 * @author Brock
 */
public class MusicPlayer {

	private JFrame frame;
	
	final static String LIBRARYPANEL = "Layout with Library";
	final static String YOUTUBEPANEL = "Layout with Youtube";
	final static String SPOTIFYPANEL = "Layout with Spotify";
	
	private static CardLayout cLayout;
	static YouTube youtube;
	
	// A fake database list
	static ArrayList<String> databaseList = new ArrayList<String>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Populate the fake database list with youtube songs
		databaseList.add("https://www.youtube.com/watch?v=fIeCx6eZjJk");
		databaseList.add("https://www.youtube.com/watch?v=ceAqZkjIFhI");
		databaseList.add("https://www.youtube.com/watch?v=LBGPJdkfT9U");
		databaseList.add("https://www.youtube.com/watch?v=vA_LgDo3WcI");
		databaseList.add("https://www.youtube.com/watch?v=4-PkAQcuZOw");
		
		/* Commented out when I don't want to use up my YouTube quota
		 ** FOR DEBUG/TESTING PURPOSES ONLY **
		VideoData vData;
		songList = new String[databaseList.size()];
		for(int i = 0; i < databaseList.size(); i++) {
			vData = getVideoData(databaseList.get(i));
			songList[i] = vData.getTitle();
		}*/
		
		NativeInterface.open();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicPlayer window = new MusicPlayer();
					window.frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MusicPlayer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel views = new JPanel(new CardLayout());
		views.setBounds(358, 11, 816, 794);
		frame.getContentPane().add(views);
		cLayout = (CardLayout)(views.getLayout());
		
		// PLAYLIST PANEL
		JPanel pnlPlaylist = new JPanel();
		pnlPlaylist.setBounds(10, 11, 338, 839);
		frame.getContentPane().add(pnlPlaylist);
		pnlPlaylist.setLayout(null);
		
		JScrollPane spPlaylist = new JScrollPane();
		spPlaylist.setBounds(0, 30, 338, 779);
		pnlPlaylist.add(spPlaylist);
		
		// ** FOR DEBUG/TESTING PURPOSES ONLY **
		// This is used when I don't want to keep making calls to Youtube API and use up my quota
		String[] tmpList = new String[databaseList.size()];
		for(int i = 0; i < databaseList.size(); i++)
			tmpList[i] = databaseList.get(i);
		
		JList lstPlaylist = new JList(tmpList);
		spPlaylist.setViewportView(lstPlaylist);
		lstPlaylist.setSelectedIndex(0);
		
		JTextField txtPlaylistName = new JTextField();
		txtPlaylistName.setEditable(false);
		txtPlaylistName.setBounds(0, 0, 338, 30);
		pnlPlaylist.add(txtPlaylistName);
		txtPlaylistName.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtPlaylistName.setText("Playlist Name");
		txtPlaylistName.setColumns(10);
		
		JButton btnAddToPlaylist = new JButton("Add song to playlist");
		btnAddToPlaylist.setBounds(0, 809, 338, 30);
		pnlPlaylist.add(btnAddToPlaylist);
		
		// YOUTUBE PANEL VIEW
		JPanel pnlYoutube = new JPanel();
		pnlYoutube.setBounds(0, 0, 816, 794);
		pnlYoutube.setLayout(null);
		
		// Youtube Web Browser
		// *NOTE* this is what's causing the "widget is disposed" error
		// However it seems to be a bug caused by SWT, so I'll maybe fix later
		JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBounds(10, 11, 796, 772);
		webBrowser.setBarsVisible(false);
		pnlYoutube.add(webBrowser);
		views.add(pnlYoutube, YOUTUBEPANEL);
				
		// LIBRARY PANEL VIEW
		JPanel pnlLibrary = new JPanel();
		pnlLibrary.setBounds(0, 0, 816, 794);
		pnlLibrary.setLayout(null);

		Canvas cnvAlbumArt = new Canvas();
		cnvAlbumArt.setBounds(200, 60, 400, 400);
		pnlLibrary.add(cnvAlbumArt);
		
		JTextField txtSongName = new JTextField();
		txtSongName.setText("Song Name");
		txtSongName.setHorizontalAlignment(SwingConstants.CENTER);
		txtSongName.setBounds(247, 493, 318, 20);
		pnlLibrary.add(txtSongName);
		txtSongName.setColumns(10);
		
		JTextField txtArtist = new JTextField();
		txtArtist.setHorizontalAlignment(SwingConstants.CENTER);
		txtArtist.setText("Artist");
		txtArtist.setBounds(257, 524, 296, 20);
		pnlLibrary.add(txtArtist);
		txtArtist.setColumns(10);
		views.add(pnlLibrary, LIBRARYPANEL);
		
		// SPOTIFY PANEL VIEW
		JPanel pnlSpotify = new JPanel();
		pnlSpotify.setBounds(0, 0, 816, 794);
		views.add(pnlSpotify, SPOTIFYPANEL);
				
		// SONG SLIDER PANEL
		JPanel pnlSongSlider = new JPanel();
		pnlSongSlider.setBounds(358, 808, 816, 53);
		frame.getContentPane().add(pnlSongSlider);
		pnlSongSlider.setLayout(null);
		
		JButton btnPreviousSong = new JButton("|<");
		btnPreviousSong.setBounds(10, 11, 45, 30);
		pnlSongSlider.add(btnPreviousSong);
		
		JButton btnPlay = new JButton(">");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = lstPlaylist.getSelectedIndex();
				// Here you'd put the if(YouTube) then YouTube, if(library) then library when thats all finished
				// but since its only YouTube right now, just do that
				if(cLayout == null) 
					cLayout = (CardLayout)(views.getLayout());
				cLayout.show(views, YOUTUBEPANEL);
				webBrowser.navigate(databaseList.get(index));
			}
		});
		btnPlay.setBounds(65, 11, 45, 30);
		pnlSongSlider.add(btnPlay);
		
		JButton btnNextSong = new JButton(">|");
		btnNextSong.setBounds(761, 11, 45, 30);
		pnlSongSlider.add(btnNextSong);
		
		JSlider sldSong = new JSlider();
		sldSong.setBounds(120, 11, 630, 31);
		pnlSongSlider.add(sldSong);
	}
	
	/** Calls YouTube's API with the given url and performs a search.
	 * It then retrieves the top video, and gets the title and puts it into a VideoData object.
	 * A single video take ~100 Units from the API Quota. We have 1 million a day.
	 * @param url
	 * @return VideoData
	 */
	private static VideoData getVideoData(String url) {
		VideoData vData = new VideoData();
		
		try {
			youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
				public void initialize(HttpRequest request) throws IOException {
				}
			}).setApplicationName("Youtube search example").build();
		
			YouTube.Search.List search = youtube.search().list("id, snippet");
			search.setKey("AIzaSyDkVHCZJgU7v4VB8wXVhQMCZ1WwNkRiJKY");
			search.setQ(url);
			search.setType("video");
			search.setFields("items(id/kind,id/videoId,snippet/title)");
			search.setMaxResults((long)1);
			
			SearchListResponse searchResponse = search.execute();
			List<SearchResult> searchResultList = searchResponse.getItems();
			if(searchResultList != null) {
				Iterator<SearchResult> iterator = searchResultList.iterator();
				
				while(iterator.hasNext()) {
					SearchResult singleVideo = iterator.next();
					ResourceId rID = singleVideo.getId();
					if(rID.getKind().equals("youtube#video")) {
						vData.setTitle(singleVideo.getSnippet().getTitle());
					}
					
				}
				
			}
		} catch (GoogleJsonResponseException e) {
			System.err.println("There was a swervice error: " + e.getDetails().getCode() + " : "
					+ e.getDetails().getMessage());
		} catch (IOException e) {
			System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
		return vData;
	}
}
