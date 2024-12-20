package com.jelisavacluka554.rmt_common.communication;

/**
 *
 * @author luka
 */
public enum Operation {
    LOGIN,          // User
    REGISTER,       // User
    APPL_GET_LIST,  // User
    APPL_CREATE,    // Application
    APPL_UPDATE,    // Application[2]
    EUC_GET_LIST,   // Null
    T_GET_LIST,     // Null
    STOP,           // Null
    MSG,            // String
    PING            // Null
}
