# Getting started!

## Requirements

Before starting you will need a API-Key from [Amari](https://docs.amaribot.com/api/api#authentication).

## How do Import this Library?

You can use [Jitpack](https://jitpack.io/#DxsSucuk/Amari4Java) to import it into your Project!

{% tabs %}
{% tab title="Maven" %}
<pre class="language-markup"><code class="lang-markup">...
&#x3C;repository>
   &#x3C;id>jitpack.io&#x3C;/id>
   &#x3C;url>https://jitpack.io&#x3C;/url>
&#x3C;/repository>
...
&#x3C;dependency>
   &#x3C;groupId>com.github.DxsSucuk&#x3C;/groupId>
   &#x3C;artifactId>Amari4Java&#x3C;/artifactId>
   &#x3C;version><a data-footnote-ref href="#user-content-fn-1">Tag</a>&#x3C;/version>
&#x3C;/dependency>
...
</code></pre>
{% endtab %}

{% tab title="Gradle" %}
<pre class="language-gradle"><code class="lang-gradle"><strong>allprojects {
</strong>    repositories {
        ...
<strong>        maven { url 'https://jitpack.io' }
</strong>    }
}
...
dependencies {
    ...
    implementation 'com.github.DxsSucuk:Amari4Java:<a data-footnote-ref href="#user-content-fn-2">Tag</a>'
}
</code></pre>
{% endtab %}
{% endtabs %}

## How do I use it?

You will need to create a new Instance of the Amari4J class!

```java
Amari4J amari4j = new Amari4J("YOUR KEY");
```

After doing that you can simply call the method you need!\
For example to get the weekly Leaderboard of a Guild you can do this:

```java
amari4j.getWeekylLeaderboard("guildId");
```



[^1]: Replace this with the version you want to use!

[^2]: Replace this with the version you want to use!
