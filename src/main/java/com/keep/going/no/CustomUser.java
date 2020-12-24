package com.keep.going.no;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomUser {

	private int id;
	private String name;
}
