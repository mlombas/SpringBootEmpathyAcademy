package co.empathy.academy.demo_search.ports.index.settings;

import java.io.IOException;
import java.io.InputStream;

public interface PSettingsSetter {
    void setSettings(InputStream settings);
    void setMapping(InputStream mapping);
}
