package com.zj.mylinearlayoutmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author 79810
 * 自定义LayuoutManger，实现item叠合在一起
 */
public class SlideCardLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        //viewHodler 回收
        detachAndScrapAttachedViews(recycler);

        int bottomPosition;
        int itemCount = getItemCount();
        if (itemCount < CardConfig.MAX_SHOW_COUNT) {
            bottomPosition = 0;
        } else {
            // 显示的个数
            bottomPosition = itemCount - CardConfig.MAX_SHOW_COUNT;
        }

        for (int i = bottomPosition; i < itemCount; i++) {
            // 复用，拿到复用的view，添加到RecycleView中
            View view = recycler.getViewForPosition(i);
            addView(view);
            // 测量
            measureChildWithMargins(view, 0, 0);

            // 布局需要在中间的位置
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

            // 布局
            layoutDecoratedWithMargins(view,
                    widthSpace / 2,
                    heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));

            // 最后一个view和倒数第二个view重叠在一起
            int level = itemCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * level);
                    view.setScaleX(1 - CardConfig.SCALE_GAP * level);
                    view.setScaleY(1 - CardConfig.SCALE_GAP * level);
                } else {
                    // 最下面的那个view 与前一个view 布局一样
                    view.setTranslationY(CardConfig.TRANS_Y_GAP * (level - 1));
                    view.setScaleX(1 - CardConfig.SCALE_GAP * (level - 1));
                    view.setScaleY(1 - CardConfig.SCALE_GAP * (level - 1));
                }
            }
        }

    }
}
