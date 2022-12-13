package view.game;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import constant.Icon;
import manager.MusicManager;
import view.MainFrame;

/**
 * 게임 화면 Panel 클래스
 */
public class GamePanel extends JPanel {
    private MainFrame rootFrame;
    private JButton playButton, pauseButton;

    private JToolBar toolbar;
    private GameArea gameArea;
    private InfoArea infoArea;
    private JSplitPane mainPane;
    private boolean isStop=false;

    public GamePanel(MainFrame rootFrame){
        this.rootFrame=rootFrame;
        setLayout(new BorderLayout());

        setButtons();
        makeToolBar();
        makePane();

        setButtonEvents();
        // 배경 음악 재생
        MusicManager.play(MusicManager.MusicType.GAME);
    }

    // 게임 일시중지, 재개 버튼 세팅 메서드
    private void setButtons(){
        playButton=new JButton(Icon.PLAYBTN_ICON);
        playButton.setRolloverIcon(Icon.PLAYBTN_ROLLOVER_ICON);
        playButton.setPressedIcon(Icon.PLAYBTN_PRESSED_ICON);

        pauseButton=new JButton(Icon.PAUSEBTN_ICON);
        pauseButton.setRolloverIcon(Icon.PAUSEBTN_ROLLOVER_ICON);
        pauseButton.setPressedIcon(Icon.PAUSEBTN_PRESSED_ICON);
    }

    // 툴바 세팅 메서드
    private void makeToolBar(){
        toolbar=new JToolBar();

        toolbar.add(playButton);
        toolbar.add(pauseButton);

        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);
    }

    // JSplitPane 를 통해 화면에서 게임, 정보 구역을 설정하는 메서드
    private void makePane(){
        mainPane=new JSplitPane();
        mainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        mainPane.setDividerLocation(700);
        mainPane.setEnabled(false);
        mainPane.setDividerSize(0);

        infoArea=new InfoArea();
        gameArea=new GameArea(this, infoArea);

        mainPane.setLeftComponent(gameArea);
        mainPane.setRightComponent(infoArea);
        add(mainPane, BorderLayout.CENTER);
    }

    // 버튼 이벤트 세팅 메서드
    private void setButtonEvents(){
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isStop) return;

                isStop=true;
                gameArea.pauseGame();
            }
        });

        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!isStop) return;

                isStop=false;
                gameArea.resumeGame();
            }
        });
    }

    // 게임 종료시 화면 전환을 위한 메서드
    public void moveToEndPanel(String playerID, int playerScore){
        rootFrame.changeToEndPanel(playerID, playerScore);
    }
}




