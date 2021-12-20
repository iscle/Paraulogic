package me.iscle.paraulgic.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.iscle.paraulgic.R;
import me.iscle.paraulgic.data.ParaulogicRepository;
import me.iscle.paraulgic.model.DatesJoc;
import me.iscle.paraulgic.model.Solucions;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        requestGameDates();
    }

    private void requestGameDates() {
        ParaulogicRepository.getInstance().getGameDates(new ParaulogicRepository.Callback<DatesJoc>() {
            @Override
            public void onSuccess(DatesJoc body) {
                requestSolutions(body);
            }

            @Override
            public void onError(Throwable t) {
                handleError(t);
            }
        });
    }

    private void requestSolutions(DatesJoc dates) {
        ParaulogicRepository.getInstance().getSolucions(dates.getDataJoc(), new ParaulogicRepository.Callback<Solucions>() {
            @Override
            public void onSuccess(Solucions body) {
                continueWithDatesAndSolutions(dates, body);
            }

            @Override
            public void onError(Throwable t) {
                handleError(t);
            }
        });
    }

    private void continueWithDatesAndSolutions(DatesJoc dates, Solucions solucions) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(MainActivity.EXTRA_DATES_JOC, dates);
        i.putExtra(MainActivity.EXTRA_SOLUCIONS, solucions);
        startActivity(i);
        finishAffinity();
    }

    private void handleError(Throwable t) {
        t.printStackTrace();
    }
}
