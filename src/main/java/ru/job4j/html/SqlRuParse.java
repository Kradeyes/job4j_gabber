package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements date = doc.select(".altCol");
        Iterator<Element> iterator = date.iterator();
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

    public static LocalDate changeData(String s) {
        String old = null, exchange = null;
        if (s.contains("сегодня") || s.contains("вчера")) {
            LocalDate today = LocalDate.now();
            return s.contains("сегодня") ? today : today.minusDays(1);
        } else {
        List<String> wrong = List.of("янв", "фев",
                "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек");
        List<String> correct = List.of("янв.", "февр.",
                "мар.", "апр.", "мая", "июн.", "июл.", "авг.", "сент.", "окт.", "нояб.", "дек.");
       for (int i = 0; i < wrong.size(); i++) {
           if (s.contains(wrong.get(i))) {
               old = wrong.get(i);
               exchange = correct.get(i);
           }
         }
        }
        String txt = s.replace(old, exchange);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMM yy, HH:mm", new Locale("ru"));
        return LocalDate.parse(txt, formatter);
    }
}