package DrifterMod.actions;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.Speedup;
import basemod.ReflectionHacks;
import basemod.animations.AbstractAnimation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import static DrifterMod.DrifterMod.EUROBEAT_ON;

public class StopEurobeatAction extends AbstractGameAction {

    public void update(){
        if (DrifterMod.config.getBool(EUROBEAT_ON) && !AbstractDungeon.player.hasPower(DriftPower.POWER_ID)) {
            if (!AbstractDungeon.player.hasPower(Speedup.POWER_ID) ||
                    (AbstractDungeon.player.hasPower(Speedup.POWER_ID) && AbstractDungeon.player.getPower(Speedup.POWER_ID).amount < 3)) {
                boolean isFightingLagavulin = false;
                boolean isFightingHexaghost = false;
                if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                    for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (mo.name.equals("Lagavulin")) {
                            isFightingLagavulin = true;
                        }
                        if (mo.name.equals("Hexaghost")) {
                            isFightingHexaghost = true;
                        }
                    }
                }
                if (isFightingLagavulin) {
                    System.out.println("Laga");
                    CardCrawlGame.music.fadeOutTempBGM();
                    CardCrawlGame.music.playTempBgmInstantly("ELITE");
                } else if (isFightingHexaghost) {
                    System.out.println("Hexa");
                    CardCrawlGame.music.fadeOutTempBGM();
                    CardCrawlGame.music.playTempBgmInstantly("BOSS_BOTTOM");
                } else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                    CardCrawlGame.music.fadeOutTempBGM();
                    CardCrawlGame.music.unsilenceBGM();
                }

            TheDrifter.drifting = false;
            TheDrifter.startOfDrift = false;
            if (AbstractDungeon.player instanceof TheDrifter) {
                ((TheDrifter) AbstractDungeon.player).resetAnimation();
                }
            }
        }
        this.isDone = true;
    }
}
