package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.TempMaxHandSizeInc;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

public class NightsOnFire extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(NightsOnFire.class.getSimpleName());
    public static final String IMG = makeCardPath("NightsOfFire.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;


    // /STAT DECLARATION/


    public NightsOnFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new AnimateFastAttackAction(p));
    }

    @Override
    public void applyPowers(){
        if (AbstractDungeon.player.hasPower(TempMaxHandSizeInc.POWER_ID)) {
            baseDamage = damage = defaultBaseSecondMagicNumber + (magicNumber * AbstractDungeon.player.getPower(TempMaxHandSizeInc.POWER_ID).amount);
        }
        else {
            baseDamage = damage = defaultBaseSecondMagicNumber;
        }
        initializeDescription();
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (AbstractDungeon.player.hasPower(TempMaxHandSizeInc.POWER_ID)) {
            baseDamage = damage = defaultBaseSecondMagicNumber + (magicNumber * AbstractDungeon.player.getPower(TempMaxHandSizeInc.POWER_ID).amount);
        } else {
            baseDamage = damage = defaultBaseSecondMagicNumber;
        }
        initializeDescription();
        super.calculateCardDamage(mo);
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_DAMAGE);
        upgradeMagicNumber(UPGRADE_MAGIC);
        initializeDescription();
    }
}
