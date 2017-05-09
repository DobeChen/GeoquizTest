package com.dobe.zer0.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.dobe.zer0.activity.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChectActivity extends BaseActivity {
    private static final String TAG = "<<<< ChectActivity ";
    private static final String KEY_ANSWER = "SaveAnswer";
    private static final String KEY_ISCLICEKCHEAT = "IsClickChect";

    private static final String EXTRA_ANSWER_TAG = "com.dobe.zer0.geoquiz.answer_tag";
    private static final String EXTRA_ANSWER_SHOWN = "com.dobe.zer0.geoquiz.answer_shown";

    private String mAnswer;

    private boolean mIsClicked;

    @BindView(R.id.show_answer_button)Button mShowAnswerButton;
    @BindView(R.id.answer_text)TextView mAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chect);
        Log.d(TAG, "onCreate() called.");

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            mAnswer = savedInstanceState.getString(KEY_ANSWER);

            mIsClicked = savedInstanceState.getBoolean(KEY_ISCLICEKCHEAT);

            if(mIsClicked){
                checkAnswer();

                setAnswerShownResult(mIsClicked);
            }
        }else{
            mAnswer = getIntent().getStringExtra(EXTRA_ANSWER_TAG);
        }
    }

    @OnClick(R.id.show_answer_button)
    public void showAnswerButtonClick(){
        checkAnswer();

        mIsClicked = true;

        setAnswerShownResult(mIsClicked);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called.");

        outState.putString(KEY_ANSWER, mAnswer);
        outState.putBoolean(KEY_ISCLICEKCHEAT, mIsClicked);
    }

    private void checkAnswer(){
        if("true".equalsIgnoreCase(mAnswer)){
            mAnswerText.setText(R.string.right_toast);
        }else {
            mAnswerText.setText(R.string.wrong_toast);
        }
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intent);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context context, String answer){
        Intent intent = new Intent(context, ChectActivity.class);
        intent.putExtra(EXTRA_ANSWER_TAG, answer);

        return intent;
    }
}
