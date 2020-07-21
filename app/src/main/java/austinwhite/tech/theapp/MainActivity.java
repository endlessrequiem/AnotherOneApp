package austinwhite.tech.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button refresh = findViewById(R.id.refresh);
        final Button startGame = findViewById(R.id.startGame);
        final Button anotherOne = findViewById(R.id.anotherOne);
        anotherOne.setEnabled(false);
        refresh.setEnabled(false);
        final int[] cyclesArr = {1};


        final TextView instructions = findViewById(R.id.instructions);
        final TextView timer = findViewById(R.id.timer);
        final TextView cycles = findViewById(R.id.cyclesView);
        final TextView anotherPress = findViewById(R.id.clicks);


        final RadioButton easy = findViewById(R.id.easy);
        final RadioButton medium = findViewById(R.id.medium);
        final RadioButton hard = findViewById(R.id.hard);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int max = 1;
                int min = 1;
                int time = 1;

                gameSet(max,
                        min,
                        time,
                        cyclesArr,
                        easy,
                        medium,
                        hard,
                        refresh,
                        startGame,
                        anotherOne,
                        instructions,
                        timer,
                        cycles,
                        anotherPress
                );


            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void gameSet(int max, int min, int time, final int[] cyclesArr,
                         final RadioButton easy, final RadioButton medium, final RadioButton hard,
                         final Button refresh, final Button startGame, final Button anotherOne,
                         final TextView instructions, final TextView timer, final TextView cycles, final TextView anotherPress) {



        if (easy.isChecked()) {
            max = 100;
            min = 1;
            time = 30000;
            medium.setEnabled(false);
            hard.setEnabled(false);
            anotherOne.setEnabled(true);
            easy.setVisibility(View.GONE);
            medium.setVisibility(View.GONE);
            hard.setVisibility(View.GONE);
            startGame.setVisibility(View.GONE);

        } else if (medium.isChecked()) {
            max = 200;
            min = 100;
            time = 45000;
            hard.setEnabled(false);
            easy.setEnabled(false);
            anotherOne.setEnabled(true);
            easy.setVisibility(View.GONE);
            medium.setVisibility(View.GONE);
            hard.setVisibility(View.GONE);
            startGame.setVisibility(View.GONE);


        } else if (hard.isChecked()) {
            max = 300;
            min = 200;
            time = 60000;
            easy.setEnabled(false);
            medium.setEnabled(false);
            anotherOne.setEnabled(true);
            easy.setVisibility(View.GONE);
            medium.setVisibility(View.GONE);
            hard.setVisibility(View.GONE);
            startGame.setVisibility(View.GONE);

        } else {
            Toast.makeText(this, getString(R.string.setDifficulty), Toast.LENGTH_SHORT).show();

        }

            if (max > 1) {
                final int randnumber = (int) ((Math.random() * (max - min)) + min);
                instructions.setText(getString(R.string.your_number) + " " + randnumber);


                final int[] score = {1};



                final CountDownTimer countDown = new CountDownTimer(time, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText(String.valueOf(millisUntilFinished / 1000));
                    }

                    public void onFinish() {
                        anotherPress.setText(getString(R.string.youLose));
                        anotherOne.setEnabled(false);
                        refresh.setEnabled(true);
                        easy.setEnabled(false);
                        medium.setEnabled(false);
                        hard.setEnabled(false);
                    }

                }.start();

                startGame.setEnabled(false);

                final int finalMax = max;
                final int finalMin = min;
                final int finalTime = time;

                anotherOne.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        int i = score[0]++;
                        if (i == randnumber) {
                            countDown.cancel();
                            anotherPress.setText(String.valueOf(i));
                            anotherOne.setEnabled(false);
                            refresh.setEnabled(true);
                            gameSet(finalMax,
                                    finalMin,
                                    finalTime,
                                    cyclesArr,
                                    easy,
                                    medium,
                                    hard,
                                    refresh,
                                    startGame,
                                    anotherOne,
                                    instructions,
                                    timer,
                                    cycles,
                                    anotherPress);
                            cycleCounter(cycles, cyclesArr);


                        } else {
                            anotherPress.setText(String.valueOf(i));
                        }
                    }
                });

            } else {
                Log.i("Debug Check", "Else Statement from anotherOne.onClickListener"); //for debug purposes

            }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
            }
        });

        }

    @SuppressLint("SetTextI18n")
    private void cycleCounter(TextView cycles, int[] cyclesArr) {
        int counterCycle = cyclesArr[0]++;

        if (counterCycle == 1) {
            cycles.setText(getString(R.string.cycle) + " " + counterCycle);
        } else {
            cycles.setText(getString(R.string.cycles) + " " + counterCycle);
        }
    }

    public void restart() {
        finish();
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}