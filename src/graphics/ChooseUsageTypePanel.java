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
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(resourceCount, 2));

		Iterator<ResourceUsage> usageIterator = resourceUsages.iterator();
		while (usageIterator.hasNext()) {
			// add radio option
			// add event listener
			// add label
		}

		return radioPanel;
	}

}
