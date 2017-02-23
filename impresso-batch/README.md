## Sliding Processing API


### Sliding on a time window

Customize your `Processor`:

```
class PrintProcessor implements Processor<TimeInterval> {
    @Override
    public void process(Iterable<TimeInterval> timeIntervals) {
        for (TimeInterval t: timeIntervals) {
            System.out.println(t);
        }
    }
}
```
Build and run the sliding window with a Fluent API:

```
SlidingTimeWindow slidingTimeWindow = SlidingTimeWindow.now()
        .every(new Seconds(10)) // Time interval
        .window(5, 2)           // Window length = 5, Slide length = 2
        .doAdmitted(new PrintProcessor())
        .doRetired(new PrintProcessor())
        .build();


slidingTimeWindow.start();
```

see `SlidingWindowExample.java` for complete example.


