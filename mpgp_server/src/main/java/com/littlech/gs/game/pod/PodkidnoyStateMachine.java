/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game.pod;

import com.littlech.gen.g.G1;
import com.littlech.gs.game.IStateMachine;


// TODO: Auto-generated Javadoc
/**
 * Durak game state machine.
 *
 * @author Kristjan Veskim�e
 */
public class PodkidnoyStateMachine implements IStateMachine<PodkidnoyGameStateType> {
	
	/** The test state. */
	private G1 testState = null;
	
	/** Durak game current state. */
	private PodkidnoyGameStateType durakGameState = PodkidnoyGameStateType.GS_DURAK_NOT_INITIALIZED;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PodkidnoyStateMachine{");
		sb.append("" + getState());
		sb.append("}");
		return sb.toString();
	}
	
	@Override
	public void reset() {
		durakGameState = PodkidnoyGameStateType.GS_DURAK_NOT_INITIALIZED;
	}
	
	/**
	 * Get the current state.
	 *
	 * @return State
	 */
	@Override
	public PodkidnoyGameStateType getState() {
		return durakGameState;
	}

	/**
	 * Set the state.
	 *
	 * @param newDurakState State
	 */

	@Override
	public void setState(PodkidnoyGameStateType newDurakState) {

		switch (this.durakGameState) {
		case GS_DURAK_NOT_INITIALIZED:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_STARTED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_ROUND_STARTED:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_DEFENDING_FIRST)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_DEFENDING_FIRST:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_DEFENDING_FIRST)
					|| newDurakState
							.equals(PodkidnoyGameStateType.GS_DURAK_WAITING_MORE_CARDS)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_PICKED_UP)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_WAITING_MORE_CARDS:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_DEFENDING_AGAIN)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)
					|| newDurakState
							.equals(PodkidnoyGameStateType.GS_DURAK_WAITING_MORE_CARDS)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_DEFENDING_AGAIN:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_PICKED_UP)
					|| newDurakState
							.equals(PodkidnoyGameStateType.GS_DURAK_WAITING_MORE_CARDS)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_PICKED_UP:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_PICKED_UP)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_ENDED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_ROUND_ENDED:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_FINISHED)
					|| newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_STARTED)) {
				this.durakGameState = newDurakState;
				break;
			}
			throwStateTransformationException(this.durakGameState,
					newDurakState);
		case GS_DURAK_FINISHED:
			if (newDurakState.equals(PodkidnoyGameStateType.GS_DURAK_ROUND_STARTED)) {
				this.durakGameState = newDurakState;
				break;
			}
		default:
			throwStateTransformationException(this.durakGameState,
					newDurakState);
			break;
		}

	}

	/**
	 * Throw exception when the requested state transformation is not allowed.
	 *
	 * @param durakGameState2 Old state
	 * @param newDurakState New state
	 */
	private void throwStateTransformationException(
			PodkidnoyGameStateType durakGameState2, PodkidnoyGameStateType newDurakState) {
		throw new IllegalArgumentException("Transformation from state " + durakGameState2 + " to " + newDurakState + " is not allowed");
	}

	/* (non-Javadoc)
	 * @see server.IStateMachine#getTableState()
	 */
	@Override
	public G1 getTableState() {
		if (testState != null) {
			return testState;
		}
		switch (getState()) {
		case GS_DURAK_ROUND_STARTED:
		case GS_DURAK_DEFENDING_FIRST:
		case GS_DURAK_DEFENDING_AGAIN:
		case GS_DURAK_PICKED_UP:
		case GS_DURAK_WAITING_MORE_CARDS:
		case GS_DURAK_ROUND_ENDED:
			return G1.G_3;
		default:
			return G1.G_2;
		}
	}

	/* (non-Javadoc)
	 * @see server.IStateMachine#setTestTableState(com.littlech.gen.g.G1)
	 */
	@Override
	public void setTestTableState(G1 _newState) {
		testState = _newState;
	}

}
