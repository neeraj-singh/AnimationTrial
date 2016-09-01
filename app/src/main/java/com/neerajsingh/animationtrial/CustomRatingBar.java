package com.neerajsingh.animationtrial;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by neeraj.singh on 23/08/16.
 */
public class CustomRatingBar extends LinearLayout {

    private Drawable setDrawable;
    private Drawable resetDrawable;
    private ImageView star[];
    private int noOfStars;
    private RatingChangeListner ratingChangeListner;
    private int rating;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v instanceof ImageView){
                int position = (int) v.getTag(key);
                if(position != rating) {
                    setRating(position);
                }
            }
        }
    };
    private int key = Integer.MAX_VALUE;

    public CustomRatingBar(Context context) {
        super(context);
        createView(context,null);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        createView(context,attrs);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        createView(context,attrs);
    }

    private void createView(Context context,AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomRatingBar,
                0, 0);

        try {
            setDrawable = a.getDrawable(R.styleable.CustomRatingBar_setStarDrawable);
            resetDrawable = a.getDrawable(R.styleable.CustomRatingBar_resetStarDrawable);
            noOfStars = a.getInt(R.styleable.CustomRatingBar_numberOfStar,5);
        } finally {
            a.recycle();
        }
        setOrientation(HORIZONTAL);
        star = new ImageView[noOfStars];
        for(int i=0; i<noOfStars ; i++){
            star[i] = new ImageView(context);
            setStar(star[i],i);
        }

    }

    private void setStar(ImageView imageView,int position) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        params.setMargins(0,0,dpToPx(6),0);
        imageView.setLayoutParams(params);

        imageView.setImageDrawable(resetDrawable);
        imageView.setOnClickListener(onClickListener);
        imageView.setTag(key,position);
        addView(imageView,position);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        createView(context,attrs);
    }

    public void setSelectedStar(ImageView imageView, Drawable drawable){
        imageView.setImageDrawable(drawable);
    }


    public void setRating(int rating){
        if(rating >= noOfStars){
            setAllStar();
        } else {
            for(int i = 0;i<noOfStars ;i++){
                setSelectedStar(star[i], i <= rating ?  setDrawable : resetDrawable);
            }
        }
        if(ratingChangeListner != null){
            ratingChangeListner.onRatingChange(rating);
        }
        this.rating = rating;
    }

    private void setAllStar() {
        for(int i=0;i<noOfStars;i++) {
            star[i].setImageDrawable(setDrawable);
        }
    }

    public void setOnRatingChange(RatingChangeListner listner){
        this.ratingChangeListner = listner;
    }

    public interface RatingChangeListner {
        void onRatingChange(int rating);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int getRating() {
        return rating;
    }
}
