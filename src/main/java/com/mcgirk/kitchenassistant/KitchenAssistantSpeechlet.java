package com.mcgirk.kitchenassistant;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a simple speechlet for handling requests for conversions between cups and grams.
 */
public class KitchenAssistantSpeechlet implements Speechlet {

    private static final String SLOT_FOOD_TYPE = "Food_Type";

    // Returns a mapping between food name and the equivalent of 1 cup in grams
    private Map<String, Integer> getConversionsList() {
        Map<String, Integer> conversions = new HashMap<>();
        conversions.put("breadcrumbs", 150);
        conversions.put("bread flour", 136);
        conversions.put("brown sugar", 180);
        conversions.put("butter", 240);
        conversions.put("Caster sugar", 200);
        conversions.put("chopped nuts", 150);
        conversions.put("cocoa powder", 125);
        conversions.put("corn starch", 120);
        conversions.put("cornflour", 120);
        conversions.put("couscous", 180);
        conversions.put("cream cheese", 120);
        conversions.put("dried apricots", 160);
        conversions.put("dried breadcrumbs", 150);
        conversions.put("dry breadcrumbs", 150);
        conversions.put("flaked almonds", 100);
        conversions.put("flour", 120);
        conversions.put("fresh breadcrumbs", 60);
        conversions.put("glace cherries", 200);
        conversions.put("granulated sugar", 200);
        conversions.put("grated parmesan", 100);
        conversions.put("grated cheddar", 100);
        conversions.put("ground almonds", 115);
        conversions.put("ground nuts", 120);
        conversions.put("hazelnuts", 135);
        conversions.put("honey", 340);
        conversions.put("icing sugar", 100);
        conversions.put("margarine", 225);
        conversions.put("molasses", 340);
        conversions.put("nuts", 150);
        conversions.put("oats", 140);
        conversions.put("peas", 150);
        conversions.put("pecans", 115);
        conversions.put("plain", 140);
        conversions.put("raisins", 200);
        conversions.put("rice", 190);
        conversions.put("rolled oats", 90);
        conversions.put("shredded coconut", 140);
        conversions.put("sultanas", 200);
        conversions.put("sugar", 128);
        conversions.put("syrup", 340);
        conversions.put("table salt", 300);
        conversions.put("treacle", 350);
        conversions.put("uncooked rice", 140);
        conversions.put("vegetable shortening", 190);
        conversions.put("walnuts", 115);
        return conversions;
    }



    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        if ("ConvertToGramsIntent".equals(intentName)) {
            return getConvertToGramsResponse(intent);
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getHelpResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        // any cleanup logic goes here
    }

    /**
     * Creates and returns a {@code SpeechletResponse} with a welcome message.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getWelcomeResponse() {
        String speechText = "Welcome to the Alexa Kitchen Assistant";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Kitchen Assistant");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the hello intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getConvertToGramsResponse(final Intent intent) {
        String speechText = null;
        Slot foodTypeSlot = intent.getSlot(SLOT_FOOD_TYPE);

        if (foodTypeSlot == null || foodTypeSlot.getValue() == null) {
            speechText = "I don't know";
        } else {
            String foodTypeName = foodTypeSlot.getValue();
            Map<String, Integer> conversions = getConversionsList();

            if (conversions.containsKey(foodTypeName.toLowerCase())) {
                String grams = Integer.toString(conversions.get(foodTypeName.toLowerCase()));
                speechText = "One cup of " + foodTypeName + " is the same as " + grams + " grams.";
            } else {
                speechText = "I'm afraid I don't know that food";
            }
        }


        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Kitchen Assistant");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    /**
     * Creates a {@code SpeechletResponse} for the help intent.
     *
     * @return SpeechletResponse spoken and visual response for the given intent
     */
    private SpeechletResponse getHelpResponse() {
        String speechText = "Welcome to Kitchen Assistant. You can ask me to convert certain foods between cups and grams.";

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Kitchen Assistant");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        // Create reprompt
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(speech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }
}
