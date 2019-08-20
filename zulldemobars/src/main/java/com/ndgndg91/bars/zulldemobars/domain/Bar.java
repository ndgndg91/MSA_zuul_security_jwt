package com.ndgndg91.bars.zulldemobars.domain;

public class Bar {
    private long id;
    private String name;

    public Bar(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
