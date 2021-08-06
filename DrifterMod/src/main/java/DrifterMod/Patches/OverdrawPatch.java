package DrifterMod.Patches;

import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TempMaxHandSizeInc;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class OverdrawPatch {
    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update")

    public static class Speed {
        public static void Prefix(DrawCardAction _instance) {
            if (_instance.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
                int s = (_instance.amount + AbstractDungeon.player.hand.size()) - BaseMod.MAX_HAND_SIZE;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TempMaxHandSizeInc(AbstractDungeon.player, AbstractDungeon.player, s), s));
                _instance.amount -= s;
            }
        }
    }
}
