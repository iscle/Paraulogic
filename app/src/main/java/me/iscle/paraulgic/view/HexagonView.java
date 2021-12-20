package me.iscle.paraulgic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.iscle.paraulgic.R;

public class HexagonView extends FrameLayout {
    private static final String TAG = "HexagonView";

    private ImageView image;
    private TextView text;

    private GestureDetector gestureDetector;
    private View.OnClickListener listener;

    private Bitmap bitmap;
    
    public HexagonView(@NonNull Context context) {
        super(context);
        init();
    }

    public HexagonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HexagonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public HexagonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bitmap = null;
    }

    private void init() {
        inflate(getContext(), R.layout.view_hexagon, this);
        image = findViewById(R.id.view_hexagon_image);
        text = findViewById(R.id.view_hexagon_text);
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                int x = Math.round(e.getX());
                int y = Math.round(e.getY());

                if (bitmap == null) {
                    bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    draw(canvas);
                }

                int color = bitmap.getPixel(x, y);
                if (color != Color.TRANSPARENT) {
                    if (listener != null) {
                        listener.onClick(HexagonView.this);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
    }

    public void setText(CharSequence text) {
        this.text.setText(text);
    }

    public void setCenter(boolean center) {
        if (center) {
            image.setColorFilter(Color.parseColor("#ec4a49"));
            text.setTextColor(Color.WHITE);
        } else {
            image.setColorFilter(Color.parseColor("#63bcca"));
            text.setTextColor(Color.BLACK);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.listener = l;
    }
}
