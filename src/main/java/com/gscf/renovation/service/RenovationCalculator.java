package com.gscf.renovation.service;

import com.gscf.renovation.model.Renovation;
import com.gscf.renovation.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RenovationCalculator {

    private static final Logger logger = Logger.getLogger(RenovationCalculator.class.getName());

    @Autowired
    private RoomFileReader roomFileReader;

    public Renovation calculate(String fileName) {
        List<Room> rooms = roomFileReader.readFile(fileName);
        long totalWallpaperOrder = calculateTotalWallpaperOrder(rooms);
        LinkedHashSet<Room> cubicRooms = getCubicRooms(rooms);
        Set<Room> duplicateRooms = findDuplicateRooms(rooms);

        logging(totalWallpaperOrder, cubicRooms, duplicateRooms);
        return new Renovation(totalWallpaperOrder, cubicRooms, duplicateRooms);
    }

    private long calculateTotalWallpaperOrder(List<Room> rooms) {
        return rooms.stream()
                .mapToInt(Room::wallpaperOrder)
                .sum();
    }

    private LinkedHashSet<Room> getCubicRooms(List<Room> rooms) {
        return rooms.stream()
                .filter(this::isCubic)
                .sorted(Comparator.comparingInt(Room::wallpaperOrder).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public boolean isCubic(Room room) {
        return room.length().equals(room.width()) && room.width().equals(room.height());
    }

    private Set<Room> findDuplicateRooms(List<Room> rooms) {
        return rooms.stream()
                .collect(Collectors.groupingBy(room -> room, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private void logging(long totalWallpaperOder, Set<Room> cubicRooms, Set<Room> duplicateRooms) {
        String cubicRoomsString = cubicRooms.stream()
                .map(Room::toString)
                .collect(Collectors.joining("\n"));
        String duplicateRoomsString = duplicateRooms.stream()
                .map(Room::toString)
                .collect(Collectors.joining("\n"));
         logger.info("""
                
                Total wallpaper order needed: %d square feet
                
                Cubic Rooms (sorted by wallpaper order descending):
                %s
                
                Duplicate Rooms:
                %s
                """.formatted(totalWallpaperOder, cubicRoomsString, duplicateRoomsString));
    }
}
