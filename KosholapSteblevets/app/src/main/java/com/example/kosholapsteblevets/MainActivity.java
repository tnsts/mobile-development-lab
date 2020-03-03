package com.example.kosholapsteblevets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<Message> list = new ArrayList<Message>();

        ListView listView = findViewById(R.id.list_view);
        final MyListAdapter adapter = new MyListAdapter(list);
        listView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance("https://kpilab-950d2.firebaseio.com/");
        myRef = database.getReference("message");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                adapter.list = fetchData(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(postListener);

        writeNewMessage("User", "Text");

    }


    private ArrayList<Message> fetchData(DataSnapshot dataSnapshot)
    {
        ArrayList<Message> tmp = new ArrayList<Message>();
        for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
        {
            Message day = postSnapShot.getValue(Message.class);
            tmp.add(day);

        }
        return tmp;
    }

    private void writeNewMessage(String name, String text) {
        Message message = new Message(name, text);

        myRef.child("messages").setValue(message);
    }

    private class MyListAdapter extends BaseAdapter {

        ArrayList<Message> list;

        public MyListAdapter(ArrayList<Message> list){
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
            }
            Message message = getItem(position);
            ((TextView) convertView.findViewById(R.id.item_text)).setText(message.Text);
            return convertView;
        }


        @Override
        public Message getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}