package DrifterMod.animations;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class RamAnimation extends AbstractGameAction {

    AbstractMonster target;
    AbstractPlayer p;
    float start;
    float startAnim;
    float hitDuration;

    public RamAnimation(AbstractPlayer player, AbstractMonster monster){
        p = player;
        target = monster;
        this.startDuration = 0.4f;
        this.hitDuration = 0.32f;
        this.duration = 0.4f;
        start = p.hb.cX;
        startAnim = p.animX;
    }

    @Override
    public void update() {
        if (this.duration > startDuration - hitDuration) {
            p.animX = (Interpolation.linear.apply(target.hb.cX, start, (this.duration-(startDuration - hitDuration))/hitDuration) - start) * 0.8f;
            p.updateAnimations();
        }
        else {
            p.animX = (Interpolation.linear.apply(start, target.hb.cX, (this.duration/(this.startDuration-hitDuration))) - start) * 0.8f;
            p.updateAnimations();
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            p.animX = 0.0f;
            p.updateAnimations();
            this.isDone = true;
        }
    }
}
