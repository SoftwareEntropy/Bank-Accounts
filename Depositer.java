
public class Depositer extends genDepositer {

    public Depositer() {
        super();
    }
    
    public Depositer (Depositer d) {
        super(d);
    }
    
    public Depositer(Name n, String s) {
        super(n,s);
    }
    
    public Depositer getDepositerCopy() {
        Depositer copy = new Depositer (name, socialSecurityNum);
        return copy;
    }
    
    public boolean equals(Depositer d) {
        if (name.equals(d.name) && socialSecurityNum.equals(d.socialSecurityNum)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String toString() {
        Name n = name.getNameCopy();
        String first = n.getFirstName();
        String last = n.getLastName();
        String str = String.format("\nLast Name: %s"
                + "\nFirst Name: %s"
                + "\nSocial Security Number: %s", last, first, socialSecurityNum);
        return str;
    }
    
    public Name getName() {
        return name;
    }
    
    public Name getNameCopy() {
        Name copy = new Name(name);
        return copy;
    }
    
    public String getSSN() {
        return socialSecurityNum;
    }
    
}
