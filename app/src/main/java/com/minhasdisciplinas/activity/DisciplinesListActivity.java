package com.minhasdisciplinas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minhasdisciplinas.R;
import com.minhasdisciplinas.adapter.DisciplineAdapter;
import com.minhasdisciplinas.util.RecyclerViewInterface;
import com.minhasdisciplinas.model.DisciplineDTO;

import java.util.ArrayList;
import java.util.List;

public class DisciplinesListActivity extends AppCompatActivity implements RecyclerViewInterface {
    List<DisciplineDTO> disciplines = new ArrayList<>();
    Button btnAddDiscipline;

    void buildStyles() {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildStyles();
        setContentView(R.layout.disciplines_list_layout);

        RecyclerView recyclerView = findViewById(R.id.recyclerDiscipline);

        DisciplineAdapter adapter = new DisciplineAdapter(this, disciplines, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddDiscipline = findViewById(R.id.btnAddDiscipline);
        btnAddDiscipline.setOnClickListener(event -> {
            Intent openNewDisciplineActivity = new Intent(this, NewDisciplineActivity.class);
            startActivity(openNewDisciplineActivity);
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent openDisciplineDetailsActivity = new Intent(this, DisciplineDetailsActivity.class);

        openDisciplineDetailsActivity.putExtra("COURSE", disciplines.get(position).getCourse());
        openDisciplineDetailsActivity.putExtra("NAME", disciplines.get(position).getDisciplineName());
        openDisciplineDetailsActivity.putExtra("PROFESSOR", disciplines.get(position).getProfessor());
        openDisciplineDetailsActivity.putExtra("PERIOD", disciplines.get(position).getPeriod());
        openDisciplineDetailsActivity.putExtra("WORKLOAD", disciplines.get(position).getWorkloadHours());
        openDisciplineDetailsActivity.putExtra("TYPE", disciplines.get(position).getDisciplineType());
        openDisciplineDetailsActivity.putExtra("STATUS", disciplines.get(position).getDisciplineStatus());
        openDisciplineDetailsActivity.putExtra("DESCRIPTION", disciplines.get(position).getDescription());
        openDisciplineDetailsActivity.putExtra("CONCLUSION", disciplines.get(position).getConclusionDate());
        openDisciplineDetailsActivity.putExtra("GRADE", disciplines.get(position).getGrade());

        startActivity(openDisciplineDetailsActivity);
    }
}
