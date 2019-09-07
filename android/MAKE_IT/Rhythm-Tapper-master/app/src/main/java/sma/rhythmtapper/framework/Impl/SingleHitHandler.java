package sma.rhythmtapper.framework.Impl;

import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;

import sma.rhythmtapper.MainActivity;
import sma.rhythmtapper.framework.DrumHitInput.DrumHitEvent;
import sma.rhythmtapper.framework.Pool;
import sma.rhythmtapper.framework.Pool.PoolObjectFactory;
import sma.rhythmtapper.models.PadInfo;


public class SingleHitHandler implements DrumHitHandler {
    private boolean isHit;
    private String drumHitNumber;
    private Pool<DrumHitEvent> drumHitEventPool;
    private List<DrumHitEvent> drumHitEvents = new ArrayList<DrumHitEvent>();
    private List<DrumHitEvent> drumHitEventsBuffer = new ArrayList<DrumHitEvent>();

    public SingleHitHandler(){
        PoolObjectFactory<DrumHitEvent> factory = new PoolObjectFactory<DrumHitEvent>(){
            @Override
            public DrumHitEvent createObject(){
                return new DrumHitEvent();
            }
        };
        drumHitEventPool = new Pool<>(factory, 100);

    }
    //@Override onHit???????

    public PadInfo getReceivedPadInfo(){
        return ((MainActivity) MainActivity.testmContext).getPadInformation();
    }

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
        synchronized(this){
            return drumHitNumber;
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
}
