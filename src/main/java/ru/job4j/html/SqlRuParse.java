package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.IIOException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.job4j.html.DateUtils.changeData;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
       getMsgDetails();
        /* List<String> urls = pageCounter();
        for (String url: urls) {
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements date = doc.select(".altCol");
            Iterator<Element> iterator = date.iterator();
            printAllPages(row, iterator);
        } */
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

    public static void getMsgDetails() throws Exception {
        String url = "https://www.sql.ru/forum/1330961/vakansiya-inzhener-testirovshhik";
        Document doc = Jsoup.connect(url).get();
        Element msg = doc.select(".msgTable").first();

        String title = msg.getElementsByAttributeValue("class", "messageHeader").text();
        System.out.println(title);

        Element authorInfo = msg.getElementsByAttributeValue("class", "msgBody").first();
        System.out.println(authorInfo.getElementsByAttribute("href").text());

        String description = msg.getElementsByAttributeValue("class", "msgBody").last().text();
        System.out.println(description);

        String[] timeField = msg.getElementsByAttributeValue("class", "msgFooter")
                .text().split(" \\[");
        LocalDate date = changeData(timeField[0]);
        System.out.println(date);
    }
}