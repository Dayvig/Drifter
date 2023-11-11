package DrifterMod.Patches;


import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import com.brashmonkey.spriter.Player;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;

@SpirePatch(
        clz = SaveAndContinue.class,
        method = "loadSaveFile",
        paramtypez = AbstractPlayer.PlayerClass.class)
public class SavePatch {
    @SpirePrefixPatch
    public static void SaveReset (AbstractPlayer.PlayerClass __class){
        TheDrifter.startOfDrift = false;
        TheDrifter.drifting = false;
        CardCrawlGame.sound.stop("Racing", Speedup.racingID);
        CardCrawlGame.sound.stop("Chime", Speedup.chimeID);
    }
}
