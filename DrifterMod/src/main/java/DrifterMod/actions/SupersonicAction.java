package DrifterMod.actions;

import DrifterMod.powers.DriftPower;
import DrifterMod.powers.Speedup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SupersonicAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private boolean upgraded;

    public SupersonicAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgrade) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        upgraded = upgrade;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                this.addToBot(new SFXAction("PassMed2"));
                addToBot(new AnimateShakeAction(p, 0.1f, 0.1f));
                this.addToBot(new ApplyPowerAction(p, p, new Speedup(p, p, effect), effect));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}