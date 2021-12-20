package me.iscle.paraulgic.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import me.iscle.paraulgic.R;
import me.iscle.paraulgic.RespostesDialogFragment;
import me.iscle.paraulgic.model.Solucions;
import me.iscle.paraulgic.view.LettersView;

public class MainActivity extends AppCompatActivity implements LettersView.OnLetterClickListener {
    private static final String TAG = "MainActivity";

    public static final String EXTRA_DATES_JOC = "extra_dates_joc";
    public static final String EXTRA_SOLUCIONS = "extra_solucions";

    private TextView editText;
    private ImageView backspace;
    private LettersView lettersView;
    private Button shuffle;
    private Button enter;
    private Button respostes;

    private Solucions solucions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        backspace = findViewById(R.id.backspace);
        lettersView = findViewById(R.id.letters_view);
        shuffle = findViewById(R.id.shuffle);
        enter = findViewById(R.id.button);
        respostes = findViewById(R.id.respostes);

        backspace.setOnClickListener(v -> {
            String text = editText.getText().toString();
            if (text.length() > 0) {
                text = text.substring(0, text.length() - 1);
                editText.setText(text);
            }
        });
        lettersView.setOnLetterClickListener(this);
        shuffle.setOnClickListener(v -> lettersView.shuffle());
        enter.setOnClickListener(v -> {
            String text = editText.getText().toString();
            editText.setText(null);
            if (!text.contains(solucions.getLletres().get(6))) {

                return;
            }
            if (solucions.getParaules().containsKey(text)) {

                return;
            }
        });
        respostes.setOnClickListener(onRespostesClickListener);

        Bundle extras = getIntent().getExtras();
        solucions = (Solucions) extras.getSerializable(EXTRA_SOLUCIONS);
        lettersView.setLetters(solucions.getLletres());
    }

    private void showText(String text) {

    }

    private final View.OnClickListener onRespostesClickListener = v -> {
        // Open dialog with custom layout
        new RespostesDialogFragment().show(getSupportFragmentManager(), null);
    };

    @Override
    public void onLetterClick(String lletra) {
        Log.d(TAG, "onLetterClick: " + lletra);
        editText.setText(editText.getText() + lletra);
    }
}