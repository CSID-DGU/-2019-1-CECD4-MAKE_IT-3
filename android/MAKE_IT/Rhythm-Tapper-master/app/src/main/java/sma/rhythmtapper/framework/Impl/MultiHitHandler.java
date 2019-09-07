package sma.rhythmtapper.framework.Impl;

import java.util.List;
import java.util.ArrayList;

import android.view.MotionEvent;
import android.view.View;

import sma.rhythmtapper.MainActivity;
import sma.rhythmtapper.framework.DrumHitInput.DrumHitEvent;
import sma.rhythmtapper.framework.Pool;
import sma.rhythmtapper.framework.Pool.PoolObjectFactory;
import sma.rhythmtapper.models.PadInfo;

public class MultiHitHandler implements DrumHitHandler {
    private static final int MAX_HITPOINTS = 10;

    private boolean[] isHit = new boolean[MAX_HITPOINTS];
    private int[] id = new int[MAX_HITPOINTS];
    private String[] drumHitNumber = new String[MAX_HITPOINTS];
    private Pool<DrumHitEvent> drumHitEventPool;
    private List<DrumHitEvent> drumHitEvents = new ArrayList<DrumHitEvent>();
    private List<DrumHitEvent> drumHitEventsBuffer = new ArrayList<DrumHitEvent>();

    public MultiHitHandler(){
        PoolObjectFactory<DrumHitEvent> factory = new PoolObjectFactory<DrumHitEvent>() {
            @Override
            public DrumHitEvent createObject() {
                return new DrumHitEvent();
            }
        };
        drumHitEventPool = new Pool<DrumHitEvent>(factory, 100);
        // view.setOnTouchListener(this);
    }
    public PadInfo getReceivedPadInfo(){
        return ((MainActivity) MainActivity.testmContext).getPadInformation();
    }

    //@Override
    //onHit????
    @Override
    public boolean getPadInfo(){
        PadInfo tempPadInfo;
        String tempPadString;
        synchronized (this){
            DrumHitEvent drumHitEvent = drumHitEventPool.newObject();
            tempPadInfo = getReceivedPadInfo();
            tempPadString = tempPadInfo.getPadNumber();

            //switch code????

            drumHitEvent.DrumHitNumber = tempPadString;
            drumHitEventsBuffer.add(drumHitEvent);
            return true;
        }
    }

    @Override
    public String getDrumHitNumber(int pointer){
        synchronized (this){
            int index = getIndex(pointer);
            return drumHitNumber[index];
        }
    }

    @Override
    public List<DrumHitEvent> getDrumHitEvents(){
        synchronized(this){
            int len = drumHitEvents.size();
            for(int i = 0; i< len; i++)
                drumHitEventPool.free(drumHitEvents.get(i));
            drumHitEvents.clear();
            drumHitEvents.addAll(drumHitEventsBuffer);
            drumHitEventsBuffer.clear();
            return drumHitEvents;
        }
    }

    private int getIndex(int pointerId){
        for(int i = 0; i< MAX_HITPOINTS; i++){
            if(id[i] == pointerId){
                return i;
            }
        }
        return -1;
    }
}
