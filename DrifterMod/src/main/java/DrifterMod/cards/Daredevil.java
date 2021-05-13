package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.DriftPower;
import DrifterMod.powers.OverdrawNextTurn;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TempMaxHandSizeInc;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.ArrayList;

import static DrifterMod.DrifterMod.makeCardPath;

public class Daredevil extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Daredevil.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_YELLOW;

    private static final int COST = 1;
    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DAMAGE = 4;


    // /STAT DECLARATION/


    public Daredevil() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void applyPowers(){
        if (AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)){
            this.exhaust = false;
        }
        else {
            this.exhaust = true;
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_DAMAGE);
        initializeDescription();
    }
}
