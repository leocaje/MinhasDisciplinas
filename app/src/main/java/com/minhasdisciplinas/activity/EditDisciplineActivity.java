package com.minhasdisciplinas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.minhasdisciplinas.R;
import com.minhasdisciplinas.model.DisciplineDTO;
import com.minhasdisciplinas.service.DisciplineService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class EditDisciplineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String URL = "https://minhasdisciplinas-2516a-default-rtdb.firebaseio.com/disciplinas.json";
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String APP_DISCIPLINES = "App Minhas Disciplinas";
    private Gson gson = new Gson();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    OkHttpClient client = new OkHttpClient();
    DisciplineService disciplineService = new DisciplineService();
    DisciplineDTO discipline = new DisciplineDTO();


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
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this, R.array.spinner_discipline_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
        spinnerStatus.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void loadDisciplineDetails(String id) {
        discipline = disciplineService.getDisciplineById();

        if (discipline != null) {
            fillInDisciplineInfo(discipline);
        } else {
            Log.e("EDIT_ACTIVITY", "Disciplina não encontrada");
        }
    }

    private void fillInDisciplineInfo(DisciplineDTO discipline) {
        if (discipline != null) {
            editCourse.setText(discipline.getCourse());
            editDisciplineName.setText(discipline.getDisciplineName());
            editProfessor.setText(discipline.getProfessor());
            editPeriod.setText(discipline.getPeriod());
            editWorkloadHours.setText(discipline.getWorkloadHours());
            spinnerType.getSelectedItem();
            spinnerStatus.getSelectedItem();
            editDescription.setText(discipline.getDescription());
            editConclusionDate.setText(discipline.getConclusionDate());
            editGrade.setText(String.valueOf(discipline.getGrade()));
        } else {
            Log.e("EDIT_ACTIVITY", "Disciplina não encontrada");
        }
    }

    private DisciplineDTO updateDisciplineInfo() {
        String newCourse = editCourse.getText().toString();
        String newDisciplineName = editDisciplineName.getText().toString();
        String newProfessor = editProfessor.getText().toString();
        int newPeriod = Integer.parseInt(editPeriod.getText().toString());
        int newWorloadHours = Integer.parseInt(editWorkloadHours.getText().toString());
        String newDisciplineType = spinnerType.getSelectedItem().toString();
        String newDisciplineStatus = spinnerStatus.getSelectedItem().toString();
        String newDescription = editDescription.getText().toString();
        int newConclusionDate = Integer.parseInt(editConclusionDate.getText().toString());
        double newGrade = Double.parseDouble(editGrade.getText().toString());

        discipline.setCourse(newCourse);
        discipline.setDisciplineName(newDisciplineName);
        discipline.setProfessor(newProfessor);
        discipline.setPeriod(newPeriod);
        discipline.setWorkloadHours(newWorloadHours);
        discipline.setDisciplineType(newDisciplineType);
        discipline.setDisciplineStatus(newDisciplineStatus);
        discipline.setDescription(newDescription);
        discipline.setConclusionDate(newConclusionDate);
        discipline.setGrade(newGrade);

        return discipline;
    }

}
