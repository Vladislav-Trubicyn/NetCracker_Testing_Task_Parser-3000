package org.example.parser;

import com.gargoylesoftware.htmlunit.html.*;

import java.util.ArrayList;
import java.util.List;

public class ParserCompass implements Parser
{

    @Override
    public List<Product> parsePages(HtmlPage pageProductsListStore, int countPage)
    {
        if(pageProductsListStore == null)
        {
            return null;
        }

        int productsCount = pageProductsListStore.getByXPath("//td[@class='searchPgroup mobileHide']/following-sibling::td[1]/a").size();
        List<Product> listProduct = new ArrayList<Product>();

        for(int i = 0; i < productsCount; i++)
        {
            listProduct.add(new Product(((HtmlElement)pageProductsListStore.getByXPath("//td[@class='searchPgroup mobileHide']/following-sibling::td[1]/a").get(i)).getTextContent(), ((HtmlElement)pageProductsListStore.getByXPath("//div[@class='price']").get(i)).getTextContent()));
        }

        return listProduct;
    }
}