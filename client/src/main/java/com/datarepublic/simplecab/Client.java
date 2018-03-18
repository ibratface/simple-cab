package com.datarepublic.simplecab;

import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Client {

    private static void printHelp() {
        StringBuffer help = new StringBuffer();
        help.append("usage: Client <command> <url> <date> <medallion>...\n");
        help.append("\n");
        help.append("<command>\n");
        help.append("  load          load cab trip counts of the specified date\n");
        help.append("  load-fresh    load cab trip counts and ignore the cache\n");
        help.append("  clear         clears the local cache\n");
        help.append("  help          prints out this help screen\n");
        help.append("\n");
        help.append("<url>           url of the server\n");
        help.append("\n");
        help.append("<date>          date in the format yyyy-mm-dd\n");
        help.append("\n");
        help.append("<medallion>...  cab medallions to retrieve separated by spaces\n");
        System.out.println(help);
    }

    private static void load(String[] args, boolean useCache) {
        if (args.length < 4)
        {
            printHelp();
            return;
        }

        SimpleCabService svc = new SimpleCabHttpService(args[1]);
        try
        {
            Date pickupDate = SimpleCabUtil.DATE_FORMATTER.parse(args[2]);
            List<String> medallions = new ArrayList<>();
            for (int i = 3; i < args.length; i++)
            {
                medallions.add(args[i]);
            }
            
            Map<String, Integer> results = svc.getTripCountsBy(medallions, pickupDate);
            if (results != null) results.forEach((medallion, tripCount) -> {
                System.out.format("%s\t%s\n", medallion, tripCount);
            });
        } catch (ParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void clear() {
    }

    public static void main(String[] args) {
        if (args.length < 1)
            printHelp();
        else
            switch (args[0]) {
            case "load":
                load(args, true);
                break;
            case "load-fresh":
                load(args, false);
                break;
            case "clear":
                clear();
                break;
            default:
                printHelp();
            }
    }
}
