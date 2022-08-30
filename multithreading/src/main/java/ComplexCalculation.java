import com.sun.tools.javac.Main;

import java.math.BigInteger;

public class ComplexCalculation {
    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        PowerCalculatingThread t1 = new PowerCalculatingThread(BigInteger.valueOf(10), BigInteger.valueOf(10));
        PowerCalculatingThread t2 = new PowerCalculatingThread(BigInteger.valueOf(10), BigInteger.valueOf(10));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BigInteger result = t1.result.add(t2.result);
        /*
            Calculate result = ( base1 ^ power1 ) + (base2 ^ power2).
            Where each calculation in (..) is calculated on a different thread
        */
        return result;
    }

    public static void main(String[] args) {
        ComplexCalculation m = new ComplexCalculation();
        m.calculateResult(BigInteger.valueOf(10), BigInteger.valueOf(10), BigInteger.valueOf(10), BigInteger.valueOf(10));
    }
    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            result = base.pow(power.intValue());
        }

        public BigInteger getResult() { return result; }
    }
}