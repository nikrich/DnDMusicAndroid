package com.richter.jannik.dndmusicandroid;

import android.support.v7.widget.RecyclerView;
import android.widget.*;
import android.view.*;

import com.richter.jannik.dndmusicandroid.models.Categories;

/**
 * Created by Jannik on 3/9/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private Categories[] mDataset;
    private ScrollingActivity context;
    private static View mPrevView;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        // each data item is just a string in this case
        protected View mCardView;
        protected TextView cat_text;
        protected TextView cur_status;
        protected ImageView cat_img;
        protected ScrollingActivity context;

        public ViewHolder(View v, ScrollingActivity context) {
            super(v);

            mCardView = v;
            mCardView.setOnClickListener(this);
            cat_text = (TextView)v.findViewById((R.id.cat_name));
            cur_status = (TextView)v.findViewById((R.id.cur_status));

            mCardView.setOnClickListener(this);

            this.context = context;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == mCardView.getId()) {
                //Toast.makeText(view.getContext(), "Position: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                ViewHolder vh = new ViewHolder(view, this.context);

                if(mPrevView != null) {
                    ViewHolder ovh = new ViewHolder(mPrevView, this.context);
                    ovh.cur_status.setText("");
                }

                vh.cur_status.setText("Playing...");
                context.playStarter(vh, R.raw.song);
                mPrevView = view;
            }

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(Categories[] myDataset, ScrollingActivity context){
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v, this.context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //ImageFragment myFragment = ImageFragment.newInstance("cat_battle_2");
        //((ScrollingActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.videoView, myFragment).commit();

        holder.cat_text.setText(mDataset[position].getEnvironment());
        //holder.cat_img.setImageResource(R.mipmap.cat_battle_2);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}