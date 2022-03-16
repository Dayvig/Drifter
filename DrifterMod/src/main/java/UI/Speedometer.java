package UI;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;


public class Speedometer extends CustomEnergyOrb implements EnergyOrbInterface {
    public static final String[] orbTextures = {
            "DrifterModResources/images/char/defaultCharacter/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb1.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",
            "QueenModResources/images/char/queen/orb/orb3.png",};

    private Hitbox tipHornet = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipBumble = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipWorker = new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);
    private Hitbox tipDrone= new Hitbox(0.0F, 0.0F, 60.0F * Settings.scale, 60.0F * Settings.scale);

    private final String droneLabel = "The number of Drones in your draw pile.";
    private static final float DRIFTER_ORB_SCALE;

    private static final String VFXTexture = ("QueenModResources/images/char/queen/orb/bee.png");

    private static final float[] layerSpeeds = new float[]{10.0F, 30.0F, 15.0F, -20.0F, 0.0F};

    public Speedometer() {
        super(orbTextures, VFXTexture, layerSpeeds);
    }


    @Override
    public void updateOrb(int energyCount) {
        super.updateOrb(energyCount);
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean active, float current_x, float current_y) {
        if (this.tipDrone.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(50.0F * Settings.scale, 380.0F * Settings.scale, "Drone Counter", droneLabel);
        }

        if (active) {
            sb.draw(energyLayers[0],
                    current_x - 155.0F,
                    current_y - 65.0F,
                    155.0F,
                    65.0F,
                    219.0F,
                    168.0F,
                    DRIFTER_ORB_SCALE,
                    DRIFTER_ORB_SCALE,
                    0.0F, 0, 0, 219, 168, false, false);

/*
            sb.draw(energyLayers[0],
                    current_x - 155.0F,
                    current_y - 65.0F,
                    energyLayers[0].getWidth() * Settings.scale,
                    energyLayers[0].getHeight() * Settings.scale);*/
        } else {
            sb.draw(noEnergyLayers[0],
                    current_x - 155.0F,
                    current_y - 65.0F,
                    155.0F,
                    65.0F,
                    219.0F,
                    168.0F,
                    DRIFTER_ORB_SCALE,
                    DRIFTER_ORB_SCALE,
                    0.0F, 0, 0, 219, 168, false, false);
            /*
            sb.draw(noEnergyLayers[0],
                    current_x - 155.0F,
                    current_y - 65.0F,
                    noEnergyLayers[0].getWidth() * Settings.scale,
                    noEnergyLayers[0].getHeight() * Settings.scale);*/
        }

        AbstractDungeon.player.getEnergyNumFont().getData().setScale(1.0F);
        tipHornet.x = current_x-(60.0F * DRIFTER_ORB_SCALE); tipHornet.y = current_y +(40.0F * DRIFTER_ORB_SCALE);
        tipBumble.x = current_x-(85.0F * DRIFTER_ORB_SCALE); tipBumble.y = current_y +(8.0F * DRIFTER_ORB_SCALE);
        tipWorker.x = current_x-(60.0F * DRIFTER_ORB_SCALE); tipWorker.y = current_y -(32.0F * DRIFTER_ORB_SCALE);
        tipDrone.x = current_x-(105.0F * DRIFTER_ORB_SCALE); tipDrone.y = current_y -(32.0F * DRIFTER_ORB_SCALE);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[0]+"", current_x-(45.0F * DRIFTER_ORB_SCALE), current_y+(62.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[1]+"", current_x-(70.0F * DRIFTER_ORB_SCALE), current_y+(20.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[3]+"", current_x-(45.0F * DRIFTER_ORB_SCALE), current_y-(20.0F * DRIFTER_ORB_SCALE));
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), i[2]+"", current_x-(95.0F * DRIFTER_ORB_SCALE), current_y-(20.0F * DRIFTER_ORB_SCALE));

    }

    static {
        DRIFTER_ORB_SCALE = 1.15F * Settings.scale;
    }
}