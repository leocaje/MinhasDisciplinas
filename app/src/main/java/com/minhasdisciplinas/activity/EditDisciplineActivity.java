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

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;

public class EditDisciplineActivity extends AppCompatActivity {
    private static final String URL = "https://minhasdisciplinas-2516a-default-rtdb.firebaseio.com/disciplinas.json";
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String APP_DISCIPLINES = "App Minhas Disciplinas";
    private Gson gson = new Gson();

    List<DisciplineDTO> disciplines = new ArrayList<>();
    private EditText editCourse;
    private EditText editDisciplineName;
    private EditText editProfessor;
    private EditText editPeriod;
    private EditText editWorkloadHours;
    private Spinner spinnerType;
    private Spinner spinnerStatus;
    private EditText editDescription;
    private EditText editConclusionDate;
    private EditText editGrade;
    private Button btnUpdateDiscipline;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildStyles();
        setContentView(R.layout.edit_discipline_layout);

        initializeUIComponents();


        btnUpdateDiscipline.setOnClickListener(event -> {
            Intent openDisciplineDetailsActivity = new Intent(this, DisciplineDetailsActivity.class);
            startActivity(openDisciplineDetailsActivity);
        });
    }

    private void initializeUIComponents() {
        editCourse = findViewById(R.id.editCourse);
        editDisciplineName = findViewById(R.id.editDisciplineName);
        editProfessor = findViewById(R.id.editProfessor);
        editPeriod = findViewById(R.id.editPeriod);
        editWorkloadHours = findViewById(R.id.editWorkloadHours);
        spinnerType = findViewById(R.id.spinnerEditDisciplineType);
        spinnerStatus = findViewById(R.id.spinnerEditDisciplineStatus);
        editDescription = findViewById(R.id.editDescription);
        editConclusionDate = findViewById(R.id.editConclusionDate);
        editGrade = findViewById(R.id.editGrade);

        btnUpdateDiscipline = findViewById(R.id.btnUpdateDiscipline);

        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.spinner_discipline_type, android.R.layout.simple_spinner_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.spinner_discipline_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
    }
}
