package com.jaswanthk.traceit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);

        Button addroute=(Button)findViewById(R.id.addroute);
        addroute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(adminpage.this,RouteActivity.class);
                startActivity(i);
            }
        });

        Button map=(Button)findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(adminpage.this,Adminmap.class);
                startActivity(i);
            }
        });
        //       Button driver=(Button)findViewById(R.id.adddriver);
//        driver.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(adminpage.this,AddDriver.class);
//                startActivity(i);
//            }
//        });

    }
}
