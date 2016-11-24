package com.mcgirk.kitchenassistant;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Martin on 23/11/2016.
 */
public class FoodConverterTests {
    private static final String KNOWN_FOOD_TYPE = "rice"; //One cup = 190g
    private static final String KNOWN_FOOD_TYPE_TWO = "butter"; //One cup = 240g
    private static final String UNKNOWN_FOOD_TYPE = "unknown food";

    @Test
    public void canConvertKnownFoodToGrams() throws UnknownFoodException {
        String result = tryFoodConversion(KNOWN_FOOD_TYPE, 1);
        assertTrue(!result.isEmpty());
        assertEquals("1 cup of rice is the same as 190 grams.", result);
    }

    @Test
    public void canConvertFoodWithLowerCase() throws UnknownFoodException {
        String result = tryFoodConversion(KNOWN_FOOD_TYPE.toUpperCase(), 1);
        assertTrue(!result.isEmpty());
        assertEquals("1 cup of rice is the same as 190 grams.", result);
    }

    @Test
    public void canConvertFoodWithUpperCase() throws UnknownFoodException {
        String result = tryFoodConversion(KNOWN_FOOD_TYPE.toUpperCase(), 1);
        assertTrue(!result.isEmpty());
        assertEquals("1 cup of rice is the same as 190 grams.", result);
    }

    @Test
    public void checkConvesionsForDifferentFoodsAreDifferent() throws UnknownFoodException {
        String result1 = tryFoodConversion(KNOWN_FOOD_TYPE, 1);
        String result2 = tryFoodConversion(KNOWN_FOOD_TYPE_TWO, 1);
        assertNotEquals(result1, result2);
    }

    @Test(expected = UnknownFoodException.class)
    public void throwsExceptionWhenWrongFoodPassedIn() throws UnknownFoodException {
        String result = tryFoodConversion(UNKNOWN_FOOD_TYPE, 1);
    }

    @Test
    public void canConvertTwoCups() throws UnknownFoodException {
        String result = tryFoodConversion(KNOWN_FOOD_TYPE, 2);
        assertEquals("2 cups of rice is the same as 380 grams.", result);
    }

    @Test
    public void canConvertTwoHalfACup() throws UnknownFoodException {
        String result = tryFoodConversion(KNOWN_FOOD_TYPE, 0.5f);
        assertEquals("0.5 cups of rice is the same as 95 grams.", result);
    }

    //Helper Methods
    private String tryFoodConversion(String foodName, float cups) throws UnknownFoodException{
        FoodConverter converter = new FoodConverter();
        return converter.convert(foodName, cups);
    }
}
