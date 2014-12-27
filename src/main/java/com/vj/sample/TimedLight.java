package com.vj.sample;

/**
 * Class representing a Light that automatically switches OFF after TIMEOUT seconds unless
 * switched ON again before TIMEOUT seconds.
 */
public class TimedLight extends Object implements TimerClient{

    public  static final String SWITCH_ON_ACTION = "SWITCH_ON";
    public  static final String SWITCH_OFF_ACTION = "SWITCH_OFF";
    private static long TIMEOUT = 60;


    private static int count = 0;

    private boolean isOn;
    private String id;


    public TimedLight(boolean isOn) {
        super();
        this.isOn = isOn;
        this.id = "LIGHT-"+count++;
    }

    public boolean isOn() {
        return isOn;
    }

    public String getId() {
        return this.id;
    }


    public void switchOn() {
        if (isOn())
            Timer.getInstance().unregister(this);

        this.isOn = true;
        Timer.getInstance().register(getId(), this, SWITCH_OFF_ACTION, TIMEOUT);
    }

    public void switchOff() {
        isOn = false;
    }


    @Override
    public void timeOut(String action) {
        if (action.equals(SWITCH_ON_ACTION)) this.switchOn();
        if (action.equals(SWITCH_OFF_ACTION)) this.switchOff();
    }
}
