package sma.rhythmtapper.framework.Impl;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import sma.rhythmtapper.framework.HitInput;
import sma.rhythmtapper.framework.HitInput.HitEvent;
import sma.rhythmtapper.framework.Pool;
import sma.rhythmtapper.framework.Pool.PoolObjectFactory;

public class MultiHitHandler extends Activity implements  HitHandler{
    private static final int MAX_HITPOINTS = 10;
    private boolean[] isHitted = new boolean[MAX_HITPOINTS];
    private int[] id = new int[MAX_HITPOINTS];
    private String drumPadNumber;
    private Pool<HitEvent> hitEventPool;
    private List<HitEvent> hitEvents = new ArrayList<HitEvent>();
    private List<HitEvent> hitEventsBuffer = new ArrayList<HitEvent>();

    //from cj
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, new IntentFilter("DrumHitNumber"));
    }
    //*from cj
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            drumPadNumber= intent.getStringExtra("DrumPadNumber");
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }
    //from cj

    public MultiHitHandler(){
        PoolObjectFactory<HitEvent> factory = new PoolObjectFactory<HitEvent>() {
            @Override
            public HitEvent createObject() {
                return new HitEvent();
            }
        };
        hitEventPool = new Pool<HitEvent>(factory, 100);
        setOnHitListener();
    }
    public void setOnHitListener(){
        synchronized (this){
            HitEvent hitEvent = hitEventPool.newObject();
            hitEvent.setPadNumber(drumPadNumber);
            switch(hitEvent.getPadNumber()){
                case "1":
                    isHitted[0] = true;
                    break;
                case "2":
                    isHitted[0]= true;
                    break;
                case "3":
                    isHitted[0] = true;
                    break;
                case "4":
                    isHitted[0] = true;
                    break;
            }
            hitEventsBuffer.add(hitEvent);
        }
    }
    @Override
    public boolean isHitDown(int pointer){
        synchronized (this){
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_HITPOINTS)
                return false;
            else
                return isHitted[index];
        }
    }

    @Override
    public int getHitX(int pointer){
        synchronized (this){
            return 0;
        }
    }

    @Override
    public int getHitY(int pointer){
        synchronized (this){
            return 0;
        }
    }

    @Override
    public List<HitInput.HitEvent> getHitEvents(){
        synchronized (this){
            int len = hitEvents.size();
            for(int i = 0; i< len; i++)
                hitEventPool.free(hitEvents.get(i));
            hitEvents.clear();
            hitEvents.addAll(hitEventsBuffer);
            hitEventsBuffer.clear();
            return hitEvents;
        }
    }
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_HITPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
