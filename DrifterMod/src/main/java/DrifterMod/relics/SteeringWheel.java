package DrifterMod.relics;

import DrifterMod.DrifterMod;
import DrifterMod.powers.Speedup;
import DrifterMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static DrifterMod.DrifterMod.makeRelicOutlinePath;
import static DrifterMod.DrifterMod.makeRelicPath;

public class SteeringWheel extends CustomRelic {

    public static final String ID = DrifterMod.makeID("SteeringWheel");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic2.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic2.png"));

    public SteeringWheel() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Speedup(AbstractDungeon.player, AbstractDungeon.player, 1)));
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
