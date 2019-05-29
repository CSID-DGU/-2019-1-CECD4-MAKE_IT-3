package com.example.sjl95.ai_drum;

import android.os.Bundle;
import android.util.Log;

import com.example.sjl95.ai_drum.framework.Impl.RTGame;
import com.example.sjl95.ai_drum.framework.Screen;
import com.example.sjl95.ai_drum.game.LoadingScreen;
import com.example.sjl95.ai_drum.models.Difficulty;

public class GameActivity extends RTGame {
    private Difficulty _diff;

    @Override
    public Screen getInitScreen() {
        // get passed difficulty object
        _diff = (Difficulty)this.getIntent().getSerializableExtra("difficulty");
        return new LoadingScreen(this, _diff);
    }
}
