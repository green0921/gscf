package com.gscf.renovation.model;

import java.util.List;

public record Renovation (Long totalWallpaperOrder, List<Room> cubicShapeRooms, List<Room> duplicationRooms) {}
