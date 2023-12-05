package com.minhasdisciplinas.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minhasdisciplinas.model.DisciplineDTO;
import com.minhasdisciplinas.util.GlobalConstants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DisciplineService {
    public static final MediaType JSON = MediaType.get("application/json");
    String url = GlobalConstants.BASE_URL + "/disciplinas.json";
    String urlId = GlobalConstants.BASE_URL + "/disciplinas/" + id + ".json";
    OkHttpClient client = new OkHttpClient();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Gson gson = new Gson();

    public void createDiscipline(DisciplineDTO discipline) {
        Future<?> createNewDiscipline = executor.submit(() -> {
            String disciplineToAdd = gson.toJson(discipline);
            RequestBody body = RequestBody.create(disciplineToAdd, JSON);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("SERVICE", "Disciplina criada");
                } else {
                    Log.i("SERVICE", "Erro ao criar disciplina");
                }

                if (response != null) {
                    response.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void getDisciplineList(List<DisciplineDTO> disciplines) {
        disciplines.clear();

        Future<?> fetchDisciplines = executor.submit(() -> {
           Request request = new Request.Builder()
                   .url(url)
                   .get()
                   .build();

           try {
               Response response = client.newCall(request).execute();

               if (response.isSuccessful()) {
                   String listDisciplineResponse = response.body().string();
                   JsonArray jsonArray = JsonParser.parseString(listDisciplineResponse).getAsJsonArray();

                   for (JsonElement element : jsonArray) {
                       DisciplineDTO disciplineToAdd = gson.fromJson(element, DisciplineDTO.class);
                       disciplines.add(disciplineToAdd);
                   }
               }
           } catch (IOException exception) {
               exception.printStackTrace();
           }
        });
    }

    public DisciplineDTO getDisciplineById() {
        Future<DisciplineDTO> fetchDisciplineById = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(urlId)
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String disciplineResponse = response.body().string();

                    if (disciplineResponse != null && !disciplineResponse.isEmpty()) {
                        DisciplineDTO selectedDiscipline = gson.fromJson(disciplineResponse, DisciplineDTO.class);
                        return selectedDiscipline;
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return null;
        });

        try {
            return fetchDisciplineById.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDisciplineById() {
        Future<?> updateDisciplineInfo = executor.submit(() -> {
            String updatedDiscipline = gson.toJson(id);
            RequestBody body = RequestBody.create(updatedDiscipline, JSON);
            Request request = new Request.Builder()
                    .url(urlId)
                    .put(body)
                    .build();

            try {
                Response response =client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("SERVICE", "Disciplina atualizada com sucesso");
                } else {
                    Log.e("SERVICE", "Falha ao atualizar disciplina");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void deleteDisciplineById() {
        Future<?> deleteSelectedDiscipline = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(urlId)
                    .delete()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    Log.i("SERVICE", "Disciplina deletada com sucesso");
                } else {
                    Log.e("SERVICE", "Erro ao deletar disciplina");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }
}

/*
import com.google.firebase.database.*;

public class DisciplineService {
    DatabaseReference databaseReference;

    public DisciplineService() {
        // Obtenha uma referência ao seu nó "disciplinas" no Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("disciplinas");
    }

    public void createDiscipline(DisciplineDTO discipline) {
        // Crie um novo nó para a disciplina
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(discipline);
    }

    public void getDisciplineList(final List<DisciplineDTO> disciplines) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                disciplines.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DisciplineDTO discipline = snapshot.getValue(DisciplineDTO.class);
                    if (discipline != null) {
                        disciplines.add(discipline);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate erros de leitura do banco de dados aqui
            }
        });
    }

    public void getDisciplineById(String id, final OnDisciplineLoadedListener listener) {
        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DisciplineDTO discipline = dataSnapshot.getValue(DisciplineDTO.class);
                if (discipline != null) {
                    listener.onDisciplineLoaded(discipline);
                } else {
                    listener.onDisciplineLoadFailure("Disciplina não encontrada");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Trate erros de leitura do banco de dados aqui
                listener.onDisciplineLoadFailure("Erro ao carregar disciplina: " + databaseError.getMessage());
            }
        });
    }

    public void updateDisciplineById(String id, DisciplineDTO updatedDiscipline) {
        databaseReference.child(id).setValue(updatedDiscipline);
    }

    public void deleteDisciplineById(String id) {
        databaseReference.child(id).removeValue();
    }
}

// Interface para manipular resultados assíncronos
interface OnDisciplineLoadedListener {
    void onDisciplineLoaded(DisciplineDTO discipline);

    void onDisciplineLoadFailure(String errorMessage);
}

 */