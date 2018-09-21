public class NaiveCalendar implements MyCalendar {
    //January, 1 A.D. 1 is Monday
    private static final int[] DAYS_OF_MONTH = {0, 31, 28, 31, 30, 31, 30, 
        31, 31, 30, 31, 30, 31};
    private static final String[] WEEK_DAY = {"SUN", "MON", "TUE", "WED", 
        "THU", "FRI", "SAT"};
    private static final int FIRST_DAY_OF_YEAR_ONE = 1;

    private int year;
    private int month;
    private int dayNumOfMonth;
    private boolean leap;
    private int leapCount;
    private int firstDayOYear;
    private int firstDayOfMonth;

    public NaiveCalendar(int year, int month) { // constructor
        if (year <= 0) {
            throw new RuntimeException("Invalid year");
        } 
        if (month < 1 || month > 12) {
            throw new RuntimeException("Invalid month");
        }

        this.year = year;
        this.month = month;

        leap = calculateLeap();
        dayNumOfMonth = calculateDayNumOfMonth();
        
        leapCount = calculateLeapCount();
        firstDayOYear = calculateFirstDayOfYear();
        firstDayOfMonth = calculateFirstDayOfMonth();
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDayNumOfMonth() {
        return dayNumOfMonth;
    }

    @Override
    public boolean isLeap() {
        return leap;
    }

    private boolean calculateLeap() {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
            return true;
        }
        else {
            return false;
        }
    }

    private int calculateLeapCount() {
        int beforeYear = year - 1;
        //count the leap year before the year you choose
        return (beforeYear / 4 - beforeYear / 100 + beforeYear / 400);
    }
    
    private int calculateDayNumOfMonth() {
        int dayNum = DAYS_OF_MONTH[month];
        
        if (month == 2 && leap) {
            dayNum++;
        }
        
        return dayNum;
    }

    private int calculateFirstDayOfYear() {
        int beforeYear = year - 1;
        return (beforeYear * 365 + leapCount + FIRST_DAY_OF_YEAR_ONE) % 7;
    }

    private int calculateFirstDayOfMonth() {
        int firstDay = firstDayOYear;
        if (month > 1) {
            for (int i = 0; i < month; i++) {
                firstDay = firstDay + DAYS_OF_MONTH[i];
                if (leap && i == 2) {
                    firstDay++;
                }
            }
        }
        return (firstDay % 7);
    }
    
    @Override
    public void printCalendar() {
        int line_count = 0;
        for (String day : WEEK_DAY) {
            System.out.print(formatEntry(day));
        }
        System.out.println();

        for (int i = 0; i < firstDayOfMonth; i++) {
            System.out.print(formatEntry(""));
            line_count++;
        }

        for (int i = 1; i <= dayNumOfMonth; i++) {
            System.out.print(formatEntry(String.valueOf(i)));
            line_count++;
            if (line_count == 7) {
                System.out.println();
                line_count = 0;
            }
        }
        /*  todo: use function formatEntry and System.out.print to print the calendar
            System.out.print(formatEntry("")); // get "    "
            System.out.print(formatEntry(String.valueOf(10))); // get "  10" 
        */
        System.out.println();
    }

    private static String formatEntry(String entry) {
        return String.format("%1$4s", entry);
    }
}
