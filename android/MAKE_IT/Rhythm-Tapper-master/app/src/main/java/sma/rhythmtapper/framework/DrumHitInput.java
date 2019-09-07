//from cj
package sma.rhythmtapper.framework;
import java.util.List;

public interface DrumHitInput {
    public static class DrumHitEvent {
        public String DrumHitNumber;
        public int pointer;
    }
    public String getDrumHitNumber(int pointer);
    public List<DrumHitEvent> getDrumHitEvent();
}
//from cj
