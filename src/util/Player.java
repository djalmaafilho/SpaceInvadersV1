package util;

import java.io.File;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Player {

	private Sequence sequence;
	private Sequencer sequencer;
	public Player() {

	}

	/**
	 * Toca um mid por vez
	 * 
	 * @param caminhoMID
	 * @param emLoop
	 */
	public synchronized void tocarMID(String caminhoMID, boolean emLoop) {
		
		try {
			sequence = MidiSystem.getSequence(new File(caminhoMID));
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);
			if (emLoop) {
				sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
			}
			sequencer.start();
		} catch (Exception e) {
			JogoUtil.salvarLog(e);
		}
	}

	public synchronized void pararMID() {
		if (sequencer != null && sequencer.isOpen() && sequencer.isRunning()) {
			sequencer.stop();
			sequencer.close();
		}
	}

	public synchronized void tocarAudio(String caminhoSom) {
		File soundFile = new File(caminhoSom);
		AudioInputStream sound;
		try {
			sound = AudioSystem.getAudioInputStream(soundFile);
			DataLine.Info info = new DataLine.Info(Clip.class,
					sound.getFormat());
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(sound);
			clip.start();
			Thread.sleep(10);
		} catch (Exception e) {
			JogoUtil.salvarLog(e);
		}
	}
}