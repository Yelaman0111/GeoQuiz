package com.bignerdranch.android.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG ="QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_CHEATER = "cheater";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private Question[] mQuestionBank = new Question[]{
            new Question( R.string.question_australia, true ),
            new Question( R.string.question_oceans,true ),
            new Question( R.string.question_mideast,false ),
            new Question( R.string.question_africa,false ),
            new Question( R.string.question_americas,true ),
            new Question( R.string.question_asia,true ),
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Log.d(TAG, "onCreate(Bundle) called");

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt( KEY_INDEX,0 );
            mIsCheater = savedInstanceState.getBoolean( KEY_CHEATER, false );
        }


       mQuestionTextView = findViewById( R.id.question_text_view );
        mQuestionTextView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionBank.length;
                updateQuection();
            }
        } );

        mCheatButton = findViewById( R.id.cheat_button );
        mCheatButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent( QuizActivity.this, answerIsTrue );

                startActivityForResult( intent, REQUEST_CODE_CHEAT );

            }
        } );

        mTrueButton = findViewById( R.id.true_button );
        mTrueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer( true );
                Log.i(TAG, String.valueOf( mIsCheater ) );
            }
        } );

        mFalseButton = findViewById( R.id.false_button );
        mFalseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkAnswer( false );
            }
        } );

        mNextButton = findViewById( R.id.next_button );
        mNextButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1)% mQuestionBank.length;
                updateQuection();
                mIsCheater = false;
            }
        } );

        mPrevButton = findViewById( R.id.prev_button );
        mPrevButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1)%mQuestionBank.length;
                updateQuection();
            }
        } );


        updateQuection();
    }


    private void updateQuection(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText( question );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data == null){
                return;
            }
        mIsCheater = CheatActivity.wasAnswerShown( data );
        }

    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInsatnseState) {
        super.onSaveInstanceState( savedInsatnseState);
        Log.i(TAG, "onSaveInstanceState");
        savedInsatnseState.putInt(KEY_INDEX, mCurrentIndex );
        savedInsatnseState.putBoolean(KEY_CHEATER, mIsCheater);
    }


    private void checkAnswer(boolean userPresedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

            if(mIsCheater) {
                messageResId = R.string.judgment_toast;
            } else {
                if (userPresedTrue == answerIsTrue) {
                    messageResId = R.string.correct_toast;
                } else {
                    messageResId = R.string.incorrect_toast;
                }
            }
            Toast.makeText( this, messageResId, Toast.LENGTH_SHORT ).show();

    }











    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d( TAG, "onPause() called" );
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
