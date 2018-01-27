package com.example.yuriy.multithreadsorter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.yuriy.multithreadsorter.incommingData.TaskGeneratorImpl;
import com.example.yuriy.multithreadsorter.model.Mechanizm;
import com.example.yuriy.multithreadsorter.receiver.LocalBroadcastReceiver;
import com.example.yuriy.multithreadsorter.service.MultiSortingIntentService;
import com.example.yuriy.multithreadsorter.service.manager.AdvancedTimeLinkedList;

import java.util.List;

public class StartActivity extends AppCompatActivity {
    public static final String ACTIVE_TAB_EXTRA = "active_tab";
    private static final int LEFT_TAB_DATA = 0;
    private static final int MIDDLE_TAB_DATA = 1;
    private static final int RIGHT_TAB_DATA = 2;
    private BottomNavigationView mNavigation;
    private int mActiveTab = LEFT_TAB_DATA;
    private RecyclerView mRecyclerView;
    private DataRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView noDataTv;
    List<AdvancedTimeLinkedList<Mechanizm>> sortedTmpData = null;

    private LocalBroadcastReceiver sortedDataReceiver =
            new LocalBroadcastReceiver(Constants.BROADCAST_SORT_ACTION) {
                @Override
                public void onReceive(Context context, Intent intent) {
                    List<AdvancedTimeLinkedList<Mechanizm>> sortedMultiTaskData = (List<AdvancedTimeLinkedList<Mechanizm>>) intent.getSerializableExtra(Constants.RESULT_SORTED_DATA);

                    getViewModel().saveSortedDataToVM(sortedMultiTaskData);

                    sortedTmpData = sortedMultiTaskData;

                    refreshAdapter(mActiveTab);

                }
            };


    private void refreshAdapter(int activeTab) {
        if (sortedTmpData == null) {
            //first launch or null data has been received
            noDataTv.setVisibility(View.VISIBLE);
            return;
        }
        AdvancedTimeLinkedList<Mechanizm> dataToDisplay = sortedTmpData.get(activeTab);

        if (dataToDisplay == null || dataToDisplay.size() == 0) {
            //single task was null or has zero size
            mRecyclerView.setAdapter(null);
            noDataTv.setVisibility(View.VISIBLE);
            return;
        }
        noDataTv.setVisibility(View.INVISIBLE);
        if (mAdapter == null) {
            // there was no data before
            mAdapter = new DataRecyclerViewAdapter(dataToDisplay);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            //there was not null adapter and not null or not zero data before
            mAdapter.updateData(dataToDisplay);
        }
    }

    //todo implement scrolling to the top on second click on active tab
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_left:
                    mActiveTab = LEFT_TAB_DATA;
                    refreshAdapter(LEFT_TAB_DATA);
                    return true;
                case R.id.navigation_middle:
                    mActiveTab = MIDDLE_TAB_DATA;
                    refreshAdapter(MIDDLE_TAB_DATA);
                    return true;
                case R.id.navigation_right:
                    mActiveTab = RIGHT_TAB_DATA;
                    refreshAdapter(RIGHT_TAB_DATA);
                    return true;
            }
            return false;
        }
    };


    public SortedDataViewModel getViewModel() {
        return ViewModelProviders.of(this).get(SortedDataViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noDataTv = findViewById(R.id.tv_empty_data);
        noDataTv.setVisibility(View.VISIBLE);
        getViewModel().getLoadedSortedData().observe(this, new Observer<List<AdvancedTimeLinkedList<Mechanizm>>>() {
            @Override
            public void onChanged(@Nullable List<AdvancedTimeLinkedList<Mechanizm>> lists) {
                sortedTmpData = lists;
                refreshAdapter(mActiveTab);

            }
        });

        sortedDataReceiver.register(this);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null && savedInstanceState.containsKey(ACTIVE_TAB_EXTRA)) {
            mActiveTab = savedInstanceState.getInt(ACTIVE_TAB_EXTRA);
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_data_container);

        final FloatingActionButton fab = findViewById(R.id.fab);
        mRecyclerView.setHasFixedSize(true);
        //todo handle correct behaviour on screen rotation change
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.isShown()) {
                    fab.hide();
                } else {
                    fab.show();
                }

            }

//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
////
////                if (newState == RecyclerView.SCROLL_STATE_IDLE){
////                    fab.show();
////                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
        });

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mNavigation.getSelectedItemId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }
   //todo implement possibility change sorting type
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fabClick(View view) {
        TaskGeneratorImpl generator = TaskGeneratorImpl.get();
        Mechanizm[][] multiTaskData = generator.getMultiTask();
        MultiSortingIntentService.startMultiTask(this, multiTaskData);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sortedDataReceiver.unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVE_TAB_EXTRA, mActiveTab);
    }
}
