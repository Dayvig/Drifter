package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.DriftingPower;
import DrifterMod.powers.TractionPower;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static DrifterMod.DrifterMod.makeCardPath;

public class OneThirtyFive extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Hold Place Gain 1(2) Keywords(s).
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(OneThirtyFive.class.getSimpleName());
    public static final String IMG = makeCardPath("135deg.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;
    public static final int UPGRADE_MAGIC = 3;
    private static final int MAGIC = 5;
    private static final int COST = 2;

    // Hey want a second magic/damage/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and theDefault.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variabls they personally have and create your own ones.

    // /STAT DECLARATION/


    public OneThirtyFive() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DriftPower(p,p,magicNumber), magicNumber));
        addToBot(new AnimateShakeAction(p, 0.2f, 0.2f));
    }

    @Override
    public void applyPowers() {
        int total = baseMagicNumber;

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)){
            total += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)){
            total += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(TractionPower.POWER_ID)){
            total += AbstractDungeon.player.getPower(TractionPower.POWER_ID).amount;
        }
        magicNumber = total;
        if (total != baseMagicNumber) {
            isMagicNumberModified = true;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}