package com.example;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

public class ListTransferHandler extends TransferHandler {
    private final int priority;
    private final DefaultListModel<String> model;
    private final FileWriterUtils fileWriterUtils;

    public ListTransferHandler(int priority, DefaultListModel<String> model) {
        this.priority = priority;
        this.model = model;
        this.fileWriterUtils = new FileWriterUtils();
    }

    @Override
    public boolean canImport(TransferSupport support) {
        // Allow only string data to be imported
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            // Extract the string data from the drag-and-drop operation
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

            // Split the data into multiple items if it contains folder contents (comma-separated)
            String[] items = data.split(",");

            for (String item : items) {
                String trimmedItem = item.trim();

                // Avoid duplicates
                if (!model.contains(trimmedItem)) {
                    model.addElement(trimmedItem);
                    fileWriterUtils.updateWordValue(trimmedItem, true, priority); // Update persistence
                }
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        if (c instanceof JTree) {
            // Handle drag from JTree
            JTree tree = (JTree) c;
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                // Gather all leaf nodes if the selected node is a folder
                List<String> items = new ArrayList<>();
                gatherNodeContents(selectedNode, items);
                return new StringSelection(String.join(",", items));
            }
        }
        return null;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE; // Support both copy and move actions
    }

    private void gatherNodeContents(DefaultMutableTreeNode node, List<String> items) {
        if (node.isLeaf()) {
            items.add(node.getUserObject().toString());
        } else {
            // If it's a folder, gather all children recursively
            for (int i = 0; i < node.getChildCount(); i++) {
                gatherNodeContents((DefaultMutableTreeNode) node.getChildAt(i), items);
            }
        }
    }
    }

