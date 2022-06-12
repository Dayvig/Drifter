package DrifterMod.powers;

import DrifterMod.DrifterMod;
import DrifterMod.actions.EurobeatAction;
import DrifterMod.actions.StopEurobeatAction;
import DrifterMod.characters.TheDrifter;
import DrifterMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

import java.util.ArrayList;

import static DrifterMod.DrifterMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DriftPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DrifterMod.makeID("DriftPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    public boolean hasTraction;

    public DriftPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        hasTraction = false;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.loadRegion("flex");

        updateDescription();
    }

    public DriftPower(final AbstractCreature owner, final AbstractCreature source, final int amount, boolean t) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        hasTraction = t;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.loadRegion("flex");

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }


    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        if (!this.owner.equals(AbstractDungeon.player)){ return; }

        DriftDamage();

        if (this.owner.hasPower(DriftingPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, DriftingPower.POWER_ID));
            int d = this.owner.getPower(DriftPower.POWER_ID).amount / 2;
            if (d > 0){
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, DriftPower.POWER_ID, d));
            }
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, DriftPower.POWER_ID));
            return;
        }
    }

    @Override
    public void onInitialApplication() {
        if (!this.owner.hasPower(DriftingPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DriftingPower(this.owner, this.owner, 1),1));
        }
        if (this.owner.hasPower(TractionPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DriftPower(this.owner, this.owner, this.owner.getPower(TractionPower.POWER_ID).amount, true), this.owner.getPower(TractionPower.POWER_ID).amount));
        }/*
                switch (TheDrifter.r){
            case 0:
                AbstractDungeon.actionManager.addToBottom(new EurobeatAction("Gas"));
                return;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new EurobeatAction("NightFire"));
                return;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new EurobeatAction("Dejavu"));
                return;
            default:
        }*/
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        super.onApplyPower(power, target, source);
        if (power.ID.equals(DriftPower.POWER_ID)){
            //Applies drifting if it doesn't already exist
            if (!this.owner.hasPower(DriftingPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DriftingPower(this.owner, this.owner, 1),1));
                //plays eurobeat if it doesn't already exist
                AbstractDungeon.actionManager.addToBottom(new EurobeatAction(TheDrifter.returnDriftKey()));
            }
            //Applies an extra stack of Drift that doesn't trigger this effect again if you have extra traction.
            if (this.owner.hasPower(TractionPower.POWER_ID)){
                DriftPower tmp = (DriftPower)power;
                if (!tmp.hasTraction) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DriftPower(this.owner, this.owner, this.owner.getPower(TractionPower.POWER_ID).amount, true),this.owner.getPower(TractionPower.POWER_ID).amount));
                }
            }
        }
    }

    @Override
    public void onRemove(){
        if (this.owner.hasPower(DriftStrengthDownPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, -this.owner.getPower(DriftStrengthDownPower.POWER_ID).amount), -this.owner.getPower(DriftStrengthDownPower.POWER_ID).amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, DriftStrengthDownPower.POWER_ID));
        }
        AbstractDungeon.actionManager.addToBottom(new StopEurobeatAction());
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    private void DriftDamage(){
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
        ArrayList<AbstractMonster> m = AbstractDungeon.getCurrRoom().monsters.monsters;

        if ( this.owner.hasPower(DriftSweepPower.POWER_ID) ) {
            int[] tmp = new int[m.size()];
            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = this.amount;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, tmp,
                    DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        }
        else {
        AbstractMonster lowest = m.get(0);
        for (AbstractMonster i : m){
            if (lowest.isDead || lowest.isDying || lowest.halfDead) {
                lowest = i;
            }
            if (!i.halfDead && !i.isDying && !i.isDead) {
                if (i.currentHealth < lowest.currentHealth) {
                    lowest = i;
                } else if (i.currentHealth == lowest.currentHealth) {
                    if (Math.random() * 2 > 1) {
                        lowest = i;
                    }
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(lowest,
                new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DriftPower(owner, source, amount);
    }
}
