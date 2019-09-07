package sma.rhythmtapper.framework.Impl;

import java.util.List;

import sma.rhythmtapper.framework.DrumHitInput;

import sma.rhythmtapper.models.PadInfoInterface;

public interface DrumHitHandler extends PadInfoInterface {
    public String getDrumHitNumber(int pointer);
    public List<DrumHitInput.DrumHitEvent> getDrumHitEvents();
}