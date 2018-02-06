package com.example.david.taxipot.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.david.taxipot.R;
import com.example.david.taxipot.model.GroupModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2018-02-05.
 */

public class FindGroupFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_findgroup,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.findgroupfrgment_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new FindGroupFragmentRecyclerviewAdapter());


        return view;
    }

    class FindGroupFragmentRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<GroupModel> groupModel;

        public FindGroupFragmentRecyclerviewAdapter() {

            groupModel = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("TaxiGroups").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    groupModel.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        groupModel.add(snapshot.getValue(GroupModel.class));
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find,parent,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return groupModel.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {

            public TextView Title;
            public TextView Destination;
            public TextView StartingPoint;
            public TextView BoardingTime;
            public TextView Pax;
            public TextView ExpirationDate;

            public CustomViewHolder(View view) {
                super(view);

                Title = view.findViewById(R.id.group_title);
                Destination = view.findViewById(R.id.destination);
                StartingPoint = view.findViewById(R.id.starting_point);
                BoardingTime = view.findViewById(R.id.boarding_time);
                Pax = view.findViewById(R.id.pax);
                ExpirationDate = view.findViewById(R.id.expiration_date);
            }
        }
    }

}
