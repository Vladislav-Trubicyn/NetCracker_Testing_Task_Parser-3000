package org.example.parser;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserDNS implements Parser
{
    final WebClient webClient = Parser.initialiseWebClient();

    @Override
    public List<Product> parsePages(HtmlPage pageProductsListStore, int countPage)
    {
        List<HtmlElement> list = new ArrayList<HtmlElement>();

        for(int page = 1; page <= countPage; page++)
        {
            try
            {
                pageProductsListStore = webClient.getPage(pageProductsListStore.getUrl() + "&p=" + page);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            webClient.waitForBackgroundJavaScript(5000);
            list.addAll(pageProductsListStore.getByXPath("//a[@class='catalog-product__name ui-link ui-link_black']/span"));
        }

        /*for(HtmlElement element : list)
        {
            System.out.println("Название товара: " + element.getTextContent());
        }
        System.out.println("Всего " + list.size() + " товаров, с " + countPage + " страниц");*/

        System.out.println(pageProductsListStore.asXml());

        return null;
    }

}
