package com.zj.mylinearlayoutmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zj.mylinearlayoutmanager.adapter.UniversalAdapter;
import com.zj.mylinearlayoutmanager.adapter.ViewHolder;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<SlideCardBean> mData;
    private UniversalAdapter<SlideCardBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new SlideCardLayoutManager());
        mData = SlideCardBean.initDatas();

        adapter = new UniversalAdapter<SlideCardBean>(this, mData, R.layout.rv_item_layout) {
            @Override
            public void convert(ViewHolder viewHolder, SlideCardBean slideCardBean) {
                viewHolder.setText(R.id.tv, slideCardBean.getName());

                Glide.with(MainActivity.this)
                        .load(slideCardBean.getUrl())
                        .into((ImageView) viewHolder.getView(R.id.iv));
            }
        };
        rv.setAdapter(adapter);
        CardConfig.initConfig(this);

        SlideCallback slideCallback = new SlideCallback(rv, adapter, mData);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(slideCallback);
        itemTouchHelper.attachToRecyclerView(rv);
    }
}