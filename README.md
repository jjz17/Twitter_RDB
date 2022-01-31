# Twitter-RDB
DS 4300 HW1: Relational vs. NoSQL Twitter Case Study

## Resources

### Read CSV Tutorial 
https://www.javatpoint.com/how-to-read-csv-file-in-java

### Inserting Records into MySQL 
https://www.vogella.com/tutorials/MySQLJava/article.html

#### Performance test for posting tweets
Start time: 2022/01/27 16:52:05
End time: 2022/01/27 16:59:10
Difference: 7:05

Posts/second = 1,000,000 / 425 = 2,352.9412 or 2353

Timelines/second = 24.4
Maybe try adding index on user_id


### Test Results
Starting performance test...
Start time: 2022/01/29 15:04:25
End time: 2022/01/29 15:10:09
Total runtime: 344.0 seconds
Average posts/second: 2906.977

Starting performance test...
Start time: 2022/01/30 21:06:57
End time: 2022/01/30 21:12:58
Total runtime: 361.0 seconds
Average posts/second: 2770.083102493075

Starting performance test...
Start time: 2022/01/30 21:16:25
End time: 2022/01/30 21:17:25
Total runtime: 60.0 seconds
Average home timelines retrieved/second: 872.8333333333334