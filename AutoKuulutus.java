public class AutoKuulutus {
    private String mudelInfo;
    private String hind;

    public AutoKuulutus(String mudelInfo, String hind) {
        this.mudelInfo = mudelInfo;
        this.hind = hind;
    }

    @Override
    public String toString() {
        return "AutoKuulutus (" +
                "Mudel:'" + mudelInfo + '\'' +
                ", hind:'" + hind + '\'' +
                ')';
    }
}
