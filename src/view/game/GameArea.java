package view.game;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import constant.Icon;
import constant.FontStyle;

import manager.TextPanelManager;

/**
 * 게임이 이뤄지는 Panel 클래스
 */

public class GameArea extends JPanel {
    // 상수 필드들
    private final int MAX_TEXT_LENGTH=20;
    private final int MAX_PLAYER_ID_LENGTH=8;
    private final int SCORE=10;

    private int playerScore=0;

    // 난이도 표시를 위한 boolean 배열
    private boolean[] isValid=new boolean[3];
    private JTextField typingField=new JTextField();
    private GamePanel gamePanel;
    private TextPanelManager tpManager;
    private InfoArea infoArea;

    public GameArea(GamePanel gamePanel, InfoArea infoArea){
        setLayout(null);
        this.gamePanel=gamePanel;
        this.infoArea=infoArea;

        // 사용자 입력을 받는 텍스트 필드 설정
        typingField.setSize(300, 40);
        typingField.setLocation(200, 540);
        typingField.setFont(FontStyle.TYPING_FONT);
        typingField.setBorder(new LineBorder(Color.BLACK, 2));
        add(typingField);

        isValid[0]=true; // 시작 난이도 1

        // 텍스트 필드 이벤트 설정
        typingField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String text=typingField.getText();
                if(text.equals("")) return; // 공백 입력시 반응하지 않음

                // 최대 길이 초과 입력시 반응하지 않음
                if(text.length()<=MAX_TEXT_LENGTH){
                    // 일치하는 TextPanel 이 있는지 판별하여 점수, 표시되는 점수 조정
                    if(tpManager.playerTyping(text)){
                        playerScore+=SCORE;
                        infoArea.setScore(String.valueOf(playerScore));
                    }

                    // 점수 100 도달시 난이도 2으로 증가하고 난이도 표시 조정
                    if(playerScore==100 && !isValid[1]){
                        isValid[1]=true;
                        tpManager.increaseDifficulty();
                    }
                    // 점수 200 도달시 난이도 3으로 증가하고 난이도 표시 조정
                    else if(playerScore==200 && !isValid[2]){
                        isValid[2]=true;
                        tpManager.increaseDifficulty();
                    }
                }

                typingField.setText(""); // 입력을 받을 때마다 공백으로 초기화
            }
        });

        // GameArea 생성후 tpManager 초기화를 위한 ComponentListener, 작업후 삭제
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tpManager=new TextPanelManager(GameArea.this);
                removeComponentListener(this);
            }
        });
    }

    // 화면 연출을 위한 paintComponent Override
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Icon.GAMEAREA_BACK_ICON.getImage(), 0, 0, getWidth(), getHeight(), this);

        int x=10;

        // isValid 배열값이 true 일 때만 난이도 표시
        for(Boolean b:isValid){
            if(b) g.drawImage(Icon.DIFFICULTY_ICON.getImage(), x, 10,
                    Icon.DIFFICULTY_ICON.getIconWidth()/2,
                    Icon.DIFFICULTY_ICON.getIconHeight()/2, this);
            x+=25;
        }
    }

    // infoArea 에 표시되는 라이프 포인트 조정을 위한 메서드
    public void passLifePoint(int lifePoint){
        infoArea.setLifePoint(lifePoint);

        if(lifePoint==0) gameOver();

    }

    // 게임 일시 정지 메서드
    public void pauseGame(){
        tpManager.stopAll();
        typingField.setEnabled(false);
    }

    // 게임 재개 메서드
    public void resumeGame(){
        tpManager.resumeAll();
        typingField.setEnabled(true);
    }

    // 게임 종료 메서드
    private void gameOver(){
        // TextPanelManager Thread 종료
        tpManager.gameOver();

        /*
            사용자 ID를 입력받는다.
            공백을 입력하거나, 창을 닫으면 입력을 그냥 다시 받고
            최대 길이를 초과하는 ID 를 입력하면 안내 메시지 출력후
            입력을 다시 받는다.
         */
        String playerID=null;
        while(true){
            playerID=(String)JOptionPane.showInputDialog(this,
                    "ID를 입력하세요 (최대 "+MAX_PLAYER_ID_LENGTH+"자)",
                    "Game Over", JOptionPane.PLAIN_MESSAGE);
            if(playerID.length()>MAX_PLAYER_ID_LENGTH){
                JOptionPane.showMessageDialog(null,
                        "ID는 "+MAX_PLAYER_ID_LENGTH+"이하여야 합니다",
                        "안내", JOptionPane.PLAIN_MESSAGE, null);
                continue;
            }
            if(playerID!=null && !playerID.equals("")) break;
        }

        // 입력받은 사용자 ID와 점수를 엔딩 화면으로 전환하며 넘겨준다.
        gamePanel.moveToEndPanel(playerID, playerScore);
    }
}
