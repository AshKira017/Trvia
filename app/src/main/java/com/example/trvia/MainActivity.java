package com.example.trvia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trvia.data.Repository;
import com.example.trvia.databinding.ActivityMainBinding;
import com.example.trvia.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static int currentqi=0;
    private float score=0;
    private int btnclicked=0;
    private int btnclicked1=0;
    private float highscore=0;
    private TextView hstext;
    private Button next;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private Button save;
    private List<Question> questionList;
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        next = binding.nextbtn;
        hstext = binding.textView2;


        questionList = new Repository().getQuestion(questionArrayList -> {
            System.out.println("blah "+currentqi);
            binding.questionText.setText( questionArrayList.get(currentqi).getAnswer());
            binding.qno.setText("Question :0/"+questionList.size());
            binding.textView4.setText("Score :0");
            next.setOnClickListener(view -> {
                currentqi = (currentqi+1);
                if(currentqi>=20){
                    Toast.makeText(MainActivity.this,"Quiz completed :))",Toast.LENGTH_SHORT)
                            .show();
                    saveHighScore();
                }else{
                    updatequestion();
                    updateqno();
                }

            });
            binding.truebtn.setOnClickListener(view -> {
                if(currentqi>=100){
                    Toast.makeText(MainActivity.this,"Quiz completed :))",Toast.LENGTH_SHORT)
                            .show();
                    saveHighScore();
                }else{
                    checkanswertrue();
                    currentqi = (currentqi+1);
                    updatequestion();
                    updateqno();
                }


            });
            binding.falsebtn.setOnClickListener(view -> {
                if(currentqi>=20){
                    Toast.makeText(MainActivity.this,"Quiz completed :))",Toast.LENGTH_SHORT)
                            .show();
                    saveHighScore();
                }else{
                    checkanswerfalse();
                    currentqi = (currentqi+1);
                    updatequestion();
                    updateqno();
                }


            });
        });
        showHighScore();



    }

    private void showHighScore() {
        SharedPreferences getdata =getSharedPreferences("MSG_ID",MODE_PRIVATE);
        String blah = String.valueOf(getdata.getFloat("hs",score));

        hstext.setText("HighScore :"+blah);

    }

    private void saveHighScore() {
         sharedPreferences = getSharedPreferences("MSG_ID",MODE_PRIVATE);
         editor = sharedPreferences.edit();
        if(score>=highscore){
            highscore=score;
            editor.putFloat("hs", highscore);
            editor.apply();
        }


    }

    @Override
    protected void onPause() {
        saveHighScore();
        super.onPause();

    }

    private void shakeanimation() {
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        binding.card.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.RED);
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void checkanswerfalse() {
        btnclicked=btnclicked+1;
        if(!questionList.get(currentqi).getAnswerTrue()){
            Snackbar.make(MainActivity.this,binding.truebtn,"Correct Answer",Snackbar.LENGTH_SHORT)
                    .show();
            fadeanimation();
            if(btnclicked==1){
                updateScore();
            }
        }else{
            Snackbar.make(MainActivity.this,binding.truebtn,"Wrong Answer",Snackbar.LENGTH_SHORT)
                    .show();
            shakeanimation();
            updateScoreneg();

        }
        btnclicked=Integer.MIN_VALUE;

    }

    private void updateScoreneg() {
        if(score<=0){
            score =0;
        }else{
            score = (float) (score-0.5);
        }

        binding.textView4.setText("Score :"+score);
    }

    private void checkanswertrue() {
        btnclicked1 +=1;
        if(questionList.get(currentqi).getAnswerTrue()) {
            Snackbar.make(MainActivity.this, binding.truebtn, "Correct Answer", Snackbar.LENGTH_SHORT)
                    .show();
            fadeanimation();

            if (btnclicked1 == 1) {
                updateScore();
            }
        }else{
            Snackbar.make(MainActivity.this,binding.truebtn,"Wrong Answer",Snackbar.LENGTH_SHORT)
                    .show();
            Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
            shakeanimation();
            updateScoreneg();
        }
        btnclicked1=Integer.MIN_VALUE;


    }

    @SuppressLint("SetTextI18n")
    private void updateScore() {
        score = score+1;
        binding.textView4.setText("Score :"+score);
    }

    @SuppressLint("SetTextI18n")
    private void updateqno() {
        binding.qno.setText("Question :"+(currentqi+1)+"/"+questionList.size());
        btnclicked=0;
        btnclicked1=0;
    }

    private void updatequestion() {
        if(currentqi<20){
            String currq = questionList.get(currentqi).getAnswer();
            binding.questionText.setText(currq);
        }

    }

    private void fadeanimation(){
        Animation fade = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);
        binding.card.setAnimation(fade);
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionText.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionText.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }




}