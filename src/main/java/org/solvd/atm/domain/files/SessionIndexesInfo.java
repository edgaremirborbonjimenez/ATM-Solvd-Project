package org.solvd.atm.domain.files;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SessionIndexesInfo {
    @JsonProperty("last_index")
    Integer lastIndex;
    @JsonProperty("sessions")
    SessionInfo[] sessions;

    public SessionIndexesInfo(){}

    public Integer getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(Integer lastIndex) {
        this.lastIndex = lastIndex;
    }

    public void setSessions(SessionInfo[] sessions) {
        this.sessions = sessions;
    }

    public SessionInfo[] getSessions() {
        return sessions;
    }

    public void addSession(SessionInfo session){
        List<SessionInfo> newList = new LinkedList<>(Arrays.stream(sessions).toList());
        newList.add(session);
        sessions = newList.toArray(new SessionInfo[0]);

    }
}
