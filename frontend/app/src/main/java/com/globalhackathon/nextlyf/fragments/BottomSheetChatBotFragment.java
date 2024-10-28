package com.globalhackathon.nextlyf.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.globalhackathon.nextlyf.adapter.ChatBotAdapter;
import com.globalhackathon.nextlyf.adapter.RoomAdapter;
import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.BottomSheetChatBinding;
import com.globalhackathon.nextlyf.listeners.ChatResponseListener;
import com.globalhackathon.nextlyf.listeners.PaymentConfirmationListener;
import com.globalhackathon.nextlyf.listeners.RecommendationListener;
import com.globalhackathon.nextlyf.model.ChatMessage;
import com.globalhackathon.nextlyf.model.ChatRequest;
import com.globalhackathon.nextlyf.model.RecommendationRequest;
import com.globalhackathon.nextlyf.model.Rooms;
import com.globalhackathon.nextlyf.model.UserDetails;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.globalhackathon.nextlyf.utils.SpaceItemDecoration;
import com.globalhackathon.nextlyf.view.AudioWaveView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;

public class BottomSheetChatBotFragment extends BottomSheetDialogFragment implements ChatResponseListener, PaymentConfirmationListener {


    private static final int SPEECH_REQUEST_CODE = 0;

    BottomSheetChatBinding binding;
    ChatBotAdapter chatBotAdapter;

    BottomSheetChatBotFragment bottomSheetChatBotFragment;

    APIManager apiManager;

    private List<ChatMessage> messagesList = new ArrayList<>();
    private int currentUserMessageIndex = -1;

    private TextToSpeech textToSpeech;
    private SpeechRecognizer speechRecognizer;

    private AudioWaveView audioWaveView;

    private boolean isListening = true;

    private String sessionId;
    private UserDetails userDetails;

    private UserSignUpData userSignUpData;

    private Boolean isTempRoomObject = false;

    private List<Rooms> roomsList;

    private FirebaseFirestore db;

    private String roomPreferenceMessage = "Great! You are all set to book a room. " +
            "Do you have any request or anything specific you are looking for?";

    public BottomSheetChatBotFragment(String sessionId, UserDetails userDetails) {
        this.sessionId = sessionId;
        this.userDetails = userDetails;
    }

    public BottomSheetChatBotFragment(String sessionId, UserDetails userDetails, UserSignUpData userSignUpData) {
        this.sessionId = sessionId;
        this.userDetails = userDetails;
        this.userSignUpData = userSignUpData;
        this.bottomSheetChatBotFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetChatBinding.inflate(inflater);

        chatBotAdapter = new ChatBotAdapter(messagesList, userSignUpData);
        audioWaveView = binding.audioWaveView;

        apiManager = new APIManager("custom");
        roomsList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        binding.recyclerViewChat.setAdapter(chatBotAdapter);
        binding.recyclerViewChat.addItemDecoration(new SpaceItemDecoration(16));
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(getContext()));

        requestPermissions();
        //Initialize textToSpeech

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //TODO: As per user profile's language
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch(0.8f);
                    textToSpeech.setSpeechRate(1.0f);
//                    Voice voice = new Voice("en-us-x-sfg#male_1-local", new Locale("en", "US"), 400, 200, true, null);
//                    textToSpeech.setVoice(voice);

//                    if (sentiment.equalsIgnoreCase("positive")) {
//                        textToSpeech.setPitch(1.5f);  // Higher pitch for happy tone
//                        textToSpeech.setSpeechRate(1.3f);  // Faster speed for happy tone
//                    } else if (sentiment.equalsIgnoreCase("negative")) {
//                        textToSpeech.setPitch(0.7f);  // Lower pitch for sad tone
//                        textToSpeech.setSpeechRate(0.8f);  // Slower speed for sad tone
//                    } else {
//                        textToSpeech.setPitch(1.0f);  // Neutral pitch
//                        textToSpeech.setSpeechRate(1.0f);  // Neutral speed
//                    }
                    Handler mainHandler = new Handler(Looper.getMainLooper());

                    textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                            Log.d("TextToSpeech", "Started");

                        }

                        @Override
                        public void onDone(String s) {
                            Log.d("TextToSpeech", "Done");
                            mainHandler.post(() ->
                            {
                                startSpeechRecognition();
                            });
                        }

                        @Override
                        public void onError(String s) {
                            Log.e("TextToSpeech", "Error");

                        }
                    });
                }
            }
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizer.setRecognitionListener(new CustomRecognitionListener());

        binding.micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start speech recognition
                binding.chatTextLayout.animate().alpha(0f).setDuration(300).withEndAction(() -> binding.chatTextLayout.setVisibility(View.GONE));
                binding.chatListeningLayout.setVisibility(View.VISIBLE);
                binding.chatListeningLayout.setAlpha(0f);
                binding.chatListeningLayout.animate().alpha(1f).setDuration(300);
                audioWaveView.setSpeaking(true);
                audioWaveView.setSpeedMultiplier(0.8f);

                startSpeechRecognition();
            }
        });

        sendChatRequest("", true);

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.editTextMessage.getText().toString();
                if (!message.isEmpty()) {
                    messagesList.add(new ChatMessage(message, false));
                    chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
                    binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);
//                    simulateBotResponse();
                    binding.editTextMessage.setText("");
                    sendChatRequest(message, false);
                }
            }
        });

        binding.buttonCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioWaveView.setSpeaking(false);
                audioWaveView.stopAnimation();
                isListening = false;
                speechRecognizer.stopListening();
                binding.chatListeningLayout.animate().alpha(0f).setDuration(300).withEndAction(() ->
                        binding.chatListeningLayout.setVisibility(View.GONE));
                binding.chatTextLayout.setVisibility(View.VISIBLE);
                binding.chatTextLayout.setAlpha(0f);
                binding.chatTextLayout.animate().alpha(1f).setDuration(300);

                removeChatMessageFromLastIndex();

            }
        });

        return binding.getRoot();

    }

    private void simulateBotResponse() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ChatMessage botMessage = new ChatMessage("This is a response from the bot", true);
                messagesList.add(botMessage);
                chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
                binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);
                textToSpeech.speak(botMessage.getText(), TextToSpeech.QUEUE_FLUSH, null, "bot");

            }
        }, 1000);
    }

    public void sendChatRequest(String message, boolean newSession) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                ChatRequest chatRequest = new ChatRequest(sessionId, newSession, message, userDetails);
                Log.d("NewChatRequest", chatRequest.toString());
                apiManager.converse(chatRequest, BottomSheetChatBotFragment.this);
            }
        }, 100);
    }

    private void startSpeechRecognition() {
        //Start speech recognition
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...");

        Log.d("SpeechRecognition", "Start Listening");
        speechRecognizer.startListening(intent);

        startInitialCustomerMessage();

        audioWaveView.startAnimation();


//
//       startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    private void stopListening() {
        if (isListening) {
            isListening = false;

            audioWaveView.setSpeaking(false);  // Set wave to idle mode (single color waves)
            // Stop Speech Recognition
            speechRecognizer.stopListening();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && result.size() > 0) {
                String userMessage = result.get(0); // Get the user's speech as text
                messagesList.add(new ChatMessage(userMessage, false)); // Add user's message to the chat
                simulateBotResponse(); // Get bot response
            }
        }
    }

    private void startInitialCustomerMessage() {
        ChatMessage userMessage = new ChatMessage("", false);
        messagesList.add(userMessage);
        currentUserMessageIndex = messagesList.size() - 1;
        chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
        binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);
    }

    @Override
    public void onSuccess(String response) {
        Log.d("ChatBotResponse", response);
        ChatMessage botMessage = new ChatMessage(response, true);
        botMessage.setIsRoomObject(response.contains("book_room") ? true : false);

        if(botMessage.getIsRoomObject()){
            isTempRoomObject = true;
            botMessage.setIsRoomObject(false);
            botMessage.setText(roomPreferenceMessage);
        } else if (isTempRoomObject) {
            isTempRoomObject = false;
            getRoomRecommendation();

            botMessage.setIsRoomObject(true);
            Log.d("ChatBotRoom", "Notifying about room");
            return;
        }
        messagesList.add(botMessage);
        chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
        binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);

        if(!botMessage.getIsRoomObject()) {
            textToSpeech.speak(botMessage.getText().replaceAll("[\\p{So}\\p{Cn}]", ""), TextToSpeech.QUEUE_FLUSH, null, "bot");
        } else {
            textToSpeech.speak("I have found a room for you as per your needs. Do you want to book it?", TextToSpeech.QUEUE_FLUSH, null, "bot");
        }
    }

    private void onRoomBookingChatBot() throws Exception{
        ChatMessage botMessage = new ChatMessage("book_room", true);
        botMessage.setIsRoomObject(true);

        messagesList.add(botMessage);
        chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
        binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);

        textToSpeech.speak("I have found a room for you as per your needs. Do you want to book it?", TextToSpeech.QUEUE_FLUSH, null, "bot");

    }

    @Override
    public void onFailure(String error) {
        Log.e("ChatBotError", error != null ? error : "Error");
        ChatMessage botMessage = new ChatMessage("This is a response from the bot", true);
        messagesList.add(botMessage);
        chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
        binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);
    }

    private void updateChatMessage(String message) {
        if (currentUserMessageIndex != -1 && messagesList.size() > currentUserMessageIndex) {
            ChatMessage currentMessage = messagesList.get(currentUserMessageIndex);
            currentMessage.setText(message);
            chatBotAdapter.notifyItemChanged(currentUserMessageIndex);
        }
    }

    private void removeChatMessageFromLastIndex() {
        if (currentUserMessageIndex != -1 && messagesList.size() > currentUserMessageIndex) {
            messagesList.remove(currentUserMessageIndex);
            chatBotAdapter.notifyItemRemoved(currentUserMessageIndex);
        }
    }

    @Override
    public void onPaymentConfirmed() {

        Log.d("Payment", "Payment Confirmed");

        ChatMessage botMessage = new ChatMessage("Hurrah! Your booking is confirmed, we are thrilled to welcome you. " +
                "In meanTime if you have any other query or requiring help with your booking, don't forget me to say hi ", true);

        messagesList.add(botMessage);
        chatBotAdapter.notifyItemInserted(messagesList.size() - 1);
        binding.recyclerViewChat.scrollToPosition(messagesList.size() - 1);
        textToSpeech.speak(botMessage.getText().replaceAll("[\\p{So}\\p{Cn}]", ""), TextToSpeech.QUEUE_FLUSH, null, "bot");

    }

    @Override
    public void onPaymentFailed() {

    }

    private class CustomRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.d("SpeechRecognition", "Ready for speech");

        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d("SpeechRecognition", "Beginning of speech");
            audioWaveView.setSpeaking(true);
        }

        @Override
        public void onRmsChanged(float v) {
            Log.d("SpeechRecognition", "RMS Changed: " + v);

            float normalizedVolume = Math.max(0, Math.min(v / 10, 1));  // Normalize rmsdB to a value between 0 and 1
            audioWaveView.setWaveHeightMultiplier(normalizedVolume);

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            Log.d("SpeechRecognition", "End of speech");
            audioWaveView.setSpeaking(false);
            speechRecognizer.stopListening();
        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {
            Log.d("SpeechRecognition", "Results");

            ArrayList<String> speechResults = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (speechResults != null && speechResults.size() > 0) {
                String finalText = speechResults.get(0);
                updateChatMessage(finalText);
                sendChatRequest(finalText, false);
//                simulateBotResponse();
            }

        }

        @Override
        public void onPartialResults(Bundle bundle) {
            Log.d("SpeechRecognition", "Partial Results");
            ArrayList<String> partial = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (partial != null && partial.size() > 0) {
                String partialText = partial.get(0);
                updateChatMessage(partialText);
            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            Log.d("SpeechRecognition", "Event");
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    private void getRoomRecommendation() {

        RecommendationRequest recommendationRequest = new RecommendationRequest(userSignUpData,
                "rooms");

        try {
            apiManager.getRecommendation(recommendationRequest, new RecommendationListener() {
                @Override
                public void onRecommendationSuccess(List<String> response) {

                    Log.d("HomeActivity", "Room Recommendation Success: " + response.toString());

                    //Get the list of rooms from Firebase Firestore Database using the list of roomIds received from API

                    db.collection("rooms")
                            .whereIn(FieldPath.documentId(), response)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // Iterate through the documents and process each user document
                                    List<Rooms> tempList = new ArrayList<>();
                                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                        Rooms room = document.toObject(Rooms.class);
                                        Map<String, Object> roomData = document.getData();
                                        Log.d("Firestore", "Room ID: " + document.getId() + " Data: " + roomData);
                                        tempList.add(room);
                                    }

                                    roomsList.clear();
                                    roomsList.addAll(tempList);
                                    RoomAdapter roomAdapter = new RoomAdapter(roomsList, true, bottomSheetChatBotFragment);
                                    Log.d("ChatBotRoom", "Setting Room Adapter");
                                    chatBotAdapter.setRoomAdapterObj(roomAdapter);

                                    try {
                                        onRoomBookingChatBot();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }


                                } else {
                                    Log.d("Firestore", "[Room ]No matching documents found.");
                                }
                            })
                            .addOnFailureListener(e -> {
                                        Log.w("Firestore", "[Room ]Error retrieving documents", e);

                                    }
                            );

                }

                @Override
                public void onRecommendationFailure(String response) {
                    Log.d("HomeActivity", "[Room ]Recommendation Failure: " + response);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.collection("rooms")
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots -> {
                                    Log.d("HomeActivity", "Rooms data loaded successfully QueryDocumentSnapshots: " +
                                            queryDocumentSnapshots.size());
                                    List<Rooms> tempList = new ArrayList<>(); // Temporary list to avoid main thread blocking
                                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                                        Rooms room = document.toObject(Rooms.class);
                                        Log.d("HomeActivity", "Room: " + room.toString());
                                        tempList.add(room);
                                    }
                                    // Update RecyclerView on the main thread
                                    roomsList.clear();
                                    roomsList.addAll(tempList);

                                    RoomAdapter roomAdapter = new RoomAdapter(roomsList, true);
                                    chatBotAdapter.setRoomAdapterObj(roomAdapter);

                                    try {
                                        onRoomBookingChatBot();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                })
                                .addOnFailureListener(e -> {
                                    // Handle any errors
                                    Log.d("HomeActivity", "Failed to load SharedSpace data: " + e.getMessage());
                                });

                    });

                }
            });
        } catch (Exception ex) {
            Log.d("HomeActivity", "[Room ]Recommendation Exception: " + ex.getMessage());

        }

    }
}
