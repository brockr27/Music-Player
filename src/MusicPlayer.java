import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * This is the main class for the Music Player project. This is where the actual program will run.
 * @author Brock
 */
public class MusicPlayer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicPlayer window = new MusicPlayer();
					window.frame.setVisible(true);
				} catch (Exception e) {
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
