package axoloti.outlets;

import axoloti.INetView;
import axoloti.MainFrame;
import axoloti.Theme;
import axoloti.iolet.IoletAbstract;
import axoloti.objectviews.AxoObjectInstanceViewAbstract;
import axoloti.objectviews.IAxoObjectInstanceView;
import components.LabelComponent;
import components.SignalMetaDataIcon;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPopupMenu;

public class OutletInstanceView extends IoletAbstract implements IOutletInstanceView {

    OutletInstancePopupMenu popup = new OutletInstancePopupMenu(this);

    OutletInstance outletInstance;

    final OutletInstanceController controller;

    public OutletInstanceView(OutletInstance outletInstance, OutletInstanceController controller, AxoObjectInstanceViewAbstract axoObj) {
        this.outletInstance = outletInstance;
        this.controller = controller;
        this.axoObj = axoObj;
        setBackground(Theme.getCurrentTheme().Object_Default_Background);
    }

    @Override
    public void PostConstructor() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setMaximumSize(new Dimension(32767, 14));
        setBackground(Theme.getCurrentTheme().Object_Default_Background);
        add(Box.createHorizontalGlue());
        if (axoObj.getObjectInstance().getType().GetOutlets().size() > 1) {
            add(new LabelComponent(outletInstance.getOutlet().getName()));
            add(Box.createHorizontalStrut(2));
        }
        add(new SignalMetaDataIcon(outletInstance.getOutlet().GetSignalMetaData()));
        jack = new components.JackOutputComponent(this);
        jack.setForeground(outletInstance.getOutlet().getDatatype().GetColor());
        add(jack);
        setToolTipText(outletInstance.getOutlet().getDescription());

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public JPopupMenu getPopup() {
        return new OutletInstancePopupMenu(this);
    }

    @Override
    public OutletInstance getOutletInstance() {
        return outletInstance;
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        if ((getRootPane() == null
                || getRootPane().getCursor() != MainFrame.transparentCursor)
                && axoObj != null
                && axoObj.getPatchView() != null) {
            INetView netView = axoObj.getPatchView().GetNetView((IOutletInstanceView) this);
            if (netView != null
                    && netView.getSelected() != highlighted) {
                netView.setSelected(highlighted);
            }
        }
    }

    @Override
    public void disconnect() {
        getPatchView().getPatchController().disconnect(this);
    }

    @Override
    public void deleteNet() {
        getPatchView().getPatchController().deleteNet(this);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OutletInstanceController getController() {
        return controller;
    }

}
