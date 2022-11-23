package co.empathy.academy.demo_search.ports.index.settings;

import java.io.InputStream;

public interface PSettingsProvider {
    InputStream getMapping();
    InputStream getSettings();
}
