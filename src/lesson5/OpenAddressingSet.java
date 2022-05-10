package lesson5;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private enum Status {
        DELETED
    }

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null && current != Status.DELETED) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     */

    // Трудоёмкость Т: О(1) в лучшем и О(N) в худшем случае
    // Ресурскоёмкость R: O(1)
    @Override
    public boolean remove(Object o) {
        int index = startingIndex(o);
        Object removable = storage[index];
        while (removable != null) {
            if (removable.equals(o)) {
                storage[index] = Status.DELETED;
                size--;
                return true;
            }
            index = (index + 1) % capacity;
            removable = storage[index];
        }
        return false;
    }

    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingIterator();
    }

    public class OpenAddressingIterator implements Iterator<T> {
        T current = null;
        int index = 0;
        int counter = 0;

        // Трудоёмкость Т: О(1)
        // Ресурскоёмкость R: O(1)
        @Override
        public boolean hasNext() {
            return counter < size;
        }

        // Трудоёмкость Т: О(1)
        // Ресурскоёмкость R: O(1)
        @Override
        public T next() {
            if (hasNext()) {
                while (storage[index] == null || storage[index] == Status.DELETED) {
                    index++;
                }
                current = (T) storage[index];
                index++;
                counter++;
                return current;
            } else {
                throw new NoSuchElementException();
            }
        }

        // Трудоёмкость Т: О(1)
        // Ресурскоёмкость R: O(1)
        @Override
        public void remove() {
            if (current != null) {
                storage[--index] = Status.DELETED;
                current = null;
                size--;
                counter--;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
