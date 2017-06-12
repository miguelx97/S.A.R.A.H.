package com.sarah.sarah;

/**
 * Created by Andrea on 23/04/2017.
 */

public class State {
    private  String action;
    private int state;

    public State(String action, int state) {
        this.action = action;
        this.state = state;
    }

    public State() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
