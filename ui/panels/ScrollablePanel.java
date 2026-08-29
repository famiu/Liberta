package ui.panels;

import java.lang.*;
import javax.swing.*;
import java.awt.*;

// Unless Scrollable interface is implemented, panels with JScrollPane don't seem to resize correctly
// Reference: https://stackoverflow.com/questions/2716274/jscrollpane-needs-to-shrink-its-width
public class ScrollablePanel extends JPanel implements Scrollable {
    private boolean fillHeightWhenShort;

    public ScrollablePanel() {
        this(false);
    }

    public ScrollablePanel(boolean fillHeightWhenShort) {
        this.fillHeightWhenShort = fillHeightWhenShort;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        if (orientation == SwingConstants.VERTICAL) {
            return visibleRect.height;
        }
        else {
            return visibleRect.width;
        }
    }

    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    public boolean getScrollableTracksViewportHeight() {
        // Fill extra height to center short content without preventing tall content from scrolling
        // Reference: https://stackoverflow.com/questions/10331129/jscrollpane-resize-containing-jpanel-when-scrollbars-appear
        if (fillHeightWhenShort && (getParent() instanceof JViewport)) {
            JViewport viewport = (JViewport) getParent();
            return getPreferredSize().height < viewport.getHeight();
        }
        return false;
    }
}
