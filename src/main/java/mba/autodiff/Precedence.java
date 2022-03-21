package mba.autodiff;

public interface Precedence {
	public default int precedence() {
		throw new UnsupportedOperationException();
	};
}
