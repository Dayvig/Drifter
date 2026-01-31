package DrifterMod.relics;

import DrifterMod.DrifterMod;
import DrifterMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static DrifterMod.DrifterMod.makeRelicOutlinePath;
import static DrifterMod.DrifterMod.makeRelicPath;

public class WaterCup extends CustomRelic {

    public static final String ID = DrifterMod.makeID("WaterCup");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("watercup.png"));
    private static final Texture IMGSPILLED = TextureLoader.getTexture(makeRelicPath("watercupspilled.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("watercup.png"));
    private static final Texture OUTLINESPILLED = TextureLoader.getTexture(makeRelicOutlinePath("watercupspilled.png"));

    public WaterCup() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        beginLongPulse();
        balanced = true;
        setTextureOutline(IMG, OUTLINE);
    }

    boolean balanced = false;

    @Override
    public void onUseCard(AbstractCard c, UseCardAction a) {
        super.onUseCard(c, a);
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.size()%2 == 0){
            setTextureOutline(IMG, OUTLINE);
        }
        else {
            setTextureOutline(IMGSPILLED, OUTLINESPILLED);
        }
        balanced = AbstractDungeon.actionManager.cardsPlayedThisTurn.size() % 2 == 0;
    }

    public void atTurnStart() {
        if (balanced) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
        }
        balanced = true;
        setTextureOutline(IMG, OUTLINE);
    }


    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
