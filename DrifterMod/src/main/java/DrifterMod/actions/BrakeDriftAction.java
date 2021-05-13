//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package DrifterMod.actions;

import DrifterMod.powers.DriftPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class BrakeDriftAction extends AbstractGameAction {
    private AbstractPlayer p;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private boolean notchip;

    public BrakeDriftAction(AbstractCreature source) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = false;
    }

    public BrakeDriftAction(AbstractCreature source, boolean notChip) {
        this.setValues(AbstractDungeon.player, source, -1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.notchip = notChip;
    }

    public void update() {
        if (this.duration == 0.5F) {
            if (this.notchip) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            }

            this.addToBot(new WaitAction(0.25F));
            this.tickDuration();
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                    this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DriftPower(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()*2), AbstractDungeon.handCardSelectScreen.selectedCards.group.size()*2));
                    Iterator var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

                    while(var1.hasNext()) {
                        AbstractCard c = (AbstractCard)var1.next();
                        AbstractDungeon.player.hand.moveToDiscardPile(c);
                        GameActionManager.incrementDiscard(false);
                        c.triggerOnManualDiscard();
                    }
                }

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GamblingChipAction");
        TEXT = uiStrings.TEXT;
    }
}
