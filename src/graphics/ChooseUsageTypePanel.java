package graphics;

import base.ResourceUsage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * Allows user to choose between all usage types at any time.
 */
class ChooseUsageTypePanel extends JPanel {

	private final UsageTypeManager usageTypeManager;

	//  ------------------------------------------------------------------------
	//  Setup panel
	//  ------------------------------------------------------------------------

	ChooseUsageTypePanel(UsageTypeManager usageTypeManager) {
		this.usageTypeManager = usageTypeManager;

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
		radioPanel.setLayout(new GridLayout(usageTypeManager.getResourceCount(), 2));

		Iterator<ResourceUsage> usageIterator = usageTypeManager.provideIterator();
		while (usageIterator.hasNext()) {
			usageTypeManager.setCurrentUsage(usageIterator.next()); // TODO: check no off-by-1 error
			// add radio option
			// add event listener
			this.add(createRadioLabel());
		}

		return radioPanel;
	}

	private void createRadioIcon() {

	}

	private JLabel createRadioLabel() {
		return new JLabel(usageTypeManager
				.getCurrentUsage()
				.getName());
	}

	private class createRadioListener implements ActionListener {

		public void actionPerformed(ActionEvent radioOptionSelected) {

		}
	}

}
