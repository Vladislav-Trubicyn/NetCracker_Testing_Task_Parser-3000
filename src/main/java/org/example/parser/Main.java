package org.example.parser;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Parser[] parser = {new ParserDNS(), new ParserCompass(), new ParserOnlineTrade()};
        /*parser[0].parsePages(parser[0].getPageProductsListStore("https://www.dns-shop.ru",
                "//input[@class='ui-input-search__input ui-input-search__input_presearch']",
                "//span[@class='ui-input-search__icon ui-input-search__icon_search ui-input-search__icon_presearch']",
                "наушники sony"), 1);*/
        /*parser[1].parsePages(parser[1].getPageProductsListStore("https://www.compass.com.ru",
                "//input[@id='searchText']",
                "//input[@id='searchSubmit']",
                "флешка"), 1);*/
        /*parser[2].parsePages(parser[2].getPageProductsListStore("https://www.onlinetrade.ru",
                "//input[@class='header__search__inputText js__header__search__inputText']",
                "//input[@class='header__search__inputGogogo']",
                "ps5"), 1);*/


    }



}
