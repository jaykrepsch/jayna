package me.jayna.service.dto;

public class EnumDTO {

	private String name;
	
	private String translationKey;
	
	public EnumDTO() {}
	
	public EnumDTO(String name) {
		setName(name);
		setTranslationKey("enums." + name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTranslationKey() {
		return translationKey;
	}

	public void setTranslationKey(String translationKey) {
		this.translationKey = translationKey;
	}
}
