public class JarRunFile3 { 
public static void main(String[] args) {
int a = 7;
String b = "t";
if(a == 6) {
System.out.println("a is 6");
}

else if(a == 5) {
System.out.println("a is 5");
}

else {
System.out.println("a is 7");
}

int c = a;
for(int i = 0; i < 5; i++) {
System.out.println("hi");
}

while(true) {
System.out.println("while worked");
break;
}

a = a+ 999999;
System.out.println("dafdga" + a + "" + a);
}


public static void execute() {
main(null);
}
}