package edu.ubb.si.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by csanad on 12/13/15.
 */
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public static void main(String[] args) throws Exception {

        int numberOfCrawlers = 7;

        CrawlConfig config = new CrawlConfig();
        config.setMaxDepthOfCrawling(3);
        config.setCrawlStorageFolder("/home/csanad/Documents/CrawlerDoc");
        config.setMaxPagesToFetch(10);

    /*
     * Since images are binary content, we need to set this parameter to
     * true to make sure they are included in the crawl.
     */
        String[] crawlDomains = {"http://www.wareable.com"};

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        ImageCrawler.configure(crawlDomains);

        System.out.println("Starting the crawler");
        controller.start(ImageCrawler.class, numberOfCrawlers);
    }
}
