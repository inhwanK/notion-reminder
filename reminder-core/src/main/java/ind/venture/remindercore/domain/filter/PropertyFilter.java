package ind.venture.remindercore.domain.filter;

public class PropertyFilter {
    private String property;
    private DateFilter dateFilter;

    public PropertyFilter(String property, DateFilter dateFilter) {
        this.property = property;
        this.dateFilter = dateFilter;
    }

    public DateFilter getDateFilter() {
        return dateFilter;
    }

    public String getProperty() {
        return property;
    }
}
