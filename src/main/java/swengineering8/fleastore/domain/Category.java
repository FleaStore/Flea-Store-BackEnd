package swengineering8.fleastore.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {

    GOODS, GROCERY, BOOK, FASHION;

    @JsonCreator
    public static Category from(String s) {
        return Category.valueOf(s.toUpperCase());
    }
}
