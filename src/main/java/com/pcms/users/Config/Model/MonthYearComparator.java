package com.pcms.users.Config.Model;
import java.time.Month;
import java.util.Comparator;
public class MonthYearComparator implements Comparator<String>{
    @Override
    public int compare(String o1, String o2) {
        String[] parts1 = o1.split(" ");
        String[] parts2 = o2.split(" ");
        Month month1 = Month.valueOf(parts1[0]);
        Month month2 = Month.valueOf(parts2[0]);
        int year1 = Integer.parseInt(parts1[1]);
        int year2 = Integer.parseInt(parts2[1]);
        if (year1 != year2) {
            return Integer.compare(year2, year1); // Descending order by year
                 } else {
            return month2.compareTo(month1); // Descending order by month
             }
    }
}

