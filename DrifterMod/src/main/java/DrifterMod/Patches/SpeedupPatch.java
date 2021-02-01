/*
package DrifterMod.Patches;

import DrifterMod.actions.OverdrawCardAction;
import DrifterMod.interfaces.hasOverdrawTrigger;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TempMaxHandSizeInc;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.graalvm.compiler.lir.LIRInstruction;


public class SpeedupPatch {
    @SpirePatch(
            clz = DrawCardAction.class,
            method = "update")

    public static class Speed {
        public static void Prefix(DrawCardAction _instance) {
            if (AbstractDungeon.player.hasPower(Speedup.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new OverdrawCardAction(AbstractDungeon.player.getPower(Speedup.POWER_ID).amount));
            }
        }
    }
}*/


