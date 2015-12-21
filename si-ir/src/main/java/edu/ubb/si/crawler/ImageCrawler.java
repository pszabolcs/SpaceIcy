package edu.ubb.si.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by csanad on 12/13/15.
 */
public class ImageCrawler extends WebCrawler {

    private static final Pattern filters = Pattern
            .compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
                    + "|bmp|gif|jpe?g|png|tiff"
                    + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static String[] crawlDomains;

    private Map<String, List<String>> images;

    public ImageCrawler() {
        images = new HashMap<>();
    }

    public static void configure(String[] domain) {
        crawlDomains = domain;
    }

    public Map<String, List<String>> getImages() {
        return images;
    }

    public void setImages(Map<String, List<String>> images) {
        this.images = images;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        if (filters.matcher(href).matches())
            return false;

        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }

        return false;
    }


    @Override
    public void visit(Page page) {

        Document doc = null;
        try {
            doc = Jsoup.connect(page.getWebURL().getURL()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get all img tags
        Elements img = doc.getElementsByTag("img");

        int counter = 0;

        // Loop through img tags
        for (Element el : img) {

            String imageUrl = el.attr("src");
            String imageContext = el.attr("alt");

            if (images.get(imageUrl) == null)
                images.put(imageUrl, new ArrayList<>());

            images.get(imageUrl).add(imageContext);
        }
    }


}

