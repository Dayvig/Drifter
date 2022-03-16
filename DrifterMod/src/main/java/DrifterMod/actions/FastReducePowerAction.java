package DrifterMod.actions;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FastReducePowerAction extends ReducePowerAction {

    public FastReducePowerAction(AbstractCreature target, AbstractCreature source, String power, int amount) {
        super(target, source, power, amount);
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = 0.02f;
        } else {
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        }
    }

    public FastReducePowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerInstance, int amount) {
        super(target, source, powerInstance, amount);
        if (Settings.FAST_MODE) {
            this.duration = this.startDuration = 0.002f;
        } else {
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
        }
    }

}
