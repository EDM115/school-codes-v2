/**
 * Utility class that provides operations to handle dates, using the java.util.Calendar methods
 *
 * @author Pascale Launay
 */
public class Calendar {
  // --------------------------------------------------------------------------
  // Static part
  // --------------------------------------------------------------------------

  /** The months names (in french) */
  private static final String[] MONTHS = {
    "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
    "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
  };

  /** The days names (in french) */
  private static final String[] DAYS = {
    "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"
  };

  // --------------------------------------------------------------------------
  // Instance part
  // --------------------------------------------------------------------------

  /** java.util.Calendar instance */
  private java.util.Calendar calendar;

  /** Constructor. Initialize the calendar to the current date */
  public Calendar() {
    this.calendar = java.util.Calendar.getInstance();
    this.calendar.setTime(new java.util.Date());
  }

  // --------------------------------------------------------------------------
  // Getters
  // --------------------------------------------------------------------------

  /**
   * Give the list of days names
   *
   * @return the days names (in french)
   */
  public String[] getDaysNames() {
    return DAYS;
  }

  /**
   * Give the name of the day of the current date
   *
   * @return the name of the day (in french)
   */
  public String getDayName() {
    int day = this.calendar.get(java.util.Calendar.DAY_OF_WEEK);
    return DAYS[day - 1];
  }

  /**
   * Give the number of the day in the month of the current date
   *
   * @return the number of the day (between 1 and 31)
   */
  public int getDayNumber() {
    return this.calendar.get(java.util.Calendar.DAY_OF_MONTH);
  }

  /**
   * Give the list of months names
   *
   * @return the months names (in french)
   */
  public String[] getMonthsNames() {
    return MONTHS;
  }

  /**
   * Give the name of the month of the current date
   *
   * @return the name of the month (in french)
   */
  public String getMonthName() {
    int month = this.calendar.get(java.util.Calendar.MONTH);
    return MONTHS[month];
  }

  /**
   * Give the number of the month of the current date
   *
   * @return the number of the month (between 1 and 12)
   */
  public int getMonthNumber() {
    return this.calendar.get(java.util.Calendar.MONTH) + 1;
  }

  /**
   * Give the number of the year of the current date
   *
   * @return the number of the year
   */
  public int getYear() {
    return this.calendar.get(java.util.Calendar.YEAR);
  }

  // --------------------------------------------------------------------------
  // Setters
  // --------------------------------------------------------------------------

  /** Change the calendar to the next day */
  public void nextDay() {
    this.calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
  }

  /** Change the calendar to the previous day */
  public void previousDay() {
    int month = this.calendar.get(java.util.Calendar.MONTH);
    int day = this.calendar.get(java.util.Calendar.DAY_OF_MONTH);
    this.calendar.add(java.util.Calendar.DAY_OF_MONTH, -1);
    // System.out.println("Previous: " + day + "/" + month + " -> " +
    // this.calendar.get(java.util.Calendar.DAY_OF_MONTH) + "/" +
    // this.calendar.get(java.util.Calendar.MONTH));
  }

  /**
   * Change the calendar to the given month and update the day number to the last day of the month
   * if the current day number is greater
   *
   * @param monthNumber the new month number (between 1 and 12, included)
   */
  public void setMonth(int month) {
    if (month >= 1 && month <= 12) {
      int lastDay = lastDay(month, this.calendar.get(java.util.Calendar.YEAR));
      if (this.calendar.get(java.util.Calendar.DAY_OF_MONTH) > lastDay) {
        this.calendar.set(java.util.Calendar.DAY_OF_MONTH, lastDay);
      }
      this.calendar.set(java.util.Calendar.MONTH, month - 1);
    }
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  /**
   * Give the number of the last day of the given month
   *
   * @param month a month number (between 1 and 12)
   * @param year a year number
   * @return the number of the last day in the month (28, 29, 30 or 31)
   */
  private int lastDay(int month, int year) {
    switch (month) {
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      case 2:
        if (year % 4 == 4 && (year % 100 != 0 || year % 400 == 0)) {
          return 29;
        } else {
          return 28;
        }
      default:
        return 31;
    }
  }
}
