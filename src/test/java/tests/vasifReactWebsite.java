package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ReactShoppingWebSite;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class vasifReactWebsite {

    // 1."https://react-shopping-cart-67954.firebaseapp.com/" adresine gidin
    // 2.Web sitesindeki tüm öğeleri listeleyin ve yazdirin
    // (sitede 16 urun var, 1.urun : 2.urun :.....seklinde yazdirin )
    // 3.Stringlerden olusan bir ArrayList oluşturun ve Ürün Adlarını ArrayList'e yerleştirin
    // 4.Siteden Rastgele 5 öğe seçin, sepete ekleyin ve sectiginiz öğelerin adlarını yazdırın
    // (Her ürün 1 defadan fazla eklenemez!)
    // 5.Her bir öğenin fiyatını toplayın ve sonucunuzu web sitesiyle karşılaştırın
    // 6.Sonuçlar eşleşiyorsa  konsola test pass yazirin
    // 7.Checkout'a tıklayın

    @Test
    public void test01() {
        // 1."https://react-shopping-cart-67954.firebaseapp.com/" adresine gidin
        Driver.getDriver().get(ConfigReader.getProperty("reactUrl"));

        // 2.Web sitesindeki tüm öğeleri listeleyin ve yazdirin
        // (sitede 16 urun var, 1.urun : 2.urun :.....seklinde yazdirin )
        ReactShoppingWebSite reactPage = new ReactShoppingWebSite();
        for (int i = 0; i < reactPage.productsElement.size(); i++) {
            System.out.println((i + 1) + ".urun : " + reactPage.productsElement.get(i).getText() + "\n");
        }

        // 3.Stringlerden olusan bir ArrayList oluşturun ve Ürün Adlarını ArrayList'e yerleştirin
        List<String> productNamesList = new ArrayList<>();
        for (WebElement each : reactPage.productNamesElement) {
            productNamesList.add(each.getText());
        }

        // 4.Siteden Rastgele 5 öğe seçin, sepete ekleyin ve sectiginiz öğelerin adlarını yazdırın
        // (Her ürün 1 defadan fazla eklenemez!)
        // 5.Her bir öğenin fiyatını toplayın ve sonucunuzu web sitesiyle karşılaştırın
        // 6.Sonuçlar eşleşiyorsa  konsola test pass yazirin

        Random rnd = new Random();
        int randomSelect = 5;

        List<String> priceList = new ArrayList<>();

        double expectedTotalPrice = 0;
        for (int i = 0; i < randomSelect; i++) {
            int randomIndex = rnd.nextInt(reactPage.addToCartButton.size());
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].click();", reactPage.addToCartButton.get(randomIndex));
            reactPage.addToCartButton.remove(randomIndex);
            System.out.println(productNamesList.get(randomIndex));
            priceList.add(reactPage.priceElement.get(randomIndex).getText().replace("$",""));
            expectedTotalPrice += Double.parseDouble(priceList.get(i));
        }

        double actualTotalPrice = Double.parseDouble(reactPage.subtotalPriceElement.getText().replace("$",""));
        Assert.assertEquals((int) expectedTotalPrice, (int) actualTotalPrice);

        // 7.Checkout'a tıklayın
        reactPage.checkOutButtonElement.click();

    }

}
