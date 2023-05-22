package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.interfaces.OnRefreshHandCard;
import DrifterMod.powers.TempMaxHandSizeInc;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

// public class ${NAME} extends AbstractDynamicCard
public class PolePosition extends AbstractDynamicCard implements OnRefreshHandCard {

    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(PolePosition.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("PolePosition.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;  // COST = ${COST}

    private static final int BLOCK = 8;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int DAMAGE = 8;    // DAMAGE = ${DAMAGE}
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private boolean prevFree;
    private boolean changed;

    // /STAT DECLARATION/


    public PolePosition() { // public ${NAME}() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseDamage = damage = DAMAGE;
        prevFree = freeToPlayOnce;
        changed = false;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            CardCrawlGame.sound.playA("Revv2", (float)Math.random()*0.4f);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public void OnRefreshHand() {
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE){
            if (!changed){
                prevFree = freeToPlayOnce;
                changed = true;
                freeToPlayOnce = true;
            }
        }
        else {
            if (changed) {
                freeToPlayOnce = prevFree;
                changed = false;
            }
        }
        initializeDescription();
    }
}
