package manager;

import view.game.GameArea;
import view.TextPanel;

import java.util.Vector;

/**
 * 게임 구성 컴포넌트 TextPanel 관리 클래스
 */
public class TextPanelManager implements Runnable{
    // 상수 필드들
    private final long delay=2000;
    private final int MAX_TEXTPANEL_NUMBER=7;
    private final long MIN_TEXTPANEL_DELAY=100;

    // 게임 작동시 필요한 제어 변수 필드들
    private long textPanelDelay=700;
    private int lifePoint=3;
    private boolean isStop=false;
    private boolean isEnd=false;

    private Thread thread;
    private GameArea gameArea;
    private RandomTermSelector rts=RandomTermSelector.getInstance();

    // 현재 화면에 존재하는 TextPanel 들을 저장하는 Vector
    private Vector<TextPanel> currentTerms=new Vector<>();
    public TextPanelManager(GameArea gameArea){
        this.gameArea=gameArea;

        thread=new Thread(this);
        thread.start();
    }

    /*
        유효하지 않은 TextPanel 을 제거하고, 조건에 따라 새로운
        TextPanel 을 게임 화면에 생성하는 run 메서드
     */
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(delay);
                if(isEnd) break; // 게임 종료시 스레드 종료
                checkInvalidPanel();
                if(!isStop && gameArea.getComponentCount()<MAX_TEXTPANEL_NUMBER){
                    TextPanel tPanel=new TextPanel(rts.getRandomTerm(), textPanelDelay);
                    currentTerms.add(tPanel);
                    int x=(int)(Math.random()*(gameArea.getWidth()/2+50));

                    tPanel.setLocation(x, 0);

                    gameArea.add(tPanel);
                    gameArea.revalidate();
                    gameArea.repaint();
                }
            }
            catch(InterruptedException e){
                break;
            }
        }
    }

    /*
        현재 화면에 존재하는 TextPanel 중 사용자 입력과 일치하는
        것이 있는지 검사하는 메서드, currentTerms 에 대한 동기화를
        위해 synchronized 키워드 사용
     */
    public boolean playerTyping(String text) {

        synchronized (this) {
            for(TextPanel tp:currentTerms){
                if(tp.getText().equals(text)){
                    tp.endRun();
                    currentTerms.remove(tp);
                    return true;
                }
            }

            return false;
        }
    }

    // 화면 밖으로 벗어난 TextPanel 을 찾아 currentTerms 에서 제거하는 메서드
    private void checkInvalidPanel(){
        for(int i=currentTerms.size()-1; i>=0; i--){
            if(currentTerms.get(i).getParent()==null){
                currentTerms.remove(i);
                gameArea.passLifePoint(--lifePoint);
            }
        }
    }

    // TextPanel 들의 낙하를 중지하는 메서드
    public void stopAll(){
        synchronized (this){
            if(isStop) return;

            isStop=true;
            for(TextPanel tp:currentTerms)
                tp.pauseFalling();
        }
    }

    // TextPanel 들의 낙하를 재개하는 메서드
    public void resumeAll(){
        synchronized (this){
            if(!isStop) return;

            isStop=false;
            for(TextPanel tp:currentTerms)
                tp.resumeFalling();
        }
    }

    // 난이도 증가 메서드
    public void increaseDifficulty(){
        if(textPanelDelay<=MIN_TEXTPANEL_DELAY) return;
        textPanelDelay-=300;
    }

    // 게임 종료 메서드
    public void gameOver(){
        synchronized (this){
            for(TextPanel tp:currentTerms) tp.endRun();
        }
        isEnd=true;
    }
}
