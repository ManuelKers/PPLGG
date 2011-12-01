package gui;

public class CloudMapThread extends Thread {

    CloudMapPanel cloudMap;
    boolean stop;

    public CloudMapThread (CloudMapPanel cloudMap) {
        this.cloudMap = cloudMap;
        stop = false;
    }

    @Override
    public void run() {

        while (cloudMap.getNoMaps()<100 && !stop) {

            cloudMap.addMaps( 1 );
            try {
                Thread.sleep( 10 );
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void stopAdding() {
        stop = true;
        
    }
}