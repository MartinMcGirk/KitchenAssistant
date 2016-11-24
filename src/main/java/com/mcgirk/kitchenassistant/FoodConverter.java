package com.mcgirk.kitchenassistant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Created by Martin on 23/11/2016.
 */
public class FoodConverter {
    // Returns a mapping between food name and the equivalent of 1 cup in grams
    private static Map<String, Integer> conversions;
    static {
        Map<String, Integer> conversionData = new HashMap<>();
        conversionData.put("breadcrumbs", 150);
        conversionData.put("bread flour", 136);
        conversionData.put("brown sugar", 180);
        conversionData.put("butter", 240);
        conversionData.put("Caster sugar", 200);
        conversionData.put("chopped nuts", 150);
        conversionData.put("cocoa powder", 125);
        conversionData.put("corn starch", 120);
        conversionData.put("cornflour", 120);
        conversionData.put("couscous", 180);
        conversionData.put("cream cheese", 120);
        conversionData.put("dried apricots", 160);
        conversionData.put("dried breadcrumbs", 150);
        conversionData.put("dry breadcrumbs", 150);
        conversionData.put("flaked almonds", 100);
        conversionData.put("flour", 120);
        conversionData.put("fresh breadcrumbs", 60);
        conversionData.put("glace cherries", 200);
        conversionData.put("granulated sugar", 200);
        conversionData.put("grated parmesan", 100);
        conversionData.put("grated cheddar", 100);
        conversionData.put("ground almonds", 115);
        conversionData.put("ground nuts", 120);
        conversionData.put("hazelnuts", 135);
        conversionData.put("honey", 340);
        conversionData.put("icing sugar", 100);
        conversionData.put("margarine", 225);
        conversionData.put("molasses", 340);
        conversionData.put("nuts", 150);
        conversionData.put("oats", 140);
        conversionData.put("peas", 150);
        conversionData.put("pecans", 115);
        conversionData.put("plain", 140);
        conversionData.put("raisins", 200);
        conversionData.put("rice", 190);
        conversionData.put("rolled oats", 90);
        conversionData.put("shredded coconut", 140);
        conversionData.put("sultanas", 200);
        conversionData.put("sugar", 128);
        conversionData.put("syrup", 340);
        conversionData.put("table salt", 300);
        conversionData.put("treacle", 350);
        conversionData.put("uncooked rice", 140);
        conversionData.put("vegetable shortening", 190);
        conversionData.put("walnuts", 115);
        conversions = Collections.unmodifiableMap(conversionData);
    }

    public String convert(String foodType, int cups) throws UnknownFoodException {
        String food = foodType.toLowerCase();
        if (conversions.containsKey(food)) {
            int gramsInOneCup = conversions.get(food);
            String cupOrCups = (cups == 1) ? "cup" : "cups";
            int grams = gramsInOneCup * cups;
            return String.format("%d %s of %s is the same as %d grams.", cups, cupOrCups, food, grams);
        } else {
            throw new UnknownFoodException("There is no transformation available for that food at this time");
        }
    }
}
