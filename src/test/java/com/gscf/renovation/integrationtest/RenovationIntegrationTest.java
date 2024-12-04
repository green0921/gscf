package com.gscf.renovation.integrationtest;

import com.gscf.renovation.model.Renovation;
import com.gscf.renovation.model.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RenovationIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void renovationIntegrationTestWhenCallingRenovationEndpoint() {
		long expectedTotalWallPaper = 1592486L;
		List<Room> expectedCubicRoomsWithRightOrder = List.of(
				new Room(28, 28, 28, 5488),
				new Room(15, 15, 15, 1575),
				new Room(12, 12, 12, 1008),
				new Room(9, 9, 9, 567),
				new Room(7, 7, 7, 343)
		);
		int expectedDuplicationRoomsSize = 13;

		ResponseEntity<Renovation> response = restTemplate.getForEntity("/renovation", Renovation.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(expectedTotalWallPaper, response.getBody().totalWallpaperOrder());
		assertEquals(expectedCubicRoomsWithRightOrder, List.copyOf(response.getBody().cubicShapeRooms()));
		assertEquals(expectedDuplicationRoomsSize, response.getBody().duplicationRooms().size());
	}

}
