package org.example.parser;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import java.util.ArrayList;


public class Main
{
    public static void main(String[] args) throws Exception
    {
        startParse("https://www.dns-shop.ru", 1);
    }

    static void startParse(String url, int countPage) throws Exception
    {
        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME))
        {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getCookieManager().setCookiesEnabled(true);
            makeWebClientWaitThroughJavaScriptLoadings(webClient);

            HtmlPage pageFirst = webClient.getPage(url);

            webClient.waitForBackgroundJavaScript(10000);


            HtmlElement inputSearch = (HtmlElement) pageFirst.getFirstByXPath("//input[@class='ui-input-search__input ui-input-search__input_presearch']");
            HtmlElement buttonSearch = (HtmlElement) pageFirst.getFirstByXPath("//span[@class='ui-input-search__icon ui-input-search__icon_search ui-input-search__icon_presearch']");

            inputSearch.setAttribute("value","наушники sony");

            HtmlPage pageSecond = buttonSearch.click();
            webClient.waitForBackgroundJavaScript(1000);

            parseAllPages(webClient, pageSecond, countPage);

        }

    }

    private static void makeWebClientWaitThroughJavaScriptLoadings(WebClient webClient)
    {
        webClient.setAjaxController(new AjaxController(){
            @Override
            public boolean processSynchron(HtmlPage page, WebRequest request, boolean async)
            {
                return true;
            }
        });
    }

    static void parseAllPages(WebClient webClient, HtmlPage pageSecond, int countPage) throws Exception
    {
        ArrayList<HtmlElement> list = new ArrayList<HtmlElement>();

        for(int i = 1; i <= countPage; i++)
        {
            pageSecond = webClient.getPage(pageSecond.getUrl() + "&p=" + i);
            webClient.waitForBackgroundJavaScript(5000);
            list.addAll(pageSecond.getByXPath("//a[@class='catalog-product__name ui-link ui-link_black']/span"));
        }

        for(HtmlElement element : list)
        {
            System.out.println("Название товара: " + element.getTextContent());
        }
        System.out.println("Всего " + list.size() + " товаров, с " + countPage + " страниц");


    }

}
