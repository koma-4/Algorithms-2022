package lesson7;

import kotlin.NotImplementedError;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    // Трудоёмкость Т: О(длина первой строки * длина второй строки)
    // Ресурскоёмкость R: O(длина первой строки * длина второй строки)
    public static String longestCommonSubSequence(String first, String second) {
        if (first.isEmpty()||second.isEmpty()) return "";
        char[][][] matrix = new char[first.length() + 1][second.length() + 1][];

        for (int i = 0; i <= first.length(); i++) {
            for (int j = 0; j <= second.length(); j++) {
                if (i == 0 || j == 0) matrix[i][j] = new char[0];
                else if (first.charAt(i-1) == second.charAt(j-1)){
                    matrix[i][j] = Arrays.copyOf(matrix[i-1][j-1], matrix[i-1][j-1].length + 1);
                    matrix[i][j][matrix[i-1][j-1].length] = first.charAt(i - 1);
                } else
                    matrix[i][j] = matrix[i-1][j].length > matrix[i][j-1].length ? matrix[i-1][j]: matrix[i][j-1];
            }
        }
        return new String(matrix[first.length()][second.length()]);
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоёмкость Т: О(N * logN)
    // Ресурскоёмкость R: O(N)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.isEmpty()) return list;
        int[] d = new int[list.size() + 1];
        int[] seq = new int[list.size() + 1];
        int[] prev = new int[list.size()];
        int length = 0;
        seq[0] = -1;
        d[0] = Integer.MAX_VALUE;
        for (int i = list.size() - 1; i >= 0; i--) {
            int j = binarySearch(d, list.get(i));
            if (d[j] < list.get(i) && d[j - 1] > list.get(i)) {
                d[j] = list.get(i);
                seq[j] = i;
                prev[i] = seq[j - 1];
                length = Math.max(length, j);
            }
        }
        if (length == 1) return List.of(list.get(0));
        ArrayList<Integer> out = new ArrayList<>();
        int p = seq[length];
        while (p != -1) {
            out.add(list.get(p));
            p = prev[p];
        }
        return out;
    }

    private static int binarySearch(int[] x, int y) {
        int l = 0;
        int r = x.length - 1;
        while (r - l > 1) {
            int m = (l + r) / 2;
            if (y < x[m])
                l = m;
            else
                r = m;
        }
        return r;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }
}
