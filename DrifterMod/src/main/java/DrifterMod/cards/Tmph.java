package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TempMaxHandSizeInc;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class Tmph extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Tmph.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("MPH.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.


    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;  // COST = ${COST}
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private static final int MAGIC = 2;
    // /STAT DECLARATION/


    public Tmph() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("PassFast", (float)Math.random()*0.5f);
        if (p.hasPower(TempMaxHandSizeInc.POWER_ID) && magicNumber > 10) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, p.getPower(TempMaxHandSizeInc.POWER_ID), magicNumber - 10));
        }
        addToBot(new VFXAction(new WhirlwindEffect(Color.WHITE, true)));
        AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, magicNumber, false));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage,
                    DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        super.canUse(p, m);
        this.cantUseMessage = "I don't have enough speed!";
        if (p.hasPower(TempMaxHandSizeInc.POWER_ID)){
            return magicNumber <= p.hand.size() + p.getPower(TempMaxHandSizeInc.POWER_ID).amount;
        }
        else {
            return magicNumber <= p.hand.size();
        }
    }

    @Override
    public void applyPowers(){
        if (AbstractDungeon.player.hasPower(Speedup.POWER_ID)){
            baseMagicNumber = magicNumber = (MAGIC * AbstractDungeon.player.getPower(Speedup.POWER_ID).amount) + MAGIC;
            baseDamage = damage = DAMAGE * (2 * AbstractDungeon.player.getPower(Speedup.POWER_ID).amount);
            this.name = ((AbstractDungeon.player.getPower(Speedup.POWER_ID).amount * 20) + 20) + " MPH";
        }
        else
        {
            baseMagicNumber = magicNumber = MAGIC;
            baseDamage = damage = DAMAGE;
            this.name = "20 MPH";
        }

        initializeDescription();
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
