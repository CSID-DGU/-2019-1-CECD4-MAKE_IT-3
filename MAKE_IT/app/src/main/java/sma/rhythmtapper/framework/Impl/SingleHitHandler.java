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
import android.widget.Toast;

import sma.rhythmtapper.framework.HitInput;
import sma.rhythmtapper.framework.HitInput.HitEvent;
import sma.rhythmtapper.framework.Pool;
import sma.rhythmtapper.framework.Pool.PoolObjectFactory;
import sma.rhythmtapper.game.GameScreen;

public class SingleHitHandler /*extends Activity*/ implements  HitHandler{
    private boolean isHitted = false;
    private String drumPadNumber="0";
    private Pool<HitEvent> hitEventPool;
    private List<HitEvent> hitEvents = new ArrayList<HitEvent>();
    private List<HitEvent> hitEventsBuffer = new ArrayList<HitEvent>();


    /*//from cj
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
    //from cj*/

    public SingleHitHandler(){
        PoolObjectFactory<HitEvent> factory = new PoolObjectFactory<HitEvent>() {
            @Override
            public HitEvent createObject() {
                return new HitEvent();
            }
        };
        hitEventPool = new Pool<>(factory, 100);
        setOnHitListener();
    }

    public void setOnHitListener(){
        synchronized (this){
            HitEvent hitEvent = hitEventPool.newObject();
            if(drumPadNumber.equals("1")){
                isHitted = true;
                hitEvent.setPadNumber(drumPadNumber);
            }
            else if(drumPadNumber.equals("2")){
                isHitted = true;
                hitEvent.setPadNumber(drumPadNumber);
            }
            else if(drumPadNumber.equals("3")){
                isHitted = true;
                hitEvent.setPadNumber(drumPadNumber);
            }
            else if(drumPadNumber.equals("4")){
                isHitted = true;
                hitEvent.setPadNumber(drumPadNumber);
            }
            else{
                isHitted = false;
            }
            hitEventsBuffer.add(hitEvent);
        }
    }

    @Override
    public boolean isHitDown(int pointer){
        synchronized (this){
            return pointer == 0 && isHitted;
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
    public List<HitEvent> getHitEvents(){
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
}
