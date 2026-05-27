package com.algorithm.capital.one;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**

 # Past Due Suppression Offer System

 Implement a system for offering customers forgiveness on missed payments.

 ## Background

 Missing a credit card payment, or being "past due", has negative consequences for customers, in the form of fees,
 higher interest rates, negative credit scoring, and more. Capital One decides to roll out a new program for financially
 struggling customers called "Past Due Suppression". Customers who qualify for this program will be considered to be in
 good standing during the program period, even if they missed any payments in that time.

 ## Problem Statement

 A "Past Due Suppression" offer is defined in terms of a start date, the number of payment cycles for which it applies,
 the direction in time (counting forwards from the start date or backwards), and whether the payment cycle containing
 the start date is included.

 Customer data arrives in the form of a list of past payment cycles, each flagged with whether payment was past-due or not.

 Your program must transform input to turn off, or suppress, the "past due" flag as appropriate to the input
 Past Due Suppression offer.

 ## Sample Input

 This is how the data might arrive, but you can come up with other representations if you prefer.

 ### Customer records

 ```
 [
 {
 "Customer_Id":"123",
 "Pay_Cycles":[
 {"Cycle_Number":1, "Start_Date":"07-Aug-2021", "End_Date":"06-Sep-2021", "Past_Due_Indicator":1},
 {"Cycle_Number":2, "Start_Date":"07-Jul-2021", "End_Date":"06-Aug-2021", "Past_Due_Indicator":1},
 {"Cycle_Number":3, "Start_Date":"07-Jun-2021", "End_Date":"06-Jul-2021", "Past_Due_Indicator":1},
 {"Cycle_Number":4, "Start_Date":"07-May-2021", "End_Date":"06-Jun-2021", "Past_Due_Indicator":1},
 ...
 ]
 }
 ]
 ```

 ### Past Due Suppression Offer
 ```
 {
 "Offer_Id" : "12345",
 "Offer_Start_Date" : "21-Jul-2021",
 "Offer_Validity" : 2,
 "Offer_Direction" : "Backward",
 "Offer_Cycle_Inclusion": "Include"
 }
 ```

 */

public class App {
    public static void main(String[] args) {
        try {
            DateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            PayCycle payCycle1 = new PayCycle(1, df.parse("07-Aug-2021"), df.parse("06-Sep-2021"), 1);
            PayCycle payCycle2 = new PayCycle(2, df.parse("07-Jul-2021"), df.parse("06-Aug-2021"), 1);
            PayCycle payCycle3 = new PayCycle(3, df.parse("07-Jun-2021"), df.parse("06-Jul-2021"), 1);
            PayCycle payCycle4 = new PayCycle(4, df.parse("07-May-2021"), df.parse("06-Jun-2021"), 1);
            List<PayCycle> payCycles = Arrays.asList(payCycle1, payCycle2, payCycle3, payCycle4);
            List<Customer> customers = Arrays.asList(new Customer(123, payCycles));

            Offer offer = new Offer(12345, df.parse("21-Jul-2021"), 2, OfferDirection.FORWARD, OfferCycleInclusion.EXCLUDE);

            for (Customer customer : customers) {
                List<PayCycle> ps = customer.getPayCycles();

                for (int i = 0; i < ps.size(); i++) {
                    PayCycle payCycle = ps.get(i);
                    Date startDate = payCycle.getStartDate();
                    Date endDate = payCycle.getEndDate();
                    OfferDirection offerDirection = offer.getOfferDirection();
                    int offerValidity = offer.getOfferValidity();
                    int incr = offer.getOfferCycleInclusion() == OfferCycleInclusion.INCLUDE ? 1 : 0;
                    Date offerDate = offer.getOfferStartDate();
                    Date offerStartDate = null;
                    Date offerEndDate = null;
                    switch (offerDirection) {
                        case FORWARD:
                            offerEndDate = addMonths(offerDate, offerValidity - incr);
                            offerStartDate = offerDate;
                            break;
                        case BACKWARD:
                            offerStartDate = addMonths(offerDate, -offerValidity + incr);
                            offerEndDate = offerDate;
                            break;
                    }

                    boolean endBw = endDate.after(offerStartDate) && endDate.before(offerEndDate);
                    boolean startBw = startDate.after(offerStartDate) && startDate.before(offerEndDate);
                    if (endBw || startBw) {
                        payCycle.setPastDueIndicator(0);
                        System.out.println(payCycle);
                    }
                }
            }
        } catch (ParseException parseException) {
            System.err.println("Cannot be parsed: " + parseException);
        }

    }

    public static Date addMonths(Date date, long monthsToAdd) {
        // Convert Date to Instant, then apply a time zone to get a ZonedDateTime
        ZonedDateTime zonedDateTime = date.toInstant().atZone(ZoneId.systemDefault());

        // Add months to the ZonedDateTime (returns a new ZonedDateTime)
        ZonedDateTime updatedZonedDateTime = zonedDateTime.plusMonths(monthsToAdd);

        // Convert back to java.util.Date
        return Date.from(updatedZonedDateTime.toInstant());
    }
}
