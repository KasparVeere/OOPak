import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Auto24Kraapija {
    public List<AutoKuulutus> otsiKuulutused(AutoOtsing otsing) {
        List<AutoKuulutus> kuulutused = new ArrayList<>();
// kuna olin rumal ja valisin sellise lehekülje nagu auto24 ja pidin seleniumit kasutama, siis see aitab avada mul chrome browseri reaalselt
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.auto24.ee/kasutatud/kasutatud.php");
            System.out.println("Leht avanes");
// leht vajas et küpsistega nõustuks, aga ei avanenud leht hetkega ja küpsistele vajutamine võttis ka aega, seega see aitab mul selle ära teha
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                WebElement cookieButton = shortWait.until(
                        ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Nõustun')]")));
                cookieButton.click();
                System.out.println("Klikiti küpsiste 'Nõustun' nupul.");
            } catch (TimeoutException e) {
                System.out.println("Küpsiste nõusoleku nuppu ei ilmunud.");
            }
// AUto24 lehel valikute müü ei olnud pildis ja Selenium ei näinud, seda, seega tuli liikuda alla poole
            driver.executeScript("window.scrollBy(0, 600)");
            System.out.println("Leht keriti alla.");
// Otsisin veebilehte inspekteerides, kus reaalselt see asub, et selenium saaks vajutada sinna või noh ta teaks mida otsida
            WebElement markDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#item-searchParam-cmm-1-make div.select-selected")));
            markDropdown.click();
            System.out.println("Klikiti mark dropdownil.");

            Thread.sleep(1000);

            WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.search-filter[contenteditable='true']")));
// Selles aknas paste-imine ei toiminud, täitsa võimalik et ma tegin midagi valesti, aga ma liiga närvis, et see huvitaks
            for (char c : otsing.getMark().toCharArray()) {
                searchField.sendKeys(String.valueOf(c));
                Thread.sleep(200);
            }
            System.out.println("Mark '" + otsing.getMark() + "' sisestatud.");

            searchField.sendKeys(Keys.ENTER);

            try {
                WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'OK')]")));
                okButton.click();

            } catch (TimeoutException ignored) {}
// Sisetsab hinna nüüd
            WebElement maksHinnaväli = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='g2']")));
            maksHinnaväli.sendKeys(String.valueOf(otsing.getMaksimaalneHind()));
            System.out.println("Maksimaalne hind " + otsing.getMaksimaalneHind() + " sisestatud.");
// Otsi nupu leidmiseks kerib alla
            WebElement otsiNupp = driver.findElement(By.cssSelector("input.input-submit[name='otsi']"));
            driver.executeScript("arguments[0].scrollIntoView(true);", otsiNupp);
            Thread.sleep(1000);
            otsiNupp.click();
            System.out.println("Klikiti 'OTSI' nupule.");

            Thread.sleep(4000);
// võtab lehelt sõidukid ja paneb listi
            List<WebElement> sõidukid = driver.findElements(By.cssSelector("div.result-row"));
            for (WebElement sõiduk : sõidukid) {
                try {
                    String mudel = sõiduk.findElement(By.cssSelector("div.title")).getText().trim();
                    String hind = "";
                    try {
                        hind = sõiduk.findElement(By.cssSelector("div.finance span.price")).getText().replace("\u00a0", " ").trim();
                    } catch (Exception ignore) {}



                    kuulutused.add(new AutoKuulutus(mudel, hind));
                } catch (Exception ignore) {}
            }

            System.out.println("Leiti " + kuulutused.size() + " kuulutust.");
        } catch (Exception e) {
            System.out.println("Tekkis viga: " + e.getMessage());
        } finally {
            driver.quit();
            System.out.println("Lõpp");
        }

        return kuulutused;
    }
}
