package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class EatMyDust extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(EatMyDust.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("overtake.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;  // COST = ${COST}
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    private static final int MAGIC2 = 3;
    // /STAT DECLARATION/


    public EatMyDust() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playAV("PassMed3", 0.5f + (float)Math.random()*0.2f, 1.5f);
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, MAGIC2, false));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        this.cantUseMessage = "I don't have enough speed!";
        return p.hand.size() >= 3;
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
