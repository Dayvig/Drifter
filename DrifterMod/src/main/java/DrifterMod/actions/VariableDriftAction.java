package DrifterMod.actions;

import DrifterMod.powers.DriftPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

public class VariableDriftAction  extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private int driftAmount;
    private boolean upgraded;

    public VariableDriftAction(AbstractPlayer p, int drift, boolean freeToPlayOnce, int energyOnUse, boolean upgrade) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        driftAmount = drift;
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
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new SFXAction("PassMed2"));
                addToBot(new AnimateShakeAction(p, 0.1f, 0.1f));
                this.addToBot(new ApplyPowerAction(p, p, new DriftPower(p, p, driftAmount), driftAmount));
            }
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
