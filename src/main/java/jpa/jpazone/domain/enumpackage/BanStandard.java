package jpa.jpazone.domain.enumpackage;

public enum BanStandard {
    COUNT(3),
    MINUTE(30),
    DAY(3);

    private int count;

    private BanStandard(int count) {
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }
}
