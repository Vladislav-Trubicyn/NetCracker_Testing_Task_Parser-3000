package org.example.parser;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        List<Product> list = new ArrayList<Product>();
        Parser[] parser = {new ParserRBT(), new ParserCompass(), new ParserOnlineTrade(), new ParserCitilink()};
        //list = parser[0].parsePages(parser[0].getPageProductsListStore("samsung"), 1);
        //list = parser[1].parsePages(parser[1].getPageProductsListStore("флешка"),1);
        //list = parser[2].parsePages(parser[2].getPageProductsListStore("ps5"),1);
        //list = parser[3].parsePages(parser[3].getPageProductsListStore("наушники sony"),1);

        for(Product item : list)
        {
            System.out.println("Название товара: " + item.getName() + " цена: " + item.getPrice());
        }
        System.out.println(list.size());
    }



}
