
package com.jaswanthk.traceit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CurrentBuses extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dr;
    public  String currentRoute=null;
    public ArrayList<String> busdetails;
    public Intent i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_buses);

        db= FirebaseDatabase.getInstance();
        final ListView buslist = (ListView) findViewById(R.id.buslist);
        dr=db.getReference();
        Toast.makeText(getApplicationContext(),"BUS ROUTES",Toast.LENGTH_SHORT).show();
        busdetails = new ArrayList<String>();
        final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, busdetails);
        buslist.setAdapter(Adapter);
        i1=new Intent(this,MapsActivity.class);
        final Bundle myb=new Bundle();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                busdetails.clear();
                if(busdetails.size()==0) {
                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                        String status = dss.child("Status").getValue().toString();
                        if (status.equals("1")) {
                            busdetails.add(dss.getKey());
                        }
                    }

                    Adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(busdetails.size()), Toast.LENGTH_SHORT).show();

                    //if(busdetails.size()>0) {
                    buslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            currentRoute = buslist.getItemAtPosition(i).toString();

                            Toast.makeText(getApplicationContext(), currentRoute + " is selected", Toast.LENGTH_SHORT).show();
                            myb.putString("Cr", currentRoute);
                            i1.putExtras(myb);
                            startActivity(i1);
                            //getLocation();


                        }
                    });
                }
                //}

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

}

