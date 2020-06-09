package com.stenleone.testl.ui.tab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.stenleone.testl.CardScript;
import com.stenleone.testl.ImageModel;
import com.stenleone.testl.JsonPlaceHolderApi;
import com.stenleone.testl.Post;
import com.stenleone.testl.R;
import com.stenleone.testl.RecyclerAdapter;
import com.stenleone.testl.SlidingImage_Adapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private Intent intent;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

        imageModelArrayList = new ArrayList<>();

    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        onCreate(savedInstanceState);

        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recycler);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://188.40.167.45:3001")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        final int finalIndex = index;
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    return;
                }

                List<Post> posts = response.body();

                RecyclerView.Adapter mAdapter = null;
                RecyclerView.LayoutManager mLayoutManager;

                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getContext());


                final ArrayList<CardScript> List1 = new ArrayList<>();
                final ArrayList<CardScript> List2 = new ArrayList<>();
                final ArrayList<CardScript> List3 = new ArrayList<>();

                final ArrayList<CardScript> TopList1 = new ArrayList<>();
                final ArrayList<CardScript> TopList2 = new ArrayList<>();
                final ArrayList<CardScript> TopList3 = new ArrayList<>();

                SlidingImage_Adapter AdapterSlide = null;


                for (Post post : posts) {

                    if(post.getTop().equals("0")){
                        if(post.getType().equals("strories") ) {
                            List1.add(new CardScript((post.getImage()), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                        if(post.getType().equals("video") ){
                            List2.add(new CardScript((post.getImage()), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                        if(post.getType().equals("favourites") ) {
                            List3.add(new CardScript((post.getImage()), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                    }
                    else{
                        if(post.getType().equals("strories") ) {
                            TopList1.add(new CardScript(post.getImage(), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                        if(post.getType().equals("video") ){
                            TopList2.add(new CardScript(post.getImage(), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                        if(post.getType().equals("favourites") ) {
                             TopList3.add(new CardScript(post.getImage(), post.getTitle(), post.getClick_url(), "- " + post.getTime()));
                        }
                    }
                }

                if(finalIndex == 1) {
                    mAdapter = new RecyclerAdapter(List1);
                    imageModelArrayList = populateList(TopList1);
                    AdapterSlide = new SlidingImage_Adapter(root.getContext(),imageModelArrayList);
                }
                if(finalIndex == 2) {
                    mAdapter = new RecyclerAdapter(List2);
                    imageModelArrayList = populateList(TopList2);
                    AdapterSlide = new SlidingImage_Adapter(root.getContext(),imageModelArrayList);
                }
                if(finalIndex == 3) {
                    mAdapter = new RecyclerAdapter(List3);
                    imageModelArrayList = populateList(TopList3);
                    AdapterSlide = new SlidingImage_Adapter(root.getContext(),imageModelArrayList);
                }

                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);

                ((RecyclerAdapter) mAdapter).setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        if(finalIndex == 1){
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(List1.get(position).getText2()));
                        }
                        else if(finalIndex == 2){
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(List2.get(position).getText2()));
                        }
                        else {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(List3.get(position).getText2()));
                        }
                        startActivity(intent);
                    }
                });

                if(imageModelArrayList.size() == 0) {
                    recyclerView.setY(0);
                }
                mPager = root.findViewById(R.id.pager);
                mPager.setAdapter(AdapterSlide);

                CirclePageIndicator indicator = root.findViewById(R.id.indicator);

                indicator.setViewPager(mPager);

                final float density = getResources().getDisplayMetrics().density;

                indicator.setRadius(5 * density);

                NUM_PAGES = imageModelArrayList.size();

                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 10000, 5000);

                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;
                    }

                    @Override
                    public void onPageScrolled(int pos, float arg1, int arg2) { }

                    @Override
                    public void onPageScrollStateChanged(int pos) { }
                });

            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e("retofit",t.toString());
            }

        });

        return root;
    }

    private ArrayList<ImageModel> populateList(ArrayList<CardScript> localList){

        ArrayList<ImageModel> list = new ArrayList<>();

        for (int i=0;i<localList.size();i++) {

            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(localList.get(i).getImageResource());
            imageModel.setText(localList.get(i).getText1(), localList.get(i).getText2(), localList.get(i).getText3());
            list.add(imageModel);
        }

        return list;
    }
}