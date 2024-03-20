package com.example.mobileprogrammingproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends BaseActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    ImageButton addJournal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase authentication
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        // ImageButton to add a journal
        addJournal = findViewById(R.id.add_journal_button);

        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        // Get the user's email and save it to the database
        reference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("data");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if user node exists
                if (!dataSnapshot.exists()) {
                    // User node doesn't exist, you can proceed to save the email
                    saveEmailToDatabase(reference, user.getEmail());
                } else {
                    // User node already exists, handle accordingly (optional)
                    System.out.println("User node already exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Log.e(TAG, "Database Error: " + databaseError.getMessage());
            }
        });

        // ImageButton to add a journal
        addJournal.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddJournal.class);
            startActivity(intent);
        });
    }

    // Method to save email to the database
    private void saveEmailToDatabase(DatabaseReference reference, String email) {
        reference.child("email").setValue(email);
    }
}