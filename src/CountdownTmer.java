public class CountdownTmer {
    private int secondsRemaining;

    public CountdownTmer(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("Timer must be set for a positive duration.");
        }
        this.secondsRemaining = minutes * 60; // Convert minutes to seconds
    }

    public boolean startCountdown() {//returns false when the time is over//
        while (secondsRemaining > 0) {
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            secondsRemaining--;
        }
       return false;
    }

   // private void displayTimeRemaining() {
       
    }//