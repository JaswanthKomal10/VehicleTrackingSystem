package com.jaswanthk.traceit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jaswanthk.traceit.R.id.buslist;

public class BusActivity extends AppCompatActivity implements LocationListener {
    LocationManager locationManager;
    FirebaseDatabase db;
    DatabaseReference dr,dc;
    public  String currentRoute=null;
    Button stop;
//    TextView txt;
    public ArrayList<String> busdetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        db= FirebaseDatabase.getInstance();
        final ListView buslist = (ListView)findViewById(R.id.buslist);
       /* busdetails.add("ROUTE 1");
        busdetails.add("ROUTE 2");
        busdetails.add("ROUTE 3");
        busdetails.add("ROUTE 4");
        busdetails.add("ROUTE 5");
        busdetails.add("ROUTE 6");
        busdetails.add("ROUTE 7");
        busdetails.add("ROUTE 8");
        busdetails.add("ROUTE 9");
        busdetails.add("ROUTE 10");
        busdetails.add("ROUTE 11");
        busdetails.add("ROUTE 12");
        busdetails.add("ROUTE 13");
        busdetails.add("ROUTE 14");
        busdetails.add("ROUTE 15");
        busdetails.add("ROUTE 16");
        busdetails.add("ROUTE 17");
        busdetails.add("ROUTE 18");
        busdetails.add("ROUTE 19");
        busdetails.add("ROUTE 20");*/


        dr=db.getReference();
        //dc=dr.child("Route"+rno.getText().toString());
        stop=(Button)findViewById(R.id.stop);
//        txt=(TextView)findViewById(R.id.txtv);

        busdetails = new ArrayList<String>();
        //busdetails.add("ROUTE 19");
        final ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, busdetails);
        buslist.setAdapter(Adapter);
        Toast.makeText(getApplicationContext(),"BUS ROUTES",Toast.LENGTH_SHORT).show();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                /*List<Object> ls = (List<Object>) td.values();
                Toast.makeText(getApplicationContext(),String.valueOf(ls.size()),Toast.LENGTH_SHORT).show();
                for(int i=0;i<ls.size();i++)
                {
                    busdetails.add(ls.get(i).toString());

                }*/
                //Toast.makeText(getApplicationContext(), String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                busdetails.clear();
                if(busdetails.size()==0) {

                    for (DataSnapshot dss : dataSnapshot.getChildren()) {
                        //Toast.makeText(getApplicationContext(),dss.getKey(),Toast.LENGTH_SHORT).show();
                        String status=dss.child("Status").getValue().toString();
                        if(status.equals("0")) {
                            busdetails.add(dss.getKey());
                        }
                    }
                    // busdetails.add("END");
                    Adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), String.valueOf(busdetails.size()), Toast.LENGTH_SHORT).show();


                    buslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            currentRoute = buslist.getItemAtPosition(i).toString();
                            Toast.makeText(getApplicationContext(), currentRoute + " is selected", Toast.LENGTH_SHORT).show();
                            getLocation();

                            stop.setVisibility(View.VISIBLE);
//                            txt.setVisibility(View.VISIBLE);
                            buslist.setVisibility(View.GONE);
                            dr.child(currentRoute).child("Status").setValue(1);
                        }
                    });
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.removeUpdates(BusActivity.this);
                dr.child(currentRoute).child("Status").setValue(0);
                Intent i=new Intent(BusActivity.this,MainActivity.class);
                startActivity(i);
            }
        });




    }
    @Override
    public void onBackPressed() {
//
//        locationManager.removeUpdates(this);
//        dr.child("Status").setValue(0);
        Button stop=(Button)findViewById(R.id.stop);
        if(stop.getVisibility()==View.INVISIBLE)
            super.onBackPressed();
//
    }

    public void storetodb(String RouteNo, String Latitude, String Longitude)
    {
        dr=db.getReference().child(RouteNo);
        //String value = dataSnapshot.child("Latitude").getValue().toString();
        // Read from the database
        /*
        dr.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Toast.makeText(getApplicationContext(),"99999",Toast.LENGTH_SHORT).show();
                String value = dataSnapshot.child("Latitude").getValue().toString();
                Toast.makeText(getApplicationContext(),value,Toast.LENGTH_SHORT).show();
               // Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(),"CANCELLED",Toast.LENGTH_SHORT);
            }
        });*/
        dr.child("Latitude").setValue(Latitude);
        dr.child("Longitude").setValue(Longitude);
        dr.child("Status").setValue(1);
        //Toast.makeText(getApplicationContext(),RouteNo+":"+Latitude+":"+Longitude,Toast.LENGTH_SHORT).show();
    }

    public void getLocation() {
        Toast.makeText(getApplicationContext(),"Reading Location",Toast.LENGTH_SHORT).show();
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0, 0, this);

            //Toast.makeText(getApplicationContext(),"Location Read",Toast.LENGTH_SHORT).show();
        }
        catch (SecurityException e){
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String a=location.getLatitude()+","+location.getLongitude();
        //Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
//        txt.setText(a);
        storetodb(currentRoute,String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
    }


    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }
}
