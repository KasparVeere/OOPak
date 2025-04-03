public class AutoOtsing {
    private String mark;
    private int maksimaalneHind;

    public AutoOtsing(String mark, int maksimaalneHind) {
        this.mark = mark;
        this.maksimaalneHind = maksimaalneHind;
    }

    public String getMark() {
        return mark;
    }

    public int getMaksimaalneHind() {
        return maksimaalneHind;
    }

    @Override
    public String toString() {
        return "AutoOtsing (" +
                "Mark='" + mark + '\'' +
                ", maksimaalne hind:" + maksimaalneHind +
                ')';
    }
}
