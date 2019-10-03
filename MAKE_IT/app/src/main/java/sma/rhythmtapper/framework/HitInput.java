package sma.rhythmtapper.framework;

import java.util.List;

public interface HitInput {
    public static class HitEvent {
        public String padNumber;
        public int pointer;

        public void setPadNumber(String padNumber){
            this.padNumber = padNumber;
        }
        public String getPadNumber(){
            return this.padNumber;
        }
    }
    public List<HitEvent> getHitEvents();
}
