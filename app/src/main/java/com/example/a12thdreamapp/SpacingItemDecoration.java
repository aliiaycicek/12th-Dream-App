package com.example.a12thdreamapp;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;

    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = spacing;
        
        // İlk öğe için üst boşluk ekle
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spacing;
        }
    }
} 