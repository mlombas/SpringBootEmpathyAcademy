package co.empathy.academy.demo_search.ports.requests.commands.settings;

import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;
import co.empathy.academy.demo_search.ports.requests.commands.SettingsCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RequiredArgsConstructor
public class SetJsonSettings implements SettingsCommand {
    private final JsonSettingReader reader;

    @Override
    public void set(PSettingsSetter setter) {
        reader.getSettings().ifPresent(setter::setSettings);
        reader.getMapping().ifPresent(setter::setMapping);
    }
}
