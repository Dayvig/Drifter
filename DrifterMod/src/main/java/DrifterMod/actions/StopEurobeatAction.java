package DrifterMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

public class StopEurobeatAction extends AbstractGameAction {

    public void update(){
        boolean isFightingLagavulin = false;
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom){
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
                if (mo.name.equals("Lagavulin")){
                    isFightingLagavulin = true;
                }
            }
        }
        if (isFightingLagavulin){
            System.out.println("test");
            CardCrawlGame.music.fadeOutTempBGM();
            CardCrawlGame.music.playTempBgmInstantly("ELITE");
        }
        else if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
            CardCrawlGame.music.fadeOutTempBGM();
            CardCrawlGame.music.unsilenceBGM();
        }
        this.isDone = true;
    }
}
