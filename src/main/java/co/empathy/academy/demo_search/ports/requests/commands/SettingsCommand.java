package co.empathy.academy.demo_search.ports.requests.commands;

import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;

import java.io.InputStream;

public interface SettingsCommand {
    void set(PSettingsSetter setter);
}
