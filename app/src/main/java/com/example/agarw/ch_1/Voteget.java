package com.example.agarw.ch_1;

/**
 * Created by agarw on 1/2/2018.
 */

public class Voteget {
    private String option;
    private String number;

    public Voteget(){

    }

    public Voteget(String number, String option) {
        this.option = option;
        this.number = number;
    }




    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
