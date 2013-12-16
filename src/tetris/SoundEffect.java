package tetris;

import java.io.*;

import javax.sound.sampled.*;

public enum SoundEffect {
	START("wav/start.wav"),
	END("wav/end.wav"),
	LINE("wav/line.wav"),
	LEVEL("wav/level.wav"),
	TOCK("wav/tock.wav");
	
	private Clip clip;
	
	SoundEffect(String soundFile) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(soundFile));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if (clip.isRunning())
			clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	static void init() {
		values();
	}

}
