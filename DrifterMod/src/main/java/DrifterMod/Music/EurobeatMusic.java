package DrifterMod.Music;

import com.megacrit.cardcrawl.audio.TempMusic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EurobeatMusic extends TempMusic {
    
    boolean EuroFadingOut;
    
    public EurobeatMusic(String key, boolean isFast) {
        super(key, isFast, true);
    }

    public EurobeatMusic(String key, boolean isFast, boolean loop) {
        super(key, isFast, loop);
    }

    public EurobeatMusic(String key, boolean isFast, boolean loop, boolean precache) {
        super(key, isFast, loop, precache);
    }
    
    @Override
    public void update() {
        if (this.music.isPlaying()) {
            if (!this.isFadingOut) {
                this.updateFadeIn();
            } else {
                this.updateFadeOut();
            }
        } else if (this.isFadingOut) {
            this.kill();
        }

    }



}
