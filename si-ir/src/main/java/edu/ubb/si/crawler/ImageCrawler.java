package edu.ubb.si.crawler;

import edu.ubb.si.model.Document;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by csanad on 12/13/15.
 */
public class ImageCrawler extends WebCrawler {

    private static final Pattern filters = Pattern
            .compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" + "|bmp|gif|jpe?g|png|tiff?" + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static String[] crawlDomains;

    public static void configure(String[] domain) {
        crawlDomains = domain;

    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String href = url.getURL().toLowerCase();
        if (filters.matcher(href).matches()) {
            return false;
        }

        for (String domain : crawlDomains) {
            if (href.startsWith(domain)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void visit(Page page) {
        int contextLength = 100;

        String patternHTMLTags = "\\<(?!img).*?>";
        String patternIMG = "\\<img.*?>";

        String patternIMGContext = "(.{1," + Integer.toString(contextLength) + "})"
                + "(" + patternIMG + ")"
                + "(.{1," + Integer.toString(contextLength)+ "})";

        String url = page.getWebURL().getURL();

        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(url).get();

            doc.select("script").remove();
            removeComments(doc);

            String docStr = doc.toString();
            docStr = docStr.replaceAll(patternHTMLTags, "");
            docStr = docStr.replaceAll("\\s+", " ");

            Pattern pattern = Pattern.compile(patternIMGContext);
            Matcher matcher = pattern.matcher(docStr);

            while (matcher.find())
            {
                String imgTag = matcher.group(2);
                String imgContext = matcher.group(1) + " " + matcher.group(3);
                logger.info(imgTag + " - " + imgContext);

                org.jsoup.nodes.Document imgDoc = Jsoup.parse(imgTag);
                Elements imgElement = imgDoc.getElementsByTag("img");

                Document newDocument = new Document(
                                            imgElement.attr("src"),
                                            imgElement.attr("alt"),
                                            imgContext);

                //TODO - Adding the documents to the map!!!

            }
        } catch (IOException e) {
            logger.warn("Can't connect to url: " + url + ". Skipped!");
        }
    }

    private void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
    }
}

