package DrifterMod.potions;

import DrifterMod.DrifterMod;
import DrifterMod.powers.Speedup;
import DrifterMod.powers.TractionPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class TractionPotion extends AbstractPotion {


    public static final String POTION_ID = DrifterMod.makeID("TractionPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TractionPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DrifterMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.JAR, PotionEffect.NONE, Color.BLACK.cpy(), Color.GRAY.cpy(), null);
        this.labOutlineColor = DrifterMod.DRIFTER_BLUE;

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(DESCRIPTIONS[4], DESCRIPTIONS[2]));
        tips.add(new PowerTip(DESCRIPTIONS[5], DESCRIPTIONS[3]));

    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, target, new TractionPower(target, target, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new TractionPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    public void upgradePotion()
    {
        potency *= 2;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
