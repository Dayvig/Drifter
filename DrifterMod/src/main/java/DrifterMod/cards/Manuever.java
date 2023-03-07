package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.*;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static DrifterMod.DrifterMod.makeCardPath;

public class Manuever extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = DrifterMod.makeID(Manuever.class.getSimpleName());
    public static final String IMG = makeCardPath("Manuever.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    // /STAT DECLARATION/


    public Manuever() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TractionPower(p, p, 2), 2));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TractionDownPower(p, p, 2), 2));
        addToBot(new AnimateShakeAction(p, 0.15f, 0.15f));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        upgradeName();
        upgradeMagicNumber(UPGRADE_MAGIC);
        upgradeBlock(UPGRADE_PLUS_BLOCK);
        initializeDescription();
    }
}
