//Borrowed from Replay the Spire.

package UI;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParalaxController {
    private ArrayList<ArrayList<ParalaxObject>> objects;
    public float width;
    private ArrayList<Float> levelSpeeds;
    public boolean running;
    private int dir;

    public ParalaxController(ArrayList<Float> levelSpeeds, float width, boolean forward) {
        objects = new ArrayList<ArrayList<ParalaxObject>>();
        this.levelSpeeds = levelSpeeds;
        for (int i=0; i < levelSpeeds.size(); i++) {
            objects.add(new ArrayList<ParalaxObject>());
        }
        this.width = width;
        this.dir = forward ? 1 : -1;
        this.running = false;
    }

    public void Start() {
        this.running = true;
    }
    public void AddObject(ParalaxObject obj, int level) {
        obj.baseSpeed = obj.speed = this.levelSpeeds.get(level);
        this.objects.get(level).add(obj);
    }

    public void ChangeObjectSpeeds(float speedMultiplier){
        for (ArrayList<ParalaxObject> level : this.objects) {
            for (ParalaxObject obj : level) {
                obj.speed = obj.baseSpeed * speedMultiplier;
            }
        }
    }

    public void DistributeObjects() {
        for (ArrayList<ParalaxObject> level : this.objects) {
            float spacing = this.width / (level.size());
            float randoff = ((float)Math.random() % (spacing));
            for (int i=0; i < level.size(); i++) {
                level.get(i).x = spacing * i + ((float)Math.random() % (spacing / 5.0f)) + randoff;
            }
        }
    }
    public void DistributeObjectsUniform(int level) {
            float spacing = this.width / objects.get(level).size();
            for (int i=0; i < objects.get(level).size(); i++) {
                objects.get(level).get(i).x = spacing * i;
            }
    }

    public void Render(SpriteBatch sb) {
        for (ArrayList<ParalaxObject> level : this.objects) {
            for (ParalaxObject obj : level) {
                if (this.running) {
                    obj.x = (obj.x - obj.speed * Gdx.graphics.getDeltaTime()) % this.width;
                    if (obj.x<0) {
                        obj.x += this.width;
                    }
                }
                obj.Render(sb);
            }
        }
    }
}