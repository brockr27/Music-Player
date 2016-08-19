import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import java.awt.Canvas;
import javax.swing.SwingConstants;

/**
 * This is the main class for the Music Player project. This is where the actual program will run.
 * @author Brock
 * Dakota's code
 */
public class MusicPlayer {

	private JFrame frame;
	private JTextField txtPlaylistName;
	private JTextField txtSongName;
	private JTextField txtArtist;
	
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
				MusicPlayer window = new MusicPlayer();
				window.frame.setVisible(true);
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
		
		JPanel views = new JPanel();
		views.setBounds(358, 11, 816, 794);
		frame.getContentPane().add(views);
		views.setLayout(null);
		
		// YOUTUBE PANEL VIEW
		JPanel pnlYoutube = new JPanel();
		pnlYoutube.setBounds(0, 0, 816, 794);
		views.add(pnlYoutube);
		
		JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBounds(10, 10, 500, 500);
		webBrowser.setBarsVisible(false);
		pnlYoutube.add(webBrowser);
		
		// LIBRARY PANEL VIEW
		JPanel pnlLibrary = new JPanel();
		pnlLibrary.setBounds(0, 0, 816, 794);
		views.add(pnlLibrary);
		pnlLibrary.setLayout(null);
		
		Canvas cnvAlbumArt = new Canvas();
		cnvAlbumArt.setBounds(200, 60, 400, 400);
		pnlLibrary.add(cnvAlbumArt);
		
		txtSongName = new JTextField();
		txtSongName.setText("Song Name");
		txtSongName.setHorizontalAlignment(SwingConstants.CENTER);
		txtSongName.setBounds(247, 493, 318, 20);
		pnlLibrary.add(txtSongName);
		txtSongName.setColumns(10);
		
		txtArtist = new JTextField();
		txtArtist.setHorizontalAlignment(SwingConstants.CENTER);
		txtArtist.setText("Artist");
		txtArtist.setBounds(257, 524, 296, 20);
		pnlLibrary.add(txtArtist);
		txtArtist.setColumns(10);
		
		// SPOTIFY PANEL VIEW
		JPanel pnlSpotify = new JPanel();
		pnlSpotify.setBounds(0, 0, 816, 794);
		views.add(pnlSpotify);
		
		// SONG SLIDER PANEL
		JPanel pnlSongSlider = new JPanel();
		pnlSongSlider.setBounds(358, 808, 816, 53);
		frame.getContentPane().add(pnlSongSlider);
		pnlSongSlider.setLayout(null);
		
		JButton btnPreviousSong = new JButton("|<");
		btnPreviousSong.setBounds(10, 11, 45, 30);
		pnlSongSlider.add(btnPreviousSong);
		
		JButton btnPlay = new JButton(">");
		btnPlay.setBounds(65, 11, 45, 30);
		pnlSongSlider.add(btnPlay);
		
		JButton btnNextSong = new JButton(">|");
		btnNextSong.setBounds(761, 11, 45, 30);
		pnlSongSlider.add(btnNextSong);
		
		JSlider sldSong = new JSlider();
		sldSong.setBounds(120, 11, 630, 31);
		pnlSongSlider.add(sldSong);
		
		// PLAYLIST PANEL
		JPanel pnlPlaylist = new JPanel();
		pnlPlaylist.setBounds(10, 11, 338, 839);
		frame.getContentPane().add(pnlPlaylist);
		pnlPlaylist.setLayout(null);
		
		JScrollPane spPlaylist = new JScrollPane();
		spPlaylist.setBounds(0, 30, 338, 779);
		pnlPlaylist.add(spPlaylist);
		
		txtPlaylistName = new JTextField();
		txtPlaylistName.setEditable(false);
		txtPlaylistName.setBounds(0, 0, 338, 30);
		pnlPlaylist.add(txtPlaylistName);
		txtPlaylistName.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtPlaylistName.setText("Playlist Name");
		txtPlaylistName.setColumns(10);
		
		JButton btnAddToPlaylist = new JButton("Add song to playlist");
		btnAddToPlaylist.setBounds(0, 809, 338, 30);
		pnlPlaylist.add(btnAddToPlaylist);
	}
}
