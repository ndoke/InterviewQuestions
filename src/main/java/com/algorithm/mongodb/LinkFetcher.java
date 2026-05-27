package com.algorithm.mongodb;

import java.util.List;

interface LinkFetcher {
    List<Url> fetchAndParse(Url url);
}
