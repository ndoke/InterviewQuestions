package com.algorithm.stripe.idempotentpayouts;

import java.util.HashMap;
import java.util.Map;

public interface Bankdata {
    Map<String, Request> processedRequests = new HashMap<>();
    Map<String, AccountInfo> accountsData =
            Map.of(
                    "id_101", new AccountInfo("id_101", 100),
                    "id_102", new AccountInfo("id_102", 200)
            );
}
