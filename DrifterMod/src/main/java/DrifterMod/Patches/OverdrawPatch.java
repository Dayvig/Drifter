package DrifterMod.Patches;

import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TempMaxHandSizeInc;
import DrifterMod.powers.ThermalPower;
import DrifterMod.relics.SteeringWheel;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static DrifterMod.characters.TheDrifter.Enums.THE_DRIFTER;


public class OverdrawPatch {
    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update")

    public static class Speed {
        public static void Prefix(DrawCardAction _instance) {
            if (AbstractDungeon.player.hasRelic(SteeringWheel.ID)) {
                if (_instance.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                    if (AbstractDungeon.player.hasPower(ThermalPower.POWER_ID)) {
                        ThermalPower pow = (ThermalPower) AbstractDungeon.player.getPower(ThermalPower.POWER_ID);
                        if (pow.numUsedThisTurn < pow.amount) {
                            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(1));
                            pow.numUsedThisTurn++;
                        }
                    }
                    if (AbstractDungeon.player.hasRelic(SteeringWheel.ID)) {
                        int s = (_instance.amount + AbstractDungeon.player.hand.size()) - BaseMod.MAX_HAND_SIZE;
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TempMaxHandSizeInc(AbstractDungeon.player, AbstractDungeon.player, s), s));
                        _instance.amount -= s;
                    }
                }
            }
        }
    }
}
