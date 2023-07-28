
import java.util.Calendar;
import module.it.format.EditHistoryEvent;


public class RandomTests {

    public static void main(String[] args) {

        EditHistoryEvent ehi = new EditHistoryEvent(Calendar.getInstance(), 
                (long) Math.round(18.2 * 361));
        
        System.out.println(ehi);
    }
}
