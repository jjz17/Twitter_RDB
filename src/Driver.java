import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

// Driver class to run performance testing
public class Driver {
  private IDatabaseAPI api;
  // Environmental variables
  public static final String url = System.getenv("url");
  public static final String user = System.getenv("user");
  public static final String password = System.getenv("password");

  public Driver(IDatabaseAPI api) {
    this.api = api;
  }

  // Read tweets from tweets.csv and posts them (inserts them into database)
  public void readTweets() {
    String csvFilePath = "data/tweet.csv";

    try {
      BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
      String lineText = null;

      // Skip first line of labels
      lineReader.readLine();

      while ((lineText = lineReader.readLine()) != null) {
        String[] data = lineText.split(",");
        int user_id = Integer.parseInt(data[0]);
        String tweet_text = data[1];

        // Create the tweet object
        Tweet tweet = new Tweet(user_id, tweet_text);

        // Insert the tweet into the database
        api.postTweet(tweet);
      }
    } catch (IOException ex) {
      System.err.println(ex.getMessage());
    }
  }

  // Retrieves random home timelines for the given number of milliseconds and returns average
  // # retrieved/second
  public double randomHomeTimeline(int run_time_ms) {
    List<Integer> users = api.getUsers();
    int count = 0;
    long endTime = System.currentTimeMillis() + run_time_ms;

    while (System.currentTimeMillis() < endTime) {
      int random_user_id = users.get((int) (Math.random() * users.size()));
      // Retrieve timeline (list of tweets)
      List<Tweet> tweets = api.getTimeline(random_user_id);
      count++;
    }

    // Return average retrieval rate per second
    return count / (run_time_ms / 1000.0);
  }

  // Main method
  public static void main(String[] args) {
    Driver driver = new Driver(new MySQLDatabaseAPI());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    driver.api.authenticate(url, user, password);

    // Performance test starting dialogue
    System.out.println("Starting performance test...");

    LocalDateTime start = LocalDateTime.now();

//    driver.readTweets();
    double retrieval_rate = driver.randomHomeTimeline(1000);

    LocalDateTime end = LocalDateTime.now();
    driver.api.closeConnection();

    // Log start and end time of performance test
    System.out.println("Start time: " + dtf.format(start));
    System.out.println("End time: " + dtf.format(end));
    double total_runtime_seconds = start.until(end, ChronoUnit.SECONDS);
    System.out.println("Total runtime: " + total_runtime_seconds + " seconds");

    // Output for profiling rate of tweet posting
//    System.out.println("Average posts/second: " + 1000000.0/total_runtime_seconds);

    // Output for profiling rate of home timeline retrieval
    System.out.println("Average home timelines retrieved/second: " + retrieval_rate);
  }
}
