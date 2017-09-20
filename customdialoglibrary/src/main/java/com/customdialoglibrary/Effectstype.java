package com.customdialoglibrary;

import com.customdialoglibrary.effects.BaseEffects;
import com.customdialoglibrary.effects.FadeIn;
import com.customdialoglibrary.effects.Fall;
import com.customdialoglibrary.effects.FlipH;
import com.customdialoglibrary.effects.FlipV;
import com.customdialoglibrary.effects.NewsPaper;
import com.customdialoglibrary.effects.RotateBottom;
import com.customdialoglibrary.effects.RotateLeft;
import com.customdialoglibrary.effects.Shake;
import com.customdialoglibrary.effects.SideFall;
import com.customdialoglibrary.effects.SlideBottom;
import com.customdialoglibrary.effects.SlideLeft;
import com.customdialoglibrary.effects.SlideRight;
import com.customdialoglibrary.effects.SlideTop;
import com.customdialoglibrary.effects.Slit;

public enum Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);

    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}
