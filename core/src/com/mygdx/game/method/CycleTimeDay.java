package com.mygdx.game.method;

import com.mygdx.game.main.Main;

public class CycleTimeDay {
    public static int timeDay,timeNight,totalTime,MaxTime,timeTransitionDay,timeTransitionNight;
    public static float lightDay,lightNight,lightTotal,lightPurpose,lightFlame;
    public static float lightVariabilityDayNight,lightVariabilityNightDay;
    public static int ConfigTime;

    public CycleTimeDay(int timeDay,int timeNight,int timeTransitionDay,int timeTransitionNight,float lightDay,float lightNight){
        CycleTimeDay.timeDay = timeDay* Main.FPS;
        CycleTimeDay.timeNight = timeNight* Main.FPS;
        CycleTimeDay.timeTransitionDay = timeTransitionDay* Main.FPS;
        CycleTimeDay.timeTransitionNight = timeTransitionNight* Main.FPS;
        CycleTimeDay.lightDay = lightDay;
        CycleTimeDay.lightFlame = CycleTimeDay.lightDay-0.1f;
        CycleTimeDay.lightNight = lightNight;
        CycleTimeDay.MaxTime = CycleTimeDay.timeDay;
        CycleTimeDay.ConfigTime = 0;
        CycleTimeDay.lightTotal = lightDay;
        data();

    }
    private void data(){
        CycleTimeDay.lightVariabilityDayNight = (lightDay-lightNight)/timeTransitionDay;
        CycleTimeDay.lightVariabilityNightDay = (lightNight-lightDay)/timeTransitionNight;
    }
    public void WorkTime(){
        totalTime+= 1;
        if(MaxTime <totalTime){
            ConfigTime += 1;
            if(ConfigTime>3){
                ConfigTime = 0;
            }
            if(CycleTimeDay.ConfigTime == 0){
                MaxTime = timeDay;
                lightPurpose = lightDay;
                lightTotal = lightDay;

            }
            else if(CycleTimeDay.ConfigTime == 1){
                MaxTime = timeTransitionDay;
                lightPurpose = timeNight;
            }
            else if(CycleTimeDay.ConfigTime == 2){
                MaxTime = timeNight;
                lightPurpose = lightNight;
                lightTotal = lightNight;


            }
            else if(CycleTimeDay.ConfigTime == 3){
                MaxTime = timeTransitionNight;
                lightPurpose = lightDay;
            }
            totalTime = 0;
        }
        if(ConfigTime == 1){
            lightTotal += lightVariabilityNightDay;
        }
        else if(ConfigTime == 3){
            lightTotal += lightVariabilityDayNight;
        }
    }
}
