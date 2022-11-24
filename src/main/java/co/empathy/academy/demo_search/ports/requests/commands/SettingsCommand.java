package co.empathy.academy.demo_search.ports.requests.commands;

import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;

import java.io.InputStream;

/**
 * Represents the command to set the settings
 */
public interface SettingsCommand {
    /**
     * Sets the settings
     * @param setter the setter for the settings
     */
    void set(PSettingsSetter setter);
}
