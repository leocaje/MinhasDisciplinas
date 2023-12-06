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
import java.util.ArrayList;
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
    String id = "1";
    String url = GlobalConstants.BASE_URL + "/disciplinas.json";
    String urlId = GlobalConstants.BASE_URL + "/disciplinas/" + id + ".json";
    OkHttpClient client = new OkHttpClient();
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Gson gson = new Gson();

    List<DisciplineDTO> disciplines = new ArrayList<>();

    public void createDiscipline(DisciplineDTO discipline, OnCreateDisciplineListener listener) {
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
                listener.onDisciplineCreated(false);
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

    public void updateDisciplineById(DisciplineDTO id) {
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

    public void loadDisciplines(OnLoadedDisciplineListener listener) {
        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String resp = response.body().string();

                if (resp != null && resp.isEmpty()) {
                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(resp);

                    if (element != null && !element.isJsonNull() && element.isJsonObject()) {
                        JsonObject convertedObject = element.getAsJsonObject();

                        for (String key : convertedObject.keySet()) {
                            JsonElement obj = convertedObject.get(key);

                            if (obj != null && !obj.isJsonNull() && obj.isJsonObject()) {
                                DisciplineDTO discipline = gson.fromJson(obj, DisciplineDTO.class);
                                disciplines.add(discipline);
                            }
                        }
                        Log.i("SERVICE", "Resposta: " + resp);
                    } else {
                        Log.e("SERVICE", "Resposta do servidor não é um objeto Json válido");
                    }
                } else {
                    Log.e("SERVICE", "Resposta do servidor nula ou vazia");
                }
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public void shutdownExecutor() {
        executor.shutdown();
    }

    public interface OnCreateDisciplineListener {
        void onDisciplineCreated(boolean success);
    }

    public interface OnLoadedDisciplineListener {
        void onDisciplineLoaded(List<DisciplineDTO> disciplines);
    }

}