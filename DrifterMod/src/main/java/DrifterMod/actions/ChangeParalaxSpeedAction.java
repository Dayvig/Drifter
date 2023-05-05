//Borrowed from Replay the Spire.

package DrifterMod.actions;

import UI.ParalaxController;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ChangeParalaxSpeedAction extends AbstractGameAction
{

    ParalaxController controller;
    float speedUp;

    public ChangeParalaxSpeedAction(ParalaxController controller, float speedChange) {
        this.duration = 0.1f;
        this.controller = controller;
        speedUp = speedChange;
    }

    @Override
    public void update() {
        if (this.duration == 0.1f) {
            this.controller.ChangeObjectSpeeds(speedUp);
            this.isDone=true;
        }
        this.tickDuration();
    }
}
