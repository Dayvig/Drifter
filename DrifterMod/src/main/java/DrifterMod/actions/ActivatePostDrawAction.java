package DrifterMod.actions;

import DrifterMod.powers.CoolPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ActivatePostDrawAction extends AbstractGameAction {

    public ActivatePostDrawAction(){

    }

    public void update(){
        if (AbstractDungeon.player.hasPower(CoolPower.POWER_ID)){
            CoolPower c = (CoolPower)AbstractDungeon.player.getPower(CoolPower.POWER_ID);
            c.isActive = true;
        }
        isDone = true;
    }

}
