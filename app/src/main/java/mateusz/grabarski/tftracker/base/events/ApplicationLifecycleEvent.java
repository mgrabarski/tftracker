package mateusz.grabarski.tftracker.base.events;

/**
 * Created by MGrabarski on 11.02.2018.
 */

public class ApplicationLifecycleEvent {

    private boolean background;

    public ApplicationLifecycleEvent(boolean background) {
        this.background = background;
    }

    public boolean isBackground() {
        return background;
    }
}
