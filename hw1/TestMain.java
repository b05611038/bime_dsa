public class TestMain {

    public static void main(String[] args) {
        final int year = 2018;
        final int month = 9;

        //use interface as parameter type
        MyCalendar calendar = new NaiveCalendar(year, month);  
        
        System.out.println(String.format("%d/%d has %d days", 
                calendar.getYear(), calendar.getMonth(), 
                calendar.getDayNumOfMonth()));
        
        System.out.println(String.format("Is %d/%d leap?%nAnswer: %b", 
                calendar.getYear(), calendar.getMonth(), calendar.isLeap()));
        
        System.out.println();
        System.out.println(String.format("Calendar of %d/%d:", 
                calendar.getYear(), calendar.getMonth()));
        calendar.printCalendar();
    }
            
}
