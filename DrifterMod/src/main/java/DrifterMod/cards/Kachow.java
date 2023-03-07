package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

import static DrifterMod.DrifterMod.makeCardPath;

public class Kachow extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Kachow.class.getSimpleName());
    public static final String IMG = makeCardPath("flash.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int DAMAGE = 3;
    private static final int MAGIC = 1;


    // /STAT DECLARATION/


    public Kachow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BorderLongFlashEffect(Color.WHITE.cpy(), true)));
        addToBot(new WaitAction(0.05f));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage,
                DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.LIGHTNING));
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters){
            if (!mo.isDead && !mo.isDying && !mo.halfDead) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeBaseCost(UPGRADED_COST);
        initializeDescription();
    }
}
