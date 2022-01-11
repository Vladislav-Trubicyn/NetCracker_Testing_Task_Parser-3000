package org.example.parser;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserRBT implements Parser
{

    @Override
    public HtmlPage getPageProductsListStore(String productName) throws IOException
    {
        HtmlPage pageFirst = webClient.getPage("https://www.rbt.ru/search/?q=" + productName);

        webClient.waitForBackgroundJavaScript(10000);

        return pageFirst;
    }

    @Override
    public List<Product> parsePages(HtmlPage pageProductsListStore, int countPage)
    {
        if(pageProductsListStore == null)
        {
            return null;
        }

        List<Product> listProduct = new ArrayList<Product>();
        int productsCount = 0;

        for(int page = 1; page <= countPage; page++)
        {
            if(page > 1)
            {
                try
                {
                    pageProductsListStore = webClient.getPage(pageProductsListStore.getUrl().toString().replace("search", "search/~/page/" + page));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                webClient.waitForBackgroundJavaScript(5000);
            }

            productsCount = pageProductsListStore.getByXPath("//a[@class='link link_theme_item-catalogue link_underline-color_orange link_size_b item-catalogue__item-name-link']").size();

            for(int i = 0; i < productsCount; i++)
            {
                listProduct.add(new Product(((HtmlElement)pageProductsListStore.getByXPath("//a[@class='link link_theme_item-catalogue link_underline-color_orange link_size_b item-catalogue__item-name-link']/span").get(i)).getTextContent(),
                        ((HtmlElement)pageProductsListStore.getByXPath("//div[@class='price__row price__row_current text_bold text']").get(i)).getTextContent().trim()));
            }

        }

        return listProduct;
    }

}
