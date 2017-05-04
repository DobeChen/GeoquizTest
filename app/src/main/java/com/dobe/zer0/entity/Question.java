package com.dobe.zer0.entity;

/**
 * Created by dobezer0 on 2017/5/3.
 */

public class Question {
    private int mQuestionResId;
    private boolean mAnswerTrue;

    public Question(int resId, boolean answerTrue){
        this.mQuestionResId = resId;
        this.mAnswerTrue = answerTrue;
    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
