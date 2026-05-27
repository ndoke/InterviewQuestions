package com.algorithm.merchantonboarding;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MerchantOnboarding {
    public final Map<Country, Set<Identification>> COUNTRY_TO_ID =
            Map.of(
                    Country.US, Set.of(Identification.ID, Identification.TAX_ID),
                    Country.DE, Set.of(Identification.ID, Identification.TAX_ID, Identification.VAT_ID)
            );

    public boolean onboardMerchant(Country country, Set<Identification> ids) {
        Set<Identification> requiredIds = COUNTRY_TO_ID.get(country);
        if (ids.containsAll(requiredIds)) {
            return true;
        }

        Set<Identification> result = requiredIds.stream()
                .filter(element -> !ids.contains(element))
                .collect(Collectors.toUnmodifiableSet());
        System.out.println("Missing documents: " + result);
        return false;
    }
}
