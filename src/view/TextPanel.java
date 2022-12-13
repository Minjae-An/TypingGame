package view;

import javax.swing.*;
import java.awt.*;

import constant.Icon;

/**
 * 떨어지는 단어 & 캐릭터 컴포넌트 클래스
 * 각 컴포넌트는 별도의 스레드로 작동한다.
 */
public class TextPanel extends JPanel implements Runnable {
    private long delay;
    private int fallingDistance=5;
    private JLabel label;
    private JLabel imgLabel;
    private Thread th;

    /*
        BorderLayout 을 이용해 단어 라벨과 캐릭터 이미지 라벨을
        조합한 TextPanel 형성
     */
    public TextPanel(String text, long delay) {
        setSize(130, 100);

        this.delay = delay;

        // 단어 라벨 설정
        label = new JLabel(text, SwingConstants.CENTER);

        label.setSize(text.length(), 40);
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);

        // 캐릭터 이미지 라벨 설정
        Image img =Icon.TEXTPANEL_ICON.getImage();
        img = img.getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        ImageIcon icon= new ImageIcon(img);

        imgLabel = new JLabel(icon);
        add(imgLabel, BorderLayout.CENTER);

        // 배경을 투명하게
        this.setBackground(new Color(255, 0, 0, 0));

        th = new Thread(this);
        th.start();
    }

    // 단어 반환 메서드
    public String getText() {
        return label.getText();
    }

    // 스레드 종료 메서드
    public void endRun(){
        th.interrupt();
    }

    // 낙하 중지 메서드
    public void pauseFalling(){
        fallingDistance=0;
    }

    // 낙하 재개 메서드
    public void resumeFalling(){
        fallingDistance=5;
    }

    /*
        TextPanel의 run 메서드 구현
        지정된 거리만큼 낙하를 지속하다 화면에서 벗어나면
        스레드를 중지하고, 스스로를 화면에서 제거한다.
     */
    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(delay);

                if(getY()>=getParent().getHeight())
                    break;
                setLocation(getX(), getY()+fallingDistance);

            }
            catch(InterruptedException e){
                break;
            }
        }
        getParent().remove(this);
    }
}
