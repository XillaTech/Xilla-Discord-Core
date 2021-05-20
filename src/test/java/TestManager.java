import net.xilla.core.library.manager.Manager;

public class TestManager extends Manager<String, TestObject> {

    public TestManager() {
        super("Test", "test/file.jsonf", TestObject.class);
    }

}
