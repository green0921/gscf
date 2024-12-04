package com.gscf.renovation.model;

import java.util.LinkedHashSet;
import java.util.Set;

public record Renovation (Long totalSquareFeetOfWallpaper, LinkedHashSet<Room> cubicShapeRooms, Set<Room> duplicationRooms) {}
