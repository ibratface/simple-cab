package com.datarepublic.simplecab;

import java.util.List;
import java.util.stream.Collectors;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

public class Client {

    public static final String CACHE_FILE = "simplecab.cache";

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
        if (args.length < 4) {
            System.out.println("Insufficient arguments");
            printHelp();
        } else {
            try {
                // extract parameters
                String baseUrl = args[1];
                Date pickupDate = SimpleCabUtil.DATE_FORMATTER.parse(args[2]);
                List<String> medallions = Arrays.asList(args).subList(3, args.length);

                SimpleCabService server = new SimpleCabHttpService(baseUrl);
                SimpleCabCache cache = new SimpleCabCache(CACHE_FILE);

                // by default we request all medallions
                List<String> uncached = medallions;
                if (useCache) {
                    // only request those not in the cache
                    uncached = medallions.stream()
                        .filter(medallion -> !cache.contains(medallion, pickupDate))
                        .collect(Collectors.toList());
                }

                if (!uncached.isEmpty())
                {
                    // fetch uncached from the server
                    TripSummary results = server.getTripSummary(uncached, pickupDate);

                    // always cache the results
                    results.forEach((medallion, tripCount) -> {
                        cache.put(medallion, pickupDate, tripCount);
                    });
                }

                // print out results if found
                medallions.forEach(medallion -> {
                    Integer tripCount = cache.get(medallion, pickupDate);
                    if (tripCount != null) System.out.format("%s\t%s\n", medallion, tripCount);
                });

                // always save the cache
                cache.save(CACHE_FILE);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }
    }

    private static void clear() {
        SimpleCabCache cache = new SimpleCabCache(CACHE_FILE);
        cache.clear();
        cache.save(CACHE_FILE);
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
