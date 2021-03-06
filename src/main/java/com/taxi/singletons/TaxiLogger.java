package com.taxi.singletons;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@LocalBean
@Singleton(name = "TaxiLogger")
public class TaxiLogger extends TailerListenerAdapter implements Serializable {

    private List<Session> sessions;
    private final Object lock = new Object();
    private ExecutorService executorService;

    @Inject
    private Logger logger;

    @PostConstruct
    public void init() {
        try {
            sessions = new ArrayList<>();
            logger.info("Initializing");
            PropertyConfigurator.configure(new Properties());
            Tailer tailer = Tailer.create(new File("/var/log/taximanager/taximanager.log"), this, 1000, true, true);
            CompletableFuture.runAsync(tailer);
            executorService = Executors.newFixedThreadPool(3);
        } catch (Throwable e) {
            Logger.getLogger(getClass()).error(e.getMessage());
        }
    }

    public void dump() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Arrays.stream(ManagementFactory.getThreadMXBean().dumpAllThreads(true, true))
                        .forEach(t -> logger.info(t.toString()));
            }
        });
    }

    @AroundInvoke
    public Object profile(InvocationContext invocation) throws Exception {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        }catch (org.hibernate.HibernateException e) {
            error("Error DB " + invocation.getMethod().getName(), e);
            throw e;
        } catch(Throwable e) {
            error("Error profiling " + invocation.getMethod().getName(), e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis() - startTime;
            info("Profile " + invocation.getMethod().getName() + ": " + endTime);
        }
    }

    @Lock(LockType.READ)
    public List<Session> getAll() {
        return new ArrayList<>(sessions);
    }

    @Lock(LockType.WRITE)
    public void add(Session session) {
        sessions.add(session);
    }

    @Lock(LockType.WRITE)
    public void remove(Session session) {
        sessions.remove(session);
    }

    @Override
    public void endOfFileReached() {
        super.endOfFileReached();
    }

    @Override
    public void fileRotated() {
        super.fileRotated();
    }

    public void info(String message) {
        log(Level.INFO, message);
    }

    public void info(String message, Object obj) {
        log(Level.INFO, message );
    }

    @Override
    public void handle(String line) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                StringBuilder sb = new StringBuilder();

                if (line.contains("- INFO -")) {
                    String[] parts = line.split("- INFO -");
                    if (parts.length > 1) {
                        sb.append("<div class='log-info'>").append("<b>").append(parts[0]).append("- INFO -").append("</b>").append("<span>").append(parts[1]).append("</span>").append("</div>");
                    }
                } else if (line.contains("- ERROR -")) {
                    String[] parts = line.split("- ERROR -");
                    if (parts.length > 1) {
                        sb.append("<div class='log-error'>").append("<b>").append(parts[0]).append("- ERROR -").append("</b>").append(parts[1]).append("</span>").append("</div>");
                    }
                } else if (line.contains("- WARN -")) {
                    String[] parts = line.split("- WARN -");
                    if (parts.length > 1) {
                        sb.append("<div class='log-warn'>").append("<b>").append(parts[0]).append("- WARN -").append("</b>").append(parts[1]).append("</span>").append("</div>");
                    }
                }

                for (Session session : getAll()) {
                    synchronized (lock) {
                        try {
                            session.getBasicRemote().sendObject(new LogMessage(sb.toString()));
                        } catch (IOException | EncodeException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void error(String message, Throwable e) {
        log(Level.ERROR, message + ", error: " + e.getMessage());
    }

    private void log(Level level, String message) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                StringBuilder trace = new StringBuilder();
                String methodCaller = "unknown(?)";
                if (stackTraceElements.length > 3) {
                    methodCaller = stackTraceElements[3].getClassName() + "." + stackTraceElements[2].getMethodName() + "(" + stackTraceElements[2].getLineNumber() + ")";
                    if (level == Level.ERROR) {
                        trace.append("\n").append("--- START TRACE ---").append("\n");
                        for (int i = 3; i < stackTraceElements.length || i < 7; i++) {
                            StackTraceElement stackTraceElement = stackTraceElements[i];
                            trace.append(i - 2).append(" - ").append(stackTraceElement.getClassName())
                                    .append(".").append(stackTraceElement.getMethodName())
                                    .append("(")
                                    .append(stackTraceElement.getLineNumber())
                                    .append(")")
                                    .append("\n");
                        }

                        trace.append("--- END TRACE ---");
                    }
                }

                logger.log(level, methodCaller + ": " + message + trace);
            }
        });
    }
}
