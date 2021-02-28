package net.xilla.discordcore.ui;

import lombok.Getter;
import net.xilla.discordcore.ui.pane.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MenuManager {

    @Getter
    private static MenuManager instance = null;

    private JFrame frame;

    private String name;

    private MenuWrapper wrapper;

    @Getter
    private ConsolePane consolePane;

    @Getter
    private StatisticPane statisticPane;

    private StatisticWorker statisticWorker;

    private Map<String, Pane> paneMap = new HashMap<>();

    @Getter
    private String currentPane = "";

    public static void start(String name) {
        try {
            if (GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices().length == 0) {
                return;
            }
            instance = new MenuManager(name);
        } catch (Exception ignored) {}
    }

    public MenuManager(String name) {
        instance = this;

        setTheme();

        this.name = name;
        this.wrapper = new MenuWrapper();
        this.consolePane = new ConsolePane();
        this.statisticPane = new StatisticPane();

        this.statisticWorker = new StatisticWorker();
        this.statisticWorker.start();

        addTheme("Console", consolePane);
        addTheme("Statistics", statisticPane);

        loadFrame();
    }

    public void addTheme(String name, Pane pane) {
        System.out.println("Adding theme " + pane);
        this.paneMap.put(name, pane);
        this.wrapper.getPageSelection().addItem(name);
    }

    public void setTheme(String name) {
        System.out.println("Setting theme " + name);
        this.currentPane = name;
        this.wrapper.getPageLabel().setText(name);

        this.wrapper.getContentPanel().removeAll();
        this.wrapper.getContentPanel().add(paneMap.get(name).getPanel());

        this.wrapper.getContentPanel().revalidate();
        this.wrapper.getContentPanel().repaint();
    }

    public void setTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFrame() {
        frame = new JFrame(name);
        frame.setContentPane(wrapper.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
