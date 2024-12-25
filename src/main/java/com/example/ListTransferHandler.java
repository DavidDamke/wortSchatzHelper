package com.example;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class ListTransferHandler extends TransferHandler {
      @Override
        protected Transferable createTransferable(JComponent c) {
            JList<?> list = (JList<?>) c;
            Object value = list.getSelectedValue();
            return new StringSelection(value.toString());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY_OR_MOVE;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

@Override
public boolean importData(TransferSupport support) {
    if (!canImport(support)) {
        return false;
    }

    try {
        JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
        int index = dropLocation.getIndex();
        JList<?> targetList = (JList<?>) support.getComponent();
        DefaultListModel<String> targetModel = (DefaultListModel<String>) targetList.getModel();

        String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

        // If index is -1, add to the end of the list
        if (index < 0) {
            index = targetModel.getSize();
        }

        targetModel.add(index, data);
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                JList<?> sourceList = (JList<?>) source;
                DefaultListModel<?> sourceModel = (DefaultListModel<?>) sourceList.getModel();
                String movedData;
                try {
                    movedData = (String) data.getTransferData(DataFlavor.stringFlavor);
                    sourceModel.removeElement(movedData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

