import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MargiSoovitaja {
    private final List<String> margid = Arrays.asList(
            "Audi", "BMW", "Citroen", "Ford", "Honda",
            "Hyundai", "Kia", "Mercedes-Benz", "Nissan", "Opel",
            "Peugeot", "Porsche", "Renault", "Skoda", "Toyota",
            "Volkswagen", "Volvo"
    );

    private final Random random = new Random();

    public String soovita() {
        return margid.get(random.nextInt(margid.size()));
    }

}
