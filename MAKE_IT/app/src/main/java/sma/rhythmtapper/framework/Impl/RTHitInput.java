package sma.rhythmtapper.framework.Impl;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import sma.rhythmtapper.framework.HitInput;

public class RTHitInput implements HitInput {
    private HitHandler hitHandler;

    public RTHitInput(Context context) {
        //if(VERSION.SDK_INT < 5)
            hitHandler = new SingleHitHandler();
       // else
            //hitHandler = new MultiHitHandler();
    }
    @Override
    public List<HitEvent> getHitEvents(){return hitHandler.getHitEvents();}
}
