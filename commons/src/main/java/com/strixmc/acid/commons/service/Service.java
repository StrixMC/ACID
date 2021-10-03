package com.strixmc.acid.commons.service;

public interface Service {


    /**
     * @return if the service was successfully initialized.
     */
    boolean isInitialized();

    /**
     * Method to set service status.
     *
     * @param value service init status.
     */
    void setInitialized(boolean value);

    /**
     * Start service method.
     */
    void start();

    /**
     * Finish service method.
     * Override method if it will be used.
     */
    default void finish() {
    }
}
