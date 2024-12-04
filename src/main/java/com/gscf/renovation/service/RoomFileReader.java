package com.gscf.renovation.service;

import com.gscf.renovation.model.Room;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.gscf.constants.Constants.ROOM_FILE_DELIMITER;
import static com.gscf.constants.Constants.ROOM_FILE_PATTERN;

@Component
public class RoomFileReader {

    private static final Logger logger = Logger.getLogger(RoomFileReader.class.getName());

    public List<Room> readFile(String fileName) {
        Resource textFile = new ClassPathResource(fileName);
        List<Room> rooms = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(textFile.getURI()))) {
            lines.forEach(line -> {
                if(!line.matches(ROOM_FILE_PATTERN)) {
                    throw new RuntimeException("Used wrong format for parsing in the source file");
                }
                String[] parts = line.split(ROOM_FILE_DELIMITER);
                int length = Integer.parseInt(parts[0].trim());
                int width = Integer.parseInt(parts[1].trim());
                int height = Integer.parseInt(parts[2].trim());
                int wallpaperOrder = calculateWallpaperOrder(length, width, height);
                rooms.add(new Room(length, width, height, wallpaperOrder));
            });
        } catch (IOException e) {
            throw new RuntimeException("Error occurred during file reading");
        }

        logger.info("File reading was successful!");
        return rooms;
    }

    int calculateWallpaperOrder(int length, int width, int height) {
        int side1 = length * width;
        int side2 = width * height;
        int side3 = height * length;
        int smallestSide = Math.min(side1, Math.min(side2, side3));
        return 2 * (side1 + side2 + side3) + smallestSide;
    }
}
