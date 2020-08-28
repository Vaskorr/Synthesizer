import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    private static Synthesizer synth;
    private static int id = 0;
    private static int notes[] = {52, 55, 57, 60, 62, 64, 67, 69, 72, 74, 76, 79, 81, 84, 86, 88};
    private static int imperial[] = {67, 67, 67, 63, 70, 67, 63, 70, 67, 74, 74, 74, 75, 70, 66, 63, 70, 67};
    static {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
    private static MidiChannel[] chan = synth.getChannels();
    private static void Tone(int noNum) {
        chan[0].noteOn(noNum, 100);
    }
    public static void NoTone(int noNum){
        chan[0].noteOff(noNum, 100);
        //chan[0].allNotesOff();
    }

    public static void main(String [] args) throws NativeHookException {
        Random random = new Random();
        chan[0].programChange(2);
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

            }

            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                int i = random.nextInt(notes.length);
                Tone(notes[i]);
                if(id == notes.length - 1){
                    id = 0;
                }else {
                    id += 1;
                }
                System.out.println("" + nativeKeyEvent.getKeyCode());
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
                NoTone(100-nativeKeyEvent.getKeyCode());
            }
        });
        int u = in.nextInt();
    }
}
