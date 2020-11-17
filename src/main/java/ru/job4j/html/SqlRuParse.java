package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ru.job4j.html.DateUtils.changeData;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        List<String> urls = pageCounter();
        for (String url: urls) {
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements date = doc.select(".altCol");
            Iterator<Element> iterator = date.iterator();
            printAllPages(row, iterator);
        }
    }

    public static List<String> pageCounter() {
        String url = "https://www.sql.ru/forum/job-offers/";
        List<String> urls = new ArrayList<>();
        int firstPage = 1;
        int lastPage = 6;
        for (int i = firstPage; i < lastPage; i++) {
            urls.add(url + i);
        }
        return urls;
    }

    public static void printAllPages(Elements row, Iterator<Element> iterator) {
        for (Element td : row) {
            Element href = td.child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            if (iterator.hasNext()) {
                iterator.next();
                Element d = iterator.next();
                LocalDate localDate = changeData(d.text());
                System.out.println(localDate);
            }
        }
    }
}