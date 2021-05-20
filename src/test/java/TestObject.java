import net.dv8tion.jda.api.EmbedBuilder;
import net.xilla.core.library.manager.ManagerObject;
import net.xilla.core.library.manager.StoredData;

public class TestObject extends ManagerObject {

    @StoredData
    private EmbedBuilder builder;

    public TestObject(String name, EmbedBuilder builder) {
        super(name, "Test");
        this.builder = builder;
    }

    public TestObject() {}

}
