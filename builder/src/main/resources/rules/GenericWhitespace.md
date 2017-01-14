
Checks that the angle brackets around Generics parameters have the correct whitespace padding:

Valid:
````
public void <K, V extends Number> boolean foo(K, V) {}
class name<T1, T2, ..., Tn> {}
OrderedPair<String, Box<Integer>> p;
boolean same = Util.<Integer, String>compare(p1, p2);
Pair<Integer, String> p1 = new Pair<>(1, "apple");
List<T> list = ImmutableList.Builder<T>::new;
sort(list, Comparable::<String>compareTo);
````
