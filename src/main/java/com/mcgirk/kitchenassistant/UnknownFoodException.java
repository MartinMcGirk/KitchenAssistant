package com.mcgirk.kitchenassistant;

/**
 * Created by Martin on 23/11/2016.
 */
public class UnknownFoodException extends Throwable {
    public UnknownFoodException(String message) {
        super(message);
    }
}
