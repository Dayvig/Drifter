package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

public class Burnout extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Burnout.class.getSimpleName());
    public static final String IMG = makeCardPath("Burnout.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/


    public Burnout() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("Screech", (float)Math.random()*0.3f);
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage,
                DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new AnimateShakeAction(p, 0.2f, 0.2f));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false));
        if (p.hasPower(Speedup.POWER_ID)){
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, p.getPower(Speedup.POWER_ID), magicNumber));
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
