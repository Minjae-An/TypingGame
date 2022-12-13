package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import constant.Icon;
import constant.FontStyle;
import manager.MusicManager;

/**
 * 단어 추가 화면 클래스
 */
public class EditPanel extends JPanel {
    private MainFrame rootFrame;
    private final String FILE_PATH="web_terms.txt";
    private final int MAX_TEXT_LENGTH=20;
    private FileWriter fw;
    private JTextField inputField=new JTextField();
    private DefaultTableModel tableModel;
    private JTable termsTable;
    private JButton exitButton=new JButton("Exit");

    public EditPanel(MainFrame rootFrame) {
        this.rootFrame=rootFrame;

        setLayout(null);

        inputField.setSize(255, 30);
        inputField.setLocation(376, 190);

        /*
            사용자의 입력 판별및 저장을 위한 ActionListener 정의, 부착
         */
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text=inputField.getText().trim(); // 앞뒤 공백 제거
                if(text.equals("")) return; // 공백 입력시 저장하지 않음

                // 최대 길이 초과 입력 발생시 경고 메시지 출력
                if(text.length()>MAX_TEXT_LENGTH){
                    JOptionPane.showMessageDialog(null,
                            "단어의 길이는 "+MAX_TEXT_LENGTH+"자를 넘길 수 없음.",
                            "안내", JOptionPane.PLAIN_MESSAGE, null);
                }
                else writeFile(text);

                inputField.setText("");
            }
        });

        add(inputField);

        // 추가된 단어를 보여줄 JTable 이 부착된 JScrollPane 설정
        String[] columns={"단어", "길이"};
        tableModel=new DefaultTableModel(columns,0);
        termsTable=new JTable(tableModel);

        JScrollPane scrollPane=new JScrollPane(termsTable);
        termsTable.setFillsViewportHeight(true);
        scrollPane.setSize(500, 350);
        scrollPane.setLocation(255, 230);
        add(scrollPane);

        // 초기 화면으로 돌아가는 버튼 설정
        exitButton.setSize(100, 50);
        exitButton.setLocation(460, 590);
        exitButton.setBorder(new EmptyBorder(0,0,0,0));
        exitButton.setFont(FontStyle.BUTTON_FONT);
        exitButton.setOpaque(true);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(Color.BLACK);

        // 버튼 클릭시 작업을 마무리하고 초기 화면으로 돌아가기 위한 ActionListener 설정
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeFile();
                rootFrame.changePanel(MainFrame.PanelName.START);
            }
        });
        add(exitButton);

        // 입력 저장을 위한 단어 파일 열람
        openFile();

        // 배경 음악 재생
        MusicManager.play(MusicManager.MusicType.EDIT);
    }

    // 화면 연출을 위한 paintComponent 메서드 Override
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(FontStyle.ANNOUNCE_FONT);
        g.drawString("저장할 용어를 입력하세요", 380, 120);

        g.setFont(FontStyle.ANNOUNCE_FONT.deriveFont(15f));
        g.drawString("용어를 입력하고 Enter를 누르면 저장 완료(최대 "+MAX_TEXT_LENGTH+"자)", 320, 180);

        g.drawImage(Icon.DECO_ICON3.getImage(), 40, 450,
                Icon.DECO_ICON3.getIconWidth()/2, Icon.DECO_ICON3.getIconHeight()/2, this);

        g.drawImage(Icon.DECO_ICON4.getImage(), 800, 450,
                Icon.DECO_ICON4.getIconWidth()/2, Icon.DECO_ICON4.getIconHeight()/2, this);
    }

    // 단어 파일 열람 메서드
    private void openFile(){
        try{
            fw=new FileWriter(FILE_PATH, true);
        }
        catch(IOException e) {
            System.exit(1);
        }
    }

    // 입력, 단어 파일 저장 메서드
    private void writeFile(String text){
        try{
            fw.write(text+"\n");
            String[] inputData={text, String.valueOf(text.length())};
            tableModel.addRow(inputData);
        }
        catch(IOException e){
            System.exit(1);
        }
    }

    // 열람한 파일을 닫는 메서드
    private void closeFile(){
        try{
            fw.close();
        }
        catch(IOException e){
            System.exit(1);
        }
    }
}
