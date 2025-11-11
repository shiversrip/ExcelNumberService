package com.example.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberFinderService {

    public int findMinimumN(List<Integer> numbers, int n) {
        if (numbers == null || numbers.isEmpty() || n <= 0 || n > numbers.size()) {
            throw new IllegalArgumentException("Invalid input");
        }

        int[] arr = numbers.stream().mapToInt(i -> i).toArray();

        return quickSelect(arr, 0, arr.length - 1, n - 1);
    }

    private int quickSelect(int[] arr, int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }

        int pivot = partition(arr, left, right);

        if (k == pivot) {
            return arr[k];
        }

        else if (k < pivot) {
            return quickSelect(arr, left, pivot - 1, k);
        }

        else {
            return quickSelect(arr, pivot + 1, right, k);
        }
    }

    private int partition(int[] arr, int left, int right) {

        int pivotValue = arr[right];

        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right);

        return storeIndex;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
