package ind.venture.objectivenotion.domain.property;

public class DatabaseProperty {

    private String id;
    private String name;
    private String type;

    public DatabaseProperty() {
    }

    public DatabaseProperty(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
