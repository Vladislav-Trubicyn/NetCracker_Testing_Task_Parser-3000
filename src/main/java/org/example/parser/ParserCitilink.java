package org.example.parser;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserCitilink implements Parser
{

    @Override
    public HtmlPage getPageProductsListStore(String productName) throws IOException
    {
        HtmlPage pageFirst = webClient.getPage("https://www.citilink.ru/search/?text=" + productName);

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
        int priceCount = 0;

        for(int page = 1; page <= countPage; page++)
        {
            if(page > 1)
            {
                try
                {
                    pageProductsListStore = webClient.getPage(pageProductsListStore.getUrl() + "&p=" + page);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                webClient.waitForBackgroundJavaScript(5000);
            }

            productsCount = pageProductsListStore.getByXPath("//a[@class=' ProductCardVertical__name  Link js--Link Link_type_default']").size();
            priceCount = pageProductsListStore.getByXPath("//span[@class='ProductCardVerticalPrice__price-current_current-price js--ProductCardVerticalPrice__price-current_current-price ']").size();

            for(int i = 0; i < productsCount; i++)
            {
                //i+i это номер элемента цены товара, так как на сайте присутсвует по два элемента цены товара
                //Ограничение, чтобы не попадали товары, которые не присутствуют на складе
                if(i+i < priceCount)
                {
                    listProduct.add(new Product(((HtmlElement)pageProductsListStore.getByXPath("//a[@class=' ProductCardVertical__name  Link js--Link Link_type_default']").get(i)).getTextContent(),
                            ((HtmlElement)pageProductsListStore.getByXPath("//span[@class='ProductCardVerticalPrice__price-current_current-price js--ProductCardVerticalPrice__price-current_current-price ']").get(i+i)).getTextContent().trim()));
                }

            }

        }

        return listProduct;
    }
}
