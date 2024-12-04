package com.gscf.renovation.service;

import com.gscf.renovation.model.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoomFileReaderTest {

    private final RoomFileReader roomFileReader = new RoomFileReader();

    @Test
    void testRoomFileReaderWhenReadFileThenReturnRooms() {
        String fileName = "test.txt";
        List<Room> expectedRooms = List.of(
                new Room(1,1,1, 7),
                new Room(1,2,3, 24),
                new Room(1,2,3, 24),
                new Room(3,12,15, 558),
                new Room(14,6,11, 674)
        );

        List<Room> rooms = roomFileReader.readFile(fileName);

        assertEquals(rooms, expectedRooms);
    }

    @Test
    void testRoomFileReaderWhenFileHasWrongFormatThenThrowError() {
        String fileName = "wrong_format.txt";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> roomFileReader.readFile(fileName));

        assertEquals("Used wrong format for parsing in the source file", thrown.getMessage());
    }

    @Test
    void testRoomFileReaderWhenFileNotExistThenThrowError() {
        String fileName = "not_exist.txt";

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> roomFileReader.readFile(fileName));

        assertEquals("Error occurred during file reading", thrown.getMessage());
    }

}
