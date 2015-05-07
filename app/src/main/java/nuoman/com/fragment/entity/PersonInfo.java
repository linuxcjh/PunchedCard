package nuoman.com.fragment.entity;

/**
 * Created by Administrator on 2015/4/19.
 */
public class PersonInfo {
    private String cardno;//car number
    private String name;
    private String number;
    private String kind;//0：学生，1：老师



    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
