package com.algorithm.mongodb;

import java.util.*;
import java.util.concurrent.*;

class Solution {
    public static void crawl(Queue<Url> urls, LinkFetcher fetcher) {
        while (!urls.isEmpty()) {
            Url url = urls.poll();

            if (url.urlState != UrlState.VISITED) {
                List<Url> links = fetcher.fetchAndParse(url);
                ExecutorService executorService = Executors.newFixedThreadPool(10);
                executorService.execute(new UrlVisitor(links, urls));
                url.urlState = UrlState.VISITED;
            }
        }
    }
}
