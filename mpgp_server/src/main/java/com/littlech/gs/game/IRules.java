/*
 * Created by: Kristjan Veskimäe
 */
package com.littlech.gs.game;
import com.littlech.gen.d.D4;
import com.littlech.gs.user.ServerUser;


// TODO: Auto-generated Javadoc
/**
 * The Interface IRules.
 */
public interface IRules {

    /**
     * Checks if is allowed.
     *
     * @param _user the _user
     * @param received the received
     * @return the rules check result
     */
    public RulesCheckResult isAllowed (ServerUser _user, D4 received);

}

