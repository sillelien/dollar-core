

#The Dollar Core API [![Alpha](https://img.shields.io/badge/Status-Alpha-yellowgreen.svg?style=flat)](http://github.com/sillelien/dollar-core)


You'll need this repository:



```
  <repositories>
    <repository>
        <id>s3-releases</id>
        <url>http://sillelien-maven-repo.s3-website-eu-west-1.amazonaws.com/release</url>
    </repository>
</repositories>
```

And the maven co-ordinates are:

```
    <dependency>
        <groupId>com.sillelien</groupId>
        <artifactId>dollar-core</artifactId>
        <version>0.2.114</version>
    </dependency>
```

An example of a project using this is [Tutum API](https://github.com/sillelien/tutum-api) which is aslo very much a work in progress.

-------

**If you use this project please consider giving us a star on [GitHub](http://github.com/sillelien/dollar-core). Also if you can spare 30 secs of your time please let us know your priorities here https://sillelien.wufoo.com/forms/zv51vc704q9ary/  - thanks, that really helps!**

Please contact us through chat or through GitHub Issues.

[![GitHub Issues](https://img.shields.io/github/issues/sillelien/dollar-core.svg)](https://github.com/sillelien/dollar-core/issues)

[![Join the chat at https://gitter.im/sillelien/dollar-core](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/sillelien/dollar-core?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

-------

# Getting Started

Every example you see below is *Java* I emphasize that as it may not look familiar to you, that is intentional - I have done my best to make it clear that you are working with *untyped* objects, to avoid confusion.

All static methods such as $() can be accessed by importing `import static com.sillelien.dollar.api.DollarStatic.*;`


## Creating objects

Let's start with our `Hello World` example.

```java

    $("Hello World");

```

What we've done here is create an object of type `var`. `var` objects have an underlying type, much like JavaScript objects, however you can apply many operations without concern for it's type. So let's assign a variable.

```java

    var myObject= $("Hello World");

```

`var` objects are immutable, any changes you make create a new `var` object. This is the *most important thing to remember*, you must use the results of a mutation to an object - the original object was not mutated. For example


```java
    var myObject= $("Hello World");
    myObject.$append($("Goodbye"));
    assert myObject.toString().equals("Hello World");
```

The original object is unchanged by the `$append()` call, so instead we do this:

```java
    var myObject= $("Hello World");
    var newObject= myObject.$append($("Goodbye"));
    assert ! newObject.toString().equals("Hello World");
```

## Creating Lists and Maps

Creating a list is as simple as using the `$list()` static method which takes a list of any type of object

```java
    var myList=$list(1,2,3,"four");
    var second=myList.$(1);
    assert second.toInteger() == 2;
```  

If we place Pairs (Pairs are defined as simply a Map with a single entry) together using the $map() method we can also create maps

```java
    var map =$map(
                    $("one",1),
                    $("two",2)
            );
   assert map.toString().equals("{\"one\":1,\"two\":2}");            
```  

For shorthand we can also overload the $() method:

```java
    var map =$(
                    $("one",1),
                    $("two",2)
            );
   assert map.toString().equals("{\"one\":1,\"two\":2}");            
```  

## Working with Lists



# Reference

## Dollar Methods

You will hopefully notice the pattern that any method that deals solely with var objects has a `$` symbol preceeding it. Methods that return or work with other Java objects should not have that symbol.

## Factory Methods

To use the factory methods to create `var` objects you just need to statically import DollarStatic.*

### $(Object)

This constructor will attempt to create a `var` object from whatever is passed in, the following are the types that Dollar recognizes and the implementation type created.

* null - DollarVoid

* Boolean - DollarBoolean

* Pipeable - DollarLambda

* Long, Integer, Short - DollarInteger

* Double, BigDecimal, Float - DollarDecimal

* File, String, InputStream - DollarString

* String (Json Array), JsonArray, JSONArray, ArrayNode, ImmutableList, List, Collection, Array - DollarList

* String (Json Object), JsonObject, JSONObject, ObjectNode,  MultiMap, ImmutableJsonObject, Map - DollarMap

* URI - DollarURI

* Date, LocalDateTime, Instant - DollarDate

* Range - DollarRange

* InputStream - DollarStream


Otherwise Dollar will attempt to convert the Java object to a JsonObject and then into a DollarMap.


### $void()

This creates a void `var` (the implementation class is DollarVoid). Voids, unlike nulls don't have any characteristics, including type. They literally represent nothing.


### $null(Type)

This creates a null `var` which has a type of that specified in the factory method.


### $range(from,to)

This will create a Range `var`.


### $uri(URI)

This will create a URI `var`.


### $uri(String)

This will create a URI `var`.


### $date(Date)

This will create a date `var`.


### $date(LocalDateTime)

This will create a date `var`.


### $string(String)

This will create a string `var` object without any attempt to parse the string.


### $list(List)

This will parse a YAML file into a `var` object.


### $yaml(File)

This will parse a YAML file into a `var` object.


### $yaml(String)

This will parse a YAML string into a `var` object.


### $(lambda)

This will create a delayed evaluation `var` object.


## Collection style methods on `var`

### $(String) or $get(String)

Returns a var object that corresponds to the string supplied. Usually this means a map member keyed by this value.

### $append(var)

Adds the supplied var to a copy of this object, usually that means adding a member to list. Note var objects are immutable.

### $prepend(var)

Inserts the supplied var at the beginning of a copy of this object, usually that means prepending a member to a list. Note var objects are immutable.

### $contains(var) / $containsValue(var)

Returns a Boolean var which will equate to `true` if the `var` supplied is contained within the current `var`.

### $has(String) / $has(var)

Returns true if the supplied key is within this `var`.

### $size() / size()

Returns the size as either a `var` or an int.

### $(String, Object) $set(String, Object) $set(var, Object)

Creates a new version of the var object with the child set to the value supplied. This is pretty much the same as jQuery.


### $removeByKey(String) 

Removes the `var` object identified with the supplied key from this `var` object.

### remove(Object) $remove(var)

Removes the object supplied from this object.

## Other Methods on `var`


### $mimeType 

Returns a valid Mime Type for this object.

## Type conversion/modification methods

### $as(Type)

Attempt to cast this type to the specificed Type.


### $split()

Convert this object into a single var list object.


### $list()

Convert this object into an immutable Java list of var objects.

### $map()

Convert this object into an immutable Java map of var objects.

### $yaml()

Convert this object into it's YAML string equivalent.

### $pairKey()

Treat this object as a Pair (single entry map) and get the Pair's key.

### $pairValue()

Treat this object as a Pair (single entry map) and get the Pair's value.

### toList()

Convert this to an immutable Java List.

### toStrings()

Convert this to an immutable list of Java strings.

### toMap()

Convert this to a Java Map.

### toStream()

Convert this to an Input Stream.

## Type enquiry methods

### type()

Returns the definitive type of this object, this will trigger execution in dynamic values.

### is(Type)

Returns true if this object is of the supplied Type.

### collection()

Returns true if this object is a collection.

### dynamic() 

Returns true if this object is dynamically evaluated.

### infinite()

Returns true if this object is infinite in value.

### isError()

Returns true if this object represents an error.

### isNull()

Returns true if this object represents a typeable null value.

### isVoid()

Returns true if this object represents an untyped valueless void.

### list()

Returns true if this object is a list.

### pair()

Returns true if this is a pair (a single valued map).

### map()

Returns true if this is a map

### string()

Returns true if this is a string.

### number()

Returns true if this is a number (decimal|integer).

### decimal()

Returns true if this is a decimal.

### range()

Returns true if this is a range.

### uri()

Returns true if this is a URI.

### singleValue()

Returns true if this is a single value, note that void is neither a single value or a collection.

## Error handling methods on `var`

Error handling in Dollar can be either fail fast or fail slow. If it is fail fast then these methods will likely trigger the throwing of an exception. If it is fail slow then they instead will return error objects.

### $error(String)

Raise or return an error.

### $error(Throwable)

Raise or return an error.

### $error(String, ErrorType)

Raise or return an error of the specified typpe and associated message.

### $error()

Raise or return an error.

### $invalid(String)

Raise or return a validation error.

### $errors() 

Return this object's errors as a `var` object

### errors()

Returns this object's errors as an immutable list of exceptions.

### errorTexts()

Returns this object's errors as a list of Strings.

### $fail(Consumer)

If this object has Java exceptions associated with it, then execute the Consumer with those exceptions.

### hasErrors()

Returns true if this object has Java exceptions associated with it.


### clearErrors()

Return a copy of this object minus any associated exceptions.





## FAQ

### Why Dollar ?

Once you've written some Dollar code you'll get the reason pretty quickly.

### Is this like jQuery for Java?

No, but Dollar uses a lot of ideas that jQuery popularized, so I would certainly acknowledge our debt to jQuery.

### Then what is it?

If you like the ease of JavaScript, Ruby, Groovy etc. but also enjoy being able to work within the Java language then this is for you. You can write typesafe code and then drop into typeless Dollar code whenever you need to. Dollar is both an alternative paradigm and a complementary resource.


## Examples

### Basic

You can just use dollar to write dynamic JSON oriented (JSON is not a requirement, you can work with maps too) using a fluent format like this:

        int age = new Date().getYear() + 1900 - 1970;
        var profile = $("name", "Neil")
                .$("age", age)
                .$("gender", "male")
                .$("projects", $array("snapito", "dollar"))
                .$("location",
                        $("city", "brighton")
                                .$("postcode", "bn1 6jj")
                                .$("number", 343)
                );

or using a more builder format like this:

        var profile = $(
                $("name", "Neil"),
                $("age", new Date().getYear() + 1900 - 1970),
                $("gender", "male"),
                $("projects", $jsonArray("snapito", "dollar")),
                $("location",
                        $("city", "brighton"),
                        $("postcode", "bn1 6jj"),
                        $("number", 343)
                ));

and these hold true:

        assertEquals(age /11, (int)profile.$("$['age']/11").$int());
        assertEquals("male", profile.$("$.gender").$());
        assertEquals(10, profile.$("5*2").$());
        assertEquals(10, (int)$eval("10").$int());
        assertEquals($("{\"name\":\"Dave\"}").$("name").$$(),"Dave");
        assertEquals($().$("({name:'Dave'})").$("name").$$(), "Dave");


## Characteristics

Dollar is designed for production, it is designed for code you are going to have to fix. Every library and language has it's sweet spot. Dollar's sweetspot is working with schema-less data in a production environment. It is not designed for high performance systems (there is a 99.9% chance your project isn't a high performance system) but there is no reason to expect it to be slow either. Where possible the code has been written with JVM optimization in mind.

With this in mind the following are Dollar's characteristics:

* Simple - Dollar does do not expose unnecessary complexity to the programmer, we keep it hidden.

    > The secret of success is to be like a duck – smooth and unruffled on top, but paddling furiously underneath.”

* Typeless - if you *need* strongly typed code stop reading now. If you're writing internet centric modest sized software this is unlikely to be the case.
* Synchronous - asynchronous flows are hard to follow and even harder to debug in production. We do not expose asynchronous behaviour to the programmer.
* Metered - key execution's are metered using Coda Hale's metrics library, this makes production monitoring and debugging easier.
* Nullsafe - Special null type reduces null pointer exceptions, which can be replaced by an isNull() check.
* Threadsafe - No shared state, always copy on write. No shared state means avoidance of synchronization primitives, reduces memory leaks and generally leaves you feeling happier. It comes at a cost (object creation) but that cost is an acceptable cost as far as Dollar is concerned.

## The Rules

1. Do not create your own Threads.
2. Do not create your own Threads.
3. Always run from a *static* context (e.g. a public static void main method)
4. All `var` objects are **immutable**, so use the returned value after 'mutation' actions.


## Badges
Build Status: [![Circle CI](https://circleci.com/gh/sillelien/dollar-core.svg?style=svg)](https://circleci.com/gh/sillelien/dollar-core)

Chat: [![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/sillelien/dollar-core?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

Waffle Stories: [![Stories in Ready](https://badge.waffle.io/sillelien/dollar-core.png?label=ready&title=Ready)](https://waffle.io/sillelien/dollar-core)

Dependencies: [![Dependency Status](https://www.versioneye.com/user/projects/55bf9094653762001a002527/badge.svg?style=flat)](https://www.versioneye.com/user/projects/55bf9094653762001a002527)

--------

[![GitHub License](https://img.shields.io/github/license/sillelien/dollar-core.svg)](https://raw.githubusercontent.com/sillelien/dollar-core/master/LICENSE)

(c) 2015 Sillelien all rights reserved. Please see [LICENSE](https://raw.githubusercontent.com/sillelien/dollar-core/master/LICENSE) for license details of this project. Please visit http://sillelien.com for help and commercial support or raise issues on [GitHub](https://github.com/sillelien/dollar-core/issues).