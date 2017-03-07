package com.cucumber007.prototypes.reusables;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class UppercaseTextView extends TextView {

    public UppercaseTextView(Context context) {
        super(context);
        init(context);
    }

    public UppercaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UppercaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setText(getText());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(((String) text).toUpperCase(), type);
    }
}
