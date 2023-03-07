package DrifterMod.cards;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import DrifterMod.powers.Speedup;
import basemod.abstracts.CustomCard;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.List;

import static DrifterMod.DrifterMod.makeCardPath;

public class GearShift extends AbstractDynamicCard implements ModalChoice.Callback
{
    public static final String NAME = "Gear Shift";
    public static final String ID = DrifterMod.makeID(GearShift.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("GearShift.png");// "public static final String IMG = makeCardPath("${NAME}.png");
    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = TheDrifter.Enums.COLOR_DARKBLUE;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final int COST = 1;
    private ModalChoice modal;

    private final ArrayList<AbstractCard> cardToPreview = new ArrayList<>();
    private float rotationTimer;
    private int previewIndex = 0;

    protected float getRotationTimeNeeded() {
        return 3.0F;
    }

    @Override
    public void update() {
        super.update();
        if (!cardToPreview.isEmpty() && AbstractDungeon.actionManager.isEmpty()) {
            if (hb.hovered) {
                if (rotationTimer <= 0F) {
                    rotationTimer = getRotationTimeNeeded();
                    if (previewIndex == cardToPreview.size() - 1) {
                        previewIndex = 0;
                    } else {
                        previewIndex++;
                    }
                    if (previewIndex >= cardToPreview.size()){
                        previewIndex = cardToPreview.size()-1;
                    }
                    cardsToPreview = cardToPreview.get(previewIndex);
                } else {
                    rotationTimer -= Gdx.graphics.getDeltaTime();
                }
            }
        }
    }


    public GearShift() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardToPreview.add(new GearUp());
        this.cardToPreview.add(new GearDown());
        if (!upgraded) {
            modal = new ModalChoiceBuilder()
                    .setCallback(this) // Sets callback of all the below options to this
                    .setColor(CardColor.GREEN) // Sets color of any following cards to red
                    .addOption(new GearUp())
                    .setColor(CardColor.RED) // Sets color of any following cards to green
                    .addOption(new GearDown())
                    .create();
        }
    }
    
    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return modal.generateTooltips();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
            modal.open();
    }

    // This is called when one of the option cards us chosen
    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        switch (i){
            case 0:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, -2), -2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new Speedup(p, p, 1), 1));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -2), -2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber), magicNumber));
                if (p.hasPower(Speedup.POWER_ID)){
                    AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
                    AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, new Speedup(p, p, 1), 1));
                }
                break;
            default:
        }
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;

                AbstractCard c = new GearUp();
                c.upgrade();
                AbstractCard d = new GearDown();
                d.upgrade();
                modal = new ModalChoiceBuilder()
                        .setCallback(this) // Sets callback of all the below options to this
                        .setColor(CardColor.GREEN) // Sets color of any following cards to red
                        .addOption(c)
                        .setColor(CardColor.RED) // Sets color of any following cards to green
                        .addOption(d)
                        .create();
            this.cardToPreview.clear();
            this.cardToPreview.add(c);
            this.cardToPreview.add(d);
            modal.generateTooltips();
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new GearShift();
    }
}