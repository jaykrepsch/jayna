package me.jayna.service.dto;

import java.util.ArrayList;
import java.util.List;

public class EnumListDTO {
	
	private String name;
	
	private List<EnumDTO> items = new ArrayList<>();
	
	public EnumListDTO() {}
	
	public EnumListDTO(String name, List<EnumDTO> items) {
		setName(name);
		setItems(items);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EnumDTO> getItems() {
		return items;
	}

	public void setItems(List<EnumDTO> items) {
		this.items = items;
	}
}
