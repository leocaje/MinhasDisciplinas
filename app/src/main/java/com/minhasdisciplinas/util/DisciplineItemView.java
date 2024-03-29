package com.minhasdisciplinas.util;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.minhasdisciplinas.R;

public class DisciplineItemView extends RecyclerView.ViewHolder {
    public TextView txtCardDisciplineName;
    public ConstraintLayout disciplineCard;

    public DisciplineItemView(View itemView) {
        super(itemView);

        txtCardDisciplineName = itemView.findViewById(R.id.cardDisciplineName);
        disciplineCard = itemView.findViewById(R.id.disciplineCard);
    }
}
