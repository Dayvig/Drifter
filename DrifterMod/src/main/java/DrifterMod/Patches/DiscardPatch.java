/*package DrifterMod.Patches;

import DrifterMod.DrifterMod;
import DrifterMod.cards.AeroChassis;
import DrifterMod.powers.AeroPower;
import DrifterMod.powers.InControlPower;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = DiscardAction.class,
        method = "update")
public class DiscardPatch {
    public static void Prefix(DiscardAction _instance) {
        if (AbstractDungeon.player.hasPower(InControlPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(_instance.amount));
            System.out.println("Called.");
        }
    }
}*/