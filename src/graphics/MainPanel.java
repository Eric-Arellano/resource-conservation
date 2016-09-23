package graphics;

import base.ResourceUsage;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Aggregates all panels into one interface.
 */
public class MainPanel extends JPanel {

	private Vector<ResourceUsage> resourceUsages;

	//  ------------------------------------------------------------------------
	//  Setup panel
	//  ------------------------------------------------------------------------

	public MainPanel(Vector<ResourceUsage> resourceUsages,
	                 String welcomeMessage) {

		this.setLayout(new BorderLayout());

		// create panels
		JPanel welcomeMessagePanel = createWelcomeMessagePanel(welcomeMessage);
		ChooseUsageTypePanel chooseUsageTypePanel = new ChooseUsageTypePanel(resourceUsages);
		UsageDataPanel usageDataPanel = new UsageDataPanel();
		AnalysisPanel analysisPanel = new AnalysisPanel();

		// layout panels
		this.add(welcomeMessagePanel, BorderLayout.NORTH);
		this.add(chooseUsageTypePanel, BorderLayout.WEST);
		this.add(usageDataPanel, BorderLayout.CENTER); // TODO: this doesn't match wireframe
		this.add(analysisPanel, BorderLayout.EAST);

	}

	//  ------------------------------------------------------------------------
	//  Create welcomeMessagePanel
	//  ------------------------------------------------------------------------

	private JPanel createWelcomeMessagePanel(String welcomeMessage) {
		return new JPanel(); // TODO: complete this
	}

}
