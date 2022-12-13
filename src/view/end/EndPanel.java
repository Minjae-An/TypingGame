package view.end;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import constant.Icon;
import constant.FontStyle;

import manager.MusicManager;
import manager.RankManager;
import view.MainFrame;

/**
 * 엔딩 화면 Panel 클래스
 */
public class EndPanel extends JPanel {
    private MainFrame rootFrame;
    private String playerID;
    private int playerScore;
    private JLabel announceLabel=new JLabel("Your Rank");
    private JLabel[] rankers=new JLabel[3];
    private JButton backButton;
    private String playerRank;

    // JTable 활용을 위한 필드들
    private final String[] COLUMN_NAME={"Rank", "USER ID", "Score"};
    private DefaultTableModel model;
    private JTable rankingTable;

    private RankManager rankManager;
    public EndPanel(MainFrame rootFrame, String playerID, int playerScore){
        this.rootFrame=rootFrame;
        this.playerID=playerID;
        this.playerScore=playerScore;

        rankManager=RankManager.getInstance(playerID, playerScore);

        setLayout(null);

        // 안내 라벨 세팅
        announceLabel.setFont(FontStyle.ANNOUNCE_FONT);
        announceLabel.setForeground(Color.WHITE);
        announceLabel.setSize(100, 40);
        announceLabel.setLocation(630, 50);

        add(announceLabel);

        // 기록을 보여주는 JTable 세팅, 랭커 정보 세팅
        setRankingTable();
        User[] rankerData=getRanking();
        setRankers(rankerData);

        setBackButton();

        // 배경 음악 재생
        MusicManager.play(MusicManager.MusicType.END);
    }

    // 기록을 보여주는 테이블 세팅 메서드
    private void setRankingTable(){
        model=new DefaultTableModel(COLUMN_NAME, 0);
        rankingTable=new JTable(model);

        // ScrollPane 를 활용해 스크롤이 가능하게 하였다.
        JScrollPane scrollPane=new JScrollPane(rankingTable);
        rankingTable.setFillsViewportHeight(true);
        scrollPane.setSize(350, 400);
        scrollPane.setLocation(500, 150);
        add(scrollPane);
    }

    // 현재 사용자의 순위와 랭커 정보를 가져오는 메서드
    private User[] getRanking(){
        ArrayList<User> rankList=rankManager.getRank();
        User[] rankerData=new User[3];

        for(int i=0; i<rankerData.length; i++){
            rankerData[i]=rankList.get(i);
        }

        for(int i=0; i<rankList.size(); i++){
            String[] data=new String[3];

            data[0]=String.valueOf(i+1);
            data[1]=rankList.get(i).getId();
            if(data[1].equals(playerID)) playerRank=data[0];
            data[2]=String.valueOf(rankList.get(i).getScore());
            model.addRow(data); // 테이블에 기록을 넣는다.
        }

        return rankerData;
    }

    // 랭커 정보를 표시하는 메서드
    private void setRankers(User[] rankerData){
        for(int i=0; i<rankers.length; i++){
            String text=(i+1)+"th ";
            text=text.concat(String.valueOf(rankerData[i].getId()));
            rankers[i]=new JLabel(text);
            rankers[i].setFont(FontStyle.ANNOUNCE_FONT);
            rankers[i].setForeground(Color.WHITE);
            rankers[i].setSize(text.length()+130, 40);
        }

        rankers[0].setLocation(250, 320);
        rankers[1].setLocation(160, 510);
        rankers[2].setLocation(350, 510);

        for(JLabel la:rankers) add(la);
    }

    // 초기 화면으로 돌아갈 수 있는 버튼을 세팅하는 메서드
    private void setBackButton(){
        backButton=new JButton("BACK");
        backButton.setFont(FontStyle.BUTTON_FONT);
        backButton.setSize(200, 50);
        backButton.setLocation(380,570);
        backButton.setBackground(new Color(244,255,255));

        // 버튼 이벤트 설정
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                rootFrame.changePanel(MainFrame.PanelName.START);
            }
        });

        add(backButton);
    }

    // 화면 연출을 위한 paintComponent Override
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(Icon.ENDPANEL_BACK_ICON.getImage(), 0, 0, getWidth(), getHeight(), this);

        g.setColor(Color.WHITE);
        g.setFont(FontStyle.ANNOUNCE_FONT);
        g.drawString(playerRank, 670, 120);

        g.drawString("Rankers", 250, 150);

        g.drawImage(Icon.RANK_ICON1.getImage(), 200, 150,
                Icon.RANK_ICON1.getIconWidth()/2, Icon.RANK_ICON1.getIconHeight()/2, null);
        g.drawImage(Icon.RANK_ICON2.getImage(), 150, 350,
                Icon.RANK_ICON2.getIconWidth()/5, Icon.RANK_ICON2.getIconHeight()/5, null);
        g.drawImage(Icon.RANK_ICON2.getImage(), 340, 350,
                Icon.RANK_ICON2.getIconWidth()/5, Icon.RANK_ICON2.getIconHeight()/5, null);
    }
}
