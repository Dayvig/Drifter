package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static DrifterMod.DrifterMod.makeCardPath;

public class Fierce extends AbstractDynamicCard {

    public static final String ID = DrifterMod.makeID(Fierce.class.getSimpleName());
    public static final String IMG = makeCardPath("Fierce.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final AbstractCard.CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_DAMAGE = 2;

    private int nonAdjustedDamage = DAMAGE;

    public Fierce(){
        super (ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        nonAdjustedDamage = baseDamage;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i=0;i<magicNumber;i++) {
            if (!m.isDying && !m.isDead && !m.halfDead) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
            }
        }
    }

    @Override
    public void applyPowers(){
        if (AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)){
            baseDamage = nonAdjustedDamage;
            baseDamage += AbstractDungeon.player.getPower(VulnerablePower.POWER_ID).amount;
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }

}
