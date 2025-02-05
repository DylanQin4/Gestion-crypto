package mg.itu.cloud.crypto;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CryptoService {
    private final CryptoRepository cryptoRepository;
    private final PriceHistoryService priceHistoryService;

    public CryptoService(CryptoRepository cryptoRepository, PriceHistoryService priceHistoryService) {
        this.cryptoRepository = cryptoRepository;
        this.priceHistoryService = priceHistoryService;
    }

    public List<Crypto> getAllCrypto() {
        return cryptoRepository.findAll();
    }

    public Optional<Crypto> getCryptoById(Integer idCrypto) {
        return cryptoRepository.findById(idCrypto);
    }
     // Method to calculate the median of an array
    public static double getMedian(double[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        if (n % 2 == 0) {
            // If even, median is the average of the two middle elements
            return (arr[n / 2 - 1] + arr[n / 2]) / 2.0;
        } else {
            // If odd, median is the middle element
            return arr[n / 2];
        }
    }

    // Method to calculate Q1 (First Quartile)
    public static double getQ1(double[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        double[] lowerHalf = Arrays.copyOfRange(arr, 0, n / 2); // First half of the data
        return getMedian(lowerHalf);
    }

    // Method to calculate Q3 (Third Quartile)
    public static double getQ3(double[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        double[] upperHalf;
        if (n % 2 == 0) {
            // If even, upper half starts from n/2
            upperHalf = Arrays.copyOfRange(arr, n / 2, n);
        } else {
            // If odd, upper half starts from n/2 + 1
            upperHalf = Arrays.copyOfRange(arr, n / 2 + 1, n);
        }
        return getMedian(upperHalf);
    }
    // Method to calculate Standard Deviation
    public static double getStandardDeviation(double[] arr) {
        
        double mean = getAverage(arr);

        double varianceSum = 0;
        for (double num : arr) {
            varianceSum += Math.pow(num - mean, 2);
        }
        double variance = varianceSum / arr.length;

        return Math.sqrt(variance);
    }

    // Method to calculate Minimum
    public static double getMin(double[] arr) {
        double min = Double.MAX_VALUE;
        for (double num : arr) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    // Method to calculate Maximum
    public static double getMax(double[] arr) {
        double max = Double.MIN_VALUE;
        for (double num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    // Method to calculate Average
    public static double getAverage(double[] arr) {
        double sum = 0;
        for (double num : arr) {
            sum += num;
        }
        return sum / arr.length;
    }
}
