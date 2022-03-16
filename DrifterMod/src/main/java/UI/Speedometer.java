package UI;

import DrifterMod.powers.Speedup;
import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;



public class Speedometer extends CustomEnergyOrb implements EnergyOrbInterface {
    public static final String[] orbTextures = {
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
    };

    //private Hitbox tipHornet = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);

    private final String droneLabel = "The number of Drones in your draw pile.";
    private static final float DRIFTER_ORB_SCALE;

    private static final String VFXTexture = ("DrifterModResources/images/char/defaultCharacter/orb/vfx.png");
    private static final String NeedleTexture = ("DrifterModResources/images/char/defaultCharacter/orb/needle.png");
    public Texture Needle;
    private static final float[] layerSpeeds = new float[]{10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    private float needleTimer = 0.0f;
    public static float needleRot = 100.0f;
    private float jumpInterval = 1.0f;
    private float jumpAmount = 1;
    private float currentAngle = 100.0f;
    private float jumpReturn = 1.2f;

    public Speedometer() {
        super(orbTextures, VFXTexture, layerSpeeds);
        Needle = loadImage(NeedleTexture);
    }


    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
        if (AbstractDungeon.player.hasPower(Speedup.POWER_ID)){
            int speedupAmount = AbstractDungeon.player.getPower(Speedup.POWER_ID).amount;
            int realSpeedup = speedupAmount;
            if (speedupAmount > 7){
                speedupAmount = 7;
            }
            needleRot = 100.0f - speedupAmount * 30.0f;
            jumpAmount = 1.0f + speedupAmount * 0.2f;
        }
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean active, float current_x, float current_y) {
        /*if (this.tipDrone.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Drone Counter", droneLabel);
        }*/

        float offsetX = 85.0f;
        float offsetY = 85.0f;

        if (active) {
            sb.draw(energyLayers[0],
                    current_x - offsetX,
                    current_y - offsetY,
                    offsetX,
                    offsetY,
                    166.0F,
                    166.0F,
                    DRIFTER_ORB_SCALE,
                    DRIFTER_ORB_SCALE,
                    0.0F, 0, 0, 166, 166, false, false);
        } else {
            sb.draw(noEnergyLayers[0],
                    current_x - offsetX,
                    current_y - offsetX,
                    offsetX,
                    offsetY,
                    166.0F,
                    166.0F,
                    DRIFTER_ORB_SCALE,
                    DRIFTER_ORB_SCALE,
                    0.0F, 0, 0, 166, 166, false, false);
        }

        AbstractDungeon.player.getEnergyNumFont().getData().setScale(1.0F);
        /*tipHornet.x = current_x-(60.0F * DRIFTER_ORB_SCALE); tipHornet.y = current_y +(40.0F * DRIFTER_ORB_SCALE);
        tipBumble.x = current_x-(85.0F * DRIFTER_ORB_SCALE); tipBumble.y = current_y +(8.0F * DRIFTER_ORB_SCALE);
        tipWorker.x = current_x-(60.0F * DRIFTER_ORB_SCALE); tipWorker.y = current_y -(32.0F * DRIFTER_ORB_SCALE);
        tipDrone.x = current_x-(105.0F * DRIFTER_ORB_SCALE); tipDrone.y = current_y -(32.0F * DRIFTER_ORB_SCALE);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[0]+"", current_x-(45.0F * DRIFTER_ORB_SCALE), current_y+(62.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[1]+"", current_x-(70.0F * DRIFTER_ORB_SCALE), current_y+(20.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[3]+"", current_x-(45.0F * DRIFTER_ORB_SCALE), current_y-(20.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[2]+"", current_x-(95.0F * DRIFTER_ORB_SCALE), current_y-(20.0F * DRIFTER_ORB_SCALE));
        */
        drawNeedle(sb, current_x, current_y, offsetX, offsetY);

    }

    private void drawNeedle(SpriteBatch sb, float current_x, float current_y, float offX, float offY){
        float offsetX = offX;
        float offsetY = offY;

        needleTimer++;
        if (needleTimer > jumpInterval){
            currentAngle = currentAngle + ((float)(Math.random() * jumpAmount * 2) - jumpAmount);
            if (AbstractDungeon.player.hasPower(Speedup.POWER_ID) && AbstractDungeon.player.getPower(Speedup.POWER_ID).amount > 7){
                currentAngle -= (float)(Math.random() * jumpAmount * (AbstractDungeon.player.getPower(Speedup.POWER_ID).amount - 7));
                jumpReturn = 1.2f * (AbstractDungeon.player.getPower(Speedup.POWER_ID).amount - 7);
            }
            needleTimer -= jumpInterval;
        }
        if (currentAngle > needleRot){
            currentAngle -= jumpReturn;
        }
        else {
            currentAngle += jumpReturn;
        }
        if (currentAngle - needleRot < 0.2f && currentAngle - needleRot > -0.2f){
            currentAngle = needleRot;
        }

        sb.draw(Needle,
                current_x - offsetX,
                current_y - offsetY,
                offsetX,
                offsetY,
                166.0F,
                166.0F,
                DRIFTER_ORB_SCALE,
                DRIFTER_ORB_SCALE,
                currentAngle, 0, 0, 166, 166, false, false);
    }

    static {
        DRIFTER_ORB_SCALE = 1.15F * Settings.scale;
    }
}