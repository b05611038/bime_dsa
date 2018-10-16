public class Calculator {
    public Double ans(String e) {
        // please replace the following null to the answer you calculated
        int leftBreaketIndex = -1;

        String[] slices = e.split(" ");

        while(true) {
            for (int i = 0; i < (slices.length - 4); i++) {
                if (slices[i].equals("(")) {
                    leftBreaketIndex = i;
                    //overwrite the index of the breaket
                }
            }

            String[] newSlices = new String[slices.length - 4];
            for (int i = 0; i < leftBreaketIndex; i++) {
                newSlices[i] = slices[i];
            }
            newSlices[leftBreaketIndex] = cal(slices[leftBreaketIndex + 1], slices[leftBreaketIndex + 2],
                slices[leftBreaketIndex + 3]);
            for (int i = leftBreaketIndex + 1; i < newSlices.length; i++) {
                newSlices[i] = slices[i + 4];
            }

            if (leftBreaketIndex == 0)
                return Double.valueOf(newSlices[0]).doubleValue();

            slices = newSlices;
            leftBreaketIndex = -1;
        }
    }

    public String cal(String left, String sym, String right) {
        double l = Double.valueOf(left).doubleValue();
        double r = Double.valueOf(right).doubleValue();

        if (sym.equals("+")) {
            return Double.toString(l + r);
        } else if (sym.equals("-")) {
            return Double.toString(l - r);
        } else if (sym.equals("*")) {
            return Double.toString(l * r);
        } else if (sym.equals( "/")) {
            return Double.toString(l / r);
        } else {
            return null;
        }
    }
}
