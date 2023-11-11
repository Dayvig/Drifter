//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package DrifterMod.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class HeadLightParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private static final float DUR = 0.75f;
    private float intensity = 10.0f;

    public HeadLightParticle(Color setColor) {
            this.x = AbstractDungeon.player.hb.cX;
            this.y = AbstractDungeon.player.hb.cY;
            this.startingDuration = duration = DUR;
            this.color = setColor;
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (duration < (startingDuration / 0.8f)){
            this.intensity = 10.0f * (duration/startingDuration);
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public float[] _lightsOutGetXYRI() {
        return new float[] {x, y, Settings.WIDTH, intensity};
    }

    public Color[] _lightsOutGetColor() {
        return new Color[] {color};
    }

    public void dispose() {
    }
}
