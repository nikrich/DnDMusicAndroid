package com.richter.jannik.dndmusicandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.*;
import android.view.*;

import com.richter.jannik.dndmusicandroid.models.Categories;

import java.io.InputStream;

/**
 * Created by Jannik on 3/9/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Categories[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected View mCardView;
        protected TextView cat_text;
        protected ImageView cat_img;
        public ViewHolder(View v) {
            super(v);
            mCardView = v;
            cat_text = (TextView)v.findViewById((R.id.cat_name));
            cat_img = (ImageView)v.findViewById((R.id.cat_img));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(Categories[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.cat_text.setText(mDataset[position].getEnvironment());
        holder.cat_img.setImageResource(R.mipmap.cat_battle_2);

        //imageLoader.displayImage(mDataset[position].getImg(), holder.cat_img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }


}