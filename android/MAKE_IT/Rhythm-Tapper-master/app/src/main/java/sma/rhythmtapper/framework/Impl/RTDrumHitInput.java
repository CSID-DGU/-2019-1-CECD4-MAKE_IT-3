package sma.rhythmtapper.framework.Impl;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import java.util.List;

import sma.rhythmtapper.framework.DrumHitInput;

public class RTDrumHitInput implements DrumHitInput {
    private DrumHitHandler drumHitHandler;

    public RTDrumHitInput(){
        if(VERSION.SDK_INT < 5)
            drumHitHandler = new SingleHitHandler();
        else
            drumHitHandler = new MultiHitHandler();
    }
    @Override
    public String getDrumHitNumber(int pointer){
        return drumHitHandler.getDrumHitNumber(pointer);
    }
    @Override
    public List<DrumHitEvent> getDrumHitEvent(){

        return drumHitHandler.getDrumHitEvents();
    }
}
