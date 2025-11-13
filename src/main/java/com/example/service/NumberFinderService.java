package com.example.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NumberFinderService {

    public int findMinimumN(Set<Integer> numbers, int n) {
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

        int pivotIndex = medianOfThree(arr, left, right);
        pivotIndex = partition(arr, left, right, pivotIndex);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, right, k);
        }
    }

    private int medianOfThree(int[] arr, int left, int right) {
        int mid = left + (right - left) / 2;

        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[mid] > arr[right]) {
            swap(arr, mid, right);
        }

        return mid;
    }

    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);

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
