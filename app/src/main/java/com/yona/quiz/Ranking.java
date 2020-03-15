package com.yona.quiz;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ranking implements Serializable {
    private String name;
    private String score;

    public Ranking(String txtname, String txtscore) {
        this.name = txtname;
        this.score = txtscore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return name.concat(": ").concat(score);
    }
}
