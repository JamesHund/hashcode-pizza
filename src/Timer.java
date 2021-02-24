public class Timer {
    private final long startTime;

    public Timer(){
        startTime = System.currentTimeMillis();
    }

    public double getElapsedSeconds(){
        return ((double)(System.currentTimeMillis() - startTime)/1000);
    }
}
