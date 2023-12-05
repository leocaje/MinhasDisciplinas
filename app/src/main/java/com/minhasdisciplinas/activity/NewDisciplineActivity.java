package com.minhasdisciplinas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.minhasdisciplinas.R;
import com.minhasdisciplinas.model.DisciplineDTO;
import com.minhasdisciplinas.util.GlobalConstants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;

public class NewDisciplineActivity extends AppCompatActivity {
    String url = GlobalConstants.BASE_URL + "/disciplinas.json";
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String APP_DISCIPLINES = "App Minhas Disciplinas";
    private Gson gson = new Gson();

    List<DisciplineDTO> disciplines = new ArrayList<>();
    private EditText txtCourse;
    private EditText txtDisciplineName;
    private EditText txtProfessor;
    private EditText txtPeriod;
    private EditText txtWorkloadHours;
    private Spinner spinnerType;
    private Spinner spinnerStatus;
    private EditText txtDescription;
    private EditText txtConclusionDate;
    private EditText txtGrade;
    private Button btnSaveDiscipline;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildStyles();
        setContentView(R.layout.new_discipline_layout);

        initializeUIComponents();

        btnSaveDiscipline.setOnClickListener(event -> {
            Intent openDisciplinesActivity = new Intent(this, DisciplinesListActivity.class);
            startActivity(openDisciplinesActivity);
        });
    }

    private void initializeUIComponents() {
        txtCourse.findViewById(R.id.txtCourse);
        txtDisciplineName.findViewById(R.id.txtDisciplineName);
        txtProfessor.findViewById(R.id.txtProfessor);
        txtPeriod.findViewById(R.id.txtPeriod);
        spinnerType.findViewById(R.id.spinnerDisciplineType);
        spinnerStatus.findViewById(R.id.spinnerDisciplineStatus);
        txtDescription.findViewById(R.id.txtDescription);
        txtConclusionDate.findViewById(R.id.txtConclusionDate);
        txtGrade.findViewById(R.id.txtGrade);

        btnSaveDiscipline = findViewById(R.id.btnSaveDiscipline);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.spinner_discipline_type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.spinner_discipline_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
    }
}
