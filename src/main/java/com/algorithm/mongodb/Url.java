package com.algorithm.mongodb;

class Url {
    String link;
    UrlState urlState;

    public Url(String link) {
        this.link = link;
        this.urlState = UrlState.UNVISITED;
    }
}
