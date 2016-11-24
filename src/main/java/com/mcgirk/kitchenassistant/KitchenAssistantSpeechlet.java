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
            speechText = "I'm sorry, I didn't catch that.";
        } else {
            String foodTypeName = foodTypeSlot.getValue();
            FoodConverter converter = new FoodConverter();
            try {
                speechText = converter.convert(foodTypeName, 1);
            } catch (UnknownFoodException e) {
                speechText = e.getMessage();
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
