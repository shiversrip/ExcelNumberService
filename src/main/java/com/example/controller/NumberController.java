package com.example.controller;

import com.example.model.NumberFindRequest;
import com.example.service.ExcelService;
import com.example.service.NumberFinderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/numbers")
@Tag(name = "Number Finder API", description = "API для поиска N-ного минимального числа в Excel файле")
public class NumberController {

    private final ExcelService excelService;
    private final NumberFinderService numberFinderService;

    public NumberController(ExcelService excelService, NumberFinderService numberFinderService) {
        this.excelService = excelService;
        this.numberFinderService = numberFinderService;
    }

    @PostMapping("/find-n-min")
    @Operation(summary = "Найти N-ное минимальное число в Excel файле")
    public ResponseEntity<?> findMinimumN(@RequestBody NumberFindRequest request) {
        try {
            if (request.getFilePath() == null || request.getFilePath().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Путь к файлу не может быть пустым");
            }

            if (request.getN() == null || request.getN() <= 0) {
                return ResponseEntity.badRequest().body("N должно быть положительным числом");
            }

            var numbers = excelService.readExcelNumbers(request.getFilePath());

            if (numbers.isEmpty()) {
                return ResponseEntity.badRequest().body("Файл не содержит чисел или пуст");
            }

            if (request.getN() > numbers.size()) {
                return ResponseEntity.badRequest().body(
                        "N не может быть больше количества чисел в файле (" + numbers.size() + ")"
                );
            }

            int result = numberFinderService.findMinimumN(numbers, request.getN());

            Map<String, Object> response = new HashMap<>();
            response.put("MinimumN", result);
            response.put("n", request.getN());
            response.put("totalNumbers", numbers.size());
            response.put("filePath", request.getFilePath());

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Ошибка чтения файла: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка обработки: " + e.getMessage());
        }
    }
}