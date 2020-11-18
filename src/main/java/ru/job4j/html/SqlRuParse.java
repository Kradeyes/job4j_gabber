package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


import static ru.job4j.html.DateUtils.changeData;

public class SqlRuParse implements Parse {
    private List<String> url;
    private int firstPage = 0;
    private int endPage = 0;

    SqlRuParse(String url) {
        this.url = pageCounter(url);
    }

    SqlRuParse(String url, int firstPage, int endPage) {
        this.url = pageCounter(url, firstPage, endPage);
    }

    public static List<String> pageCounter(String url) {
        return List.of(url);
    }

    public static List<String> pageCounter(String url, int firstPage, int endPage) {
        List<String> urls = new ArrayList<>();
        for (int i = firstPage; i < endPage; i++) {
            urls.add(url + i);
        }
        return urls;
    }

    public String getUrl(List<String> url) {
        String rsl = null;
        for (String x: url) {
            rsl = x;
        }
      return rsl;
    }

    @Override
    public List<Post> list(String url) {
        Document doc = null;
        List<Post> allPosts = new ArrayList<>();
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements row = Objects.requireNonNull(doc).select(".postslisttopic");
         for (int i = 3; i < row.size(); i++) {
            Element href = row.get(i).child(0);
            allPosts.add(detail(href.attr("href")));
        }
        return allPosts;
    }

    @Override
    public Post detail(String url) {
        Post post = new Post();
        try {
            Document doc = Jsoup.connect(url).get();
            post.setUrl(url);
            Element msg = doc.select(".msgTable").first();
            post.setTitle(msg.getElementsByAttributeValue("class", "messageHeader").text());
            Element authorInfo = msg.getElementsByAttributeValue("class", "msgBody").first();
            post.setAuthor(authorInfo.getElementsByAttribute("href").text());
            post.setDescription(msg.getElementsByAttributeValue("class", "msgBody").last().text());
            String[] timeField = msg.getElementsByAttributeValue("class", "msgFooter")
                    .text().split(" \\[");
             post.setCreationTime(changeData(timeField[0]));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        String url = "https://www.sql.ru/forum/job-offers/";
        SqlRuParse parse = new SqlRuParse(url);
        /*List<Post> allPosts = parse.list(parse.getUrl(parse.url));
        for (Post x : allPosts) {
            System.out.println(x);
        }*/
        SqlRuParse parse1 = new SqlRuParse(url, 1, 5);
        List<Post> allPosts1 = parse1.list(parse1.getUrl(parse1.url));
        for (Post x: allPosts1) {
            System.out.println(x);
        }
    }
}