package com.algorithm.merchantonboarding;

import java.util.Set;

public class MerchantOnboardingApp {
    public static void main(String[] args) {
        MerchantOnboarding merchantOnboarding = new MerchantOnboarding();
        System.out.println(merchantOnboarding.onboardMerchant(Country.DE, Set.of(Identification.ID)));
    }
}
