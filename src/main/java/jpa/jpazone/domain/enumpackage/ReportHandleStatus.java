package jpa.jpazone.domain.enumpackage;

public enum ReportHandleStatus {
    REPORTED("신고됨"),
    PROCEEDING("처리중"),
    COMPLETE("처리완료"),
    DENIED("신고이유부적합");

    private String status_title;

    ReportHandleStatus(String status_title) {
        this.status_title = status_title;
    }

    public String getTitle(){
        return status_title;
    }
}
