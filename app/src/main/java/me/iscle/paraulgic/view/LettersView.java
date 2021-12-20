package me.iscle.paraulgic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Collections;
import java.util.List;

public class LettersView extends RelativeLayout {
    private static final String TAG = "LettersView";

    private HexagonView[] hexagons;
    private OnLetterClickListener listener;

    private LinearLayout r1;
    private LinearLayout r2;
    private LinearLayout r3;

    private List<String> letters;

    public LettersView(Context context) {
        super(context);
        init();
    }

    public LettersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LettersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LettersView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setClipChildren(false);

        hexagons = new HexagonView[]{
                new HexagonView(getContext()),
                new HexagonView(getContext()),
                new HexagonView(getContext()),
                new HexagonView(getContext()),
                new HexagonView(getContext()),
                new HexagonView(getContext()),
                new HexagonView(getContext()),
        };

        hexagons[6].setCenter(true);

        r1 = new LinearLayout(getContext());
        r1.setId(View.generateViewId());
        r1.setOrientation(LinearLayout.HORIZONTAL);
        r1.setGravity(Gravity.CENTER);
        r1.addView(hexagons[0]);
        r1.addView(hexagons[1]);
        LayoutParams r1Lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        r1Lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        addView(r1, r1Lp);

        r2 = new LinearLayout(getContext());
        r2.setId(View.generateViewId());
        r2.setOrientation(LinearLayout.HORIZONTAL);
        r2.setGravity(Gravity.CENTER);
        r2.addView(hexagons[2]);
        r2.addView(hexagons[6]);
        r2.addView(hexagons[4]);
        LayoutParams r2Lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        r2Lp.addRule(RelativeLayout.BELOW, r1.getId());
        addView(r2, r2Lp);

        r3 = new LinearLayout(getContext());
        r3.setId(View.generateViewId());
        r3.setOrientation(LinearLayout.HORIZONTAL);
        r3.setGravity(Gravity.CENTER);
        r3.addView(hexagons[5]);
        r3.addView(hexagons[3]);
        LayoutParams r3Lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        r3Lp.addRule(RelativeLayout.BELOW, r2.getId());
        addView(r3, r3Lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart() - getPaddingEnd();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();

        int hexHeight = Math.min(width / 3, height / 3);
        int hexWidth = (int) Math.round(hexHeight / 1.1547005);

        Log.d(TAG, "onMeasure: hexWidth: " + hexWidth + ", hexHeight: " + hexHeight);

        for (HexagonView hexagon : hexagons) {
            hexagon.setLayoutParams(new LinearLayout.LayoutParams(hexHeight, hexHeight));
        }

        int diff = hexHeight - hexWidth;
        r1.setPadding(0, 0, 0, -diff);
        r2.setPadding(0, 0, 0, -diff);
    }

    public void setLetters(List<String> letters) {
        this.letters = letters;
        for (int i = 0; i < hexagons.length; i++) {
            if (i < letters.size()) {
                final String letter = letters.get(i);
                hexagons[i].setText(letter.toUpperCase());
                hexagons[i].setOnClickListener(v -> onLetterClick(letter));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(letters.subList(0, letters.size() - 1));
        setLetters(letters);
    }

    private void onLetterClick(String letter) {
        if (listener != null) listener.onLetterClick(letter);
    }

    public void setOnLetterClickListener(OnLetterClickListener listener) {
        this.listener = listener;
    }

    public interface OnLetterClickListener {
        void onLetterClick(String letter);
    }
}
