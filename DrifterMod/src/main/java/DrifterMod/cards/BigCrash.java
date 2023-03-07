package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.animations.RamAnimation;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.TempMaxHandSizeInc;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class BigCrash extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(BigCrash.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Hypercrash.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 3;  // COST = ${COST}
    private static final int UPGRADED_COST = 2;
    private static final int MAGIC = 5;
    // /STAT DECLARATION/


    public BigCrash() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RamAnimation(p, m));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        CardCrawlGame.sound.playAV("BigCrash", -0.5f, 1.5f);
        for (int i = 0; i < damage/5; i++) {
            addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
        }
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, p.hand.size(), true));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, TempMaxHandSizeInc.POWER_ID));
    }

    public void applyPowers(){
        int k = AbstractDungeon.player.hand.size();
        if (AbstractDungeon.player.hasPower(TempMaxHandSizeInc.POWER_ID)){
            k += AbstractDungeon.player.getPower(TempMaxHandSizeInc.POWER_ID).amount;
        }
        baseDamage = k * magicNumber;
        initializeDescription();
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
