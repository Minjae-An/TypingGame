package view.game;

import javax.swing.*;
import java.awt.*;

import constant.Icon;
import constant.FontStyle;

/**
 * 게임 이용자 정보 표시 Panel 클래스
 */
public class InfoArea extends JPanel {
    // 라이프 포인트 표시를 위한 boolean 배열
    private boolean[] isLife=new boolean[3];

    private JLabel textLabel=new JLabel("SCORE");
    private JLabel scoreLabel=new JLabel("0", SwingConstants.CENTER);

    public InfoArea(){
        setLayout(null);

        // 정보 표시를 위한 label 세팅
        textLabel.setSize(120, 40);
        textLabel.setLocation(90, 180);
        textLabel.setFont(FontStyle.INFO_FONT);
        add(textLabel);

        scoreLabel.setSize(70, 40);
        scoreLabel.setLocation(120, 230);
        scoreLabel.setFont(FontStyle.INFO_FONT);
        add(scoreLabel);

        // 초기 라이프 포인트는 3
        for(int i=0; i<isLife.length; i++) isLife[i]=true;
    }

    // 화면 연출을 위한 paintComponent Override
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x=65;

        // isLife 값이 true 일때만 아이콘 표시
        for(Boolean b:isLife){
            if(b) g.drawImage(Icon.LIFE_ICON.getImage(), x, 330,
                    Icon.LIFE_ICON.getIconWidth()/3,
                    Icon.LIFE_ICON.getIconHeight()/3, this);
            x+=Icon.LIFE_ICON.getIconWidth()/3+10;
        }
    }

    // 표시되는 사용자 점수 조정 메서드
    public void setScore(String playerScore){
        scoreLabel.setText(playerScore);
    }

    // 표시되는 라이프 포인트 조정 메서드
    public void setLifePoint(int lifePoint){
        switch(lifePoint){
            case 2:
                isLife[0]=false; break;
            case 1:
                isLife[1]=false; break;
            case 0:
                isLife[2]=false; break;
        }

        repaint();
    }
}
