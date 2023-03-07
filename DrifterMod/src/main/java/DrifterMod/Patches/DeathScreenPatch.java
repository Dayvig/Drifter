package DrifterMod.Patches;

import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.DeathScreen;

@SpirePatch(
        clz = DeathScreen.class,
        method = SpirePatch.CONSTRUCTOR)
public class DeathScreenPatch {
        @SpirePrefixPatch
        public static void DeathReset (DeathScreen __instance){
            System.out.println("Resetti");
            TheDrifter.startOfDrift = false;
            TheDrifter.drifting = false;
            CardCrawlGame.sound.stop("Racing", Speedup.racingID);
        }
    }
