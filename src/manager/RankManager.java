package manager;

import view.end.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * 사용자들의 기록, 순위를 관리하는 클래스
 * 인스턴스를 단 한 번만 생성토록 하기 위해 Lazy-Holder 패턴 응용
 */
public class RankManager {
    private final String FILE_PATH="rank.txt";
    private ArrayList<User> rankList=new ArrayList<>();
    private RankManager(){}
    private static class LazyHolder{
        private final static RankManager rankManager=new RankManager();
    }

    public static RankManager getInstance(String playerID, int playerScore){
        LazyHolder.rankManager.addPlayerData(playerID, playerScore);
        LazyHolder.rankManager.getData();
        LazyHolder.rankManager.sortingData();

        return LazyHolder.rankManager;
    }

    // 현재 사용자 기록, 파일 추가 메서드
    private void addPlayerData(String playerID, int playerScore){
        User newUser=new User(playerID, playerScore);
        FileWriter fw;

        try{
            fw=new FileWriter(FILE_PATH, true);
            fw.write(playerID+" "+playerScore+"\n");
            fw.close();
        }
        catch(IOException e){
            System.out.println("파일 입출력 작업 도중 오류 발생");
            e.printStackTrace();
        }
    }

    // 기록 파일로부터 데이터를 읽어오는 메서드
    private void getData(){
        FileReader fr;
        Scanner scanner=null;
        String[] rankData=new String[2];

        try{
            fr=new FileReader(FILE_PATH);
            scanner=new Scanner(fr);

            while(scanner.hasNext()){
                String data=scanner.nextLine();
                if(data.split(" ").length<2) continue;
                rankData=data.split(" ");
                User user=new User(rankData[0], Integer.parseInt(rankData[1]));
                rankList.add(user);
            }

            fr.close();
        }
        catch(Exception e) {
            System.out.println("파일 I/O 작업 도중 오류 발생");
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }
    }

    // 사용자 기록을 점수 기준으로 정렬하는 메서드
    private void sortingData(){
        Collections.sort(rankList, new UserComparator());
    }

    // 사용자 기록 정렬을 위한 Comparator 구현 클래스
    private class UserComparator implements Comparator<User>{
        @Override
        public int compare(User u1, User u2){
            return u2.getScore()-u1.getScore();
        }
    }

   // 사용자 기록 반환 메서드
    public ArrayList<User> getRank(){
        return rankList;
    }
}
