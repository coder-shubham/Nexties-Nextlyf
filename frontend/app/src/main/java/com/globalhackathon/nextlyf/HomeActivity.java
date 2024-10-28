package com.globalhackathon.nextlyf;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.globalhackathon.nextlyf.adapter.CategoryAdapter;
import com.globalhackathon.nextlyf.adapter.CommunityStateAdapter;
import com.globalhackathon.nextlyf.adapter.ConnectionAdapter;
import com.globalhackathon.nextlyf.adapter.EventAdapter;
import com.globalhackathon.nextlyf.adapter.RoomAdapter;
import com.globalhackathon.nextlyf.adapter.SharedSpaceAdapter;
import com.globalhackathon.nextlyf.api.APIManager;
import com.globalhackathon.nextlyf.databinding.ActivityHomeBinding;
import com.globalhackathon.nextlyf.fragments.BottomSheetChatBotFragment;
import com.globalhackathon.nextlyf.fragments.CommunityFragments;
import com.globalhackathon.nextlyf.fragments.LoyaltyPointsPopUpFragment;
import com.globalhackathon.nextlyf.fragments.OTPFragment;
import com.globalhackathon.nextlyf.listeners.RecommendationListener;
import com.globalhackathon.nextlyf.model.Category;
import com.globalhackathon.nextlyf.model.Events;
import com.globalhackathon.nextlyf.model.RecommendationRequest;
import com.globalhackathon.nextlyf.model.Rooms;
import com.globalhackathon.nextlyf.model.SearchResult;
import com.globalhackathon.nextlyf.model.SharedSpace;
import com.globalhackathon.nextlyf.model.UserDetails;
import com.globalhackathon.nextlyf.model.UserSignUpData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.search.SearchView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.type.DateTime;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    public ActivityHomeBinding binding;

    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private List<SharedSpace> sharedSpaceList;
    private List<Rooms> roomsList;
    private List<UserSignUpData> userSignUpDataList;

    private List<Events> eventsList;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    APIManager apiManager;

    RecyclerView roomRecyclerView;
    RecyclerView meetRecyclerView;
    RecyclerView sharedRecyclerView;
    RecyclerView eventsRecyclerView;

    RoomAdapter roomAdapter;
    EventAdapter eventAdapter;
    ConnectionAdapter connectionAdapter;
    SharedSpaceAdapter sharedSpaceAdapter;

    FragmentManager fragmentManager;
    CommunityFragments communityFragments;

    UserSignUpData userSignUpData;


    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = new Gson();

        //Get Data passed from Another Intent
        String data = getIntent().getStringExtra("userSignUpData");
        Boolean isFromSignUp = getIntent().getBooleanExtra("isFromSignUp", false);
        Log.d("HomeActivity", "Data: " + data);
        userSignUpData = gson.fromJson(data, UserSignUpData.class);

        Log.d("HomeActivity", "User Data: " + userSignUpData.toString());

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        roomRecyclerView = binding.roomRecyclerView;
        meetRecyclerView = binding.meetRecyclerView;
        sharedRecyclerView = binding.sharedRecyclerView;
        eventsRecyclerView = binding.eventsRecyclerView;
        apiManager = new APIManager(APIManager.ML_BASE_URL);
        fragmentManager = getSupportFragmentManager();
        communityFragments = new CommunityFragments();


        sharedSpaceList = new ArrayList<SharedSpace>();
        roomsList = new ArrayList<Rooms>();
        userSignUpDataList = new ArrayList<>();
        eventsList = new ArrayList<>();
        roomAdapter = new RoomAdapter(roomsList);
        eventAdapter = new EventAdapter(eventsList);
        connectionAdapter = new ConnectionAdapter(userSignUpDataList);
        sharedSpaceAdapter = new SharedSpaceAdapter(sharedSpaceList);

        // Create mock data
        categories = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categories);

        // Properties category with its items
        List<SearchResult> properties = new ArrayList<>();
        properties.add(new SearchResult("Lyf Funan", "Singapore", 4.5));
        properties.add(new SearchResult("Lyf One-North", "Singapore", 4.0));
        properties.add(new SearchResult("Lyf Farrer Park", "Singapore", 3.9));

        // Rooms category with its items
        List<SearchResult> rooms = new ArrayList<>();
        rooms.add(new SearchResult("One of a Kind Plus", "Singapore", 3.8));
        rooms.add(new SearchResult("All Together", "Singapore", 4.1));

        // Events category with its items
        List<SearchResult> events = new ArrayList<>();
        events.add(new SearchResult("Halloween 2024", "Singapore", 4.9));
        events.add(new SearchResult("Switch 2024", "Singapore", 4.7));

        // Adding the categories to the list
        categories.add(new Category("Properties", properties, false));  // Collapsed by default
        categories.add(new Category("Rooms", rooms, false));           // Collapsed by default
        categories.add(new Category("Events", events, false));


        binding.appName.setText(String.format("%s\n%s", getGreetingMessage(), userSignUpData.getName()));
        binding.loyaltyPoints.setText(isFromSignUp ? "100" : "200");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        roomRecyclerView.setLayoutManager(layoutManager);
        roomRecyclerView.setAdapter(roomAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        meetRecyclerView.setLayoutManager(layoutManager2);
        meetRecyclerView.setAdapter(connectionAdapter);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        sharedRecyclerView.setLayoutManager(layoutManager3);
        sharedRecyclerView.setAdapter(sharedSpaceAdapter);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        eventsRecyclerView.setLayoutManager(layoutManager4);
        eventsRecyclerView.setAdapter(eventAdapter);


        Log.d("HomeActivity", "Getting Room Recommendation");
        getRoomRecommendation();
        getUserRecommendation();
        getEventRecommendation();

        binding.searchView.addTransitionListener(new SearchView.TransitionListener() {

            @Override
            public void onStateChanged(@NonNull SearchView searchView, @NonNull SearchView.TransitionState previousState, @NonNull SearchView.TransitionState newState) {
                if (newState == SearchView.TransitionState.SHOWING) {
                    // Hide other UI elements
                    binding.homeScrollView.setVisibility(View.GONE);
                    binding.navigationBarLayout.setVisibility(View.GONE);
                }

                // When search is closed
                if (newState == SearchView.TransitionState.HIDDEN) {
                    // Show UI elements again
                    binding.homeScrollView.setVisibility(View.VISIBLE);
                    binding.navigationBarLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

//        binding.updateMlUrlButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                apiManager.setCustomUrl(binding.mlBaseUrlText.getText().toString());
//                Toast.makeText(HomeActivity.this, "ML URL updated", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.navigation_home) {
                    Log.d("HomeActivity", "Home clicked");
                    binding.homeScrollView.setVisibility(View.VISIBLE);
                    binding.searchBarLayout.setVisibility(View.VISIBLE);
//                    binding.mlUrlLayout.setVisibility(View.VISIBLE);
                    binding.communityFrameLayout.setVisibility(View.GONE);
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(communityFragments);
                    fragmentTransaction.commit();
                    return true;
                } else if (item.getItemId() == R.id.navigation_explore) {
                    Log.d("HomeActivity", "Connections clicked");
                    return true;
                } else if (item.getItemId() == R.id.navigation_community) {

                    //Start Community Fragment
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    communityFragments = new CommunityFragments();
                    fragmentTransaction.add(binding.communityFrameLayout.getId(), communityFragments);
                    fragmentTransaction.addToBackStack("Community");
                    binding.homeScrollView.setVisibility(View.GONE);
                    binding.searchBarLayout.setVisibility(View.GONE);
//                    binding.mlUrlLayout.setVisibility(View.GONE);
//                            signUpBinding.registerLayout.setVisibility(View.GONE);
                    fragmentTransaction.commit();


                    Log.d("HomeActivity", "Community clicked");
                    return true;
                } else if (item.getItemId() == R.id.navigation_account) {
                    Log.d("HomeActivity", "Account clicked");
                    return true;
                } else {
                    return false;
                }
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                Fragment fragment = fragmentManager.findFragmentById(binding.communityFrameLayout.getId());

                if (fragment != null && fragment.isVisible()) {
                    Log.d("HomeActivity", "Community Fragment Found");
                    fragmentManager.beginTransaction().remove(fragment).commit();
                    binding.homeScrollView.setVisibility(View.VISIBLE);
                    binding.searchBarLayout.setVisibility(View.VISIBLE);
//                    binding.mlUrlLayout.setVisibility(View.VISIBLE);
                    FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                    if (childFragmentManager.getBackStackEntryCount() > 0) {
                        childFragmentManager.popBackStack();
                    }
                    binding.bottomNavigation.setSelectedItemId(R.id.navigation_home);
                    return;
                } else {
                    // Remove this callback and call the next one in the chain
                    Log.d("HomeActivity", "Back Pressed");
                    this.remove();
                    getOnBackPressedDispatcher().onBackPressed();
                }

//                for (Fragment fragment : fragmentManager.getFragments()) {
//                    if (fragment != null && fragment.isVisible()) {
//                        FragmentManager childFragmentManager = fragment.getChildFragmentManager();
//                        Log.d("HomeActivity", "Child Fragment Manager: " + fragment.getClass().getSimpleName());
//
//                        if (childFragmentManager.getBackStackEntryCount() > 0) {
//                            childFragmentManager.popBackStack();
//                            return;  // If there is a back stack, pop it and exit the callback
//                        }
//                    }
//                }


            }
        });

        binding.searchView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                Log.d("HomeActivity", "Search Text: " + binding.searchView.getText());
//                binding.globalSearchBar.setText(binding.searchView.getText());
                binding.searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                binding.searchResultsRecyclerView.setAdapter(categoryAdapter);
                return false;

            }
        });

        binding.globalSearchBar.addCollapseAnimationListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                categories.clear();
                categoryAdapter.notifyDataSetChanged();
            }
        });

        loadDataFromFirestore();

    }


    private void getRoomRecommendation() {

        try {
            DateTime dateTime = DateTime.parseFrom(userSignUpData.getDob().getBytes());
            Date date = new Date(userSignUpData.getDob());
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");

            userSignUpData.setDob(userSignUpData.getDob() != null ?
                    formatter.format(userSignUpData.getDob()) : "07/07/99");
        } catch (Exception ex) {
            Log.d("HomeActivity", "Date Exception: " + ex.getMessage());
            userSignUpData.setDob("07/07/99");
        }


        userSignUpData.setUserId(currentUser.getUid());
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

                                    runOnUiThread(() -> {
                                        roomsList.clear();
                                        roomsList.addAll(tempList);
                                        roomAdapter.notifyDataSetChanged();
                                    });
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
                                    runOnUiThread(() -> {
                                        roomsList.clear();
                                        roomsList.addAll(tempList);
                                        roomAdapter.notifyDataSetChanged();
                                    });


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

    private void getEventRecommendation(){

        try {
            db.collection("events")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        List<Events> tempList = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Events events = document.toObject(Events.class);
                            Map<String, Object> userDate = document.getData();
                            Log.d("Firestore", "Event ID: " + document.getId() + " Data: " + userDate);
                            tempList.add(events);
                        }

                        runOnUiThread(() -> {
                            eventsList.clear();
                            eventsList.addAll(tempList);
                            eventAdapter.notifyDataSetChanged();
                        });
                    })
                    .addOnFailureListener(e -> {
                                Log.w("Firestore", "[Events]Error retrieving documents", e);

                            }
                    );
        }catch (Exception ex){
            Log.d("HomeActiving", "Error in Events");
        }

    }

    private void getUserRecommendation() {

        RecommendationRequest recommendationRequest = new RecommendationRequest(userSignUpData,
                "friends");

        try {
            apiManager.getRecommendation(recommendationRequest, new RecommendationListener() {
                @Override
                public void onRecommendationSuccess(List<String> response) {

                    Log.d("HomeActivity", "Users Recommendation Success: " + response.toString());

                    //Get the list of rooms from Firebase Firestore Database using the list of roomIds received from API

                    db.collection("users")
                            .whereIn(FieldPath.documentId(), response)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<UserSignUpData> tempList = new ArrayList<>();
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    UserSignUpData userSignUpData = document.toObject(UserSignUpData.class);
                                    Map<String, Object> userDate = document.getData();
                                    Log.d("Firestore", "User ID: " + document.getId() + " Data: " + userDate);
                                    tempList.add(userSignUpData);
                                }

                                runOnUiThread(() -> {
                                    userSignUpDataList.clear();
                                    userSignUpDataList.addAll(tempList);
                                    connectionAdapter.notifyDataSetChanged();
                                });
                            })
                            .addOnFailureListener(e -> {
                                        Log.w("Firestore", "[Users]Error retrieving documents", e);

                                    }
                            );

                }

                @Override
                public void onRecommendationFailure(String response) {
                    Log.d("HomeActivity", "[Users]Recommendation Failure: " + response);

                    db.collection("users")
                            .limit(10)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                List<UserSignUpData> tempList = new ArrayList<>();
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    UserSignUpData userSignUpData = document.toObject(UserSignUpData.class);
                                    Map<String, Object> userDate = document.getData();
                                    Log.d("Firestore", "User ID: " + document.getId() + " Data: " + userDate);
                                    tempList.add(userSignUpData);
                                }

                                runOnUiThread(() -> {
                                    userSignUpDataList.clear();
                                    userSignUpDataList.addAll(tempList);
                                    connectionAdapter.notifyDataSetChanged();
                                });
                            })
                            .addOnFailureListener(e -> {
                                        Log.w("Firestore", "[Users]Error retrieving documents", e);

                                    }
                            );

                }
            });
        } catch (Exception ex) {
            Log.d("HomeActivity", "[Users]Recommendation Exception: " + ex.getMessage());

        }

    }

    private void showBottomSheetDialog() {
        String sessionId = UUID.randomUUID().toString();
        UserDetails userDetails = new UserDetails(userSignUpData != null && userSignUpData.getName() != null ? userSignUpData.getName(): "Shubham", "20-25");
        BottomSheetChatBotFragment bottomSheetDialogFragment = new BottomSheetChatBotFragment(sessionId, userDetails, userSignUpData);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), "ModelBottomSheet");
    }

    private String getGreetingMessage() {
        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Determine greeting based on the time of day
        if (hourOfDay >= 0 && hourOfDay < 12) {
            return "Good Morning";
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
    }

//    @Override
//    public void onBackPressed() {
//
//
//        Fragment fragment = fragmentManager.findFragmentById(binding.communityFrameLayout.getId());
//
//        if (fragment != null) {
//            Log.d("HomeActivity", "Community Fragment Found");
//            fragmentManager.beginTransaction().remove(fragment).commit();
//            binding.homeScrollView.setVisibility(View.VISIBLE);
//            binding.searchBarLayout.setVisibility(View.VISIBLE);
//            binding.mlUrlLayout.setVisibility(View.VISIBLE);
//            FragmentManager childFragmentManager = fragment.getChildFragmentManager();
//            if (childFragmentManager.getBackStackEntryCount() > 0) {
//                childFragmentManager.popBackStack();
//            }
//            binding.bottomNavigation.setSelectedItemId(R.id.navigation_home);
//            return;
//        }
//
//        super.onBackPressed();
//    }


    private void loadDataFromFirestore() {

        Executors.newSingleThreadExecutor().execute(() -> {
            db.collection("shared_space")
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        Log.d("HomeActivity", "SharedSpace data loaded successfully QueryDocumentSnapshots: " +
                                queryDocumentSnapshots.size());
                        List<SharedSpace> tempList = new ArrayList<>(); // Temporary list to avoid main thread blocking
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            SharedSpace sharedSpaces = document.toObject(SharedSpace.class);
                            Log.d("HomeActivity", "SharedSpace: " + sharedSpaces.toString());
                            tempList.add(sharedSpaces);
                        }
                        // Update RecyclerView on the main thread
                        runOnUiThread(() -> {
                            sharedSpaceList.clear();
                            sharedSpaceList.addAll(tempList);
                            sharedSpaceAdapter.notifyDataSetChanged();
                        });


                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors
                        Log.d("HomeActivity", "Failed to load SharedSpace data: " + e.getMessage());
                        Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show();
                    });

        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(() -> {
            // Show the dialog after the delay
            LoyaltyPointsPopUpFragment loyaltyPointsPopUpFragment = new LoyaltyPointsPopUpFragment();
            loyaltyPointsPopUpFragment.show(getSupportFragmentManager(), "LoyaltyPointsPopUpFragment");
        }, 2000);


    }


}