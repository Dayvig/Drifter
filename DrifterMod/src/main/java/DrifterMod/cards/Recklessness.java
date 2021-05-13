package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.OverdrawNextTurn;
import DrifterMod.powers.TempMaxHandSizeInc;
import DrifterMod.powers.TempRetainPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Recklessness extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Recklessness.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_YELLOW;

    private static final int COST = 0;  // COST = ${COST}

    private static final int DAMAGE = 9;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public Recklessness() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDamage = damage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, true), magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
