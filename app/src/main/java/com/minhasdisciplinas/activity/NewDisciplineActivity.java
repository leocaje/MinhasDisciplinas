package com.minhasdisciplinas.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.minhasdisciplinas.util.GlobalConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class NewDisciplineActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String url = GlobalConstants.BASE_URL + "/disciplinas.json";
    public static final MediaType JSON = MediaType.get("application/json");
    public static final String APP_DISCIPLINES = "App Minhas Disciplinas";
    private Gson gson = new Gson();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    OkHttpClient client = new OkHttpClient();
    DisciplineService disciplineService = new DisciplineService();
    DisciplineDTO discipline = new DisciplineDTO();


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
            saveDiscipline();
            Intent openDisciplinesActivity = new Intent(this, DisciplinesListActivity.class);
            startActivity(openDisciplinesActivity);
        });
    }

    private void initializeUIComponents() {
        txtCourse = findViewById(R.id.txtCourse);
        txtDisciplineName = findViewById(R.id.txtDisciplineName);
        txtProfessor = findViewById(R.id.txtProfessor);
        txtPeriod = findViewById(R.id.txtPeriod);
        spinnerType = findViewById(R.id.spinnerDisciplineType);
        spinnerStatus = findViewById(R.id.spinnerDisciplineStatus);
        txtDescription = findViewById(R.id.txtDescription);
        txtConclusionDate = findViewById(R.id.txtConclusionDate);
        txtGrade = findViewById(R.id.txtGrade);

        btnSaveDiscipline = findViewById(R.id.btnSaveDiscipline);

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

    private DisciplineDTO generateDiscipline() {
        discipline.setCourse(txtCourse.getText().toString());
        discipline.setDisciplineName(txtDisciplineName.getText().toString());
        discipline.setProfessor(txtProfessor.getText().toString());
        discipline.setPeriod(Integer.parseInt(txtPeriod.getText().toString()));
        discipline.setWorkloadHours(Integer.parseInt(txtWorkloadHours.getText().toString()));
        discipline.setDisciplineType(spinnerType.getSelectedItem().toString());
        discipline.setDisciplineStatus(spinnerStatus.getSelectedItem().toString());
        discipline.setDescription(txtDescription.getText().toString());
        discipline.setConclusionDate(Integer.parseInt(txtConclusionDate.getText().toString()));
        discipline.setGrade(Double.parseDouble(txtGrade.getText().toString()));

        Toast.makeText(this, "Disciplina cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

        return discipline;
    }

    private void saveDiscipline() {
        DisciplineDTO discipline = generateDiscipline();
        disciplineService.createDiscipline(discipline, success -> {
            if (success) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Disciplina cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(this, "Erro ao cadastrar disciplina", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadDisciplines() {
        disciplineService.loadDisciplines(new DisciplineService.OnLoadedDisciplineListener() {
            @Override
            public void onDisciplineLoaded(List<DisciplineDTO> disciplines) {

            }
        });
    }

}
