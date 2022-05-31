package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ReactShoppingWebSite;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class reactWebsiteTest {
    @Test
    public void test01() throws IOException, InterruptedException {

        // 1."https://react-shopping-cart-67954.firebaseapp.com/" adresine gidin
        ReactShoppingWebSite reactShoppingWebSite = new ReactShoppingWebSite();
        Driver.getDriver().get(ConfigReader.getProperty("reactUrl"));

        // 2.Web sitesindeki tüm öğeleri listeleyin ve yazdirin
        // (sitede 16 urun var, 1.urun : 2.urun :.....seklinde yazdirin )
        List<WebElement> tumOgelerList = reactShoppingWebSite.tumOgelerList; //ogeler List
        List<WebElement> addtoCartButtonWebElementList = reactShoppingWebSite.addtoCartButtonList; //buttonlar List

        System.out.println(tumOgelerList.size() + " urun var.");
        for (int i = 0; i < tumOgelerList.size(); i++) {
            System.out.println((i+1) + " . urun: " + tumOgelerList.get(i).getText());
        }

        // 3.Stringlerden olusan bir ArrayList oluşturun ve Ürün Adlarını ArrayList'e yerleştirin
        List<String> tumOgelerString = new ArrayList<>();
        for (WebElement each:tumOgelerList) {
            tumOgelerString.add(each.getText());
        }

        // 4.Siteden Rastgele 5 öğe seçin, sepete ekleyin ve sectiginiz öğelerin adlarını yazdırın
        // (Her ürün 1 defadan fazla eklenemez!)

        // 5.Her bir öğenin fiyatını toplayın ve sonucunuzu web sitesiyle karşılaştırın

        double toplamFiyat = 0;
        Random rnd = new Random();
        List<Integer> randomSecilenOgeler = new ArrayList<>();
        do {
            int randomOgeIndex = rnd.nextInt(tumOgelerList.size());
            if (!randomSecilenOgeler.contains(randomOgeIndex)) {
                randomSecilenOgeler.add(randomOgeIndex);
                System.out.print("Random ürün no : " + (randomOgeIndex+1));
                System.out.println();
                addtoCartButtonWebElementList.get(randomOgeIndex).click();
                Thread.sleep(3000);
                reactShoppingWebSite.Xbutton.click(); // her bir eklemeden sonra X buttona basılmalıdır ki, unvisible element kalmasın.
                String urunFiyat = reactShoppingWebSite.pricesList.get(randomOgeIndex).getText();
                Double urunFiyatDouble = Double.valueOf(urunFiyat.substring(1));
                //System.out.println(urunFiyatDouble);
                toplamFiyat += urunFiyatDouble;
            }
        } while (randomSecilenOgeler.size()<5);

        //Not: checkout ve toplam fiyat bilgisini alabilmek icin oncelikle sepet linke basıp o sayfanın görünür olması saglanmalıdır.

        reactShoppingWebSite.sepetLink.click(); // bu kısımda explicitWait kullandım, çünkü Checkout ve fiyat bilgisinin visible olması icin
        WebDriverWait wait= new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(reactShoppingWebSite.toplamFiyatWebElement));

        // 5.Her bir öğenin fiyatını toplayın ve sonucunuzu web sitesiyle karşılaştırın

        System.out.println(reactShoppingWebSite.toplamFiyatWebElement.getText());
        double actualToplamFiyat = Double.parseDouble(reactShoppingWebSite.toplamFiyatWebElement.getText().substring(1));
        double expectedToplamFiyat = toplamFiyat;
        //System.out.println("expected:" + expectedToplamFiyat);
        //System.out.println("actual:" + actualToplamFiyat);
        Assert.assertTrue((int)actualToplamFiyat == (int)expectedToplamFiyat);

        // Not: burada rapor almak icin screenshot alabiliriz. Toplam fiyat bilgisini rapor alalım.
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYMMddHHmmss");
        String tarih = date.format(dtf);

        File tumSayfa = new File("target/screenshots/tumSayfa_"+tarih+"_.jpeg");
        File temp = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(temp,tumSayfa);

        File fiyatBilgisiResim = new File("target/screenshots/toplamfiyatBilgisi_"+tarih+"_.jpeg");
        temp = reactShoppingWebSite.toplamFiyatWebElement.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(temp, fiyatBilgisiResim);

        // 7.Checkout'a tıklayın
        reactShoppingWebSite.checkout.click();

        Driver.getDriver().switchTo().alert().accept();
        Driver.closeDriver();

    }
}
