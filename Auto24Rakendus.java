import javax.swing.*;
import java.util.List;

public class Auto24Rakendus {
    public static void main(String[] args) {

        MargiSoovitaja soovitaja = new MargiSoovitaja();

        // Küsime kasutajalt margi sisendit, makes sense kui vaatad ,mida küsitakse tho
        String[] valikud = {"Sisesta oma mark", "Soovita mulle mark"};
        int valik = JOptionPane.showOptionDialog(null, "Kuidas soovid valida margi?", "Margi valik", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, valikud, valikud[0]);

        String mark;
        if (valik == 1) {
            mark = soovitaja.soovita();
            JOptionPane.showMessageDialog(null, "Soovitatud mark: " + mark);
        } else {
            mark = JOptionPane.showInputDialog("Sisesta auto mark:");
        }

        if (mark == null || mark.isBlank()) {
            JOptionPane.showMessageDialog(null, "Auto mark jäi sisestamata. Programmi töö lõpeb.");
            return;
        }



        String hindStr = JOptionPane.showInputDialog("Sisesta maksimaalne hind:");


        int hind;
        try {
            hind = Integer.parseInt(hindStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Sisestatud hind ei olnud number. Programmi töö lõpeb.");
            return;
        }

        // Loome AutoOtsi objekti
        AutoOtsing otsing = new AutoOtsing(mark, hind);
        Auto24Kraapija kraapija = new Auto24Kraapija();
        List<AutoKuulutus> kuulutused = kraapija.otsiKuulutused(otsing);

        // Kuvame tulemused
        if (kuulutused.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ühtegi kuulutust ei leitud!");
        } else {
            StringBuilder tulemus = new StringBuilder("Leitud kuulutused:\n\n");
            for (AutoKuulutus kuulutus : kuulutused) {
                tulemus.append(kuulutus).append("\n");
            }
// Loon kastikese, kus reaalselt ka vastuseid näeks
            JTextArea textArea = new JTextArea(tulemus.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
            JOptionPane.showMessageDialog(null, scrollPane, "Tulemused", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
