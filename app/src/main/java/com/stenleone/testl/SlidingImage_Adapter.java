package com.stenleone.testl;

import android.content.Context;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {

    private ArrayList<ImageModel> imageModelArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<ImageModel> img) {
        this.context = context;
        imageModelArrayList = img;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageModelArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        final ImageView imageView =  imageLayout.findViewById(R.id.image);
        final TextView textView =  imageLayout.findViewById(R.id.test_top);
        final TextView textView2 =  imageLayout.findViewById(R.id.test_top2);
        final TextView textView3 =  imageLayout.findViewById(R.id.test_top3);

        Glide.with(view.getContext())
                .load(imageModelArrayList.get(position).getImage_drawable())
                .override(1100, 1000)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        textView.setText(imageModelArrayList.get(position).getText1());
        textView2.setText(imageModelArrayList.get(position).getText2());
        textView3.setText(imageModelArrayList.get(position).getText3());

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}