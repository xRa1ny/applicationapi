package me.xra1ny.applicationapi;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.xra1ny.applicationapi.exceptions.ClassNotAnnotatedException;
import me.xra1ny.applicationapi.models.screen.RScreen;
import me.xra1ny.applicationapi.screens.DefaultScreen;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;


@Slf4j
public abstract class RApplication extends JFrame {
    @Getter(onMethod = @__(@NotNull))
    private static RApplication instance;

    @Getter(onMethod = @__(@NotNull))
    private final String initialTitle;

    @Getter(onMethod = @__(@NotNull))
    private final Dimension initialSize;

    @Getter(onMethod = @__(@NotNull))
    private RScreen currentScreen = new DefaultScreen();

    @Getter(onMethod = @__(@NotNull))
    private RScreen lastScreen = this.currentScreen;

    public RApplication() {
        final ApplicationInfo info = getClass().getDeclaredAnnotation(ApplicationInfo.class);

        if(info == null) {
            throw new ClassNotAnnotatedException(getClass(), ApplicationInfo.class);
        }

        instance = this;

        this.initialTitle = info.title();
        this.initialSize = new Dimension(info.width(), info.height());

        initialise();
    }

    /**
     * initialises this applications default mainframe settings and applies those specified in the application annotation
     */
    private void initialise() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(this.initialSize);
        setResizable(false);
        setLocationRelativeTo(null); // Center this Window
        setTitle(this.initialTitle);
        setVisible(true);
    }

    /**
     * enables and show the specified screen on this application
     */
    public void show(@NotNull RScreen screen) {
        if(screen == this.currentScreen) {
            return;
        }

        log.info("switching to new screen " + screen);

        this.lastScreen = this.currentScreen;
        this.currentScreen = screen;

        remove(this.lastScreen);
        this.lastScreen.setEnabled(false); // Disable last Screen
        this.lastScreen.onDisable();
        this.lastScreen.updateUI();
        this.lastScreen.revalidate();

        add(this.currentScreen);
        this.currentScreen.requestFocus();
        this.currentScreen.setEnabled(true); // Enable current Screen
        this.currentScreen.onEnable();
        this.currentScreen.updateUI();
        this.currentScreen.revalidate();
    }
}
