package com.sync.certificate.synccertificatejob.utils;

public enum MonthsOfYearEnum {
    JANUARY("January", "JANUARY", "01"),
    FEBRUARY("February", "FEBRUARY", "02"),
    MARCH("March", "MARCH", "03"),
    APRIL("April", "APRIL", "04"),
    MAY("May", "MAY", "05"),
    JUNE("June", "JUNE", "06"),
    JULY("July", "JULY", "07"),
    AUGUST("August", "AUGUST", "08"),
    SEPTEMBER("September", "SEPTEMBER", "09"),
    OCTOBER("October", "OCTOBER", "10"),
    NOVEMBER("November", "NOVEMBER", "11"),
    DECEMBER("December", "DECEMBER", "12");

    private final String monthTiny;
    private final String monthCapital;
    private final String monthNumber;

    MonthsOfYearEnum(final String monthTiny, final String monthCapital, final String monthNumber) {
        this.monthTiny = monthTiny;
        this.monthCapital = monthCapital;
        this.monthNumber = monthNumber;
    }

    public String getMonthTiny() {
        return monthTiny;
    }

    public String getMonthCapital() {
        return monthCapital;
    }

    public String getMonthNumber() {
        return monthNumber;
    }
}
