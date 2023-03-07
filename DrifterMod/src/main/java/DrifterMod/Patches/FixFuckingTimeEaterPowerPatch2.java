/*package DrifterMod.Patches;

import DrifterMod.powers.DrawDownPower;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

public class FixFuckingTimeEaterPowerPatch2 {
    @SpirePatch(
            clz = DrawReductionPower.class,
            method = "onRemove")
    public static class Fix2 {
        @SpirePostfixPatch
        public static void PostFix(DrawReductionPower __instance) {
            if (__instance.owner.hasPower(DrawDownPower.POWER_ID)) {
                --AbstractDungeon.player.gameHandSize;
            }
        }
    }
}*/