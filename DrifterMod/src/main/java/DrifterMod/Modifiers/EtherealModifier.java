package DrifterMod.Modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EtherealModifier extends AbstractCardModifier {

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL Ethereal.";
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EtherealModifier();
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        if (card.isEthereal) {
            return false;
        }
        return true;
    }
}