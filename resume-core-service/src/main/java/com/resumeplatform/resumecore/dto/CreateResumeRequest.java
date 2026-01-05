package com.resumeplatform.resumecore.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateResumeRequest {

    @NotBlank(message = "Resume title is required")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
