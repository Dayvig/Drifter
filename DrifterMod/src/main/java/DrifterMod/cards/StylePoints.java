package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.TractionPower;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class StylePoints extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(StylePoints.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("StylePoints.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;  // COST = ${COST}

    private static final int MAGIC = 1;    // DAMAGE = ${DAMAGE}
    private static final int MAGIC2 = 2;
    private static final int UPGRADE_MAGIC = 1;

    // /STAT DECLARATION/


    public StylePoints() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = MAGIC2;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AnimateShakeAction(p, 0.05f, 0.05f));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, defaultSecondMagicNumber, false), defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DriftPower(p, p, magicNumber), magicNumber));
    }

    @Override
    public void applyPowers(){
        if (AbstractDungeon.player.hasPower(TractionPower.POWER_ID)){
            isMagicNumberModified = true;
            this.magicNumber = baseMagicNumber + AbstractDungeon.player.getPower(TractionPower.POWER_ID).amount;
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDefaultSecondMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
