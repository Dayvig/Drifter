package DrifterMod.actions;

import DrifterMod.cards.AbstractDynamicCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.EndTurnDeathPower;

public class EqualizeAction extends AbstractGameAction {

    private int amount;
    private AbstractCreature player;

    public EqualizeAction(int magic, AbstractCreature target){
        amount = magic;
        player = target;
    }

    @Override
    public void update() {
        if (player == AbstractDungeon.player){
            int handDiff = BaseMod.MAX_HAND_SIZE - AbstractDungeon.player.hand.size();
            addToBot(new DrawCardAction(handDiff));
            addToBot(new GainEnergyAction(handDiff*amount));
            this.addToBot(new ApplyPowerAction(player, player, new EndTurnDeathPower(player)));
        }
        this.isDone = true;
    }
}
