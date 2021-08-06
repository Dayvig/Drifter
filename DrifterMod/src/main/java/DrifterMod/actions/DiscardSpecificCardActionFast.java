//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package DrifterMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardSpecificCardActionFast extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;

    public DiscardSpecificCardActionFast(AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.DISCARD;
        this.duration = 0.01f;
    }

    public DiscardSpecificCardActionFast(AbstractCard targetCard, CardGroup group) {
        this.targetCard = targetCard;
        this.group = group;
        this.actionType = ActionType.DISCARD;
        this.duration = 0.01f;
    }

    public void update() {
        if (this.duration == 0.01f) {
            if (this.group == null) {
                this.group = AbstractDungeon.player.hand;
            }

            if (this.group.contains(this.targetCard)) {
                this.group.moveToDiscardPile(this.targetCard);
                GameActionManager.incrementDiscard(false);
                this.targetCard.triggerOnManualDiscard();
            }
        }

        this.tickDuration();
    }
}
