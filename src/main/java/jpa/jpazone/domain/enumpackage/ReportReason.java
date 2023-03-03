package jpa.jpazone.domain.enumpackage;

public enum ReportReason {
    BAD_LANGUAGE("욕설"),
    COMMERCIAL("광고"),
    ADULT("19금"),
    HATE_CONTENT("싫어요");

    private String title;

    ReportReason(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
