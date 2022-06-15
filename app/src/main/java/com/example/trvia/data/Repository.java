package com.example.trvia.data;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trvia.MainActivity;
import com.example.trvia.controller.AppController;
import com.example.trvia.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questionArrayList = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";
    public List<Question> getQuestion(final AnswerAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < 100; i++) {
                        try {
                            Question question = new Question(response.getJSONArray(i).getString(0),
                                    response.getJSONArray(i).getBoolean(1));
                            questionArrayList.add(question);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(null!=callBack) {
                            callBack.processFinished(questionArrayList);
                        }

                    }

        }, error -> {
            Log.d("blah", "getQuestion: Failed");

        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }
    }
