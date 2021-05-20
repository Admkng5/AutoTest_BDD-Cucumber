package ru.open;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StepDefs extends Steps{

    private String inputField = "//input[@name='q']";
    private String searchElement = "//div[@class='g']//div/cite";
    private String selectorExchangeRates = "//*[@class='main-page-exchange main-page-info__card']";
    private String selectorTableHeaders=".//tbody/tr[contains(@class,'header')]/td";
    private String selectorTableRows = ".//tbody/tr[contains(@class,'row')]";

    private List<Map<String,String>> collectExchangeRates = new ArrayList<>();

    @Given("go to {string}")
    public void go_to(String string) {
        openWD();
        wd.get(string);
    }

    @When("search {string}")
    public void search(String string) {
        wd.findElement(By.xpath(inputField)).click();
        wd.findElement(By.xpath(inputField)).sendKeys(string);
        wd.findElement(By.xpath(inputField)).submit();
    }

    @Then("search results contains {string}")
    public void search_results_contains(String string) {
        Assertions.assertTrue(wd.findElements(By.xpath(searchElement)).stream().anyMatch(x->x.getText()
                .contains(string)),"Не найдено");
        closeWD();
    }

    @Then("check sell rate is bigger than buy rate")
    public void checkSellRateIsBiggerThanBuyRate() {
        collectExchangeRates = getCollectExchangeRates();
        Assertions.assertTrue(
                Double.parseDouble(collectExchangeRates.stream()
                        .filter(x->x.get("Валюта обмена").contains("USD"))
                        .findFirst()
                        .get().get("Банк покупает").replace(",","."))
                        <
                        Double.parseDouble(collectExchangeRates.stream()
                                .filter(x->x.get("Валюта обмена").contains("EUR"))
                                .findFirst()
                                .get().get("Банк продает").replace(",",".")
                        )
        );

        closeWD();
    }

    public List<Map<String, String>> getCollectExchangeRates() {

        WebElement exchangeRates= wd.findElement(By.xpath(selectorExchangeRates));

        List<WebElement> tableHeaders = exchangeRates.findElements(By.xpath(selectorTableHeaders));
        List<WebElement> tableRows = exchangeRates.findElements(By.xpath(selectorTableRows));

        for(int i=0; i<tableRows.size();++i){
            Map<String,String> collectRow = new HashMap<>();
            for(int j=0; j<tableHeaders.size();++j){
                collectRow.put(
                        tableHeaders.get(j).getText(),
                        tableRows.get(i).findElement(By.xpath("./td["+(j+1)+"]")).getText()
                );
            }
            collectExchangeRates.add(collectRow);
        }
        return collectExchangeRates;
    }

}
