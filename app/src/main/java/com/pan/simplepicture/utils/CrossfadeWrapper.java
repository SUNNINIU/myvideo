package com.pan.simplepicture.utils;

import com.mikepenz.crossfader.Crossfader;
import com.pan.materialdrawer.interfaces.ICrossfader;

/**
 * Created by mikepenz on 18.07.15.
 */
public class CrossfadeWrapper implements ICrossfader {
    private Crossfader mCrossfader;

    public CrossfadeWrapper(Crossfader crossfader) {
        this.mCrossfader = crossfader;
    }

    @Override
    public void crossfade() {
        mCrossfader.crossFade();
    }

    @Override
    public boolean isCrossfaded() {
        return mCrossfader.isCrossFaded();
    }
}
