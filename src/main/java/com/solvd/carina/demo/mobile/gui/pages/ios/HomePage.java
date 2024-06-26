package com.solvd.carina.demo.mobile.gui.pages.ios;

import com.solvd.carina.demo.mobile.gui.pages.common.HomePageBase;
import com.solvd.carina.demo.mobile.gui.pages.common.ProductDetailPageBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@DeviceType(pageType = DeviceType.Type.IOS_PHONE, parentClass = HomePageBase.class)
public class HomePage extends HomePageBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FindBy(css = "input[id='kw']")
    private ExtendedWebElement searchBar;

    @FindBy(css = "button[class='gh-search__submitbtn']")
    private ExtendedWebElement searchButton;

    @FindBy(css = "ul li[class='s-item s-item__pl-on-bottom'] a")
    private List<ExtendedWebElement> resultsList;

    @FindBy(css = "div[class='carousel__viewport'] li span[class='vl-item__displayPrice']")
    private List<ExtendedWebElement> productsCarousel;

    @FindBy(css = "")
    private ExtendedWebElement popular;

    @FindBy(css = "button[class='carousel__control carousel__control--next']")
    private ExtendedWebElement carouselNextButton;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void searchForAProduct(String input) throws InterruptedException {
        searchBar.click();
        searchBar.type(input);
        searchButton.click();
        pause(5);
    }

    @Override
    public boolean doResultsMatchSearch(String input) {
        for (ExtendedWebElement e : resultsList) {
            if (e.getText().contains(input)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean areCarouselTitlesPresent() {
        swipe(popular, 2);
        for (ExtendedWebElement e : productsCarousel) {
            LOGGER.info(e.getText());
            if (e.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ProductDetailPageBase clickOnACarouselProduct() {
        productsCarousel.get(0).click();
        pause(3L);
        return initPage(getDriver(), ProductDetailPageBase.class);
    }

    @Override
    public void switchToWindow() {
        Set<String> handles = getDriver().getWindowHandles();
        Iterator it = handles.iterator();
        String parent = (String) it.next();
        getDriver().switchTo().window(parent);
    }
}
