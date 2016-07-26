
public class UsageNumTimes extends ResourceUsage
{
	
	// ================================================================================
	// Constructors
	// ================================================================================
	public UsageNumTimes (String name, double rate, String usageUnit, Tips tips, AverageUsage avg,
			HistoricalUsage historical)
	{
		super(name, rate, usageUnit, tips, avg, historical);
		setInputUnit("times");
	}
	
	public UsageNumTimes (String name, double rate, String usageUnit, HistoricalUsage historical)
	{
		super(name, rate, usageUnit, historical);
		setInputUnit("times");
	}
	
	// ================================================================================
	// Methods
	// ================================================================================
	public void promptInput()
	{
		System.out.print("How many " + this.getInputUnit() + " did you use the " + getName() + "? ");
		double input = in.nextDouble(); // double so that it can include things like halves
		setInputAmt(input);
	}
	
	public double calcInputChange(double usageToChange)
	{
		double inputChange = usageToChange / getRate();
		return inputChange;
	}
	
}
