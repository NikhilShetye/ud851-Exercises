/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity.example.quizexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContentResolverCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Gets the data from the ContentProvider and shows a series of flash cards.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // The current state of the app
    private int mCurrentState;


    private Button mButton;
    private TextView mDefinitionTextView;
    private TextView mWordTextView;

    // This state is when the word definition is hidden and clicking the button will therefore
    // show the definition
    private final int STATE_HIDDEN = 0;

    // This state is when the word definition is shown and clicking the button will therefore
    // advance the app to the next word
    private final int STATE_SHOWN = 1;


    private int colWord,colDef;
    private Cursor mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the views
        mButton = (Button) findViewById(R.id.button_next);
        mDefinitionTextView = (TextView) findViewById(R.id.text_view_definition);
        mWordTextView = (TextView) findViewById(R.id.text_view_word);

        new MyTask().execute();
    }

    /**
     * This is called from the layout when the button is clicked and switches between the
     * two app states.
     * @param view The view that was clicked
     */
    public void onButtonClick(View view) {

        // Either show the definition of the current word, or if the definition is currently
        // showing, move to the next word.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mData.close();

    }

    class MyTask extends AsyncTask<Void,Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            ContentResolver resolver=getContentResolver();
            return resolver.query(DroidTermsExampleContract.CONTENT_URI,
                    null, null, null, null);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mData=cursor;
            colWord = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);
            colDef = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
            while (mData.moveToNext()) {
               String word = mData.getString(colWord);
               String def = mData.getString(colDef);
               Log.i(TAG, "Term : " + word + " " + def);
           }
            nextWord();

        }

    }

    public void nextWord() {
        if (mData != null) {
            // Move to the next position in the cursor, if there isn't one, move to the first
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }
            // Hide the definition TextView
            mDefinitionTextView.setVisibility(View.INVISIBLE);

            // Change button text
            mButton.setText(getString(R.string.show_definition));
            // Get the next word
            mWordTextView.setText(mData.getString(colWord));
            mDefinitionTextView.setText(mData.getString(colDef));

            mCurrentState = STATE_HIDDEN;
        }
    }
    public void showDefinition() {

        // COMPLETED (4) Show the definition
        if (mData != null) {
            // Show the definition TextView
            mDefinitionTextView.setVisibility(View.VISIBLE);

            // Change button text
            mButton.setText(getString(R.string.next_word));

            mCurrentState = STATE_SHOWN;
        }

    }

}
