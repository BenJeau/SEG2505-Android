package com.seg2505.project.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seg2505.project.R;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        String user = getIntent().getExtras().getString("user");
        TextView txt = (TextView) findViewById(R.id.WelcomText);
        txt.setText("Welcome " + user );

        Button ViewUser = (Button) findViewById(R.id.BtnViewUser);
        Button ViewService = (Button) findViewById(R.id.BtnViewServices);

        ViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });

        ViewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionActivity.this,AdminServiceActivity.class);
                startActivity(intent);
            }
        });
    }
}
