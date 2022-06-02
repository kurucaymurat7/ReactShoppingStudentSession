package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ReactShoppingWebSite;
import utilities.ConfigReader;
import utilities.Driver;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class reactWebSiteTest_StudentPractice {
    @Test
    public void reactWebSiteTest() throws InterruptedException {
        // 1."https://react-shopping-cart-67954.firebaseapp.com/" adresine gidin
        ReactShoppingWebSite reactShoppingWebSite = new ReactShoppingWebSite();
        Driver.getDriver().get(ConfigReader.getProperty("reactUrl"));

        // 2.Web sitesindeki tüm öğeleri listeleyin ve yazdirin
        // (sitede 16 urun var, 1.urun : 2.urun :.....seklinde yazdirin )
        List<WebElement> tumOgelerList = reactShoppingWebSite.tumOgelerList; //ogeler List
        List<WebElement> addtoCartButtonWebElementList = reactShoppingWebSite.addtoCartButtonList; //buttonlar List

        // 3.Stringlerden olusan bir ArrayList oluşturun ve Ürün Adlarını ArrayList'e yerleştirin
        System.out.println(tumOgelerList.size() + " urun var.");
        List<String> urunlerList = new ArrayList<>();
        for (int i = 0; i < tumOgelerList.size(); i++) {
            System.out.println((i+1) + " . urun: " + tumOgelerList.get(i).getText());
            urunlerList.add(tumOgelerList.get(i).getText());
        }

        // 4.Siteden Rastgele 5 öğe seçin, sepete ekleyin ve sectiginiz öğelerin adlarını yazdırın
        // (Her ürün 1 defadan fazla eklenemez!)

        double expectedToplam = 0;
        List<Integer> randomSecilenOgeler = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Faker faker = new Faker();
            int random = faker.random().nextInt(tumOgelerList.size());
            if (!randomSecilenOgeler.contains(random)) {
                reactShoppingWebSite.addToCartButton.get(random).click();
                Thread.sleep(3000);
                reactShoppingWebSite.Xbutton.click();
                randomSecilenOgeler.add(random);
                String fiyatString = reactShoppingWebSite.pricesList.get(random).getText().substring(1);
                Double fiyatDouble = Double.parseDouble(fiyatString);
                expectedToplam += fiyatDouble;
            } else {
                i--;
            }
        }
        // 5.Her bir öğenin fiyatını toplayın ve sonucunuzu web sitesiyle karşılaştırın

        Thread.sleep(1000);
        reactShoppingWebSite.sepetLink.click();
        System.out.println("toplam fiyat :" + reactShoppingWebSite.toplamFiyatWebElement.getText());
        Double actualToplam = Double.valueOf(reactShoppingWebSite.toplamFiyatWebElement.getText().substring(1));
        actualToplam = Double.parseDouble(new DecimalFormat("##.##").format(actualToplam));
        expectedToplam = Double.parseDouble(new DecimalFormat("##.##").format(expectedToplam));
        Assert.assertEquals(actualToplam, expectedToplam,"toplamlar eşit degil");

        reactShoppingWebSite.checkOutButtonElement.click();
        Driver.getDriver().switchTo().alert().accept();
        Driver.closeDriver();
    }
}
