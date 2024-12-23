package com.api.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Users {
	private String email;
	private String name;
	private String status;
	private String gender;
	private String id;

}
