package net.xilla.discordcore.ui.pane;

import lombok.Getter;
import net.xilla.core.library.manager.XillaManager;
import net.xilla.core.library.worker.Worker;
import net.xilla.discordcore.library.DiscordAPI;

import javax.swing.*;
import java.awt.*;

public class StatisticPane implements Pane {

    @Getter
    private JPanel panel;

    @Getter
    private JTextArea statistics;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        statistics = new JTextArea();
        statistics.setEditable(false);
        statistics.setLineWrap(true);
        panel.add(statistics, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(640, 480), new Dimension(640, 480), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}