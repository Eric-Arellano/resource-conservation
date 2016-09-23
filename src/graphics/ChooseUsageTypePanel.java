package graphics;

import base.ResourceUsage;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Vector;

/**
 * Allows user to choose between all usage types at any time.
 */
class ChooseUsageTypePanel extends JPanel {

	private Vector<ResourceUsage> resourceUsages;
	private int resourceCount;


	//  ------------------------------------------------------------------------
	//  Setup panel
	//  ------------------------------------------------------------------------

	ChooseUsageTypePanel(Vector<ResourceUsage> resourceUsages) {
		this.resourceUsages = resourceUsages;
		resourceCount = resourceUsages.size();

		JLabel prompt = new JLabel("Choose resource usage:");
		JPanel radioOptions = createRadioOptions();

		this.setLayout(new GridLayout());
		this.add(prompt, BorderLayout.NORTH);
		this.add(radioOptions, BorderLayout.CENTER);
	}

	//  ------------------------------------------------------------------------
	//  Setup Radio Options
	//  ------------------------------------------------------------------------

	private JPanel createRadioOptions() {
		JPanel radioButtonsPanel = createRadioButtonsPanel();
		JPanel radioLabelsPanel = createRadioLabelsPanel();

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(1, 2));

		radioPanel.add(radioButtonsPanel);
		radioPanel.add(radioLabelsPanel);
		return radioPanel;
	}

	private JPanel createRadioButtonsPanel() {
		Iterator<ResourceUsage> usageIterator = resourceUsages.iterator();

		JPanel radioButtonsPanel = new JPanel();
		radioButtonsPanel.setLayout(new GridLayout(resourceCount, 1));
		while (usageIterator.hasNext()) {
			// create new radio option
			// add event listener
			// add to panel
		}
		return radioButtonsPanel;
	}

	private JPanel createRadioLabelsPanel() {
		Iterator<ResourceUsage> usageIterator = resourceUsages.iterator();

		JPanel radioLabelsPanel = new JPanel();
		radioLabelsPanel.setLayout(new GridLayout(resourceCount, 1));
		while (usageIterator.hasNext()) {
			// create new label from name attribute
			// add to panel
		}
		return radioLabelsPanel;
	}

}
