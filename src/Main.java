import publisher.Publisher;
import subscriber.Subscriber;

public class Main {
    public static void main(String[] args) {
        Publisher publisher1 = new Publisher();
        Publisher publisher2 = new Publisher();

        Subscriber subscriber1 = new Subscriber("Eduardo");

        publisher1.subscribe(subscriber1);
        publisher2.subscribe(subscriber1);

        Thread t1 = new Thread(publisher1);
        Thread t2 = new Thread(publisher2);

        t1.start();
        t2.start();

        try {
            synchronized (publisher1){
                while (publisher1.publishersCount < 2){
                    publisher1.wait();
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}