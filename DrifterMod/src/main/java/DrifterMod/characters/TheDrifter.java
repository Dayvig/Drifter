package DrifterMod.characters;

import DrifterMod.DrifterMod;
import DrifterMod.Patches.BeyondScenePatch;
import DrifterMod.actions.ChangeParalaxSpeedAction;
import DrifterMod.actions.EurobeatAction;
import DrifterMod.actions.StartParalaxAction;
import DrifterMod.actions.StopEurobeatAction;
import DrifterMod.cards.*;
import DrifterMod.relics.SteeringWheel;
import UI.ParalaxController;
import UI.ParalaxObject;
import UI.Speedometer;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Ref;
import java.util.ArrayList;

import static DrifterMod.DrifterMod.*;
import static DrifterMod.characters.TheDrifter.Enums.COLOR_DARKBLUE;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DrifterMod-character-Strings.json in the resources

public class TheDrifter extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(DrifterMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static PlayerClass THE_DRIFTER;
        @SpireEnum(name = "DRIFTER_BLUE") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_DARKBLUE;
        @SpireEnum(name = "DRIFTER_BLUE")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;
    public static int r = 0;
    public static boolean drifting = false;
    public static boolean startOfDrift = false;
    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DrifterMod");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "DrifterModResources/images/char/defaultCharacter/orb/layer1.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer2.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer3.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer4.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer5.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer6.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer1d.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer2d.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer3d.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer4d.png",
            "DrifterModResources/images/char/defaultCharacter/orb/layer5d.png",};


    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    private static EnergyOrbInterface e = new Speedometer();

    // =============== CHARACTER CLASS START =================

    public TheDrifter(String name, PlayerClass setClass) {
        super(name, setClass, e, new SpineAnimation("DrifterModResources/images/char/defaultCharacter/Testproj.atlas",
                "DrifterModResources/images/char/defaultCharacter/Testproj.json", 0.1f));

        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DrifterMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_1, // campfire pose
                THE_DEFAULT_SHOULDER_2, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================

        //Texture t = TextureLoader.getTexture("DrifterModResources/images/char/defaultCharacter/hornetCounter.png");
        //HornetCounter hornetctr = new HornetCounter(t, 100.0F, 100.0F);

        // =============== ANIMATIONS =================

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animtion0", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        logger.info("Begin loading starter Deck Strings");
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Speedstar.ID);
        retVal.add(InControl.ID);

        return retVal;
    }

    // Starting Relics
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(SteeringWheel.ID);
        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_DARKBLUE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return DrifterMod.DRIFTER_BLUE;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Tmph();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheDrifter(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return DrifterMod.DRIFTER_BLUE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return DrifterMod.DRIFTER_BLUE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public void applyPreCombatLogic() {
        super.applyPreCombatLogic();
        r = (int) (Math.random() * 9);
        Speedometer.needleRot = 100.f;

        System.out.println(AbstractDungeon.actNum);

        switch (AbstractDungeon.actNum){
            case 1:
                setupBottomParalax();
                break;
            case 2:
                setupCityParalax();
                break;
            case 3: setupBeyondParalax();
                break;
            case 4:
                setupEndingParalax();
                break;
            default:
                setupBeyondParalax();
                break;
        }
    }


    void setupBeyondParalax(){
        //Taken from replay the spire
        ArrayList<Float> levelSpeeds = new ArrayList<Float>();
        levelSpeeds.add(1900.0f);//0 - floor
        levelSpeeds.add(200.0f);//1 - true bg
        levelSpeeds.add(400.0f);//2 - bg addons
        levelSpeeds.add(700.0f);//3 - shapes
        levelSpeeds.add(1400.0f);//4 - thin pillars
        levelSpeeds.add(1850.0f);//5 - a few shapes

        System.out.println("Width " + Settings.WIDTH + " | "+ Settings.scale);
        System.out.println("Height " + Settings.HEIGHT + " | "+ Settings.scale);

        BeyondScenePatch.bg_controller = new ParalaxController(levelSpeeds, Settings.WIDTH*3, true);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/bg_2.png", false, false, true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/bg_1.png", false, false, true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/bg_3.png", false, false, true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(2),"DrifterModResources/images/ui/wl_2.png", false, false), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(2),"DrifterModResources/images/ui/wl_4.png", false, false), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(2),"DrifterModResources/images/ui/wl_1.png", false, false), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(4),"DrifterModResources/images/ui/pl_1.png", false, false), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(4),"DrifterModResources/images/ui/pl_3.png", false, false), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(4),"DrifterModResources/images/ui/pl_2.png", false, false), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(4),"DrifterModResources/images/ui/pl_1.png", false, false), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(4),"DrifterModResources/images/ui/sh_2.png", false, false), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(3),"DrifterModResources/images/ui/sh_3.png", false, false), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(3),"DrifterModResources/images/ui/sh_1.png", false, false), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(3),"DrifterModResources/images/ui/wl_3.png", false, false), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(3),"DrifterModResources/images/ui/sh_3.png", false, false), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(3),"DrifterModResources/images/ui/sh_1.png", false, false), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5),"DrifterModResources/images/ui/sh_4.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5),"DrifterModResources/images/ui/sh_5.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5),"DrifterModResources/images/ui/sh_2.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5),"DrifterModResources/images/ui/fg_1.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5),"DrifterModResources/images/ui/sh_4.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5), "DrifterModResources/images/ui/sh_5.png", false, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(5), "DrifterModResources/images/ui/sh_1.png", true, false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/fl_1.png", false), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/fl_1.png", false), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/fl_1.png", false), 0);
        BeyondScenePatch.bg_controller.DistributeObjects();
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(0);
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(1);

        AbstractDungeon.actionManager.addToBottom(new StartParalaxAction(BeyondScenePatch.bg_controller));
        AbstractDungeon.actionManager.addToBottom(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, 0.75f));
    }

    void setupBottomParalax(){
        //Taken from replay the spire
        ArrayList<Float> levelSpeeds = new ArrayList<Float>();
        levelSpeeds.add(1900.0f);//0 - floor
        levelSpeeds.add(150.0f);//1 - true bg
        levelSpeeds.add(200.0f);//2 - bg addons
        levelSpeeds.add(200.0f);//3 - shapes
        levelSpeeds.add(200.0f);//4 - thin pillars
        levelSpeeds.add(1900.0f);//4 - thin pillars

        BeyondScenePatch.bg_controller = new ParalaxController(levelSpeeds, Settings.WIDTH*3, true);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(5), "DrifterModResources/images/ui/bottom/fl_1.png", false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(5), "DrifterModResources/images/ui/bottom/fl_1.png", false), 5);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(5), "DrifterModResources/images/ui/bottom/fl_1.png", false), 5);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(1), "DrifterModResources/images/ui/bottom/bg_1.png", true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(1),"DrifterModResources/images/ui/bottom/bg_1.png", true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(1),"DrifterModResources/images/ui/bottom/bg_1.png", true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(3),"DrifterModResources/images/ui/bottom/bg_2.png", true), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(3),"DrifterModResources/images/ui/bottom/bg_2.png", true), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, Settings.HEIGHT * 0.3f, levelSpeeds.get(3), "DrifterModResources/images/ui/bottom/bg_2.png", true), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(Settings.WIDTH * 0.75f, Settings.HEIGHT * 0.75f, "DrifterModResources/images/ui/bottom/bg_4.png"), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(Settings.WIDTH * 0.75f, Settings.HEIGHT * 0.75f, "DrifterModResources/images/ui/bottom/bg_4.png"), 4);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(Settings.WIDTH * 0.75f, Settings.HEIGHT * 0.75f, "DrifterModResources/images/ui/bottom/bg_4.png"), 4);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(400f, 400f,"DrifterModResources/images/ui/bottom/bg_3.png"), 2);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(400f, 400f,"DrifterModResources/images/ui/bottom/bg_3.png"), 2);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(400f, 400f,"DrifterModResources/images/ui/bottom/bg_3.png"), 2);

        BeyondScenePatch.bg_controller.DistributeObjects();
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(1);
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(3);
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(4);

        AbstractDungeon.actionManager.addToBottom(new StartParalaxAction(BeyondScenePatch.bg_controller));
        AbstractDungeon.actionManager.addToBottom(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, 0.75f));
    }

    void setupCityParalax(){
        //Taken from replay the spire
        ArrayList<Float> levelSpeeds = new ArrayList<Float>();
        levelSpeeds.add(200.0f);//0 - bg
        levelSpeeds.add(1900.0f);//1 - floor
        levelSpeeds.add(300.0f);//2 - pillar1
        levelSpeeds.add(325.0f);//3 - pillar2
        levelSpeeds.add(350.0f);//4 - pillar3
        levelSpeeds.add(375.0f);//4 - pillar4
        levelSpeeds.add(275.0f);//rok

        BeyondScenePatch.bg_controller = new ParalaxController(levelSpeeds, Settings.WIDTH*3, true);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(1),"DrifterModResources/images/ui/city/fl_1.png", false, false), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(1),"DrifterModResources/images/ui/city/fl_1.png", false, true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(1),"DrifterModResources/images/ui/city/fl_1.png", false, false), 1);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/4), levelSpeeds.get(0), "DrifterModResources/images/ui/city/bg_1.png", false, false, true), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/4), levelSpeeds.get(0), "DrifterModResources/images/ui/city/bg_1.png", false, true, true), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/4), levelSpeeds.get(0), "DrifterModResources/images/ui/city/bg_1.png", false, false, true), 0);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0,850,"DrifterModResources/images/ui/city/bg_2.png"), 0);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0,850,"DrifterModResources/images/ui/city/bg_2.png"), 0);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 850,"DrifterModResources/images/ui/city/bg_2.png"), 0);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f), "DrifterModResources/images/ui/city/pl_1.png"), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT * 0.4f),"DrifterModResources/images/ui/city/pl_1.png"), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_2.png"), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_2.png"), 2);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_3.png"), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_3.png"), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_4.png"), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_4.png"), 3);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT* 0.4f),"DrifterModResources/images/ui/city/pl_4.png"), 3);
        //BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 300,"DrifterModResources/images/ui/city/rk_1.png"), 2);

        BeyondScenePatch.bg_controller.DistributeObjects();
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(0);
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(1);

        AbstractDungeon.actionManager.addToBottom(new StartParalaxAction(BeyondScenePatch.bg_controller));
        AbstractDungeon.actionManager.addToBottom(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, 0.75f));
    }

    void setupEndingParalax(){
        //Taken from replay the spire
        ArrayList<Float> levelSpeeds = new ArrayList<Float>();
        levelSpeeds.add(1900.0f);//0 - floor
        levelSpeeds.add(200.0f);//4 - thin pillars

        BeyondScenePatch.bg_controller = new ParalaxController(levelSpeeds, Settings.WIDTH*3, true);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/end/fl_1.png", true), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/end/fl_1.png", true), 0);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, 0, levelSpeeds.get(0), "DrifterModResources/images/ui/end/fl_1.png", true), 0);

        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/end/bg_1.png", true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/end/bg_1.png", true), 1);
        BeyondScenePatch.bg_controller.AddObject(new ParalaxObject(0, (int)(Settings.HEIGHT/3), levelSpeeds.get(1),"DrifterModResources/images/ui/end/bg_1.png", true), 1);

        BeyondScenePatch.bg_controller.DistributeObjects();
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(0);
        BeyondScenePatch.bg_controller.DistributeObjectsUniform(1);

        AbstractDungeon.actionManager.addToBottom(new StartParalaxAction(BeyondScenePatch.bg_controller));
        AbstractDungeon.actionManager.addToBottom(new ChangeParalaxSpeedAction(BeyondScenePatch.bg_controller, 0.75f));
    }

    public static String returnDriftKey(){
        switch (TheDrifter.r){
            case 0:
                return "Gas";
            case 1:
                return "NightFire";
            case 2:
                return "Dejavu";
            case 3:
                return "CrazyEmotion";
            case 4:
                return "HotLimit";
            case 5:
                return "IWannaBeTheNight";
            case 6:
                return "DontStopTheMusic";
            case 7:
                return "PerfectHero";
            case 8:
                return "RisingSun";
            case 9:
                return "ChemicalLove";
            default:
                return "Gas";
        }
    }

    public void update(){
        super.update();
        if (startOfDrift && !drifting){
            System.out.println("Drift Start");
            playEurobeat(returnDriftKey());
            ReflectionHacks.setPrivate(AbstractDungeon.player,
                    AbstractCreature.class,
                    "animation",
                    DriftingToyota);
            ReflectionHacks.setPrivate(AbstractDungeon.player,
                    AbstractCreature.class,
                    "animationtimer",
                    0f);
            SpineAnimation spine = (SpineAnimation)DriftingToyota;
            loadAnimation(spine.atlasUrl, spine.skeletonUrl, 1f);
            AnimationState.TrackEntry e = state.setAnimation(0, "animtion0", true);
            e.setTime(e.getEndTime() * MathUtils.random());
            startOfDrift = false;
            drifting = true;
        }
    }

    public void resetAnimation(){
        ReflectionHacks.setPrivate(AbstractDungeon.player,
                AbstractCreature.class,
                "animation",
                Toyota);
        ReflectionHacks.setPrivate(AbstractDungeon.player,
                AbstractCreature.class,
                "animationtimer",
                0f);
        SpineAnimation spine = (SpineAnimation)Toyota;
        System.out.println(spine.skeletonUrl);
        loadAnimation(spine.atlasUrl, spine.skeletonUrl, 1f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animtion0", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    private float ramAnimationTimer = 0.0f;
    private float ramAnimationDuration = 1.0f;

    public static void playEurobeat(String key){
        AbstractDungeon.actionManager.addToTop(new EurobeatAction(key));
    }

    @Override
    public void onVictory() {
        super.onVictory();
        AbstractDungeon.actionManager.addToTop(new StopEurobeatAction());
        CardCrawlGame.music.fadeOutTempBGM();

        resetAnimation();
        drifting = false;
        startOfDrift = false;
    }

    public AbstractAnimation Toyota = new SpineAnimation("DrifterModResources/images/char/defaultCharacter/Testproj.atlas",
            "DrifterModResources/images/char/defaultCharacter/Testproj.json", 0.1f);
    public AbstractAnimation DriftingToyota = new SpineAnimation("DrifterModResources/images/char/defaultCharacter/Drifting.atlas",
                                                "DrifterModResources/images/char/defaultCharacter/Drifting.json", 0.1f);

}
