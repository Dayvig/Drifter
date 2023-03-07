package DrifterMod.Patches;

import DrifterMod.powers.DrawDownPower;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

public class FixFuckingTimeEaterPowerPatch {
    @SpirePatch(
            clz = DrawReductionPower.class,
            method = "onInitialApplication")
    public static class Fix {
        @SpirePostfixPatch
        public static void PostFix(DrawReductionPower __instance) {
            if (__instance.owner.hasPower(DrawDownPower.POWER_ID)) {
                ++AbstractDungeon.player.gameHandSize;
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(__instance.owner, __instance.owner, __instance));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance.owner, __instance.owner, new DrawDownPower(__instance.owner, __instance.owner, __instance.amount)));
            }
        }
    }
}

