//Borrowed from Replay the Spire.


package DrifterMod.Patches;

import DrifterMod.DrifterMod;
import DrifterMod.characters.TheDrifter;
import UI.ParalaxController;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.scenes.TheBeyondScene;
import com.megacrit.cardcrawl.scenes.TheBottomScene;
import com.megacrit.cardcrawl.scenes.TheCityScene;
import com.megacrit.cardcrawl.scenes.TheEndingScene;
import com.megacrit.cardcrawl.vfx.scene.InteractableTorchEffect;

import java.util.ArrayList;

import static DrifterMod.DrifterMod.EUROBEAT_ON;
import static DrifterMod.DrifterMod.SCROLLING_ON;
import static DrifterMod.characters.TheDrifter.Enums.THE_DRIFTER;

public class BeyondScenePatch {

    public static ParalaxController bg_controller = null;

    public static boolean ShouldScroll(){
        return AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && (AbstractDungeon.getCurrRoom() instanceof MonsterRoom || (AbstractDungeon.getCurrRoom() instanceof EventRoom && AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT))) && bg_controller != null && AbstractDungeon.player.chosenClass.equals(THE_DRIFTER) && DrifterMod.config.getBool(SCROLLING_ON);
    }


    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "renderCombatRoomBg")
    public static class BottomSceneParalaxBGPatch {
        public static void Postfix(TheBottomScene __instance, final SpriteBatch sb) {
            if (ShouldScroll()) {
                bg_controller.Render(sb);
            }
        }
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBottomScene", method = "updateTorches")
    public static class BottomSceneParalaxBGTorchPatch {
        public static void Prefix(TheBottomScene __instance) {
            if (ShouldScroll()) {
                ArrayList<InteractableTorchEffect> hackedTorches = ReflectionHacks.getPrivate(__instance, TheBottomScene.class, "torches");
                hackedTorches.clear();
                SpireReturn.Return();
            }
        }
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheBeyondScene", method = "renderCombatRoomBg")
    public static class BeyondSceneParalaxBGPatch {
        public static void Postfix(TheBeyondScene __instance, final SpriteBatch sb) {
            if (ShouldScroll()) {
                bg_controller.Render(sb);
            }
        }
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheEndingScene", method = "renderCombatRoomBg")
    public static class EndingSceneParalaxBGPatch {
        public static void Postfix(TheEndingScene __instance, final SpriteBatch sb) {
            if (ShouldScroll()) {
                bg_controller.Render(sb);
            }
        }
    }
    @SpirePatch(cls = "com.megacrit.cardcrawl.scenes.TheCityScene", method = "renderCombatRoomBg")
    public static class CitySceneParalaxBGPatch {
        public static void Postfix(TheCityScene __instance, final SpriteBatch sb) {
            if (ShouldScroll()) {
                bg_controller.Render(sb);
            }
        }
    }
}