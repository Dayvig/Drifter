//Borrowed from Replay the Spire.
package UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class ParalaxObject {
    public float x;
    public float y;
    public float speed;
    public float baseSpeed;
    public Texture img;
    public boolean flipped;
    public boolean reversed;
    public boolean background;

    public ParalaxObject(String texturePath) {
        this(0, 400.0f, 100.0f, texturePath, false, false,false);
    }
    public ParalaxObject(float x, float y, String texturePath) {
        this(x, y, 100.0f, texturePath, false, false,false);
    }

    public ParalaxObject(float x, float y, float speed, String texturePath, boolean backgroundElement) {
        this(x, y, speed, texturePath, false, false, backgroundElement);
    }
    public ParalaxObject(float x, float y, float speed, String texturePath, boolean flipped, boolean reversed) {
        this(x, y, speed, texturePath, flipped, reversed, false);
    }
    public ParalaxObject(float x, float y, float speed, String texturePath, boolean flipped, boolean reversed, boolean backgroundElement) {
        this.x = x;
        this.y = y;
        this.flipped = flipped;
        this.reversed = flipped || reversed;
        this.speed = speed;
        this.img = ImageMaster.loadImage(texturePath);
        this.background = backgroundElement;
    }

    public void Render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        if (background){
            float newWidth = Settings.WIDTH * 1.2f;
            float newHeight = Settings.HEIGHT * 0.66f;
            sb.draw(this.img, this.x - newWidth, this.y, newWidth, newHeight, 0, 0, this.img.getWidth(), this.img.getHeight(), this.reversed, this.flipped);
        }
        sb.draw(this.img, this.x - this.img.getWidth() * Settings.scale, this.y + AbstractDungeon.sceneOffsetY, this.img.getWidth() * Settings.scale, this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.reversed, this.flipped);
    }
}
