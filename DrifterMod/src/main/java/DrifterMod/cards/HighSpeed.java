package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TractionPower;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.FastShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

public class HighSpeed extends AbstractDriftCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(HighSpeed.class.getSimpleName());
    public static final String IMG = makeCardPath("SpeedDrifting.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 2;
    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;
    private static final int MAGIC2 = 1;


    // /STAT DECLARATION/


    public HighSpeed() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = MAGIC2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DriftPower(p, p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Speedup(p, p, defaultSecondMagicNumber), defaultSecondMagicNumber));
        addToBot(new FastShakeAction(p, 0.05f, 0.05f));
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(UPG_MAGIC);
        initializeDescription();
    }
}
