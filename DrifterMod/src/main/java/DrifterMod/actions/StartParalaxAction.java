//Borrowed from Replay the Spire.

package DrifterMod.actions;

import UI.ParalaxController;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class StartParalaxAction extends AbstractGameAction
{

    ParalaxController controller;
    public StartParalaxAction(ParalaxController controller) {
        this.duration = 0.5f;
        this.controller = controller;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            this.controller.Start();
            this.isDone=true;
        }
        this.tickDuration();
    }
}
