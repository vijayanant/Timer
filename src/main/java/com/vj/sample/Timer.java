package com.vj.sample;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A timer to which all TimerClients can register for getting timeout call-back
 */
public final class Timer {
    private static Timer instance;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Map<String, ScheduledFuture<?>> register;

    private Timer() {
        this.register = new HashMap<String, ScheduledFuture<?>>();
    }

    public static Timer getInstance() {
        if (instance == null)
            instance = new Timer();
        return instance;
    }

    public void register(String id, final TimerClient client, final String action, long delay) {
        final Runnable beeper = new Runnable() {
            public void run() {
                client.timeOut(action);
            }
        };

        final ScheduledFuture<?> handle = scheduler.schedule(beeper, delay, SECONDS);

        register.put(id, handle);
    }

    public void unregister(TimerClient e) {
        ScheduledFuture<?> handle = register.get(e);
        if (handle != null) {
            handle.cancel(true);
        }
    }
}
