package com.example.trvia.data;

import com.example.trvia.model.Question;

import java.util.ArrayList;

public interface AnswerAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
