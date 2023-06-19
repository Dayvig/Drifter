package DrifterMod.Patches;

import DrifterMod.DrifterMod;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong")
public class EurobeatPatch {

    @SpirePrefixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        DrifterMod.logger.info("Music patch Temp hit");
        switch (key) {
            case "Gas":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Gas.ogg"));
            case "NightFire":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/NightFire.ogg"));
            case "Dejavu":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Dejavu.ogg"));
            case "CrazyEmotion":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/CrazyEmotion.ogg"));
            case "HotLimit":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/HotLimit.ogg"));
            case "IWannaBeTheNight":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/IWannaBeTheNight.ogg"));
            case "DontStopTheMusic":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/DontStopTheMusic.ogg"));
            case "PerfectHero":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/PerfectHero.ogg"));
            case "RisingSun":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/RisingSun.ogg"));
            case "ChemicalLove":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/ChemicalLove.ogg"));
            case "SaveMe":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/SaveMe.ogg"));
            case "Spark":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Spark.ogg"));
            case "90s":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/90s.ogg"));
            case "TheTop":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/TheTop.ogg"));
            case "Heartbeat":
                return SpireReturn.Return(MainMusic.newMusic("DrifterModResources/audio/music/Heartbeat.ogg"));
            default:
                return SpireReturn.Continue();
        }
    }
}
