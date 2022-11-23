package co.empathy.academy.demo_search.ports.requests.commands.settings;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Optional;

@RequiredArgsConstructor
public class JsonSettingReader {
    private final Optional<String> settingsName;
    private final Optional<String> mappingName;

    public JsonSettingReader(String settings, String mapping) {
        this(Optional.ofNullable(settings), Optional.ofNullable(mapping));
    }

    public Optional<InputStream> getMapping() {
        return mappingName.map(name -> getClass().getClassLoader().getResourceAsStream(name));
    }

    public Optional<InputStream> getSettings() {
        return settingsName.map(name -> getClass().getClassLoader().getResourceAsStream(name));
    }
}
