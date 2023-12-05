package com.minhasdisciplinas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.minhasdisciplinas.R;

public class HomeActivity extends AppCompatActivity {

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        View decorView = getWindow().getDecorView();
        int flags = decorView.getSystemUiVisibility(); // retrieve current flags
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flags
        decorView.setSystemUiVisibility(flags);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildStyles();
        setContentView(R.layout.home_layout);

        Button btnAccess = findViewById(R.id.btnAccess);
        btnAccess.setOnClickListener(event -> {
            Intent openDisciplinesActivity = new Intent(this, DisciplinesListActivity.class);
            startActivity(openDisciplinesActivity);
        });
    }

}
