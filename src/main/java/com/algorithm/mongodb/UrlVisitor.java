package com.algorithm.mongodb;

import java.util.List;
import java.util.Queue;

class UrlVisitor implements Runnable {
    List<Url> links;
    Queue<Url> urls;

    public UrlVisitor(List<Url> links, Queue<Url> urls) {
        this.links = links;
        this.urls = urls;
    }

    @Override
    public void run() {
        for (Url link : links) {
            synchronized (link) {
                if (link.urlState != UrlState.VISITED) {
                    System.out.println("Visiting: " + link.link);
                    urls.add(link);
                }
            }
        }
    }
}
