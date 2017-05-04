package com.dobe.zer0.geoquiz;

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
    private static final int NEXT_QUESTION = 1;
    private static final int BACK_QUESTION = 0;

    @BindView(R.id.text_view)
    TextView mTextView;

    @BindViews({R.id.true_button, R.id.false_button})
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
    public void nextButtonClick(){
        chooseQuestion(1);

        updateTextView();
    }

    @OnClick(R.id.back_button)
    public void backButtonClick(){
        chooseQuestion(0);

        updateTextView();
    }

    @OnClick(R.id.text_view)
    public void textViewClick(){
        chooseQuestion(1);

        updateTextView();
    }

    private void chooseQuestion(int chooseType){
        int mQuestionCount = mQuestions.length;

        switch (chooseType){
            case NEXT_QUESTION:
                mCurrentIndex++;
                break;

            case BACK_QUESTION:
                mCurrentIndex--;
                break;
            default:
                break;
        }

        if(mCurrentIndex == mQuestionCount){
            mCurrentIndex = mQuestionCount-1;
        }else if(mCurrentIndex < 0){
            mCurrentIndex = 0;
        }
    }

    private void updateTextView(){
        int mQuestionResId = mQuestions[mCurrentIndex].getQuestionResId();
        mTextView.setText(mQuestionResId);
    }

    private void checkAnswer(boolean pressedAnswer){
        boolean answer = mQuestions[mCurrentIndex].isAnswerTrue();

        int toastId = 0;
        if(answer == pressedAnswer){
            toastId = R.string.right_toast;
        }else{
            toastId = R.string.wrong_toast;
        }

        Toast.makeText(this, toastId, Toast.LENGTH_SHORT).show();
    }
}
