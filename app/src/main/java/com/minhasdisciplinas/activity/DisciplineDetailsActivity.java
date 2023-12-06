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

import okhttp3.MediaType;

public class DisciplineDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String URL = "https://minhasdisciplinas-2516a-default-rtdb.firebaseio.com/disciplinas.json";
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String APP_DISCIPLINES = "App Minhas Disciplinas";
    private Gson gson = new Gson();
    DisciplineService disciplineService = new DisciplineService();
    DisciplineDTO discipline = new DisciplineDTO();

    List<DisciplineDTO> disciplines = new ArrayList<>();
    private EditText infoCourse;
    private EditText infoDisciplineName;
    private EditText infoProfessor;
    private EditText infoPeriod;
    private EditText infoWorkloadHours;
    private Spinner spinnerType;
    private Spinner spinnerStatus;
    private EditText infoDescription;
    private EditText infoConclusionDate;
    private EditText infoGrade;

    private Button btnEditDiscipline;
    private Button btnDeleteDiscipline;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildStyles();
        setContentView(R.layout.discipline_details_layout);

        initializeUIComponents();

        btnEditDiscipline.setOnClickListener(event -> {
            Intent openEditDisciplineActivity = new Intent(this, DisciplinesListActivity.class);
            startActivity(openEditDisciplineActivity);
        });

        btnDeleteDiscipline.setOnClickListener(event -> {
            disciplineService.deleteDisciplineById();
            Intent reopenDisciplinesActivity = new Intent(this, DisciplinesListActivity.class);
            startActivity(reopenDisciplinesActivity);
        });
    }

    private void initializeUIComponents() {
        infoCourse = findViewById(R.id.infoCourse);
        infoDisciplineName = findViewById(R.id.infoDisciplineName);
        infoProfessor = findViewById(R.id.infoProfessor);
        infoPeriod = findViewById(R.id.infoPeriod);
        spinnerType = findViewById(R.id.spinnerInfoDisciplineType);
        spinnerStatus = findViewById(R.id.spinnerInfoDisciplineStatus);
        infoDescription = findViewById(R.id.infoDescription);
        infoConclusionDate = findViewById(R.id.infoConclusionDate);
        infoGrade = findViewById(R.id.infoGrade);

        btnEditDiscipline = findViewById(R.id.btnEditDiscipline);
        btnDeleteDiscipline = findViewById(R.id.btnDeleteDiscipline);

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
            Log.e("DETAILS_ACTIVITY", "Disciplina não encontrada");
        }
    }

    private void fillInDisciplineInfo(DisciplineDTO discipline) {
        if (discipline != null) {
            infoCourse.setText(discipline.getCourse());
            infoDisciplineName.setText(discipline.getDisciplineName());
            infoProfessor.setText(discipline.getProfessor());
            infoPeriod.setText(discipline.getPeriod());
            infoWorkloadHours.setText(discipline.getWorkloadHours());
            spinnerType.getSelectedItem();
            spinnerStatus.getSelectedItem();
            infoDescription.setText(discipline.getDescription());
            infoConclusionDate.setText(discipline.getConclusionDate());
            infoGrade.setText(String.valueOf(discipline.getGrade()));
        } else {
            Log.e("DETAILS_ACTIVITY", "Disciplina não encontrada");
        }
    }

}
