package com.example.dailyreader;

import android.content.Context;
import android.util.AttributeSet;


public class ReadPageView extends androidx.appcompat.widget.AppCompatTextView {
    public ReadPageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ReadPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReadPageView(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

//    public int resize() {
//        CharSequence newContent = getText().subSequence(0, getCharNum());
//        setText(newContent);
//        return getText().length() - newContent.length();
//    }


    public int getCharNum() {
        int lastLine = getHeight() - getPaddingTop() - getPaddingBottom() - getLineHeight();
        int totalLines = getLayout().getLineForVertical(lastLine);
        return getLayout().getLineEnd(totalLines);
    }
}

