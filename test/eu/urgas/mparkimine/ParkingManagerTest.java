package eu.urgas.mparkimine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingManagerTest {
    private ParkingManager manager = ParkingManager.getInstance();

    @Before
    public void setUp() {
        manager.reset();
    }

    @Test
    public void testShouldBeOfflineByDefault() {
        assertEquals(ParkingManager.Status.OFFLINE, manager.getStatus());
    }

    @Test
    public void testStartedAtShouldNotBeSetByDefault() {
        assertNull(manager.getStartedAt());
    }

    @Test
    public void testSetStarting() {
        manager.setStarting();
        assertEquals(ParkingManager.Status.STARTING, manager.getStatus());
    }

    @Test
    public void testSetStarted() {
        manager.setStarted();
        assertEquals(ParkingManager.Status.STARTED, manager.getStatus());
    }

    @Test
    public void testSetStartedChangesStartedAt() {
        manager.setStarted();
        assertNotNull(manager.getStartedAt());
    }

    @Test
    public void testSetStopping() {
        manager.setStopping();
        assertEquals(ParkingManager.Status.STOPPING, manager.getStatus());
    }

    @Test
    public void testSetStopped() {
        manager.setStopped();
        assertEquals(ParkingManager.Status.STOPPED, manager.getStatus());
    }

    @Test
    public void testSetFailed() {
        manager.setFailed();
        assertEquals(ParkingManager.Status.FAILED, manager.getStatus());
    }
}
