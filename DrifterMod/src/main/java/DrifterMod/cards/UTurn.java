package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DamageNextTurnPower;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.DriftingPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import java.util.ArrayList;

import static DrifterMod.DrifterMod.makeCardPath;
import static basemod.helpers.BaseModCardTags.BASIC_DEFEND;
import static basemod.helpers.BaseModCardTags.BASIC_STRIKE;

// public class ${NAME} extends AbstractDynamicCard
public class UTurn extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(UTurn.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Attack.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_YELLOW;

    private static final int COST = 3;  // COST = ${COST}
    private static final int UPGRADED_COST = 2;
    // /STAT DECLARATION/


    public UTurn() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(DriftPower.POWER_ID)){

            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, p.getPower(DriftPower.POWER_ID).amount));
            ArrayList<AbstractMonster> mo = AbstractDungeon.getCurrRoom().monsters.monsters;
            int[] tmp = new int[mo.size()];
            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = p.getPower(DriftPower.POWER_ID).amount;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, tmp, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, p.getPower(DriftPower.POWER_ID).amount)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, DriftPower.POWER_ID));
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
