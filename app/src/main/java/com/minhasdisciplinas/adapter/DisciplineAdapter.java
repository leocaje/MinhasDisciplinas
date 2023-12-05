package com.minhasdisciplinas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhasdisciplinas.R;
import com.minhasdisciplinas.model.DisciplineDTO;
import com.minhasdisciplinas.util.RecyclerViewInterface;

import java.util.List;

public class DisciplineAdapter extends RecyclerView.Adapter<DisciplineAdapter.MyViewHolder> {
    Context context;
    List<DisciplineDTO> disciplines;

    private final RecyclerViewInterface recyclerViewInterface;


    public DisciplineAdapter(Context context, List<DisciplineDTO> disciplines, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.disciplines = disciplines;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public DisciplineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Cria as visualizações em tela (inflando o layout em cada linha)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.discipline_item_layout, parent, false);
        return new DisciplineAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DisciplineAdapter.MyViewHolder holder, int position) {
        //Exibe as visualizações para o usuário (passa os valores para os elementos de cada visualização)
        holder.cardDisciplineName.setText(disciplines.get(position).getDisciplineName());
    }

    @Override
    public int getItemCount() {
        //Conta o número de elementos no array para exibir a lista para o usuário
        return disciplines.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //Recupera as views do arquivo discipline_item_layout

        TextView cardDisciplineName;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            cardDisciplineName.findViewById(R.id.cardDisciplineName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
