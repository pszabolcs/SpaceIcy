package edu.ubb.si.crawler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubb.si.model.Document;
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

    private Map<String, Document> imageDocuments;

    private int numberOfCrawlers;

    private String storageFolder;

    private int maxDepthOfCrawling;

    private int maxPagesToFetch;


    public Controller()
    {
        imageDocuments = new HashMap<String, Document>();
        numberOfCrawlers = 10;
        storageFolder = "CrawlerFolder";
        maxDepthOfCrawling = 1;
        maxPagesToFetch = 10;
    }

    public Controller(Map<String, Document> imageDocuments, int numberOfCrawlers, String storageFolder, int maxDepthOfCrawling, int maxPagesToFetch) {
        this.imageDocuments = imageDocuments;
        this.numberOfCrawlers = numberOfCrawlers;
        this.storageFolder = storageFolder;
        this.maxDepthOfCrawling = maxDepthOfCrawling;
        this.maxPagesToFetch = maxPagesToFetch;
    }

    public void setMaxPagesToFetch(int maxPagesToFetch) {
        this.maxPagesToFetch = maxPagesToFetch;
    }

    public void setMaxDepthOfCrawling(int maxDepthOfCrawling) {
        this.maxDepthOfCrawling = maxDepthOfCrawling;
    }

    public void setStorageFolder(String storageFolder) {
        this.storageFolder = storageFolder;
    }

    public void setNumberOfCrawlers(int numberOfCrawlers) {
        this.numberOfCrawlers = numberOfCrawlers;
    }

    public Map<String, Document> getImageDocuments() {
        return imageDocuments;
    }


    public void fetchPage(String url) throws Exception {

        CrawlConfig config = new CrawlConfig();
        config.setMaxDepthOfCrawling(maxDepthOfCrawling);
        config.setCrawlStorageFolder(storageFolder);
        config.setMaxPagesToFetch(maxPagesToFetch);

        String[] crawlDomains = {url};

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        for (String domain : crawlDomains) {
            controller.addSeed(domain);
        }

        ImageCrawler.configure(crawlDomains);

        logger.info("ImageController", "ImageCrawler is started!");
        controller.start(ImageCrawler.class, numberOfCrawlers);
    }
}
