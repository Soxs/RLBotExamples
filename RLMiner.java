import com.runeliteplus.Utils;
import net.runelite.api.GameObject;
import net.runelite.client.pluginsplus.soxs.rlbot.api.APIUtils;
import net.runelite.client.pluginsplus.soxs.rlbot.api.input.Mouse;
import net.runelite.client.pluginsplus.soxs.rlbot.api.scripting.RLScript;
import net.runelite.client.pluginsplus.soxs.rlbot.api.scripting.RLScriptDescriptor;

import java.awt.*;

@RLScriptDescriptor(
        name = "RLMiner",
        description = "A miner that supports banking and power-levelling. Can fill inventory or mine X drop X.",
        version = 0.01,
        author = "Soxs",
        requiresAuthentication = true
)
public class RLMiner extends RLScript {

    private GameObject selectedOre = null;
    private int[] ORE_IDS = {11361};
    private int[] INVENTORY_ID = {438};

    private final Color shapeColor = new Color(255, 89, 0, 109);
    private Shape oreArea = null;

    private RLMinerGUI gui;

    @Override
    public boolean onStart() {
        System.out.println("Starting RLMiner!");

        EventQueue.invokeLater(() -> gui = new RLMinerGUI());

        while (gui == null || !gui.isSet)
            sleep(1000, 1550);

        ORE_IDS = gui.selectedOre;
        INVENTORY_ID = gui.selectedInventory;

        gui = null;

        return true;
    }

    @Override
    public void onEnd() {
        System.out.println("Stopping RLMiner...");
    }

    @Override
    public int loop() {
        if (getInventory().getItemCount(INVENTORY_ID) >= 3 ) {
            dropOres();
        } else {
            findAndMineOre();
            return Utils.randInt(4007, 5007); //longer rests.
        }

        return Utils.randInt(2000, 4000); //Rest for a random amount of time.
    }

    private void findAndMineOre() {
        GameObject[] ores = getGameObjects(ORE_IDS);
        if (ores.length > 0) {
            selectedOre = APIUtils.getNearestObject(context, ores); //Get the closest tree.
            if (selectedOre != null && APIUtils.isPlayerIdle(context)) { //Ensure the tree is valid, and only proceed if our player is idle.
                oreArea = selectedOre.getClickbox(); //Save the click box of the tree so we can paint our target.
                Mouse.seekAndClickTileObject(this, selectedOre); //Move the mouse and click the tree.
            }
        }
        else {
            if (Utils.randInt(0, 100) > Utils.randInt(55, 65))
                Mouse.moveToSuitableRestArea(this);
        }
        Mouse.sleepWhileMouseActive(this);
    }

    private void dropOres() {
        if (getInventory().getItemCount(INVENTORY_ID) > 0)
            Mouse.moveAndShiftClick(this, getInventory().getInventoryClickPoint(INVENTORY_ID));
    }

    @Override
    public void overlay(Graphics2D graphics) {
        graphics.setColor(shapeColor); //Set the paint colour to green.
        graphics.fill(oreArea); //Draw the hit box of the tree.
    }
}
