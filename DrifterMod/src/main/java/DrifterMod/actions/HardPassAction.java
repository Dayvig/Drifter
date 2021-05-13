//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package DrifterMod.actions;

import DrifterMod.powers.Speedup;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class HardPassAction extends AbstractGameAction {
    private DamageInfo info;

    public HardPassAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Speedup(AbstractDungeon.player, AbstractDungeon.player, 1)));
            }
        }
        this.isDone = true;
    }
}
