package com.example.model;

import io.swagger.v3.oas.annotations.media.Schema;

public class NumberFindRequest {
    @Schema(description = "Путь к файлу XLSX", example = "C:/data/numbers.xlsx")
    private String filePath;

    @Schema(description = "Порядковый номер минимального числа n", example = "2")
    private Integer n;

    public NumberFindRequest() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public NumberFindRequest(String filePath, Integer n) {
        this.filePath = filePath;
        this.n = n;
    }
}
