package com.example.sjl95.ai_drum.models;

public class Highscore {
    private String _date;
    private int _score;

    public Highscore(String datetime, int score) {
        this._date = datetime;
        this._score = score;

    }
    @Override
    public String toString() {
        return this.getDate() + " " + this.getScore();
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
    }

    public int getScore() {
        return _score;
    }

    public void setScore(int _score) {
        this._score = _score;
    }
}
