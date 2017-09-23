/*
 * Entry logger class for log4j, the application wishes to log to log4j must use this class
 */
package com.intelym.quick.java.client.logger;
import org.apache.log4j.Logger;

/**
 *
 * @author Hari Nair
 * @since Mar 2012
 * @version 1.0
 */
public class IntelymLogger {

    /** The logger. */
    private Logger logger = null;

    public IntelymLogger(Class className) {
        logger = Logger.getLogger(className);
    }

    public void debug(Object arg0) {
        if (logger != null) {
            logger.debug(arg0);
        }
    }

    public void debug(Object arg0 , Throwable t) {
        if(logger != null) {
            logger.debug(arg0, t);
        }
    }

    public void info(Object arg0) {
        if (logger != null) {
            logger.info(arg0);
        }
    }

    public void info(Object arg0 , Throwable t){
        if(logger != null) {
            logger.info(arg0, t);
        }
    }

    public void warn(Object arg0) {
        if (logger != null) {
            logger.warn(arg0);
        }
    }

    public void warn(Object arg0, Throwable t){
        if(logger != null) {
            logger.warn(arg0, t);
        }
    }

    public void error(Object arg0) {
        if (logger != null) {
            logger.error(arg0);
        }
    }

    public void error(Object arg0, Throwable t){
        if(logger != null) {
            logger.error(arg0, t);
        }
    }

    public void fatal(Object arg0) {
        if (logger != null) {
            logger.fatal(arg0);
        }
    }

    public void fatal(Object arg0, Throwable t){
        if(logger != null) {
            logger.fatal(arg0, t);
        }
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }
}
