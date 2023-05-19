package publisher;

import contract.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Publisher implements Runnable{
    private final List<Subscriber> subscribers;
    public volatile int publishersCount;

    public Publisher(){
        subscribers = new ArrayList<>();
        publishersCount = 0;
    }

    public void subscribe(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public ArrayList<Double> generateRandomNumber(int upperBound) {
        Random random = new Random();
        ArrayList<Double> list = new ArrayList<>();

            System.out.println("generateRandomNumber thread running...");
            for (int i = 0; i < upperBound; i++) {
                double rand = random.nextDouble(1000); //int 0 - 999
                list.add(rand);
            }

            return list;

    }

    public double averageXMod3(ArrayList<Double> list) {
        double count = 0;

        System.out.println("averageXMod3 thread running...");
            for (Double listEach : list) {
                double hold = listEach % 3;
                count += hold;
            }
            double result = count / list.size();

            return  result;
    }

    private synchronized void publication(double average){
        for(Subscriber subscriber : subscribers){
            subscriber.email(average);
        }
    }


    @Override
    public void run() {

        //for generating infinitive threads we used while true
        while (true) {

            ArrayList<Double> numbers = generateRandomNumber(1000000);
            double average = averageXMod3(numbers);
            publication(average);
            publishersCount++;

            //considering the subscriber will receive two notifications because we set two publishers.
            if (publishersCount >= 2) {
                synchronized (this) {
                    this.notify();
                }
            }

            try {
                Thread.sleep(1000); //It will wait 1 second before the next set of numbers.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }




}
