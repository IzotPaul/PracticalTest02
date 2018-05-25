package eim.systems.cs.pub.ro.practicaltest;

/**
 * Created by student on 25.05.2018.
 */
        import java.util.Date;

public class DataInfo {
    private Date date;
    private String value;

    public DataInfo(Date date, String value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        return date + " " + value;
    }
}
