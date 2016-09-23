package graphics;

import base.ResourceUsage;

import java.util.Iterator;
import java.util.Vector;

/**
 * Interface for graphics classes to keep track of resource usage types.
 */
class UsageTypeManager {

	private final Vector<ResourceUsage> resourceUsages;
	private final int resourceCount;
	private ResourceUsage currentUsage;

	UsageTypeManager(Vector<ResourceUsage> resourceUsages) {
		this.resourceUsages = resourceUsages;
		this.resourceCount = resourceUsages.size();
		this.currentUsage = resourceUsages.firstElement();
	}

	ResourceUsage getCurrentUsage() {
		return currentUsage;
	}

	void setCurrentUsage(ResourceUsage newUsage) {
		currentUsage = newUsage;
	}

	int getResourceCount() {
		return resourceCount;
	}

	Iterator<ResourceUsage> provideIterator() {
		return resourceUsages.iterator();
	}
}
