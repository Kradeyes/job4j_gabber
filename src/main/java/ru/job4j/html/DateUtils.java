package ru.job4j.html;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

import static java.util.Map.entry;

public class DateUtils {
    public static LocalDate changeData(String s) {
        final Map<String, String> months = Map.ofEntries(
                entry("янв", "янв."),
                entry("фев", "февр."),
                entry("мар", "мар."),
                entry("апр", "апр."),
                entry("май", "мая."),
                entry("июн", "июн."),
                entry("июл", "июл."),
                entry("авг", "авг."),
                entry("сен", "сент."),
                entry("окт", "окт."),
                entry("ноя", "нояб."),
                entry("дек", "дек.")
        );
        String[] split = s.split(" ");
        if (split.length == 2) {
            LocalDate localDate = LocalDate.now();
            return s.contains("сегодня") ? localDate : localDate.minusDays(1);
        } else {
            String txt = s.replace(split[1], months.get(split[1]));
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("d MMM yy, HH:mm", new Locale("ru"));
            return LocalDate.parse(txt, formatter);
        }
    }
}