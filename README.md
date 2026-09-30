# CMPUT 301: Assignment 0
- I had to make an app called "Rapid Recall." The user inputs the sequence length they want from 1-10, and it then flashes on the screen
  that amount of random digits, the user then has to guess the random sequence. It gives back results immediately- saying if they got the
  sequence correct overall, and shows the individual digits on the screen in a vertical list as boxes- green box for the digit they got
  right and red if wrong. This app also has a log records screen and a summary screen, and total games played functionality in home screen

## Student Details
- **Full Name:** `Leighan Baroro`
- **CCID:** `1886935`

## References and Resources

- [Mastering Kotlin Getter and Setter: Best Practices]
  LINK: https://www.dhiwise.com/post/exploring-kotlin-getter-setter-enhancing-your-code

- [color picker - Google Search]

- [Android Jetpack Compose Lazy column items with index? - Stack Overflow]
  LINK: https://stackoverflow.com/questions/70755946/android-jetpack-compose-lazy-column-items-with-index
- Gabriele Mariotti answered Jan 18, 2022 at 13:03
Code Referenced:
```
LazyColumn() {     itemsIndexed(viewModel.list) { index, item ->         //..     } }
```

- [android - How LaunchedEffect works - Stack Overflow]
  LINK: https://stackoverflow.com/questions/78569757/how-launchedeffect-works

- [Exception and error handling | Kotlin Documentation]
  LINK: https://kotlinlang.org/docs/exceptions.html#the-finally-block

  - [Get the Current Date/Time in Kotlin | Baeldung on Kotlin]
  LINK: https://www.baeldung.com/kotlin/current-date-time

- Used Microsoft, Copilot (9/27/2026) 8:38pm, "How to get current time in Kotlin", so I can get a timestamp for my log. The LocalDateTime() wasn't working the way I wanted to,
Code Used: 
```
val millis = System.currentTimeMillis()
val date = Date(millis)
println(date)
```

- [android jetpack compose - How to show a composable just for e few seconds? - Stack Overflow]
  LINK: https://stackoverflow.com/questions/73333287/how-to-show-a-composable-just-for-e-few-seconds
- Thracian answered Aug 12, 2022 at 11:21
Code Referenced:
```
@Composable
private fun TimedLayout() {
    var show by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit){
        delay(5000)
        show = false
    }
    Column(modifier=Modifier.fillMaxSize()) {
        Text("Box showing: $show")
        if(show){
            Box{
                Text(text = "BlaBla"    )
            }
        }
    }
}
```

## Verbal Collaboration
N/A. 
