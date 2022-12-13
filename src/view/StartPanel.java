package view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import constant.Icon;
import constant.FontStyle;
import manager.MusicManager;

/**
 * 초기 화면 클래스
 */

public class StartPanel extends JPanel {
    private MainFrame rootFrame;
    private final String TITLE="HSU WEB TERMS TYPING GAME";
    private final Color BUTTON_COLOR=new Color(244, 255, 255);
    private final EmptyBorder BUTTON_BORDER=new EmptyBorder(0, 0, 0, 0);
    private JButton gameModeBtn=new JButton("game start");
    private JButton editModeBtn=new JButton("edit mode");

    // 버튼 클릭시 화면 전환을 위한 ActionListener 정의
    private ActionListener buttonActionListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b=(JButton)e.getSource();
            String str=b.getText();

            if(str.equals("game start"))
                rootFrame.changePanel(MainFrame.PanelName.GAME);
            else if(str.equals("edit mode"))
                rootFrame.changePanel(MainFrame.PanelName.EDIT);
        }
    };

    public StartPanel(MainFrame rootFrame){
        this.rootFrame=rootFrame;
        setLayout(null);

        // 버튼 세팅
        gameModeBtn.setSize(200, 50);
        editModeBtn.setSize(200, 50);

        gameModeBtn.setLocation(380, 400);
        editModeBtn.setLocation(380 ,500);

        gameModeBtn.setBorder(BUTTON_BORDER);
        editModeBtn.setBorder(BUTTON_BORDER);

        gameModeBtn.setBackground(BUTTON_COLOR);
        editModeBtn.setBackground(BUTTON_COLOR);

        gameModeBtn.setFont(FontStyle.BUTTON_FONT);
        editModeBtn.setFont(FontStyle.BUTTON_FONT);

        gameModeBtn.addActionListener(buttonActionListener);
        editModeBtn.addActionListener(buttonActionListener);

        add(gameModeBtn);
        add(editModeBtn);

        // 배경 음악 재생
        MusicManager.play(MusicManager.MusicType.START);
    }

    // 화면 연출을 위한 paintComponent 메서드 Override
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(Icon.START_BACK_ICON.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.setFont(FontStyle.TITLE_FONT);
        g.drawString(TITLE, 150, 250);

        g.drawImage(Icon.DECO_ICON1.getImage(), 100, 450,
                Icon.DECO_ICON1.getIconWidth()/2, Icon.DECO_ICON1.getIconHeight()/2, this);
        g.drawImage(Icon.DECO_ICON2.getImage(), 800, 450,
                Icon.DECO_ICON2.getIconWidth()/2, Icon.DECO_ICON2.getIconHeight()/2, this);
    }

}
