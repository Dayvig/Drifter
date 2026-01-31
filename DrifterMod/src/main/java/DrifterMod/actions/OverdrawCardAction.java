//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package DrifterMod.actions;

import DrifterMod.characters.TheDrifter;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OverdrawCardAction extends DrawCardAction {

    public OverdrawCardAction(AbstractCreature source, int amount) {
        super(source, amount, false);
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = 0.02f;
        } else {
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        }
    }

    public void update() {
        if (AbstractDungeon.overlayMenu.endTurnButton.enabled){
            TheDrifter.drawnCardsThisTurn += amount;
        }
        super.update();
    }
}
