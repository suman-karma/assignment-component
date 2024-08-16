package io.mhe.assignmentcomponent.common.constant;

/**
 * Represents a generic interface for a visitor pattern.
 * User: Stephen Lazaronak
 * Date: 1/16/14
 */
public interface Visitable<T> {

	void accept(T visitor);
}
