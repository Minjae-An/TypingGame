package view.end;

/**
 * 사용자 기록을 다루기 위한 클래스
 */

public class User {
    private String id;
    private int score;

    public User(String id, int score){
        this.id=id;
        this.score=score;
    }

    public String getId(){ return id; } // id 반환 메서드
    public int getScore(){return score; } // 점수 반환 메서드
}
