package co.empathy.academy.demo_search.ports.index.settings;

import java.io.IOException;
import java.io.InputStream;

public interface PSettingsSetter {
    /**
     * Sets index settings
     * @param settings the settings to set
     */
    void setSettings(InputStream settings);

    /**
     * Sets mapping for index
     * @param mapping the mapping to set
     */
    void setMapping(InputStream mapping);
}
