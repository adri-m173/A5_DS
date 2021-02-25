package com.example.database;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Temporizador {
    private CountDownTimer timer;
    private TextView timeText;
    private long timeRemining;
    private boolean ended;
    private long limitTime;
    private String finishMessage;

    public Temporizador(TextView timeText,long limitTime,String finishMessage) {
        this.timeText = timeText;
        this.timer = crearTimer(limitTime);
        this.timeRemining = limitTime;
        this.limitTime = limitTime;
        this.finishMessage = finishMessage;
    }

    private CountDownTimer crearTimer(long time) {
        CountDownTimer countDownTimer = new CountDownTimer(time, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText(millisUntilFinished/1000+" seg");
                timeRemining = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                timeText.setText(finishMessage);
                ended = true;
            }
        };
        return countDownTimer;
    }

    /**
     * Metodo para reducir el tiempo del temporizador simbolizando el castigo al usuario
     * por fallar en el juego. Se resta 2 segundos al tiempo actual
     */
    public void fallar(){
        timer.cancel();
        timer = crearTimer(timeRemining-2000);
        timer.start();
    }
    /**
     * Metodo para aumentar el tiempo del temporizador simbolizando la recompensa al usuario
     * por acertar en el juego. Se suman 4 segundos al tiempo actual
     */
    public void acertar(){
        timer.cancel();
        timer = crearTimer(timeRemining+4000);
        timer.start();
    }

    public void star(){
        timer.start();
        ended = false;
        this.timeRemining = limitTime;
    }

    public boolean hasEnd() {
        return ended;
    }


    public void restart() {
        this.timer = crearTimer(limitTime);
        this.timeRemining = limitTime;
        this.timer.start();
        ended = false;
    }
}
