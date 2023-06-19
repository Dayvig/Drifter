package DrifterMod.cards;

import DrifterMod.powers.ResolvePower;
import DrifterMod.powers.TractionPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class AbstractDriftCard extends AbstractDynamicCard {
    public AbstractDriftCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void applyPowers(){
        super.applyPowers();
        this.magicNumber = baseMagicNumber;
        if (AbstractDungeon.player.hasPower(TractionPower.POWER_ID)){
            this.magicNumber += AbstractDungeon.player.getPower(TractionPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID) && AbstractDungeon.player.hand.size() <= 2){
            this.magicNumber += AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
        }
        this.isMagicNumberModified = AbstractDungeon.player.hasPower(TractionPower.POWER_ID) ||
                (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID) && AbstractDungeon.player.hand.size() <= 2);     }
}
