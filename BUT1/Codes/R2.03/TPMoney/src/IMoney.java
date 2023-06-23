public interface IMoney {
	public abstract IMoney add(Money m);

	public abstract int getAmount();

	public abstract String getCurrency();
}