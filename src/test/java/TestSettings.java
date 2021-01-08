import lombok.Getter;
import lombok.Setter;
import net.xilla.core.library.config.Settings;
import net.xilla.core.library.manager.StoredData;

public class TestSettings extends Settings {

    @Getter
    private static TestSettings instance;

    @Getter
    @Setter
    @StoredData
    private Integer number = 5;

    @Getter
    @Setter
    @StoredData
    private String text = "default text";

    public TestSettings(String file) {
        super("test-settings.json");

        instance = this;

        startup();
    }

}
