package DrifterMod.powers;

import DrifterMod.DrifterMod;
import DrifterMod.Patches.BeyondScenePatch;
import DrifterMod.actions.ChangeParalaxSpeedAction;
import DrifterMod.actions.EurobeatAction;
import DrifterMod.characters.TheDrifter;
import DrifterMod.effects.SpeedParticleEffect;
import DrifterMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;

import java.util.ArrayList;

import static DrifterMod.DrifterMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class Speedup extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DrifterMod.makeID("Speedup");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("speed84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("speed32.png"));
    public int onlyOnce;
    private float particleTimer;
    private float particleInterval;
    private float racingTimer = 12f;
    public Color windColor = Color.WHITE.cpy();
    private int count = 0;
    public static long racingID;
    private static final int SPEEDPENALTY1 = 3;
    private static final int SPEEDPENALTY2 = 5;
    private static final int SPEEDPENALTY3 = 7;


    public Speedup(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public Speedup(final AbstractCreature owner, final AbstractCreature source, final int amount, int k) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        onlyOnce = k;
        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.loadRegion("flex");

        updateDescription();
    }

    @Override
    public void onInitialApplication(){
        racingID = CardCrawlGame.sound.playAndLoop("Racing");
        float totalSpeed = (this.amount * 0.25f) + 0.75f;
        if (BeyondScenePatch.bg_controller != null) {
            addToBot(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, totalSpeed));
        }
    }

    @Override
    public void stackPower(int stackAmount){
        super.stackPower(stackAmount);
        float totalSpeed = (this.amount * 0.25f) + 0.75f;
        if (BeyondScenePatch.bg_controller != null) {
            addToBot(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, totalSpeed));
        }
    }

    @Override
    public void onRemove(){
        CardCrawlGame.sound.fadeOut("Racing", racingID);
        if (BeyondScenePatch.bg_controller != null) {
            addToBot(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, 0.75f));
        }
    }

    @Override
    public void onVictory(){
        CardCrawlGame.sound.fadeOut("Racing", racingID);
    }


    @Override
    public void atStartOfTurnPostDraw(){
        int drawAmount = this.amount;
        if (AbstractDungeon.player.hasPower(DrawDownPower.POWER_ID)){
            DrawDownPower tmp = (DrawDownPower) AbstractDungeon.player.getPower(DrawDownPower.POWER_ID);
            if (tmp.actualDrawReduction > TheDrifter.CARD_DRAW)
            drawAmount -= tmp.actualDrawReduction - TheDrifter.CARD_DRAW;
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(drawAmount));
            int adjustedAmount = this.amount;
            if (this.owner.hasPower(CruiseControlPower.POWER_ID)){
                adjustedAmount -= this.owner.getPower(CruiseControlPower.POWER_ID).amount;
            }
            if (adjustedAmount > 2){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, 1, true),1));
            }
            if (adjustedAmount > 4){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, -2),-2));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new DexterityNextTurnPower(this.owner, this.owner, 2),2));
            }
        if (adjustedAmount > 6){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new TractionPower(this.owner, this.owner, -2),-2));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new TractionRestorePower(this.owner, this.owner, 2),2));
        }
    }

    @Override
    public void updateParticles(){
        particleTimer += Gdx.graphics.getDeltaTime();
        racingTimer += Gdx.graphics.getDeltaTime();
        if (!Settings.DISABLE_EFFECTS) {
            if (this.amount >= 2) {
                particleInterval = (0.2f / (float) (this.amount / 2));
            } else {
                particleInterval = 0.2f;
            }
            if (particleTimer >= particleInterval) {
                AbstractDungeon.effectsQueue.add(new SpeedParticleEffect(windColor, true, 1f + 1f / (float) this.amount / 4));
                particleTimer = 0f;
                count++;
                if (count >= 10) {
                    AbstractDungeon.effectsQueue.add(new BorderFlashEffect(flashColor(this.amount)));
                    count = 0;
                }
            }
        }
    }

    private Color flashColor(int amount){
        if (amount < SPEEDPENALTY1){
            return windColor.cpy();
        }
        else if (amount < SPEEDPENALTY2){
            return Color.RED.cpy();
        }
        else if (amount < SPEEDPENALTY3){
            return Color.GREEN.cpy();
        }
        else {
            return Color.CYAN.cpy();
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + " NL "+ DESCRIPTIONS[3] + " NL "+ DESCRIPTIONS[4] + " NL "+ DESCRIPTIONS[5];
        } else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + " NL "+ DESCRIPTIONS[3] + " NL "+ DESCRIPTIONS[4] + " NL "+ DESCRIPTIONS[5];
        }
    }


    @Override
    public AbstractPower makeCopy() {
        return new Speedup(owner, source, amount);
    }
}
