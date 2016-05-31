package com.richter.jannik.dndmusicandroid;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.richter.jannik.dndmusicandroid.fragments.PlayerYouTubeFrag;
import com.richter.jannik.dndmusicandroid.models.Categories;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class ScrollingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;
    private ScrollingActivity mContext;
    //Current active ViewHolder in Adapter
    private CardAdapter.ViewHolder mPrevViewHolder;

    //Constants
    private final static int PLAYING = 1;
    private final static int PAUSED = 0;

    MediaPlayer mediaPlayer;

    //State
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        mContext = this;

        mediaPlayer = MediaPlayer.create(mContext, R.raw.song);

        //FAB
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){

                    if (view.getId() == fab.getId()) {
                        if(mPrevViewHolder != null) {
                            switch(state) {
                                case PLAYING:
                                    fab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_play_arrow_white));
                                    mPrevViewHolder.cur_status.setText("Paused...");
                                    state = PAUSED;
                                    pauseMedia();
                                    break;
                                case PAUSED:
                                    fab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_pause_white));
                                    mPrevViewHolder.cur_status.setText("Playing...");
                                    state = PLAYING;
                                    startMedia();
                                    break;
                            }
                        }
                    }

                    return true;
                }
                return true; // consume the event
            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new HttpRequestTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void playStarter(CardAdapter.ViewHolder vh, int song){
        mPrevViewHolder = vh;
        fab.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_pause_white));
        state = PLAYING;

        setSong(song);

        fab.setVisibility(View.VISIBLE);
    }

    //Media Player handler
    public void startMedia(){
        mediaPlayer.start();
    }

    public void pauseMedia(){
        mediaPlayer.pause();
    }

    public void stopMedia(){
        mediaPlayer.stop();
    }

    public void setSong(int song){
        //TODO, implement song

        if(mediaPlayer.isPlaying()) {
            stopMedia();
            mediaPlayer = MediaPlayer.create(mContext, song);
        }

        startMedia();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Categories[]> {

        private ScrollingActivity mContext;

        public HttpRequestTask(ScrollingActivity context) {
            mContext = context;
        }

        @Override
        protected Categories[] doInBackground(Void... params) {
            try {
                final String url = getString(R.string.rest_url) + "categories";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Categories[] categories = restTemplate.getForObject(url, Categories[].class);
                return categories;
            } catch (Exception e) {
                Toast.makeText(mContext, "An error has ocurred while fetching your data", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Categories[] categories) {

            mAdapter = new CardAdapter(categories,mContext);
            mRecyclerView.setAdapter(mAdapter);

        }

    }
}
