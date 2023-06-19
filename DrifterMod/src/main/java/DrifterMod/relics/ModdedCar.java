package DrifterMod.relics;

import DrifterMod.DrifterMod;
import DrifterMod.powers.Speedup;
import DrifterMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import static DrifterMod.DrifterMod.makeRelicOutlinePath;
import static DrifterMod.DrifterMod.makeRelicPath;

public class ModdedCar extends CustomRelic {

    public static final String ID = DrifterMod.makeID("ModdedCar");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("moddedcar.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("moddedcar.png"));

    public ModdedCar() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        beginLongPulse();
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
