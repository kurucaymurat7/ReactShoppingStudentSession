package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

import java.util.List;

public class ReactShoppingWebSite {
    public ReactShoppingWebSite() {
        PageFactory.initElements(Driver.getDriver(), this);
    }
    @FindBy (xpath = "//p[@class='sc-124al1g-4 eeXMBo']")
    public List<WebElement> tumOgelerList;

    @FindBy (xpath = "//*[text()='Add to cart']")
    public List<WebElement> addtoCartButtonList;

    @FindBy (xpath = "//p[@class='sc-124al1g-6 ljgnQL']")
    public List<WebElement> pricesList;

    @FindBy (xpath = "//p[@class='sc-1h98xa9-9 jzywDV']")
    public WebElement toplamFiyatWebElement;

    @FindBy (xpath = "//*[text()='Checkout']")
    public WebElement checkout;

    @FindBy (xpath = "//button[@class='sc-1h98xa9-0 gFkyvN']")
    public WebElement Xbutton;

    @FindBy (xpath = "//button[@class='sc-1h98xa9-0 gFkyvN']")
    public WebElement sepetLink;

    // vasif projedeki web element locators

    @FindBy(xpath = "//div[@class='sc-uhudcz-0 iZZGui']/div")
    public List<WebElement> productsElement;

    @FindBy(xpath = "//div[@class='sc-uhudcz-0 iZZGui']/div/p")
    public List<WebElement> productNamesElement;

    @FindBy(xpath = "//div[@class='sc-124al1g-5 fTQxRg']/p[1]")
    public List<WebElement> priceElement;

    @FindBy(xpath = "//button[@class='sc-124al1g-0 jCsgpZ']")
    public List<WebElement> addToCartButton;

    @FindBy(xpath = "//div[@class='sc-1h98xa9-8 bciIxg']/p[1]")
    public WebElement subtotalPriceElement;

    @FindBy(xpath = "//button[@class='sc-1h98xa9-11 gnXVNU']")
    public WebElement checkOutButtonElement;


}
