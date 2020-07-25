package com.zj.mylinearlayoutmanager;


import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.zj.mylinearlayoutmanager.adapter.UniversalAdapter;

import java.util.List;

/**
 * @author 79810
 */
public class SlideCallback extends ItemTouchHelper.SimpleCallback {

    private final RecyclerView recyclerView;
    private final UniversalAdapter<SlideCardBean> adapter;
    private final List<SlideCardBean> data;

    public SlideCallback(RecyclerView recyclerView, UniversalAdapter<SlideCardBean> adapter, List<SlideCardBean> data) {
        super(0, 15);
        this.recyclerView = recyclerView;
        this.adapter = adapter;
        this.data = data;
    }

    /**
     * 拖拽
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * 滑动
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        SlideCardBean remove = data.remove(viewHolder.getLayoutPosition());
        data.add(0, remove);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        float maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX + dY * dY);
        double fraction = distance / maxDistance;

        if (fraction > 1) {
            fraction = 1;
        }

        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            int level = itemCount - 1;
            if (level > 0 && level < CardConfig.MAX_SHOW_COUNT - 1) {
                childAt.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                childAt.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                childAt.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
            }
        }
    }

}
