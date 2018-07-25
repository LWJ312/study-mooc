# java的一些注意点#

### Data type###

int, double, boolean, char

### Array###

数组名被赋予另一个数组，这两个名字指向同一个数组

>ex:
>
>```java
>int[] a = new int[N];
>a[i] = 1234;
>int[] b = a;
>b[i] =5678; 	//a[i] is now 5678
>```
>
>

