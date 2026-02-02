package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.TempMaxHandSizeInc;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static DrifterMod.DrifterMod.makeCardPath;

public class DaringStunt extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(DaringStunt.class.getSimpleName());
    public static final String IMG = makeCardPath("DaringStunt.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String DESC = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;
    private static final int DAMAGE = 50;
    private static final int UPGRADE_PLUS_DAMAGE = 16;
    private static final int MAGIC = 4;
    private static final int DISCARDS = 12;
    int totalHand;


    // /STAT DECLARATION/


    public DaringStunt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = DISCARDS;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.playA("PassFast", (float)Math.random()*0.8f);
        applyPowers();
        if (defaultSecondMagicNumber > 0) {
            addToBot(new DiscardAction(p, p, defaultSecondMagicNumber, false));
        }
        calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    public void applyPowers(){
        totalHand = AbstractDungeon.player.hand.size();
        if (AbstractDungeon.player.hasPower(TempMaxHandSizeInc.POWER_ID)){
            totalHand += AbstractDungeon.player.getPower(TempMaxHandSizeInc.POWER_ID).amount;
        }
        int reduction = 0;
        if (AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)){
            reduction = AbstractDungeon.player.getPower(VulnerablePower.POWER_ID).amount * magicNumber;
        }
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = DISCARDS - reduction;
        if (defaultBaseSecondMagicNumber < 0){
            defaultBaseSecondMagicNumber = defaultSecondMagicNumber = 0;
            this.rawDescription = EXTENDED_DESCRIPTION[0];
        }
        else {
            this.rawDescription = DESC;
        }
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m){
        this.cantUseMessage = EXTENDED_DESCRIPTION[1];
        applyPowers();
        return super.canUse(p, m) && totalHand > defaultSecondMagicNumber;
    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(UPGRADE_PLUS_DAMAGE);
        initializeDescription();
    }
}
