package view;

import view.end.EndPanel;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * 게임 프레임 클래스
 */

public class MainFrame extends JFrame {
    // enum 통해 Panel 타입 정의
    public enum PanelName {START, GAME, EDIT};
    private final String TITLE = "HSU WEB TERM TYPING GAME";

    public MainFrame() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(new StartPanel(this));

        setLocationRelativeTo(null);
        setLocation(getX() - 400, getY() - 300);
        setSize(1000, 700);
        setResizable(false);
        setVisible(true);
    }

    // 화면 전환 메서드
    public void changePanel(PanelName name) {
        Container c = getContentPane();

        c.removeAll();
        switch (name) {
            case START:
                c.add(new StartPanel(this));
                break;
            case GAME:
                c.add(new GamePanel(this));
                break;
            case EDIT:
                c.add(new EditPanel(this));
                break;
        }

        c.revalidate();
        c.repaint();
    }

   /*
        엔딩 화면 전환 메서드
        playerID, playerScore 전달을 위해 별도로 정의
    */
    public void changeToEndPanel(String playerID, int playerScore){
        Container c=getContentPane();

        c.removeAll();
        c.add(new EndPanel(this, playerID, playerScore));
        c.revalidate();
        c.repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
