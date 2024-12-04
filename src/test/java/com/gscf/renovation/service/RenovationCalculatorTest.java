package com.gscf.renovation.service;

import com.gscf.renovation.model.Renovation;
import com.gscf.renovation.model.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RenovationCalculatorTest {

	@Mock
	private RoomFileReader roomFileReader;
	@InjectMocks
	private RenovationCalculator renovationCalculator;

	@Test
	void testRenovationCalculatorWhenCalculateThenReturnRenovation() {
		String fileName = "test.txt";
		List<Room> mockRooms = List.of(
				new Room(1,1,1, 7),
				new Room(1,2,3, 24),
				new Room(1,2,3, 24),
				new Room(3,12,15, 558),
				new Room(14,6,11, 674)
		);
		Renovation expectedRenovation = new Renovation(
				1287L,
				new LinkedHashSet<>(Set.of(new Room(1,1,1, 7))),
				Set.of(new Room(1,2,3, 24))
		);
		when(roomFileReader.readFile(fileName)).thenReturn(mockRooms);

		Renovation calculate = renovationCalculator.calculate(fileName);

		assertEquals(calculate, expectedRenovation);
	}

}
