package com.group.mysa.Controller;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.group.mysa.Model.FeedInfo;
import com.group.mysa.Model.FutureEvents;
import com.group.mysa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendingProfileFragment extends Fragment {

    private ListView listView;
    FirebaseListAdapter<FeedInfo> adapter;
    public AttendingProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_attending_profile, container, false);

        listView = (ListView)mView.findViewById(R.id.future_events_list_view);

        Query query = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("attend");

        FirebaseListOptions<FeedInfo> options = new FirebaseListOptions.Builder<FeedInfo>()
                .setLayout(android.R.layout.simple_list_item_1)
                .setLayout(android.R.layout.simple_list_item_2)
                .setQuery(query,FeedInfo.class)
                .build();

        adapter = new FirebaseListAdapter<FeedInfo>(options) {
            @Override
            protected void populateView(View v, FeedInfo model, int position) {
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(model.getTitle());



            }



        };

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FeedInfo info = (FeedInfo) adapterView.getItemAtPosition(i);
                System.out.println(info.getTitle());

                Intent startIntent = new Intent(getActivity(),ItemInfo.class);
                startIntent.putExtra("FeedInfo", info);
                startActivity(startIntent);

            }
        });

        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
