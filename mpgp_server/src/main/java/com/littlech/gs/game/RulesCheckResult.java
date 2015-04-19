/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;

// TODO: Auto-generated Javadoc
/**
 * The Class RulesCheckResult.
 */
public class RulesCheckResult {
	
	/** The allowed. */
	private final boolean 
	allowed;
	
	/** The reason. */
	private final String reason;

	/** The e. */
	private Exception e;
	
	/**
	 * Instantiates a new rules check result.
	 *
	 * @param _allowed the _allowed
	 */
	public RulesCheckResult(final boolean _allowed) {
		allowed = _allowed;
		reason = null;
	}
	
	/**
	 * Instantiates a new rules check result.
	 *
	 * @param _allowed the _allowed
	 * @param _reason the _reason
	 * @param _e the _e
	 */
	public RulesCheckResult(final boolean _allowed, final String _reason, Exception _e) {
		allowed = _allowed;
		reason = _reason;
		e = _e;
	}

	/**
	 * Checks if is allowed.
	 *
	 * @return true, if is allowed
	 */
	public boolean isAllowed() {
		return allowed;
	}

	/**
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	} 
	
	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public Exception getException() {
		return e;
	}

}
