package DrifterMod.Patches;

import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch2(clz = AbstractDungeon.class, method = "resetPlayer")
@SpirePatch2(clz = CardCrawlGame.class, method = "startOver")
public class StopLoopingPlz {
    @SpirePostfixPatch
    public static void plz() {
        TheDrifter.startOfDrift = false;
        TheDrifter.drifting = false;
        CardCrawlGame.sound.stop("Racing", Speedup.racingID);
        CardCrawlGame.sound.stop("Chime", Speedup.chimeID);
    }
}
