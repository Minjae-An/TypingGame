package manager;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

/**
 * 게임에 등장하는 단어들을 생성하는 클래스
 * 인스턴스를 단 한 번만 생성토록 하기 위해 Lazy-Holder 패턴 응용
 */
public class RandomTermSelector {
    private final String FILE_PATH="web_terms.txt";
    private Vector<String> termVector=new Vector<>();

    private static class LazyHolder{
        private static final RandomTermSelector randomTermSelector=new RandomTermSelector();
    }
    private RandomTermSelector(){

        Scanner scanner=null;
        try {
            FileReader fr= new FileReader(FILE_PATH);

            scanner = new Scanner(fr);
            while (scanner.hasNext()) {
                termVector.add(scanner.nextLine().trim());
            }

            fr.close();
        }catch(IOException e){
            System.out.println("파일 작업 도중 오류 발생");
        }
        finally{
            scanner.close();
        }
    }

    public static RandomTermSelector getInstance(){
        return LazyHolder.randomTermSelector;
    }

   // 랜덤한 단어를 반환하는 메서드
    public String getRandomTerm(){
        int index=(int)(Math.random()* termVector.size());
        return termVector.get(index);
    }
}
