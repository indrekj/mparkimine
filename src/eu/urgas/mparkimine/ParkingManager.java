package eu.urgas.mparkimine;

import java.util.Date;

public class ParkingManager {
    enum Status {
        OFFLINE, STARTING, STARTED, FAILED, STOPPING, STOPPED
    }

    public static final String PARKING_OPERATOR_NUMBER = "1902";

    private Status status = Status.OFFLINE;
    private Date startedAt;

    public ParkingManager() {
    }

    //public void reset() {
    //    status = Status.OFFLINE;
    //    startedAt = null;
    //}

    public Status getStatus() {
        return status;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStarting() {
        status = Status.STARTING;
    }

    public void setStarted() {
        startedAt = new Date();
        status = Status.STARTED;
    }

    public void setFailed() {
        status = Status.FAILED;
    }

    public void setStopping() {
        status = Status.STOPPING;
    }

    public void setStopped() {
        status = Status.STOPPED;
    }
}
