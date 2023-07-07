package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.Modifiers.EtherealModifier;
import DrifterMod.characters.TheDrifter;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.green.AfterImage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Dejavu extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Dejavu.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Dejavu.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.NONE;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;  // COST = ${COST}
    private AbstractCard cardToUse;
    private int numLeft;

    // /STAT DECLARATION/


    public Dejavu() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (cardToUse == null){
            cardToUse = new Madness().makeStatEquivalentCopy();
        }
        cardToUse.applyPowers();
        cardToUse.freeToPlayOnce = true;
        cardToUse.purgeOnUse = true;
        AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction(cardToUse, true));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = EXTENDED_DESCRIPTION[0];
        return AbstractDungeon.actionManager.cardsPlayedThisCombat.size() != 0;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() != 0) {
            AbstractCard tmp = AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1);
            if (tmp.name.equals(Dejavu.ID)){
                tmp = new AfterImage();
            }
            cardToUse = tmp.makeStatEquivalentCopy();
            if (upgraded){ cardToUse.upgrade(); }
            this.cardsToPreview = cardToUse;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
