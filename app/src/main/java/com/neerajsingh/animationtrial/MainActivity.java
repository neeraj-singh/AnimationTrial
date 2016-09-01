package com.neerajsingh.animationtrial;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CustomRatingBar ratingBarTop,ratingBarBottom;
    private TextView title,subtext,thankText,writereview;
    private Animation animFadeIn;
    private Animation animTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.firstView);
        final LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.secondView);
        ratingBarBottom = (CustomRatingBar) linearLayout.findViewById(R.id.ratingBarBottom);
        ratingBarTop = (CustomRatingBar) linearLayout2.findViewById(R.id.ratingBarTop);
        title = (TextView) linearLayout.findViewById(R.id.rating_title_message);
        subtext = (TextView) linearLayout.findViewById(R.id.rating_subtitle_write_review);
        thankText = (TextView) linearLayout2.findViewById(R.id.thanks_text);
        writereview = (TextView) linearLayout2.findViewById(R.id.write_review);
        animFadeIn = AnimationUtils.loadAnimation(this, R.anim.rating_fade_in);
        animTrans = AnimationUtils.loadAnimation(this, R.anim.rating_trans);
        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        ratingBarBottom.setOnRatingChange(new CustomRatingBar.RatingChangeListner() {
            @Override
            public void onRatingChange(int rating) {
                ratingBarTop.setRating(rating);
                ratingBarBottom.startAnimation(animTrans);
                linearLayout.startAnimation(animFadeIn);
                linearLayout2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayout2.setVisibility(View.VISIBLE);
                    }
                },390);
            }
        });

        ratingBarTop.setOnRatingChange(new CustomRatingBar.RatingChangeListner() {
            @Override
            public void onRatingChange(int rating) {
                linearLayout.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.GONE);
            }
        });
    }

    public int getColorForStar(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(id, null);
        }else {
            return getResources().getColor(id);
        }
    }
}
