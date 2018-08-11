package com.jaswanthk.traceit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RouteActivity extends AppCompatActivity {
    FirebaseDatabase db;
    DatabaseReference dr,dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        db= FirebaseDatabase.getInstance();
        Button add=(Button)findViewById(R.id.add);
        final EditText rno=(EditText)findViewById(R.id.rno);
        final EditText rname=(EditText)findViewById(R.id.rname);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr=db.getReference();
                dc=dr.child("Route"+rno.getText().toString());
                dc.child("Latitude").setValue("12.9870879");
                dc.child("Longitude").setValue("79.9728308");
                dc.child("Status").setValue("0");
                rno.setText("");
                rname.setText("");
                rno.requestFocus();
                Toast.makeText(getApplicationContext(),"Route is Updated",Toast.LENGTH_SHORT).show();

            }
        });
        rno.requestFocus();
    }
}
