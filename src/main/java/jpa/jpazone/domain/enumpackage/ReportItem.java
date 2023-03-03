package jpa.jpazone.domain.enumpackage;

public enum ReportItem {
    BOARD("Board"),
    COMMENT("Comment");

    private String item_title;

    ReportItem(String item_title) {
        this.item_title = item_title;
    }

    public String getItem_title(){
        return item_title;
    }
}
