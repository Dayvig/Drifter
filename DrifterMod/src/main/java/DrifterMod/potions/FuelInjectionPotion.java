package DrifterMod.potions;

import DrifterMod.DrifterMod;
import DrifterMod.powers.Speedup;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class FuelInjectionPotion extends AbstractPotion {


    public static final String POTION_ID = DrifterMod.makeID("FuelInjectionPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public FuelInjectionPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DrifterMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.S, PotionColor.SMOKE);
        this.labOutlineColor = DrifterMod.DRIFTER_BLUE;

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(DESCRIPTIONS[3], DESCRIPTIONS[2]));
    }

    public void initializeData() {
        this.potency = this.getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        this.tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(DESCRIPTIONS[3], DESCRIPTIONS[2]));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, new Speedup(target, target, potency), potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FuelInjectionPotion();
    }

    // This is your potency.
    public int getPotency(int potency) {
        return 1;
    }

    public void upgradePotion()
    {
        potency += 1;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
