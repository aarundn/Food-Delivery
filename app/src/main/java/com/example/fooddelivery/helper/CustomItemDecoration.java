package com.example.fooddelivery.helper;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private final int smallSpacing;
    private final int normalSpacing;

    public CustomItemDecoration(int smallSpacing, int normalSpacing) {
        this.smallSpacing = smallSpacing;
        this.normalSpacing = normalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);

        // Add a small space only above the second item
        if (position == 1) {
            outRect.top = smallSpacing;
        }

        // Default spacing for other items
        outRect.left = normalSpacing / 2;
        outRect.right = normalSpacing / 2;
        outRect.bottom = normalSpacing;
    }
}
