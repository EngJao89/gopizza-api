package com.gopizza.dto;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserResponseDtoJsonTest {

	@Test
	void serializesAdminWithDefaultJsonMapper() throws Exception {
		JsonMapper mapper = JsonMapper.builder().build();
		UserResponseDTO dto = new UserResponseDTO(
				UUID.randomUUID(),
				"a@b.com",
				"N",
				"62998321586",
				LocalDate.of(1987, 12, 15),
				"11019802065",
				true,
				LocalDateTime.parse("2026-01-21T23:09:01.353502"),
				LocalDateTime.parse("2026-01-22T06:36:49.022834"));
		String json = mapper.writeValueAsString(dto);
		assertThat(json).contains("\"admin\":true");
	}

	@Test
	void serializesAdminWhenFalse() throws Exception {
		JsonMapper mapper = JsonMapper.builder().build();
		UserResponseDTO dto = new UserResponseDTO(
				UUID.randomUUID(),
				"a@b.com",
				"N",
				"62998321586",
				LocalDate.of(1987, 12, 15),
				"11019802065",
				false,
				LocalDateTime.parse("2026-01-21T23:09:01.353502"),
				LocalDateTime.parse("2026-01-22T06:36:49.022834"));
		String json = mapper.writeValueAsString(dto);
		assertThat(json).contains("\"admin\":false");
	}

	@Test
	void serializesAdminWithJackson2CompatibleDefaults() throws Exception {
		JsonMapper mapper = JsonMapper.builderWithJackson2Defaults().build();
		UserResponseDTO dto = new UserResponseDTO(
				UUID.randomUUID(),
				"a@b.com",
				"N",
				"62998321586",
				LocalDate.of(1987, 12, 15),
				"11019802065",
				false,
				LocalDateTime.parse("2026-01-21T23:09:01.353502"),
				LocalDateTime.parse("2026-01-22T06:36:49.022834"));
		String json = mapper.writeValueAsString(dto);
		assertThat(json).contains("\"admin\":false");
	}
}
