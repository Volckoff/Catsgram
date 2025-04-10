package ru.yandex.practicum.catsgram.controller;

public enum SortOrder {
    ASCENDING, DESCENDING;

    // Преобразует строку в элемент перечисления
    public static SortOrder from(String order) {
        if (order == null) {
            return DESCENDING;
        }
        switch (order.toLowerCase()) {
            case "ascending":
            case "asc":
                return ASCENDING;
            case "descending":
            case "desc":
                return DESCENDING;
            default:
                return DESCENDING;
        }
    }
}
