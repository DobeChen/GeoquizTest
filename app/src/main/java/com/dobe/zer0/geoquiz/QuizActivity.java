package com.dobe.zer0.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dobe.zer0.activity.BaseActivity;
import com.dobe.zer0.entity.Question;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizActivity extends BaseActivity {
    private static final String TAG = "<<<< QuizActivity ";
    private static final String KEY_INDEX = "SaveIndex";
    private static final String KEY_ISCHEAT = "IsCheat";
    private static final int REQUEST_CODE_CHECT = 0;

    private static final int NEXT_QUESTION = 1;
    private static final int BACK_QUESTION = 0;

    private boolean mIsCheat;

    @BindView(R.id.text_view)
    TextView mTextView;

    @BindViews({R.id.true_button, R.id.false_button, R.id.cheat_button})
    List<Button> mButtons;

    @BindViews({R.id.back_button, R.id.next_button})
    List<ImageButton> mImageButtons;

    private int mCurrentIndex = 0;
    private Question[] mQuestions = new Question[]{new Question(R.string.question_me, true),
            new Question(R.string.question_love, true),
            new Question(R.string.question_you, true)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called.");

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
            mIsCheat = savedInstanceState.getBoolean(KEY_ISCHEAT);
        }

        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);

        updateTextView();
    }

    @OnClick(R.id.true_button)
    public void trueButtonClick() {
        checkAnswer(true);
    }

    @OnClick(R.id.false_button)
    public void falseButtonClick() {
        checkAnswer(false);
    }

    @OnClick(R.id.next_button)
    public void nextButtonClick() {
        mIsCheat = false;

        chooseQuestion(1);

        updateTextView();
    }

    @OnClick(R.id.back_button)
    public void backButtonClick() {
        chooseQuestion(0);

        updateTextView();
    }

    @OnClick(R.id.cheat_button)
    public void chectButtonClick() {
//        showCheatToast();

        //start ChectActivity
        String answer = String.valueOf(mQuestions[mCurrentIndex].isAnswerTrue());
        Intent intent = ChectActivity.newIntent(this, answer);
//        startActivity(intent);
        startActivityForResult(intent, REQUEST_CODE_CHECT);
    }

    @OnClick(R.id.text_view)
    public void textViewClick() {
        chooseQuestion(1);

        updateTextView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() called.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState() called.");

        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_ISCHEAT, mIsCheat);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause() called.");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called.");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop() called.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy() called.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHECT) {
            if (data != null) {
                mIsCheat = ChectActivity.wasAnswerShown(data);
            } else {
                return;
            }
        }
    }

    private void chooseQuestion(int chooseType) {
        int mQuestionCount = mQuestions.length;

        switch (chooseType) {
            case NEXT_QUESTION:
                mCurrentIndex++;
                break;

            case BACK_QUESTION:
                mCurrentIndex--;
                break;
            default:
                break;
        }

        if (mCurrentIndex == mQuestionCount) {
            mCurrentIndex = mQuestionCount - 1;
        } else if (mCurrentIndex < 0) {
            mCurrentIndex = 0;
        }
    }

    private void updateTextView() {
        int mQuestionResId = mQuestions[mCurrentIndex].getQuestionResId();
        mTextView.setText(mQuestionResId);
    }

    private void checkAnswer(boolean pressedAnswer) {
        boolean answer = mQuestions[mCurrentIndex].isAnswerTrue();

        int toastId;

        //check is cheater
        if (mIsCheat) {
            toastId = R.string.judgment_toast;
        } else {
            if (answer == pressedAnswer) {
                toastId = R.string.right_toast;
            } else {
                toastId = R.string.wrong_toast;
            }
        }

        Toast.makeText(this, toastId, Toast.LENGTH_SHORT).show();
    }

    private void showCheatToast() {
        String chect = String.valueOf(mQuestions[mCurrentIndex].isAnswerTrue());

        Toast.makeText(this, "Chect is " + chect, Toast.LENGTH_SHORT).show();
    }
}
