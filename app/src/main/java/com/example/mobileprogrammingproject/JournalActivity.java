package com.example.mobileprogrammingproject;

import android.os.Bundle;
import android.widget.TextView;

public class JournalActivity extends  BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        // Get the title and body from the MainActivity
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        TextView journalTitle = findViewById(R.id.journal_title_text);

        // Set the title and body to the TextViews
        journalTitle.setText(title);
    }
}
