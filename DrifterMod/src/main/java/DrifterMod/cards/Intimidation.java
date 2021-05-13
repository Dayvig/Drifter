package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.CoolPower;
import DrifterMod.powers.ZoomPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static DrifterMod.DrifterMod.makeCardPath;
import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;

// public class ${NAME} extends AbstractDynamicCard
public class Intimidation extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Intimidation.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_YELLOW;

    private static final int COST = 1;  // COST = ${COST}
    private static final int UPGRADED_COST = 0;

    // /STAT DECLARATION/


    public Intimidation() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(VulnerablePower.POWER_ID)) {
            int apply = p.getPower(VulnerablePower.POWER_ID).amount;
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying && !mo.halfDead) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, apply, false), apply));
                }
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
