/**
 *
 */
package ej.widget.listener;

/**
 * Defines an object which listens to value change events.
 */
public interface OnValueChangeListener {

	/**
	 * Invoked when the target of the listener has changed its value.
	 *
	 * @param newValue the new value of the listened object.
	 */
	void onValueChange(int newValue);

	/**
	 * Invoked when the target of the listener has changed its maximum value.
	 *
	 * @param newMaximum the new maximum value of the listened object.
	 */
	void onMaximumValueChange(int newMaximum);

	/**
	 * Invoked when the target of the listener has changed its minimum value.
	 *
	 * @param newMinimum the new minimum value of the listened object.
	 */
	void onMinimumValueChange(int newMinimum);
}
