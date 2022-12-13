package manager;

import constant.Music;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * 게임내 재생되는 음악을 관리하는 클래스
 */
public class MusicManager {
    /**
     * enum 을 활용하여 재생되는 음악의 유형 정의
     * play 메서드에서는 현재 재생중인 음악을 중지하고
     * musicType 에 주어진 새로운 음악을 재생
     */
    public enum MusicType{START, EDIT, GAME, END};
    private static File audioFile;
    private static Clip audioClip;

    public static void play(MusicType musicType){
        if(audioClip!=null){
            audioClip.stop();
            audioClip.close();
        }

        switch(musicType){
            case START:
                audioFile=Music.START_BGM; break;
            case EDIT:
                audioFile=Music.EDIT_BGM; break;
            case GAME:
                audioFile=Music.GAME_BGM; break;
            case END:
                audioFile=Music.END_BGM; break;
        }

        try{
            audioClip=AudioSystem.getClip();
            audioClip.open(AudioSystem.getAudioInputStream(audioFile));
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();
        }
        catch(Exception e){ e.printStackTrace(); }
    }
}
