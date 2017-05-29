package pt.idk;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.system.AppSettings;

public class TestApp extends SimpleApplication {

    private static final String PLAYER_UP = "Up";
    private static final String PLAYER_DOWN = "Down";
    private static final String PLAYER_RIGHT = "Right";
    private static final String PLAYER_LEFT = "Left";
    private static final int PLAYER_SPEED = 20;
    private static final int PLAYER_ROTATION_SPEED = 3;

    private Geometry player;

    private void setUpLight() {
        // We add light so we see the scene
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }

    @Override
    public void simpleInitApp() {
        setUpLight();

        Box b = new Box(1, 1, 1);
        player = new Geometry("Player", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient", ColorRGBA.Orange);
        mat.setColor("Diffuse", ColorRGBA.Orange);
        player.setMaterial(mat);
        player.move(0, -20, 0);

        rootNode.attachChild(player);
        initKeys();

        flyCam.setEnabled(false);
        cam.setLocation(new Vector3f(0, 0, 60));
    }

    /**
     * Custom Keybinding: Map named actions to inputs.
     */
    private void initKeys() {
        inputManager.addMapping(PLAYER_UP, new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping(PLAYER_DOWN, new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping(PLAYER_LEFT, new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping(PLAYER_RIGHT, new KeyTrigger(KeyInput.KEY_L));
        inputManager.addListener(analogListener, PLAYER_UP, PLAYER_DOWN, PLAYER_LEFT, PLAYER_RIGHT);

    }

    private AnalogListener analogListener = new AnalogListener() {

        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals(PLAYER_UP)) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x, v.y + value * speed * PLAYER_SPEED, v.z);
            }

            if (name.equals(PLAYER_DOWN)) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x, v.y - value * speed * PLAYER_SPEED, v.z);
            }

            if (name.equals(PLAYER_RIGHT)) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x + value * speed * PLAYER_SPEED, v.y, v.z);
            }

            if (name.equals(PLAYER_LEFT)) {
                Vector3f v = player.getLocalTranslation();
                player.setLocalTranslation(v.x - value * speed * PLAYER_SPEED, v.y, v.z);
            }
        }
    };

    @Override
    public void simpleUpdate(float tpf) {
        player.rotate(0, PLAYER_ROTATION_SPEED * tpf, 0);
    }

    public static void main(String[] args) {
        TestApp app = new TestApp();
        AppSettings settings = new AppSettings(true);
        settings.setVSync(true);

        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();
    }
}
