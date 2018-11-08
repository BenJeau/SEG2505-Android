package com.seg2505.project;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        Button ViewUser = (Button) findViewById(R.id.BtnViewUser);
        Button ViewService = (Button) findViewById(R.id.BtnViewServices);

        ViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });


//        ViewService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(OptionActivity.this,OptionActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
