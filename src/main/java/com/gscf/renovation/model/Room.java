package com.gscf.renovation.model;

import java.util.Objects;

public record Room (Integer length, Integer width, Integer height, Integer wallpaperOrder) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;

        return length.equals(room.length) && width.equals(room.width) && height.equals(room.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width, height);
    }
}
