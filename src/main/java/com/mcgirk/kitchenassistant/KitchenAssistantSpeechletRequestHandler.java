package com.mcgirk.kitchenassistant;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

/**
 * Created by Martin on 15/11/2016.
 */
public final class KitchenAssistantSpeechletRequestHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.4ccd1942-ec5f-4faa-9719-c9f18d567e2f");
    }

    public KitchenAssistantSpeechletRequestHandler() {
        super(new KitchenAssistantSpeechlet(), supportedApplicationIds);
    }
}
