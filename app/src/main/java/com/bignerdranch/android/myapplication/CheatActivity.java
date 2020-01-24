package com.bignerdranch.android.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN ="com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_CHEATER = "cheater";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private boolean mIsCheater = false;

    public static Intent newIntent(Context packageContext, boolean answerIstrue){
        Intent intent = new Intent( packageContext, CheatActivity.class );
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIstrue );
        return intent;
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra( EXTRA_ANSWER_SHOWN,false );
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cheat );

        mAnswerIsTrue = getIntent().getBooleanExtra( EXTRA_ANSWER_IS_TRUE, false );

        if(savedInstanceState != null){
            mIsCheater = savedInstanceState.getBoolean( KEY_CHEATER, false );
        }
        if(mIsCheater){
            setAnswerShownResult( true );
        }
        mAnswerTextView = findViewById( R.id.answer_text_view );
        mShowAnswerButton = findViewById( R.id.show_answer_button );
        mShowAnswerButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue) {
                    mAnswerTextView.setText( R.string.true_button );
                }
                    else {
                    mAnswerTextView.setText( R.string.false_button );
                }
                         setAnswerShownResult( true );
                            mIsCheater = true;
            }
        } );

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra( EXTRA_ANSWER_SHOWN, isAnswerShown );
        setResult( RESULT_OK, data );
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInsatnseState) {
        super.onSaveInstanceState( savedInsatnseState);
        savedInsatnseState.putBoolean(KEY_CHEATER, mIsCheater);
    }
}
