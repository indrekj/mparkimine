
package eu.urgas.mparkimine.items;

public class CarRegistrationNumber {
    private String number;

    public CarRegistrationNumber(String number) {
        this.number = number;
    }

    public String toString() {
        return this.number;
    }

    public boolean equals(Object o) {
        return o instanceof CarRegistrationNumber
                && ((CarRegistrationNumber) o).number.compareTo(number) == 0;
    }
}
