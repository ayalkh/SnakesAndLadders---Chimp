package control;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusicPlayer {
	private static BackgroundMusicPlayer instance = new BackgroundMusicPlayer();
	private MediaPlayer mediaPlayer;
	private boolean isPlaying = false;

	private BackgroundMusicPlayer() {
	}

	public static BackgroundMusicPlayer getInstance() {
		return instance;
	}

	public void playMusic(String soundFileName) {
		if (mediaPlayer != null) {
			// If music is currently playing, or if the user wants to restart the music
			if (isPlaying) {
				mediaPlayer.stop();
				isPlaying = false;
			} else {
				mediaPlayer.play();
				isPlaying = true;
				return; // Exit method to avoid reloading the media if already loaded
			}
		}

		try {
			Media media = new Media(getClass().getResource("/sound/" + soundFileName).toExternalForm());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();
			isPlaying = true;
		} catch (NullPointerException e) {
			System.err.println("Error loading sound file: " + soundFileName);
		}
	}

	public void toggleMusic() {
		if (mediaPlayer == null) {
			System.err.println("Music player not initialized.");
			return;
		}
		if (isPlaying) {
			mediaPlayer.pause(); // Use pause instead of stop to allow resuming
		} else {
			mediaPlayer.play();
		}
		isPlaying = !isPlaying;
	}
}