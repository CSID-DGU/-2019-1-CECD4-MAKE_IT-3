package sma.rhythmtapper.framework.Impl;

import java.util.List;
import sma.rhythmtapper.framework.HitInput;

public interface HitHandler extends onHitListener {
    public boolean isHitDown(int pointer);
    public int getHitX(int pointer);
    public int getHitY(int pointer);
    public List<HitInput.HitEvent> getHitEvents();
}
