package DrifterMod.actions;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import basemod.ReflectionHacks;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.audio.Music;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import javax.smartcardio.Card;

import static DrifterMod.DrifterMod.EUROBEAT_ON;


public class EurobeatAction extends AbstractGameAction {
    private String key;
    public EurobeatAction (String k){
        key = k;
    }

    public void update() {
        if (DrifterMod.config.getBool(EUROBEAT_ON)) {
            CardCrawlGame.music.fadeOutTempBGM();
            AbstractDungeon.getCurrRoom().playBGM(key);
        }
        this.isDone = true;
    }
}

