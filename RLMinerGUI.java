import javax.swing.*;
import java.awt.*;

public class RLMinerGUI extends JFrame {

    public int[] selectedOre;
    public int[] selectedInventory;

    public JTextField ore_id = new JTextField();
    public JTextField inventory_id = new JTextField();
    public JButton start = new JButton("Start");

    public boolean isSet = false;

    public RLMinerGUI() {
        super();
        setTitle("RLMiner");
        setLayout(new BorderLayout());

        start.addActionListener((e) -> {
            String[] ints = ore_id.getText().split(",");
            selectedOre = new int[ints.length];
            int i = 0;
            for (String s : ints)
            {
                selectedOre[i] = Integer.valueOf(s);
                i++;
            }

            String[] ints2 = inventory_id.getText().split(",");
            selectedInventory = new int[ints2.length];
            int i2 = 0;
            for (String s : ints)
            {
                selectedInventory[i2] = Integer.valueOf(s);
                i2++;
            }
            isSet = true;
            setVisible(false);
        });

        add(new JPanel() {
            {
                setLayout(new BoxLayout(getPanel(), BoxLayout.Y_AXIS));

                add(new JLabel("Ore GameObject ID"));
                add(ore_id);
                add(new JLabel("Ore Inventory ID"));
                add(inventory_id);
            }

            private JPanel getPanel() {
                return this;
            }
        }, BorderLayout.CENTER);

        add(start, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(400, 150));
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


}
