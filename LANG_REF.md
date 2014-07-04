Argument accessor
--

- When replacing `~[argumentKey]~` for the actual object, `toString()` method is called.
- Dot notation is only allowed within for loops.

```
~[argumentKey]~
```





If
--

- Else is trigeered when the value of the key `[argumentKey]` is false.
- Only booleans are allowed to perform the if condition.

```
~if [argumentKey]:
// if statements
~else:
// else statements
~end~
```






For
--

- Else is triggered when `[argumentKey]` is **null** or **has zero elements**.
- `[argumentKey]` must be of _TemplateCollection_ type.
- `x` is a name of your choice.

```
~for x in [argumentKey]:
// for statements
~else:
// else statements
~end~
```





If within for
--

Hence during a for loop you can access `x` mapped properties, they can be used as if conditions aswell.
```
~for x in [argumentKey]:
// for statements

~if [x.aBoolean]:
// if statements
~end~

// more for statements
~else:
// else statements
~end~
```

Reserved expressions
--

`~content~`	Stands for a placeholder within master templates. Where the templates that use some master template is put after rendered.

`~end~`		Stands for the end of a command. Must be used after for/if/else clauses.	  




