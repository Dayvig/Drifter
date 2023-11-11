package DrifterMod.powers;

import DrifterMod.DrifterMod;
import DrifterMod.actions.FastGainBlockAction;
import DrifterMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnCardDrawPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static DrifterMod.DrifterMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class FireTrailPower extends TwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DrifterMod.makeID("FireTrailPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("firetrail84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("firetrail32.png"));

    public FireTrailPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        int flameDamage = AbstractDungeon.player.hand.size();
        if (this.owner.hasPower(TempMaxHandSizeInc.POWER_ID)){
            flameDamage += this.owner.getPower(TempMaxHandSizeInc.POWER_ID).amount;
        }
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(flameDamage * this.amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void onDrawOrDiscard(){
        int flameDamage = AbstractDungeon.player.hand.size();
        if (this.owner.hasPower(TempMaxHandSizeInc.POWER_ID)){
            flameDamage += this.owner.getPower(TempMaxHandSizeInc.POWER_ID).amount;
        }
        this.amount2 = flameDamage;
        this.updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + amount2 + DESCRIPTIONS[2];
        } else if (amount > 1) {
            description = DESCRIPTIONS[3] + amount + DESCRIPTIONS[1] + amount2 * amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new FireTrailPower(owner, source, amount);
    }
}
