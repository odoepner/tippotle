package net.doepner.log;

/**
 * Log abstraction
 */
public interface Log {

    enum Level {
        trace, debug, info, warn, error
    }

    void $(Level level, Throwable t);

    void $(Level level, String message, Throwable t);

    void $(Level level, String message, Object... parameters);
}
